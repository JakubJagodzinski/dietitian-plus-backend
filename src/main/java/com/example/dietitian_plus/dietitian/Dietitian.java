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

import java.util.ArrayList;
import java.util.List;

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

    private String email;

    private String password;

    private String title = "";

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "dietitian")
    @JsonBackReference
    @ToString.Exclude
    private final List<Patient> patients = new ArrayList<>();

    @OneToMany(mappedBy = "dietitian")
    @JsonBackReference
    @ToString.Exclude
    private final List<Dish> dishes = new ArrayList<>();

    @OneToMany(mappedBy = "dietitian")
    @JsonBackReference
    @ToString.Exclude
    private final List<Note> notes = new ArrayList<>();

}
