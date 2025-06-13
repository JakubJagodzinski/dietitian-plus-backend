package com.example.dietitian_plus.domain.dish;

import com.example.dietitian_plus.domain.dietitian.Dietitian;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "dishes")
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dish_id")
    private Long dishId;

    @Column(name = "dish_name", nullable = false)
    private String dishName;

    @Column(name = "is_template", nullable = false)
    private Boolean isTemplate = false;

    @Column(name = "is_public", nullable = false)
    private Boolean isPublic = false;

    private String recipe = null;

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
    @JoinColumn(name = "dietitian_id", foreignKey = @ForeignKey(name = "fk_dishes_dietitian_id"), nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Dietitian dietitian;

}
