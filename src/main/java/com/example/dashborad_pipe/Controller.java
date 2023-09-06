package com.example.dashborad_pipe;

import EmailDetailsService.EmailDetails;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.dashborad_pipe.Model.SiteToEspaceVert;
import com.example.dashborad_pipe.Repo.EspaceVertRepo;
import com.example.dashborad_pipe.Repo.FuitRepo;
import com.example.dashborad_pipe.Repo.IhmRepo;
import com.example.dashborad_pipe.Repo.SiteRepository;
import com.example.dashborad_pipe.entities.*;
import com.example.dashborad_pipe.notification.NotificationService;
import com.example.dashborad_pipe.services.EmailService;
import com.example.dashborad_pipe.services.EspaceVertService;
import com.example.dashborad_pipe.services.Service;
import com.example.dashborad_pipe.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Geometry;
import org.n52.jackson.datatype.jts.JtsModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
public class Controller {
    private IhmRepo ihmRepo;

    private Service service;
    private NotificationService notificationService;


    private final UserService userService;
    private final EspaceVertService espaceVertService;

    private final EspaceVertRepo espaceVertRepo;
    private final SiteRepository siteRepository;
    private final FuitRepo fuitRepo;
     private EmailService emailService;


    @Autowired
    public Controller(IhmRepo ihmRepo, Service service, NotificationService notificationService, UserService userService, EspaceVertService espaceVertService, EspaceVertRepo espaceVertRepo, SiteRepository siteRepository, FuitRepo fuitRepo, EmailService emailService) {
        this.ihmRepo = ihmRepo;
        this.service = service;
        this.notificationService = notificationService;

        this.userService = userService;
        this.espaceVertService = espaceVertService;
        this.espaceVertRepo = espaceVertRepo;
        this.siteRepository = siteRepository;
        this.fuitRepo = fuitRepo;
        this.emailService = emailService;
    }





    @GetMapping("/getAllSpaces")
    public ResponseEntity<List<EspaceVert>> getAllEspaceVerts() {
        List<EspaceVert> espaceVerts = espaceVertService.getAllEspaceVerts();
        return new ResponseEntity<>(espaceVerts, HttpStatus.OK);
    }


    @PostMapping("/addEspaceVert")
    public ResponseEntity<EspaceVert> addEspaceVert(@RequestBody EspaceVert espaceVert) {
        EspaceVert savedEspaceVert = espaceVertService.addEspaceVert(espaceVert);
        return new ResponseEntity<>(savedEspaceVert, HttpStatus.CREATED);
    }


    @GetMapping("/deleteEspaceVert/{id_espaceVert}")
    public ResponseEntity<EspaceVert> deleteEspaceVert(@PathVariable Long id_espaceVert) {
         espaceVertService.deleteEspaceVert(id_espaceVert);
        return new ResponseEntity<>( HttpStatus.OK);
    }





    @PostMapping("/{espaceVertId}")
    public ResponseEntity<?> saveFuite(@PathVariable Long espaceVertId, @RequestBody Fuites fuite) {
        // Find the EspaceVert entity by its ID
        EspaceVert espaceVert = espaceVertRepo.findById(espaceVertId)
                .orElseThrow(() -> new RuntimeException("EspaceVert not found with ID: " + espaceVertId));

        // Add the Fuites entity to the EspaceVert's fuites list

        System.out.println(fuite);
        espaceVert.getFuites().add(fuite);

        String data = "<h2>" + "un fuite a été ajouté au espace vert dans le detail suivant :" + "<h2>"+
                "<p style='font-size:12px;color:green'> Désignation :" +espaceVert.getDesignation() +"</p>"
              +  "<p style='font-size:12px;color:green'> type de Fuite  :" +fuite.getType_fuite() +"</p>"
              +  "<p style='font-size:12px;color:green'> coordonnées lattitude :" +fuite.getLatitude() +"</p>"
              +  "<p style='font-size:12px;color:green'> coordonnées longitude :" +fuite.getLongitude() +"</p>"
              +  "<p style='font-size:12px;color:green'>  statut :" +fuite.getStatut() +"</p>";

        String data2 =  "un fuite a été ajouté au espace vert dans le detail suivant : \n"+
                "Désignation :" +espaceVert.getDesignation() +"\n"
                +  " type de Fuite  :" +fuite.getType_fuite()+"\n"
                +  " coordonnées lattitude :" +fuite.getLatitude() +"\n"
                +  " coordonnées longitude :" +fuite.getLongitude() +"\n"
                +  "  statut :" +fuite.getStatut() +"\n";


        this.notificationService.sentAddedNotification(data,data2);

        espaceVert.updateFuiteStatus();



        // Save the updated EspaceVert entity
        espaceVert = espaceVertRepo.save(espaceVert);


        // Return the saved Fuites entity
        return new ResponseEntity<>(espaceVert, HttpStatus.CREATED);


    }


