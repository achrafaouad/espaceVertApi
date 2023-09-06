package com.example.dashborad_pipe.notification;

import EmailDetailsService.EmailDetails;
import com.example.dashborad_pipe.Repo.NotificationRepo;
import com.example.dashborad_pipe.Repo.Notification_content_repo;
import com.example.dashborad_pipe.Repo.RoleRepository;
import com.example.dashborad_pipe.Repo.UserRepository;
import com.example.dashborad_pipe.entities.MyUser;
import com.example.dashborad_pipe.entities.Notification;
import com.example.dashborad_pipe.services.EmailService;
import com.example.dashborad_pipe.services.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

@org.springframework.stereotype.Service


public class NotificationService {
    private Service service;
    private NotificationRepo notificationRepo;
    private Notification_content_repo notification_content_repo;
    private UserRepository userRepo;

    private EmailService emailService;

    private RoleRepository roleRepositor;

    @Autowired
    public NotificationService(Service service, NotificationRepo notificationRepo, Notification_content_repo notification_content_repo, UserRepository userRepo, EmailService emailService, RoleRepository roleRepositor) {
        this.service = service;
        this.notificationRepo = notificationRepo;
        this.notification_content_repo = notification_content_repo;
        this.userRepo = userRepo;
        this.emailService = emailService;
        this.roleRepositor = roleRepositor;
    }


    public void sentAddedNotification(String content,String content2) {



        for(MyUser ele : userRepo.findAll()){
            System.out.println(ele);
            if(ele.getRoles() != null){
                if(ele.getRoles().contains(this.roleRepositor.findByName("Role_Admin"))){

                    EmailDetails emailDetails = EmailDetails.builder().recipient(ele.getEmail()).subject("un nouveau fuite a été inseré : "+ getformatedDate(new Date()) ).build();


                    ele.getNotifications().add(Notification.builder().intitule("un nouveau fuite a été inseré : "+ getformatedDate(new Date())).content(content2).build());

                    this.userRepo.save(ele);

                    emailDetails.setMsgBody(content);

                    String status = emailService.sendHtmlSimpleMail(emailDetails);
                    System.out.println(status);

                }




            }


        }
    }


    public String getformatedDate(Date d){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        if(d != null){
            return  formatter.format(d);

        }
        return "-----";
    }

}
