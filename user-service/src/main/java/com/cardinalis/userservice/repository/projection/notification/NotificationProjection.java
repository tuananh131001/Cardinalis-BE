package com.cardinalis.userservice.repository.projection.notification;

import com.cardinalis.userservice.enums.NotificationType;

import java.time.LocalDateTime;

public interface NotificationProjection {
    Long getId();
    LocalDateTime getDate();
    NotificationType getNotificationType();
    NotificationUserProjection getUser();
    NotificationUserProjection getUserToFollow();
    NotificationListProjection getList();

    interface NotificationUserProjection {
        Long getId();
        String getUsername();
        String getAvatar();
    }

    interface NotificationListProjection {
        Long getId();
        String getName();
    }
}
