package com.example.dietitian_plus.domain.note.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CreateNoteRequestDto {

    private String title;

    private String text;

    @JsonProperty("patient_id")
    private UUID patientId;

    @JsonProperty("dietitian_id")
    private UUID dietitianId;

}
