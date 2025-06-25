package com.example.dietitian_plus.domain.patient.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatientQuestionnaireStatusResponseDto {

    @JsonProperty("is_questionnaire_completed")
    private Boolean isQuestionnaireCompleted;

}
