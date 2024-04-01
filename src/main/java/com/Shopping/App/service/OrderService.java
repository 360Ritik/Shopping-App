package com.Shopping.App.service;

import com.Shopping.App.dtoResponse.*;
import com.Shopping.App.exceptionHandling.GenericException;
import com.Shopping.App.exceptionHandling.InvalidCouponException;
import com.Shopping.App.schema.*;
import com.Shopping.App.service.repo.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class OrderService {


    private final OrderRepository orderRepository;

    private final ProductService productService;

    private final CouponService couponService;
    private final UserService userService;

    private final TransactionService transactionService;


    public OrderService(OrderRepository orderRepository, ProductService productService, CouponService couponService, UserService userService, TransactionService transactionService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.couponService = couponService;
        this.userService = userService;

        this.transactionService = transactionService;

    }

    @Transactional
    public UserOrder placeOrder(Long userId, int qty, String couponCode) {
        Product product = productService.getInventory();
        User user = userService.findUserById(userId);
        if (qty < 1 || qty > product.getAvailable()) {
            throw new GenericException("Invalid quantity");
        } else if (user == null) {
            throw new GenericException("Invalid User_id");
        }

        Coupon coupon = validateAndFetchCoupon(couponCode, user);
        int price = product.getPrice();
        int discount = coupon.getDiscount();
        int amount = (int) (qty * price * (100 - discount) / 100.0);

        OrderDetail order = new OrderDetail();

        order.setUser(user);
        order.setQuantity(qty);
        order.setAmount(amount);
        order.setCoupon(couponCode);
        order.setDate(new Date());
        order.setProduct(product);
        order.setOrderStatus(OrderStatus.PAYMENT_PENDING.name());

        OrderDetail newOrder = orderRepository.save(order);

        // Here we can also use Asynchronous method to save the values in the database
        product.setOrdered(product.getOrdered() + qty);
        product.setAvailable(product.getAvailable() - qty);
        productService.save(product);


        return UserOrder.builder()
                .order_id(newOrder.getOrderId())
                .user_id(newOrder.getUser().getUserId())
                .amount(newOrder.getAmount())
                .coupon(newOrder.getCoupon())
                .quantity(newOrder.getQuantity())
                .build();
    }

    private Coupon validateAndFetchCoupon(String couponCode, User user) {

        Coupon coupon = couponService.findByCouponCode(couponCode);
        List<OrderDetail> orders = orderRepository.findAllByUser(user);
        boolean isCouponAlreadyUsed = orders.stream().anyMatch(x -> x.getCoupon().equals(couponCode));
        if (coupon == null || isCouponAlreadyUsed) {
            throw new InvalidCouponException("Invalid coupon " + couponCode);
        }
        return coupon;
    }

    @Transactional
    public PaymentResponse makePayment(Long userId, Long orderId, int amount) {
        PaymentResponse paymentResponse = new PaymentResponse();
        Optional<OrderDetail> order = orderRepository.findById(orderId);

        paymentResponse.setOrderId(orderId);
        paymentResponse.setUserId(userId);


        if (!isValidOrder(order, userId, amount, paymentResponse)) {
            paymentResponse.setStatus(OrderStatus.FAILED.name());
            return paymentResponse;
        } else {
            return processPayment(order, amount, paymentResponse);
        }

    }

    private boolean isValidOrder(Optional<OrderDetail> order, Long userId, int amount, PaymentResponse paymentResponse) {

        if (order.isEmpty()) {
            paymentResponse.setDescription("Invalid order id");
            return false;
        }
        if (!order.get().getUser().getUserId().equals(userId)) {
            paymentResponse.setDescription("Invalid user id for order");
            return false;
        }
        if (order.get().getAmount() != amount) {
            paymentResponse.setDescription("Invalid amount for order");
            return false;
        }

        if (order.get().getOrderStatus().equals(OrderStatus.ORDER_PLACED.name())) {
            paymentResponse.setDescription("Order is already placed");
            return false;
        }

        return true;
    }

    private PaymentResponse processPayment(Optional<OrderDetail> order, int amount, PaymentResponse paymentResponse) {

        orderRepository.save(order.get());

        // Mocking payment status
        int statusCode = (int) (Math.random() * 3) + 1;


        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setTransactionDate(new Date());
        transaction.setOrder(order.get());


        String description = "";
        if (statusCode == 1) {

            order.get().setOrderStatus(OrderStatus.ORDER_PLACED.name());
            paymentResponse.setDescription("Order Placed Successfully");
            paymentResponse.setStatus(OrderStatus.SUCCESSFUL.name());
            transaction.setStatus(OrderStatus.SUCCESSFUL.name());

        } else {
            order.get().setOrderStatus(OrderStatus.FAILED.name());
            description = switch (statusCode) {
                case 2 -> "No response from payment server";
                case 3 -> "Payment failed from bank";
                default -> "Unknown error occurred during payment";
            };
            order.get().setOrderStatus(OrderStatus.FAILED.name());
            paymentResponse.setDescription(description);
            paymentResponse.setStatus(OrderStatus.FAILED.name());
            transaction.setStatus(OrderStatus.FAILED.name());

        }
        orderRepository.save(order.get());
        transactionService.saveTransaction(transaction);
        paymentResponse.setTransactionId(transaction.getTransactionId());
        return paymentResponse;
    }


    public List<UserOrders> getUserOrders(Long userId) {
        User user = new User();
        user.setUserId(userId);
        List<UserOrders> userOrders = orderRepository.findByUser(user);
        if (userOrders.isEmpty()) {
            throw new GenericException("wrong user_id");
        }
        return userOrders;
    }

    public UserOrderDetail getOrderDetails(User userId, Long orderId) {
        UserOrderDetailProjection userOrderDetailPro = orderRepository.findOrderByOrderIdAndUser(userId.getUserId(), orderId);

        if (userOrderDetailPro == null) {
            throw new GenericException("wrong order_id or user_id");
        }
        return UserOrderDetail.builder()
                .orderId(userOrderDetailPro.getOrderId())
                .status(userOrderDetailPro.getStatus())
                .amount(userOrderDetailPro.getAmount())
                .transactionId(userOrderDetailPro.getTransactionId())
                .date(userOrderDetailPro.getDate())
                .coupon(userOrderDetailPro.getCoupon())
                .build();

    }

}
