package com.example.dietitian_plus.domain.dietitian;

import com.example.dietitian_plus.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
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

    private String title;

}
