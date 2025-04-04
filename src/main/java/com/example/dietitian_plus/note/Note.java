package com.example.dietitian_plus.note;

import com.example.dietitian_plus.dietitian.Dietitian;
import com.example.dietitian_plus.user.User;
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
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    @ToString.Exclude
    private User user;

    @ManyToOne
    @JoinColumn(name = "dietitian_id")
    @JsonManagedReference
    @ToString.Exclude
    private Dietitian dietitian;

}
