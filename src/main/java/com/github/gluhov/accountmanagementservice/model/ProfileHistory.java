package com.github.gluhov.accountmanagementservice.model;

import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("profile_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProfileHistory extends BaseEntity {
    @Transient
    private Users user;
    @Column("profile_id")
    private UUID profileId;
    @Column("profile_type")
    private String profileType;
    @Column("reason")
    private String reason;
    @Column("comment")
    private String comment;
    @Column("changed_values")
    private String changedValues;

    @Builder

    public ProfileHistory(UUID id, LocalDateTime created, LocalDateTime updated, Users user, UUID profileId,
                          String profileType, String reason, String comment, String changedValues) {
        super(id, created, updated);
        this.user = user;
        this.profileId = profileId;
        this.profileType = profileType;
        this.reason = reason;
        this.comment = comment;
        this.changedValues = changedValues;
    }
}