package com.example.dietitian_plus.domain.note.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({ "note_id", "patient_id", "dietitian_id", "created_at", "last_edited_at", "title", "text" })
public class NoteResponseDto {

    @JsonProperty("note_id")
    private Long noteId;

    @JsonProperty("patient_id")
    private UUID patientId;

    @JsonProperty("dietitian_id")
    private UUID dietitianId;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("last_edited_at")
    private LocalDateTime lastEditedAt;

    private String title;

    private String text;

}
