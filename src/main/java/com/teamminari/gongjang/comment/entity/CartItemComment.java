package com.teamminari.gongjang.comment.entity;

import com.teamminari.gongjang.cart.entity.CartItem;
import com.teamminari.gongjang.global.common.BaseTimeEntity;
import com.teamminari.gongjang.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart_item_comments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItemComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_item_id", nullable = false)
    private CartItem cartItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Builder
    public CartItemComment(CartItem cartItem, User user, String content) {
        this.cartItem = cartItem;
        this.user = user;
        this.content = content;
    }
}