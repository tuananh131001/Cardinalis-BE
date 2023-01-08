package com.cardinalis.userservice.model;

import com.cardinalis.userservice.enums.NotificationType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notifications_seq")
    @SequenceGenerator(name = "notifications_seq", sequenceName = "notifications_seq", initialValue = 100, allocationSize = 1)
    private Long id;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "notification_type")
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @ManyToOne
    @JoinColumn(name = "notified_user_id")
    private UserEntity notifiedUser;

    @OneToOne
    private UserEntity user;

    @OneToOne
    private UserEntity userToFollow;



    public Notification() {
        this.date = LocalDateTime.now().withNano(0);
    }
}