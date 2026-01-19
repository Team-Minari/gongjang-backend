package com.teamminari.gongjang.cart.entity;

import com.teamminari.gongjang.global.common.BaseTimeEntity;
import com.teamminari.gongjang.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "carts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(nullable = false)
    private String name;

    @Column
    private Integer budget;
}