package com.Shopping.App.shopController;


import com.Shopping.App.schema.Coupon;

import com.Shopping.App.schema.Product;
import com.Shopping.App.schema.User;
import com.Shopping.App.service.CouponService;

import com.Shopping.App.service.ProductService;
import com.Shopping.App.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/shop/")
public class ShoppingController {


    private final ProductService productService;
    private final CouponService couponService;


    private final UserService userService;

    public ShoppingController(ProductService productService, CouponService couponService, UserService userService) {
        this.productService = productService;
        this.couponService = couponService;

        this.userService = userService;
    }


    // to Save New User.
    @PostMapping("/saveUser")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    // to Save New Coupons
    @PostMapping("/saveCoupon")
    public ResponseEntity<Coupon> saveCoupon(@RequestBody Coupon coupon) {
        Coupon couponCode = couponService.saveCoupon(coupon);
        return new ResponseEntity<>(couponCode, HttpStatus.CREATED);
    }

    // to Save New Products
    @PostMapping("/saveProduct")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        Product savedProduct = productService.saveProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }


}