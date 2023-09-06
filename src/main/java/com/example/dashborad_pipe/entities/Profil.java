package com.example.dashborad_pipe.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profil {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_prof", nullable = false)
    private Long id_prof;
    private String name;
    private String description;
    private Date dateAjout;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "profil_habilitaion",
            joinColumns = @JoinColumn(name = "id_prof"),
            inverseJoinColumns = @JoinColumn(name = "id_hab"))
    private Set<Habilitation> habilitations = new HashSet<>();


}
