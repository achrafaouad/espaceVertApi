package com.example.dashborad_pipe.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EspaceVert {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    private Boolean fuite;

    private String designation;
    private Double surface;
    private Date dateAmenagement;
    private String entreprise;

    private String reseauArrosage;


    @OneToMany(cascade = CascadeType.ALL)
    private List<Gazon> gazon;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Palmiers> palmiers;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Arbres> arbres;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Arbustes> arbustes;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Fuites> fuites= new ArrayList<>();

    @JsonIgnoreProperties("espaceVert")
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "espaceVert", fetch = FetchType.EAGER)
    private List<Sites> sites= new ArrayList<>();



    // Method to check for "réparée" status in associated Fuites
    public void updateFuiteStatus() {
        for (Fuites fuite : fuites) {
            if (!Statut.réparée.equals(fuite.getStatut())) {
                this.fuite = true;
                return;
            }
        }
        fuite = false; // If all fuites have status "réparée," set fuite to false
    }

}
