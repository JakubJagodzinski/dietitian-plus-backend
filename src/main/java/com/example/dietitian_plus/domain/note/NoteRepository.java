package com.example.dietitian_plus.domain.note;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findAllByPatient_UserId(UUID patientId);

    List<Note> findAllByDietitian_UserId(UUID dietitianId);

}
