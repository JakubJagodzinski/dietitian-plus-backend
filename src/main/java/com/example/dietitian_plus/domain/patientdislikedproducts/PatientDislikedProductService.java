package com.example.dietitian_plus.domain.patientdislikedproducts;

import com.example.dietitian_plus.domain.patient.Patient;
import com.example.dietitian_plus.domain.patient.PatientRepository;
import com.example.dietitian_plus.domain.patientdislikedproducts.dto.AssignDislikedProductToPatientRequestDto;
import com.example.dietitian_plus.domain.patientdislikedproducts.dto.PatientDislikedProductDtoMapper;
import com.example.dietitian_plus.domain.patientdislikedproducts.dto.PatientDislikedProductResponseDto;
import com.example.dietitian_plus.domain.product.Product;
import com.example.dietitian_plus.domain.product.ProductRepository;
import com.example.dietitian_plus.domain.product.dto.ProductDtoMapper;
import com.example.dietitian_plus.domain.product.dto.ProductResponseDto;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientDislikedProductService {

    private final PatientDislikedProductRepository patientDislikedProductRepository;
    private final PatientRepository patientRepository;
    private final ProductRepository productRepository;

    private final PatientDislikedProductDtoMapper patientDislikedProductDtoMapper;
    private final ProductDtoMapper productDtoMapper;

    private static final String PATIENT_NOT_FOUND_MESSAGE = "Patient not found";
    private static final String PRODUCT_NOT_FOUND_MESSAGE = "Product not found";
    private static final String PRODUCT_AlREADY_ASSIGNED_TO_PATIENT_MESSAGE = "Product already assigned to patient";
    private static final String PRODUCT_IS_NOT_ASSIGNED_TO_PATIENT_MESSAGE = "Product is not assigned to patient";

    @Transactional
    public List<ProductResponseDto> getPatientAllDislikedProducts(Long patientId) throws EntityNotFoundException {
        if (!patientRepository.existsById(patientId)) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        List<PatientDislikedProduct> patientDislikedProductList = patientDislikedProductRepository.findAllByPatient_Id(patientId);

        return patientDislikedProductList.stream()
                .map(PatientDislikedProduct::getProduct)
                .map(productDtoMapper::toDto)
                .toList();
    }

    @Transactional
    public PatientDislikedProductResponseDto assignDislikedProductToPatient(Long patientId, AssignDislikedProductToPatientRequestDto assignDislikedProductToPatientRequestDto) throws EntityNotFoundException, EntityExistsException {
        Patient patient = patientRepository.findById(patientId).orElse(null);

        if (patient == null) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        Long productId = assignDislikedProductToPatientRequestDto.getProductId();
        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        PatientDislikedProductId patientDislikedProductId = new PatientDislikedProductId(patientId, productId);

        if (patientDislikedProductRepository.existsById(patientDislikedProductId)) {
            throw new EntityExistsException(PRODUCT_AlREADY_ASSIGNED_TO_PATIENT_MESSAGE);
        }

        PatientDislikedProduct patientDislikedProduct = new PatientDislikedProduct();

        patientDislikedProduct.setId(patientDislikedProductId);
        patientDislikedProduct.setPatient(patient);
        patientDislikedProduct.setProduct(product);

        return patientDislikedProductDtoMapper.toDto(patientDislikedProductRepository.save(patientDislikedProduct));
    }

    @Transactional
    public void unassignDislikedProductFromPatient(Long patientId, Long productId) throws EntityNotFoundException {
        if (!patientRepository.existsById(patientId)) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        PatientDislikedProductId patientDislikedProductId = new PatientDislikedProductId(patientId, productId);

        if (!patientDislikedProductRepository.existsById(patientDislikedProductId)) {
            throw new EntityNotFoundException(PRODUCT_IS_NOT_ASSIGNED_TO_PATIENT_MESSAGE);
        }

        patientDislikedProductRepository.deleteById(patientDislikedProductId);
    }

}
