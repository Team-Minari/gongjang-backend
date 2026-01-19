package com.teamminari.gongjang.invitation.entity;

import com.teamminari.gongjang.cart.entity.Cart;
import com.teamminari.gongjang.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "invitations")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invitation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inviter_id", nullable = false)
    private User inviter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invitee_id")
    private User invitee;

    @Column(name = "invite_token", unique = true, length = 255)
    private String inviteToken;

    @Enumerated(EnumType.STRING)
    @Column(name = "invite_type", nullable = false, length = 20)
    private InvitationType inviteType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private InvitationStatus status = InvitationStatus.PENDING;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public Invitation(Cart cart, User inviter, User invitee, String inviteToken,
                      InvitationType inviteType, LocalDateTime expiresAt) {
        this.cart = cart;
        this.inviter = inviter;
        this.invitee = invitee;
        this.inviteToken = inviteToken;
        this.inviteType = inviteType;
        this.status = InvitationStatus.PENDING;
        this.expiresAt = expiresAt;
        this.createdAt = LocalDateTime.now();
    }
}