package com.Shopping.App.service.repo;

import com.Shopping.App.dtoResponse.UserOrderDetailProjection;
import com.Shopping.App.dtoResponse.UserOrders;
import com.Shopping.App.schema.OrderDetail;
import com.Shopping.App.schema.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<OrderDetail, Long> {

    // user projection
    List<UserOrders> findByUser(User userId);

    @Query(value = "SELECT o.order_id as orderId, " +
            "o.amount, " +
            "o.date, " +
            "o.coupon, " +
            "t.transaction_id as transactionId, " +
            "t.status " +
            "FROM order_detail o " +
            "JOIN transaction t ON o.order_id = t.order_id " +
            "WHERE o.order_id = :id AND o.user_id = :user AND t.status = 'SUCCESSFUL'  LIMIT 1",
            nativeQuery = true)
    UserOrderDetailProjection findOrderByOrderIdAndUser(@Param("user") Long user, @Param("id") Long id);

    List<OrderDetail> findAllByUser(User userId);


}
