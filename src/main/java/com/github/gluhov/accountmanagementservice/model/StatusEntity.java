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
@AllArgsConstructor
@NoArgsConstructor
public abstract class StatusEntity extends BaseEntity{
    @Column("status")
    private Status status;

    public StatusEntity(UUID id, LocalDateTime created, LocalDateTime updated, Status status) {
        super(id, created, updated);
        this.status = status;
    }
}