package com.example.dietitian_plus.domain.disease;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "diseases")
public class Disease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "disease_id")
    private Long diseaseId;

    @Column(name = "disease_name", nullable = false, unique = true, length = 100)
    private String diseaseName;

    @Column(length = 1000)
    private String description;

}
