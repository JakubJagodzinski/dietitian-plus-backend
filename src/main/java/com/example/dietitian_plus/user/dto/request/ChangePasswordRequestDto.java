package com.example.dietitian_plus.user.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonPropertyOrder({"current_password", "new_password", "confirmation_password"})
public class ChangePasswordRequestDto {

    @Schema(
            description = "Current password of the user",
            example = "OldP@ssw0rd123"
    )
    @NotBlank
    @JsonProperty("current_password")
    private String currentPassword;

    @Schema(
            description = "New password the user wants to set",
            example = "NewP@ssw0rd456",
            maxLength = 100
    )
    @NotBlank
    @Size(max = 100)
    @JsonProperty("new_password")
    private String newPassword;

    @Schema(
            description = "Confirmation of the new password, must match exactly with newPassword field",
            example = "NewP@ssw0rd456",
            maxLength = 100
    )
    @NotBlank
    @Size(max = 100)
    @JsonProperty("confirmation_password")
    private String confirmationPassword;

}
