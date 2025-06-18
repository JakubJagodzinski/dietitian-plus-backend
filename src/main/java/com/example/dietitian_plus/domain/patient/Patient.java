package com.example.dietitian_plus.domain.patient;

import com.example.dietitian_plus.domain.dietitian.Dietitian;
import com.example.dietitian_plus.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "patients")
@PrimaryKeyJoinColumn(name = "patient_id", foreignKey = @ForeignKey(name = "fk_patients_user_id"))
public class Patient extends User {

    private Float height;

    @Column(name = "starting_weight")
    private Float startingWeight;

    @Column(name = "current_weight")
    private Float currentWeight;

    private Float pal; // Physical Activity Level

    private LocalDate birthdate;

    @Column(name = "is_active")
    private Boolean isActive = false;

    @ManyToOne
    @JoinColumn(name = "dietitian_id", foreignKey = @ForeignKey(name = "fk_patients_dietitian_id"))
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Dietitian dietitian = null;

}
