package com.teamminari.gongjang.order.entity;

import com.teamminari.gongjang.cart.entity.Cart;
import com.teamminari.gongjang.global.common.BaseTimeEntity;
import com.teamminari.gongjang.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @Column(name = "shipping_address", nullable = false, length = 255)
    private String shippingAddress;

    @Column(name = "shipping_fee", nullable = false)
    private Integer shippingFee;

    @Column(name = "total_price", nullable = false)
    private Integer totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderStatus status;

    @Column(name = "tracking_number", length = 255)
    private String trackingNumber;

    @Builder
    public Order(User user, Cart cart, String shippingAddress,
                 Integer shippingFee, Integer totalPrice, OrderStatus status,
                 String trackingNumber) {
        this.user = user;
        this.cart = cart;
        this.shippingAddress = shippingAddress;
        this.shippingFee = shippingFee;
        this.totalPrice = totalPrice;
        this.status = status;
        this.trackingNumber = trackingNumber;
    }
}