    @GetMapping("/getspace/{id}")
    public ResponseEntity<EspaceVert> getEspaceVertById(@PathVariable Long id) {
        Optional<EspaceVert> espaceVert = espaceVertRepo.findById(id);
        return espaceVert.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/getNotifications/{id}")
    public ResponseEntity<?> getNotifications(@PathVariable Long id) {
        Optional<MyUser> user = userService.getUserById(id);
        List<Notification> notifications = user.get().getNotifications();

        // Reverse the order of notifications
        Collections.reverse(notifications);

        return new ResponseEntity<>(notifications, HttpStatus.OK);    }



    @PostMapping("/updateFuite")
    public ResponseEntity<?> saveFuite( @RequestBody Fuites fuite) {
        try {
            fuitRepo.save(fuite);

            EspaceVert espaceVert =  this.service.findEspaceVertByFuitesId(fuite.getId());
            espaceVert.updateFuiteStatus();

            // Save the updated EspaceVert entity
            espaceVert = espaceVertRepo.save(espaceVert);

        }catch (Exception e){

        }


        // Return the saved Fuites entity
        return new ResponseEntity<>(fuite, HttpStatus.CREATED);


    }


 @PostMapping("/addSiteToSpace/{espaceId}")
    public ResponseEntity<?> addSiteToSpace( @PathVariable Long espaceId,@RequestBody SiteToEspaceVert siteToEspaceVert) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JtsModule());
            ObjectReader reader = objectMapper.readerFor(Geometry.class);
            Geometry geometry = reader.readValue(siteToEspaceVert.getGeom());

            EspaceVert espaceVert = this.espaceVertRepo.findById(espaceId).get();

           Sites s =  Sites.builder().name(siteToEspaceVert.getName()).description(siteToEspaceVert.getDescription()).geom(geometry).espaceVert(espaceVert).build();


