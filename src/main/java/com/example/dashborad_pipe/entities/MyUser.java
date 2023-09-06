package com.example.dashborad_pipe.entities;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyUser {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String sex;
    private String cin;
    private String email;
    private String image;
    private String firstName;
    private String lastName;
    private String userPhone;
    private String userCodeNumber;
    private Date lastConnect;
    private Date birthday;
    private String username;
    private String password ;
    @ManyToMany(fetch= FetchType.LAZY,cascade = CascadeType.MERGE)
    private Collection<Role> roles = new ArrayList<>();

    @ManyToOne(fetch= FetchType.EAGER,cascade = CascadeType.ALL)
    Profil profil;

    @OneToMany(fetch= FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Notification> notifications= new ArrayList<>();
}
