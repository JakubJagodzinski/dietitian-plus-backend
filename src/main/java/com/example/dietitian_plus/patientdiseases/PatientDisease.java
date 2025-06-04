package com.example.dietitian_plus.patientdiseases;


import com.example.dietitian_plus.disease.Disease;
import com.example.dietitian_plus.patient.Patient;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "patient_diseases")
public class PatientDisease {

    @EmbeddedId
    private PatientDiseaseId id;

    @ManyToOne
    @MapsId("patientId")
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_patient_disease_patient"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Patient patient;

    @ManyToOne
    @MapsId("diseaseId")
    @JoinColumn(name = "disease_id", foreignKey = @ForeignKey(name = "fk_patient_disease_disease"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Disease disease;

}
