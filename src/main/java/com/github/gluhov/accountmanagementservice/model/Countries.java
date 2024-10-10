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
public class Countries extends BaseEntity {
    @Column("name")
    private String name;
    @Column("alpha2")
    private String alpha2;
    @Column("alpha3")
    private String alpha3;

    @Builder
    public Countries(UUID id, Status status, LocalDateTime created,
                     LocalDateTime updated, String name, String alpha2, String alpha3) {
        super(id, status, created, updated);
        this.name = name;
        this.alpha2 = alpha2;
        this.alpha3 = alpha3;
    }
}