package com.example.dietitian_plus.domain.note;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findAllByPatient_Id(Long patientId);

    List<Note> findAllByDietitian_Id(Long dietitianId);

}
