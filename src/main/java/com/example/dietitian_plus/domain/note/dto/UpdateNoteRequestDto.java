package com.example.dietitian_plus.domain.note.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateNoteRequestDto {

    private String text;

    @JsonProperty("patient_id")
    private Long patientId;

}
