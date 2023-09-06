package com.example.dashborad_pipe.notification;

import EmailDetailsService.EmailDetails;
import com.example.dashborad_pipe.Repo.NotificationRepo;
import com.example.dashborad_pipe.Repo.Notification_content_repo;
import com.example.dashborad_pipe.Repo.RoleRepository;
import com.example.dashborad_pipe.Repo.UserRepository;
import com.example.dashborad_pipe.entities.MyUser;
import com.example.dashborad_pipe.services.EmailService;
import com.example.dashborad_pipe.services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@EnableScheduling
public class Scheduler {


    private Service service;
    private NotificationRepo notificationRepo;
    private Notification_content_repo notification_content_repo;
    private UserRepository userRepo;

    private EmailService emailService;

    private RoleRepository roleRepositor;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
@Autowired
    public Scheduler(Service service, NotificationRepo notificationRepo, Notification_content_repo notification_content_repo, UserRepository userRepo, EmailService emailService, RoleRepository roleRepositor) {
        this.service = service;
        this.notificationRepo = notificationRepo;
    this.notification_content_repo = notification_content_repo;
    this.userRepo = userRepo;
    this.emailService = emailService;
    this.roleRepositor = roleRepositor;
}

//    @Scheduled(cron = "0 0 0 * * MON")
//    public void run() {
//
//
//        Tuyaux tuyaux = this.service.getTuyItemSynth();
//        Electriquevolution electriquevolution = this.service.getElectriqueItemSynth();
//        PompEvolution pompEvolution = this.service.getPompEvolutioneItemSynth();
//        String content ="<h1> Résumé hebdomadaire des valeurs insérées <h1>";
//
//        content = content + "<h3>Tuyaux</h3> ";
//        content = content + "<p style='color:green'>Date d'insertion par l'agent : </p>" + getformatedDate(tuyaux.getInputDate());
//        content = content + "<p style='color:green'>Date de premiere validation : </p>" + getformatedDate(tuyaux.getValidation1());
//        content = content + "<p style='color:green'>Date de deuxième validation : </p>" + getformatedDate(tuyaux.getValidation2());
//
//        content = content + "<h3>Electrique </h3> ";
//        content = content + "<p style='color:green'>Date d'insertion par l'agent : </p>" + getformatedDate(electriquevolution.getInputDate());
//        content = content + "<p style='color:green'>Date de premiere validation : </p>" + getformatedDate(electriquevolution.getValidation1());
//        content = content + "<p style='color:green'>Date de deuxième validation : </p>" + getformatedDate(electriquevolution.getValidation2());
//
//        content = content + "<h3>Pomp </h3> ";
//        content = content + "<p style='color:green'>Date d'insertion par l'agent : </p>" + getformatedDate(pompEvolution.getInputDate());
//        content = content + "<p style='color:green'>Date de premiere validation : </p>" + getformatedDate(pompEvolution.getValidation1());
//        content = content + "<p style='color:green'>Date de deuxième validation : </p>" + getformatedDate(pompEvolution.getValidation2());
//
//        for(MyUser ele : userRepo.findAll()){
//            System.out.println(ele);
//            if(ele.getRoles() != null){
//            if(ele.getRoles().contains(this.roleRepositor.findByName("Role_Admin"))){
//
//                EmailDetails emailDetails = EmailDetails.builder().recipient(ele.getEmail()).subject("un nouveau fuite a été inseré : "+ getformatedDate(new Date()) ).build();
//
//
//
//                emailDetails.setMsgBody(content);
//
//                String status = emailService.sendHtmlSimpleMail(emailDetails);
//                System.out.println(status);
//
//            }
//
//
//
//
//                    }
//
//
//    }
//    }


//    @Scheduled(cron = "0 0 0 * * MON")
////    @Scheduled(cron = "*/10 * * * * *")
//    public void reportCurremtTime(){
//    if(this.service.getTuyItemToEdit().getReel_tuyaux().getId_reel_tuyaux() == null){
//        for(MyUser ele : userRepo.findAll()){
//            System.out.println(ele);
//            if(ele.getProfil() != null){
//            for(Habilitation ha : ele.getProfil().getHabilitations()){
//                if(ha.getName().equals("insert")){
//                    Notification_content n = notification_content_repo.getNotification_content("tuyaux_Semaine");
//                  if(ele.getNotifications() != null){
//                      System.out.println(ele.getNotifications());
//                      ele.getNotifications().add(Notification.builder().intitule(n.getIntitule()).content(n.getContent()).type("warning").sended_by("system").sended_at(new Date()).read(false).build());
//
//                  }else {
//                      List<Notification> notificationList = new ArrayList<>();
//                      notificationList.add(Notification.builder().intitule(n.getIntitule()).content(n.getContent()).type("warning").sended_by("system").sended_at(new Date()).read(false).build());
//                      ele.setNotifications(notificationList);
//                  }
//                    userRepo.save(ele);
//                  if(ele.getEmail() != null){
//                      EmailDetails emailDetails = EmailDetails.builder().recipient(ele.getEmail()).subject(n.getIntitule()).msgBody(n.getContent()).build();
//                      String status = emailService.sendSimpleMail(emailDetails);
//                      System.out.println(status);
//                  }
//                }
//            }
//            }
//        }
//
//    }else {
//        System.out.println("tout ca marche");
//
//    }
//
//    }



    public String getformatedDate(Date d){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        if(d != null){
            return  formatter.format(d);

        }
        return "-----";
    }

}
