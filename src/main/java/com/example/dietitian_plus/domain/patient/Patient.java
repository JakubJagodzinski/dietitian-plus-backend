package com.example.dietitian_plus.domain.patient;

import com.example.dietitian_plus.domain.dietitian.Dietitian;
import com.example.dietitian_plus.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "patients")
@PrimaryKeyJoinColumn(name = "patient_id")
public class Patient extends User {

    private Float height;

    @Column(name = "starting_weight")
    private Float startingWeight;

    @Column(name = "current_weight")
    private Float currentWeight;

    @Column(name = "is_active")
    private Boolean isActive = false;

    @ManyToOne
    @JoinColumn(name = "dietitian_id")
    private Dietitian dietitian = null;

}
