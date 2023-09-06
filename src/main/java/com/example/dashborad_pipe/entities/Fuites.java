    package com.example.dashborad_pipe.entities;

    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import javax.persistence.*;
    import java.util.Date;

    @Entity
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public class Fuites {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        @Column(name = "id", nullable = false)
        private Long id;

        private Double latitude;

        private Double longitude;

        private Date date_constat;

        private Date date_reparation;

        private Long delais_reparation;

        private String type_fuite;

        @Enumerated(EnumType.STRING)
        private Type type_reseau;

        @Enumerated(EnumType.STRING)
        private Statut statut;

    }
