package com.example.dietitian_plus.domain.dietitian.dto.request;

import com.example.dietitian_plus.common.validation.NotEmptyIfPresent;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateDietitianRequestDto {

    @Schema(
            description = "Dietitian's first name",
            example = "John",
            maxLength = 100
    )
    @NotBlank
    @Size(max = 100)
    @JsonProperty("first_name")
    private String firstName;

    @Schema(
            description = "Dietitian's last name",
            example = "Smith",
            maxLength = 100
    )
    @NotBlank
    @Size(max = 100)
    @JsonProperty("last_name")
    private String lastName;

    @Schema(
            description = "Dietitian's title",
            example = "Phd",
            maxLength = 50,
            nullable = true
    )
    @NotEmptyIfPresent
    @Size(max = 50)
    @JsonProperty("title")
    private String title;

}
