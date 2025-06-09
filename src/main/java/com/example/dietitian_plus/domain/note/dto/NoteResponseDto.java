package com.example.dietitian_plus.domain.note.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class NoteResponseDto {

    @JsonProperty("note_id")
    private Long noteId;

    private String title;

    private String text;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("last_edited_at")
    private LocalDateTime lastEditedAt;

    @JsonProperty("patient_id")
    private Long patientId;

    @JsonProperty("dietitian_id")
    private Long dietitianId;

}
