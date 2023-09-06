package com.example.dashborad_pipe.Repo;

import com.example.dashborad_pipe.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepo extends JpaRepository<Notification,Long> {
}