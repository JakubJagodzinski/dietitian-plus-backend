package com.example.dietitian_plus.note;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateNoteDto {

    private String text;

    private Long patientId;

    private Long dietitianId;

}
