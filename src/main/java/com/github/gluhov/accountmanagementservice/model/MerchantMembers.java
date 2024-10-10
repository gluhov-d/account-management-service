package com.github.gluhov.accountmanagementservice.model;

import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("merchant_members")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class MerchantMembers extends BaseEntity {
    @Transient
    private Users user;
    @Column("user_id")
    private UUID userId;
    @Transient
    private Merchants merchant;
    @Column("merchant_id")
    private UUID merchantId;
    @Column("member_role")
    private Role memberRole;

    @Builder
    public MerchantMembers(UUID id, Status status, LocalDateTime created, LocalDateTime updated, Users user,
                           UUID userId, Merchants merchant, UUID merchantId, Role memberRole) {
        super(id, status, created, updated);
        this.user = user;
        this.userId = userId;
        this.merchant = merchant;
        this.merchantId = merchantId;
        this.memberRole = memberRole;
    }
}