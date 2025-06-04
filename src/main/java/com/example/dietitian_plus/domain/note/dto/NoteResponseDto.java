package com.example.dietitian_plus.domain.note.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class NoteResponseDto {

    private Long noteId;

    private String text;

    private LocalDateTime datetime;

    private Long patientId;

    private Long dietitianId;

}