           s = this.siteRepository.save(s);
            espaceVert.getSites().add(s);
            this.espaceVertRepo.save(espaceVert);
            return new ResponseEntity<>( HttpStatus.CREATED);


        }catch (Exception e){

        }


        // Return the saved Fuites entity

     return new ResponseEntity<>( HttpStatus.CREATED);

    }














    @PostMapping(value = "/sendNotification")
    public ResponseEntity<?> sendNotification(@RequestBody String header){
        this.service.send_mail(header);
        return new ResponseEntity<>(HttpStatus.OK);
    }




    @PostMapping(value = "/saveNotification")
    public ResponseEntity<?> saveNotification(@RequestBody List<Notification> notifications){

        System.out.println("achraf ouad");
        service.saveNotification(notifications);

        return new ResponseEntity<>(HttpStatus.OK);
    }



    @PostMapping("/me")
    public ResponseEntity<?> returnMe(@RequestBody Long id){



      return   new ResponseEntity<>(this.userService.getUserById(id),HttpStatus.OK);
    }
    @PostMapping("/getUser")
    public ResponseEntity<?> getUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorisqtionHeader = request.getHeader(AUTHORIZATION);
        if(authorisqtionHeader != null && authorisqtionHeader.startsWith("Bearer ")){
            try {
                String refreshToken = authorisqtionHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                MyUser user = userService.getUser(username);
                System.out.println(user);
                return new ResponseEntity<>(user,HttpStatus.OK);

            }catch (Exception e){

            }
        }else {
            throw new RuntimeException("refresh tocken is missing");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/users")
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(this.userService.getUsers(), HttpStatus.OK);
    }






    @GetMapping("/espacesVerts")
    public ResponseEntity<?> espacesVerts(){
        return new ResponseEntity<>(this.espaceVertRepo.findAll(), HttpStatus.OK);
    }






    @PostMapping("/user/save")
    public ResponseEntity<?> saveUser(@RequestBody MyUser user){
        this.userService.saveUser(user);
        return new ResponseEntity<>(this.userService.getUsers(), HttpStatus.OK);
    }



    @PostMapping("/role/save")
    public ResponseEntity<?> saveRole(@RequestBody Role role){
        return new ResponseEntity<>(this.userService.saveRole(role), HttpStatus.OK);
    }
    @PostMapping("/role/addToUser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUser roleToUser){
        this.userService.addRoleToUser(roleToUser.getUserName(), roleToUser.getRoleName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/getUsers")
    public ResponseEntity<?> getUsers(){
        this.userService.getUsers();
        return new ResponseEntity<>(this.userService.getUsers(),HttpStatus.OK);
    }

    @GetMapping("/getprofiles")
    public ResponseEntity<?> getprofiles(){

        return new ResponseEntity<>(this.service.getprofiles(),HttpStatus.OK);
    }

    @GetMapping("/getIhm")
    public ResponseEntity<?> getIhm(){

        return new ResponseEntity<>(this.service.getIhm(),HttpStatus.OK);
    }


    @PostMapping("/saveprofil")
    public ResponseEntity<?> saveprofil(@RequestBody Profil profil){
        System.out.println(profil);
        Set<Habilitation> habilitations = new HashSet<>();
        for(Habilitation h :profil.getHabilitations()){
            habilitations.add(Habilitation.builder().name(h.getName()).components(h.getComponents()).build());
        }
        Profil myProf = Profil.builder().name(profil.getName()).description(profil.getDescription()).dateAjout(profil.getDateAjout()).habilitations(habilitations).build();

        return new ResponseEntity<>(this.service.saveProfil(myProf),HttpStatus.OK);
    }


    @PostMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody MyUser myUser){
        System.out.println(myUser);
        return new ResponseEntity<>(this.userService.save(myUser),HttpStatus.OK);
    }



























    @GetMapping("/notification_content")
    public ResponseEntity<?> notification_content(){
        System.out.println("achraf");
        return new ResponseEntity<>(this.service.notification_content(),HttpStatus.OK);
    }

    @GetMapping("/deleteSite/{id}/{id_site}")
    public ResponseEntity<?> deleteSiteFromEspace(@PathVariable Long id ,@PathVariable Long id_site){
        System.out.println("achraf");
        System.out.println(id);
        System.out.println(id_site);
        return new ResponseEntity<>(this.espaceVertService.deleteSiteFromEspace(id,id_site),HttpStatus.OK);
    }




    @PostMapping("/saveNotification_content")
    public ResponseEntity<?> saveNotification_content(@RequestBody Notification_content notification_content){
        return new ResponseEntity<>(this.service.saveNotification_content(notification_content),HttpStatus.OK);
    }






















    // Sending a simple Email
    @PostMapping("/sendMail")
    public String
    sendMail(@RequestBody EmailDetails details)
    {
        String status = emailService.sendSimpleMail(details);
        return status;
    }


    // Sending email with attachment
    @PostMapping("/sendMailWithAttachment")
    public String sendMailWithAttachment(
            @RequestBody EmailDetails details)
    {
        String status
                = emailService.sendMailWithAttachment(details);

        return status;
    }



}



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class RoleToUser {
    private String userName;
    private String roleName;
}




