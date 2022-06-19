package com.example.projecti_trello_app_backend.entities.combinations;

import com.example.projecti_trello_app_backend.entities.notification.Notification;
import com.example.projecti_trello_app_backend.entities.user.User;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "${database.name}", name = "user_notification")
public class UserNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "notification_id", nullable = false)
    @NotNull
    private Notification notification;

    @Column(name = "sent_at")
    private Timestamp sentAt;

    @Column(name = "read")
    private boolean read;
}
