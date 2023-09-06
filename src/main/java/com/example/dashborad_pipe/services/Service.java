package com.example.dashborad_pipe.services;

import EmailDetailsService.EmailDetails;
import com.example.dashborad_pipe.Repo.*;
import com.example.dashborad_pipe.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;

@org.springframework.stereotype.Service
public class Service {

    private String ip;
    private EmailService emailService;

    private UserRepository userRepo;
    private NotificationRepo notificationRepo;
    private ProfileRepository profilRepository;

    private IhmRepo ihmRepo;


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


    private final UserService userService;

    private Notification_content_repo notification_content_repo;
    private final EspaceVertRepo espaceVertRepo;


    @Autowired
    public Service(EmailService emailService, UserRepository userRepo, NotificationRepo notificationRepo1, ProfileRepository profilRepository, IhmRepo ihmRepo, NotificationRepo notificationRepo, UserService userService, Notification_content_repo notification_content_repo,
                   EspaceVertRepo espaceVertRepo) {

        this.emailService = emailService;
        this.userRepo = userRepo;
        this.notificationRepo = notificationRepo1;

        this.profilRepository = profilRepository;
        this.ihmRepo = ihmRepo;

        this.userService = userService;

        this.notification_content_repo = notification_content_repo;

        this.espaceVertRepo = espaceVertRepo;
    }



    public EspaceVert findEspaceVertByFuitesId(Long fuitesId) {
        List<EspaceVert> espaceVerts = espaceVertRepo.findAll();
        for (EspaceVert espaceVert : espaceVerts) {
            for (Fuites fuites : espaceVert.getFuites()) {
                if (fuites.getId().equals(fuitesId)) {
                    return espaceVert;
                }
            }
        }
        return null; // Return null if no matching 'EspaceVert' is found
    }







    public void saveNotification(List<Notification> m) {
        for(Notification n : m){
            this.notificationRepo.save(n);
        }

    }
































    public List<Ihm> getIhm() {
        return this.ihmRepo.findAll();
    }





    public String getformatedDate(Date d){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        if(d != null){
            return  formatter.format(d);

        }
        return "-----";
    }








































    public List<Notification_content> notification_content() {
    return  this.notification_content_repo.findAll();
    }

 public Notification_content saveNotification_content(Notification_content notification_content) {
    return  this.notification_content_repo.save(notification_content);
    }


    public Principal user(Principal user) {
        return user;
    }


    public Profil saveProfil(Profil profil){
        return this.profilRepository.save(profil);
    }
    public List<Profil> getprofiles(){
       return this.profilRepository.findAll();

    }

    @PostMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody MyUser myUser){

        return new ResponseEntity<>(this.userService.saveUser(myUser), HttpStatus.OK);
    }





    public void send_mail(String header){
        for(MyUser ele : userRepo.findAll()){
            if(ele.getProfil() != null) {
                for(Habilitation ha : ele.getProfil().getHabilitations()){
                    if(ha.getName().equals("insert")){
                        if(ele.getEmail() != null){
                            String data = "<h6>" + header + "<h6>"+
                                    "<p style='font-size:12px;color:grey'> " + "utilisateur:"+(String) SecurityContextHolder.getContext().getAuthentication().getPrincipal()+ "a bien validé les données a "+ getformatedDate(new Date())+"</p>";


                            String data2 = "utilisateur:"+(String) SecurityContextHolder.getContext().getAuthentication().getPrincipal()+
                                    "a bien validé les données a "+ getformatedDate(new Date());



                            if(ele.getNotifications() != null){
                                System.out.println(ele.getNotifications());
                                ele.getNotifications().add(Notification.builder().intitule(header).content(data2).type("warning").sended_by("system").sended_at(new Date()).read(false).build());

                            }else {
                                List<Notification> notificationList = new ArrayList<>();
                                notificationList.add(Notification.builder().intitule(header).content(data2).type("warning").sended_by("system").sended_at(new Date()).read(false).build());
                                ele.setNotifications(notificationList);
                            }
                            userRepo.save(ele);


                            EmailDetails emailDetails = EmailDetails.builder().subject(header)
                                    .msgBody(data).recipient(ele.getEmail()).build();

                            String status = emailService.sendHtmlSimpleMail(emailDetails);
                            System.out.println(status);



                        }}}}}

    }




}
