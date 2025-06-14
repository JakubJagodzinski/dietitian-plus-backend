package com.example.dietitian_plus.domain.patientdiseases;

import com.example.dietitian_plus.domain.disease.Disease;
import com.example.dietitian_plus.domain.patient.Patient;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "patient_diseases")
public class PatientDisease {

    @EmbeddedId
    private PatientDiseaseId id;

    @ManyToOne
    @MapsId("patientId")
    @JoinColumn(name = "patient_id", foreignKey = @ForeignKey(name = "fk_patient_diseases_patient_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Patient patient;

    @ManyToOne
    @MapsId("diseaseId")
    @JoinColumn(name = "disease_id", foreignKey = @ForeignKey(name = "fk_patient_diseases_disease_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Disease disease;

}
