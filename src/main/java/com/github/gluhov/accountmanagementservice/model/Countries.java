package com.github.gluhov.accountmanagementservice.model;

import lombok.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("countries")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Countries extends StatusEntity {
    @Column("name")
    private String name;
    @Column("alpha2")
    private String alpha2;
    @Column("alpha3")
    private String alpha3;

    @Builder
    public Countries(UUID id, LocalDateTime created, LocalDateTime updated, Status status, String name, String alpha2, String alpha3) {
        super(id, created, updated, status);
        this.name = name;
        this.alpha2 = alpha2;
        this.alpha3 = alpha3;
    }
}