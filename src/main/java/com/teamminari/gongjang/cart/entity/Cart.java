package com.teamminari.gongjang.cart.entity;

import com.teamminari.gongjang.global.common.BaseTimeEntity;
import com.teamminari.gongjang.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "carts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(nullable = false, length = 255)
    private String name;

    @Column
    private Integer budget;

    @Builder
    public Cart(User owner, String name, Integer budget) {
        this.owner = owner;
        this.name = name;
        this.budget = budget;
    }
}