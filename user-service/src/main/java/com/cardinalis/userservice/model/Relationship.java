package com.cardinalis.userservice.model;


import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(RelationshipId.class)
@Table(name = "Relationship")
public class Relationship implements Serializable {

    @Id
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(name = "follower_id", length = 36)
    private UUID followerId;

    @Id
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(name = "followed_id", length = 36)
    private UUID followedId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}