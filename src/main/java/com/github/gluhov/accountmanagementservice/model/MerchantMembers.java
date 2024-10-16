package com.github.gluhov.accountmanagementservice.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;
import java.util.UUID;

@Table("merchant_members")
@Data
@NoArgsConstructor
public class MerchantMembers implements Persistable<UUID> {
    @Id
    @Column("id")
    private UUID id;
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
    public MerchantMembers(UUID id, Users user, UUID userId, Merchants merchant, UUID merchantId, Role memberRole) {
        this.id = id;
        this.user = user;
        this.userId = userId;
        this.merchant = merchant;
        this.merchantId = merchantId;
        this.memberRole = memberRole;
    }

    @Override
    @Transient
    public boolean isNew() {
        boolean result = Objects.isNull(id);
        this.id = result ? UUID.randomUUID() : this.id;
        return result;
    }
}