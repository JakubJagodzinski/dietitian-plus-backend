package com.example.dietitian_plus.user;

import com.example.dietitian_plus.dietitian.Dietitian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByDietitian(Dietitian dietitian);

}
