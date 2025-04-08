package com.example.dietitian_plus.disease;

import com.example.dietitian_plus.patient.Patient;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany(mappedBy = "diseases")
    @JsonBackReference
    @ToString.Exclude
    private final Set<Patient> patients = new HashSet<>();

}
