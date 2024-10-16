package com.github.gluhov.accountmanagementservice.model;

import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Users extends VerifiedEntity {
    @Column("secret_key")
    private String secretKey;
    @Column("first_name")
    private String firstName;
    @Column("last_name")
    private String lastName;
    @Column("filled")
    private boolean filled;
    @Transient
    private Addresses addresses;
    @Column("address_id")
    private UUID addressId;

    @Builder
    public Users(UUID id, LocalDateTime created, LocalDateTime updated, Status status, LocalDateTime verifiedAt,
                 LocalDateTime archivedAt, String secretKey, String firstName, String lastName, boolean filled, Addresses addresses, UUID addressId) {
        super(id, created, updated, status, verifiedAt, archivedAt);
        this.secretKey = secretKey;
        this.firstName = firstName;
        this.lastName = lastName;
        this.filled = filled;
        this.addresses = addresses;
        this.addressId = addressId;
    }
}