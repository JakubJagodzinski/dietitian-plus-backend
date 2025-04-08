package com.example.dietitian_plus.patient;

import com.example.dietitian_plus.dietitian.Dietitian;
import com.example.dietitian_plus.disease.Disease;
import com.example.dietitian_plus.note.Note;
import com.example.dietitian_plus.product.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private Long patientId;

    private String email;

    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private Float height;

    @Column(name = "starting_weight")
    private Float startingWeight;

    @Column(name = "current_weight")
    private Float currentWeight;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "dietitian_id")
    @JsonManagedReference
    private Dietitian dietitian;

    @ManyToMany
    @JoinTable(
            name = "patients_allergenic_products",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @JsonManagedReference
    @ToString.Exclude
    private List<Product> allergenicProducts = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "patients_disliked_products",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @JsonManagedReference
    @ToString.Exclude
    private List<Product> dislikedProducts = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "patients_diseases",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "disease_id")
    )
    @JsonManagedReference
    @ToString.Exclude
    private List<Disease> diseases = new ArrayList<>();

    @OneToMany(mappedBy = "patient")
    @JsonBackReference
    private final List<Note> notes = new ArrayList<>();

}
