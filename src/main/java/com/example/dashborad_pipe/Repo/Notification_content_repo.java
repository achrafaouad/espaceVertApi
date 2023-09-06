package com.example.dashborad_pipe.Repo;

import com.example.dashborad_pipe.entities.Notification;
import com.example.dashborad_pipe.entities.Notification_content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface Notification_content_repo extends JpaRepository<Notification_content,Long> {


    @Query(value = "select * from notification_content where intitule = :intitule",nativeQuery = true)
    public Notification_content getNotification_content(@Param("intitule") String intitule);
}
