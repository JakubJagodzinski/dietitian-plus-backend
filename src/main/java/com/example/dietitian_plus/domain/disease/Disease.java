package com.example.dietitian_plus.domain.disease;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "diseases")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Disease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "disease_id")
    private Long diseaseId;

    @Column(name = "disease_name", nullable = false, unique = true)
    private String diseaseName;

}
