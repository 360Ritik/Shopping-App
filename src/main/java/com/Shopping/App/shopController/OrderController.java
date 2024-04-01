package com.Shopping.App.shopController;

import com.Shopping.App.dtoResponse.PaymentResponse;
import com.Shopping.App.dtoResponse.UserOrder;
import com.Shopping.App.dtoResponse.UserOrderDetail;
import com.Shopping.App.dtoResponse.UserOrders;
import com.Shopping.App.schema.Product;

import com.Shopping.App.schema.User;
import com.Shopping.App.service.CouponService;
import com.Shopping.App.service.OrderService;
import com.Shopping.App.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/order/")
public class OrderController {


    private final ProductService productService;
    private final CouponService couponService;
    private final OrderService orderService;

    public OrderController(ProductService productService, CouponService couponService, OrderService orderService) {
        this.productService = productService;
        this.couponService = couponService;
        this.orderService = orderService;
    }

    // to Fetch All Inventory
    @GetMapping("/inventory")
    public ResponseEntity<Product> getInventory() {
        Product product = productService.getInventory();
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    // to Fetch Coupons Available in the Store.
    @GetMapping("/fetchCoupons")
    public ResponseEntity<Map<String, Integer>> fetchCoupons() {
        Map<String, Integer> coupons = couponService.fetchCoupons();
        return new ResponseEntity<>(coupons, HttpStatus.OK);
    }

    // to Place an Order.
    @PostMapping("/{userId}/order")
    public ResponseEntity<UserOrder> placeOrder(@PathVariable Long userId, @RequestParam int qty, @RequestParam(required = false) String coupon) {
            UserOrder order = orderService.placeOrder(userId, qty, coupon);
            return new ResponseEntity<>(order, HttpStatus.OK);

    }

    // to Make a Payment.
    @PostMapping("/payment/{userId}/{orderId}/pay")
    public ResponseEntity<PaymentResponse> makePayment(@PathVariable Long userId, @PathVariable Long orderId, @RequestParam int amount) {
        PaymentResponse paymentResponse = orderService.makePayment(userId, orderId, amount);

        if (paymentResponse.getStatus().equals("SUCCESSFUL")) {
            return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(paymentResponse, HttpStatus.BAD_REQUEST);
        }

    }


    // to Fetch All Orders all a user.
    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<UserOrders>> getUserOrders(@PathVariable Long userId) {
        List<UserOrders> orders = orderService.getUserOrders(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }


    // to Fetch Order Details of a user.
    @GetMapping("/{userId}/orders/{orderId}")
    public ResponseEntity<UserOrderDetail> getOrderDetails(@PathVariable User userId, @PathVariable Long orderId) {
        UserOrderDetail order = orderService.getOrderDetails(userId, orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }


}