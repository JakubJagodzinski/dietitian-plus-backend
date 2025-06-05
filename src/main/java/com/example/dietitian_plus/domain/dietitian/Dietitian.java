package com.example.dietitian_plus.domain.dietitian;

import com.example.dietitian_plus.domain.dish.Dish;
import com.example.dietitian_plus.domain.note.Note;
import com.example.dietitian_plus.domain.patient.Patient;
import com.example.dietitian_plus.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "dietitians")
@PrimaryKeyJoinColumn(name = "dietitian_id")
public class Dietitian extends User {

    private String title = "";

    @OneToMany(mappedBy = "dietitian")
    @JsonBackReference
    @ToString.Exclude
    private final Set<Patient> patients = new HashSet<>();

    @OneToMany(mappedBy = "dietitian")
    @JsonBackReference
    @ToString.Exclude
    private final Set<Dish> dishes = new HashSet<>();

}
