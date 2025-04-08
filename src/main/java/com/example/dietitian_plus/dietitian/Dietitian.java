package com.example.dietitian_plus.dietitian;

import com.example.dietitian_plus.dish.Dish;
import com.example.dietitian_plus.note.Note;
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
@Table(name = "dietitians")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dietitian {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dietitian_id")
    private Long dietitianId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String title = "";

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @OneToMany(mappedBy = "dietitian")
    @JsonBackReference
    @ToString.Exclude
    private final Set<Patient> patients = new HashSet<>();

    @OneToMany(mappedBy = "dietitian")
    @JsonBackReference
    @ToString.Exclude
    private final Set<Dish> dishes = new HashSet<>();

    @OneToMany(mappedBy = "dietitian")
    @JsonBackReference
    @ToString.Exclude
    private final Set<Note> notes = new HashSet<>();

}
