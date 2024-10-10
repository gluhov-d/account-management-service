package com.github.gluhov.accountmanagementservice.model;

import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("individuals")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Individuals extends VerifiedEntity {
    @Transient
    private Users user;
    @Column("user_id")
    private UUID userId;
    @Column("passport_number")
    private String passportNumber;

    @Builder
    public Individuals(UUID id, Status status, LocalDateTime created, LocalDateTime updated, LocalDateTime verifiedAt, LocalDateTime archivedAt, UUID userId, String passportNumber, String phoneNumber, String email) {
        super(id, status, created, updated, verifiedAt, archivedAt);
        this.userId = userId;
        this.passportNumber = passportNumber;
    }
}