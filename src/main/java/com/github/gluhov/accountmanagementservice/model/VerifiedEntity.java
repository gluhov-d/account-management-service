package com.github.gluhov.accountmanagementservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public abstract class VerifiedEntity extends StatusEntity {
    @Column("verified_at")
    private LocalDateTime verifiedAt;
    @Column("archived_at")
    private LocalDateTime archivedAt;

    public VerifiedEntity(UUID id, LocalDateTime created, LocalDateTime updated, Status status, LocalDateTime verifiedAt, LocalDateTime archivedAt) {
        super(id, created, updated, status);
        this.verifiedAt = verifiedAt;
        this.archivedAt = archivedAt;
    }
}