package com.example.dietitian_plus.domain.note;

import com.example.dietitian_plus.domain.dietitian.Dietitian;
import com.example.dietitian_plus.domain.patient.Patient;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "notes")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id")
    private Long noteId;

    private String text = "";

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime lastEditedAt = null;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "dietitian_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference
    private Dietitian dietitian;

}
