package com.example.dietitian_plus.note;

import com.example.dietitian_plus.dietitian.Dietitian;
import com.example.dietitian_plus.patient.Patient;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "notes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id")
    private Long noteId;

    private String text;

    private LocalDateTime datetime = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "patient_id")
    @JsonManagedReference
    @ToString.Exclude
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "dietitian_id")
    @JsonManagedReference
    @ToString.Exclude
    private Dietitian dietitian;

}
