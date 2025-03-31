package com.example.dietitian_plus.meal;

import com.example.dietitian_plus.dietitian.Dietitian;
import com.example.dietitian_plus.dish.Dish;
import com.example.dietitian_plus.user.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "meals")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meal_id")
    private Long mealId;

    private LocalDateTime datetime;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;

    @OneToOne
    @JoinColumn(name = "dietitian_id")
    @JsonManagedReference
    private Dietitian dietitian;

    @ManyToMany
    @JoinTable(
            name = "meals_dishes",
            joinColumns = @JoinColumn(name = "meal_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id")
    )
    @JsonManagedReference
    @ToString.Exclude
    private final List<Dish> dishes = new ArrayList<>();

}
