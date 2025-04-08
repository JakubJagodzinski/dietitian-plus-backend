package com.example.dietitian_plus.note;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class NoteDto {

    private Long noteId;

    private String text;

    private LocalDateTime datetime;

    private Long patientId;

    private Long dietitianId;

}
