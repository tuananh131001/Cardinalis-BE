package com.cardinalis.userservice.repository.projection.notification;

import com.cardinalis.userservice.enums.NotificationType;
import com.cardinalis.userservice.repository.projection.user.UserProjection;

import java.time.LocalDateTime;

public interface NotificationInfoProjection {
    Long getId();
    LocalDateTime getDate();
    NotificationType getNotificationType();
    UserProjection getUser();
}