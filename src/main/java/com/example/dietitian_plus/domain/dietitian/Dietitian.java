package com.example.dietitian_plus.domain.dietitian;

import com.example.dietitian_plus.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "dietitians")
@PrimaryKeyJoinColumn(name = "dietitian_id", foreignKey = @ForeignKey(name = "fk_dietitians_user_id"))
public class Dietitian extends User {

    @Column(length = 50)
    private String title;

}
