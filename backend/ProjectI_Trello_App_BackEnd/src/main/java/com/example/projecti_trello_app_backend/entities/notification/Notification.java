package com.example.projecti_trello_app_backend.entities.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "${database.name}", name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id", unique = true)
    private int notificationId;

    @Column(name = "notification_content")
    private String notificationContent;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "deleted")
    private boolean deleted;
}
