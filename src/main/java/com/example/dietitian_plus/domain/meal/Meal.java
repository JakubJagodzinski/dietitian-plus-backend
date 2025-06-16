package com.example.dietitian_plus.domain.meal;

import com.example.dietitian_plus.domain.dietitian.Dietitian;
import com.example.dietitian_plus.domain.patient.Patient;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

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

    @Column(name = "meal_name", nullable = false)
    private String mealName;

    @Column(nullable = false)
    private LocalDateTime datetime;

    @Column(nullable = false)
    private Float kcal = 0.0f;

    @Column(nullable = false)
    private Float fats = 0.0f;

    @Column(nullable = false)
    private Float carbs = 0.0f;

    @Column(nullable = false)
    private Float protein = 0.0f;

    @Column(nullable = false)
    private Float fiber = 0.0f;

    @Column(name = "glycemic_index", nullable = false)
    private Float glycemicIndex = 0.0f;

    @Column(name = "glycemic_load", nullable = false)
    private Float glycemicLoad = 0.0f;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false, foreignKey = @ForeignKey(name = "fk_meals_patient_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "dietitian_id", nullable = false, foreignKey = @ForeignKey(name = "fk_meals_dietitian_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Dietitian dietitian;

}
