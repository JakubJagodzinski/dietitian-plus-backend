package com.example.dietitian_plus.domain.patientdislikedproducts;

import com.example.dietitian_plus.domain.patient.Patient;
import com.example.dietitian_plus.domain.product.Product;
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
@Table(name = "patient_disliked_products")
public class PatientDislikedProduct {

    @EmbeddedId
    private PatientDislikedProductId id;

    @ManyToOne
    @MapsId("patientId")
    @JoinColumn(name = "patient_id", foreignKey = @ForeignKey(name = "fk_patient_disliked_product_patient"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Patient patient;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_patient_disliked_product_product"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

}
