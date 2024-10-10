package com.github.gluhov.accountmanagementservice.model;

import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("merchant_members_invitations")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class MerchantMembersInvitations extends BaseEntity {
    @Column("expires")
    private LocalDateTime expires;
    @Transient
    private Merchants merchant;
    @Column("merchant_id")
    private UUID merchantId;
    @Column("first_name")
    private String firstName;
    @Column("last_name")
    private String lastName;
    @Column("email")
    private String email;

    public MerchantMembersInvitations(UUID id, Status status, LocalDateTime created, LocalDateTime updated,
                                      LocalDateTime expires, UUID merchantId, String firstName, String lastName, String email) {
        super(id, status, created, updated);
        this.expires = expires;
        this.merchantId = merchantId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
