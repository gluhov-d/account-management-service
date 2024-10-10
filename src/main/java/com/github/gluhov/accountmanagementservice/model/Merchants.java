package com.github.gluhov.accountmanagementservice.model;

import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("merchants")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Merchants extends VerifiedEntity {
    @Column("company_name")
    private String companyName;
    @Transient
    private Users creator;
    @Column("creator_id")
    private UUID creatorId;
    @Column("email")
    private String email;
    @Column("phone_number")
    private String phoneNumber;
    @Column("filled")
    private boolean filled;

    @Builder
    public Merchants(UUID id, Status status, LocalDateTime verifiedAt, LocalDateTime archivedAt, LocalDateTime created,
                     LocalDateTime updated, String companyName, UUID creatorId, String email, String phoneNumber, boolean filled) {
        super(id, status, verifiedAt, archivedAt, created, updated);
        this.companyName = companyName;
        this.creatorId = creatorId;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.filled = filled;
    }
}