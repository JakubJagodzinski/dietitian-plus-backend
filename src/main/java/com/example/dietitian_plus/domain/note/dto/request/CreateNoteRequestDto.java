package com.example.dietitian_plus.domain.note.dto.request;

import com.example.dietitian_plus.common.validation.NotEmptyIfPresent;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CreateNoteRequestDto {

    @Schema(
            description = "Note's dietitian author id",
            example = "123e4567-e89b-12d3-a456-426614174000",
            type = "string",
            format = "uuid"
    )
    @NotNull
    @JsonProperty("dietitian_id")
    private UUID dietitianId;

    @Schema(
            description = "Id of the patient the note is assigned to",
            example = "123e4567-e89b-12d3-a456-426614174000",
            type = "string",
            format = "uuid"
    )
    @NotNull
    @JsonProperty("patient_id")
    private UUID patientId;

    @Schema(
            description = "Title of the note, doesn't need to be unique",
            example = "Patient's bad habits",
            maxLength = 100,
            nullable = true
    )
    @NotEmptyIfPresent
    @Size(max = 100)
    @JsonProperty("title")
    private String title;

    @Schema(
            description = "Note's content",
            example = "Patient is addicted to beer",
            maxLength = 1_000
    )
    @NotBlank
    @Size(max = 1_000)
    @JsonProperty("text")
    private String text;

}
