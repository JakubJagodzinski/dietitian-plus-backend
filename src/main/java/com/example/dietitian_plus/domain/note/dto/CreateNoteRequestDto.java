package com.example.dietitian_plus.domain.note.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateNoteRequestDto {

    private String text;

    private Long patientId;

    private Long dietitianId;

}
