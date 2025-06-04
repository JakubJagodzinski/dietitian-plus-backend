package com.example.dietitian_plus.domain.meal;

import com.example.dietitian_plus.domain.dietitian.Dietitian;
import com.example.dietitian_plus.domain.dish.Dish;
import com.example.dietitian_plus.domain.mealsdishes.MealsDishes;
import com.example.dietitian_plus.domain.patient.Patient;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "meals")
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meal_id")
    private Long mealId;

    @Column(nullable = false)
    private LocalDateTime datetime;

    @OneToOne
    @JoinColumn(name = "patient_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference
    private Patient patient;

    @OneToOne
    @JoinColumn(name = "dietitian_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference
    private Dietitian dietitian;

    @OneToMany(mappedBy = "meal")
    @JsonBackReference
    @ToString.Exclude
    private final List<MealsDishes> mealsDishes = new ArrayList<>();

    public final List<Dish> getDishes() {
        return mealsDishes.stream().map(MealsDishes::getDish).collect(Collectors.toList());
    }

}
