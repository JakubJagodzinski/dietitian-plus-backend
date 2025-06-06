package com.example.dietitian_plus.domain.patientallergenicproducts;

import com.example.dietitian_plus.domain.patient.Patient;
import com.example.dietitian_plus.domain.patient.PatientRepository;
import com.example.dietitian_plus.domain.patientallergenicproducts.dto.CreatePatientAllergenicProductRequestDto;
import com.example.dietitian_plus.domain.patientallergenicproducts.dto.PatientAllergenicProductDtoMapper;
import com.example.dietitian_plus.domain.patientallergenicproducts.dto.PatientAllergenicProductResponseDto;
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
public class PatientAllergenicProductService {

    private final PatientAllergenicProductRepository patientAllergenicProductRepository;
    private final PatientRepository patientRepository;
    private final ProductRepository productRepository;

    private final PatientAllergenicProductDtoMapper patientAllergenicProductDtoMapper;
    private final ProductDtoMapper productDtoMapper;

    private static final String PATIENT_NOT_FOUND_MESSAGE = "Patient not found";
    private static final String PRODUCT_NOT_FOUND_MESSAGE = "Product not found";
    private static final String PRODUCT_AlREADY_ASSIGNED_TO_PATIENT_MESSAGE = "Product already assigned to patient";
    private static final String PRODUCT_IS_NOT_ASSIGNED_TO_PATIENT_MESSAGE = "Product is not assigned to patient";

    @Transactional
    public List<ProductResponseDto> getPatientAllergenicProductsByPatientId(Long patientId) throws EntityNotFoundException {
        if (!patientRepository.existsById(patientId)) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        List<PatientAllergenicProduct> patientAllergenicProductList = patientAllergenicProductRepository.findAllById_PatientId(patientId);

        return patientAllergenicProductList.stream()
                .map(PatientAllergenicProduct::getProduct)
                .map(productDtoMapper::toDto)
                .toList();
    }

    @Transactional
    public PatientAllergenicProductResponseDto createPatientAllergenicProduct(CreatePatientAllergenicProductRequestDto createPatientAllergenicProductRequestDto) throws EntityNotFoundException, EntityExistsException {
        Long patientId = createPatientAllergenicProductRequestDto.getPatientId();
        Patient patient = patientRepository.findById(patientId).orElse(null);

        if (patient == null) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        Long productId = createPatientAllergenicProductRequestDto.getProductId();
        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        PatientAllergenicProductId patientAllergenicProductId = new PatientAllergenicProductId(patientId, productId);

        if (patientAllergenicProductRepository.existsById(patientAllergenicProductId)) {
            throw new EntityExistsException(PRODUCT_AlREADY_ASSIGNED_TO_PATIENT_MESSAGE);
        }

        PatientAllergenicProduct patientAllergenicProduct = new PatientAllergenicProduct();

        patientAllergenicProduct.setId(patientAllergenicProductId);
        patientAllergenicProduct.setPatient(patient);
        patientAllergenicProduct.setProduct(product);

        return patientAllergenicProductDtoMapper.toDto(patientAllergenicProductRepository.save(patientAllergenicProduct));
    }

    @Transactional
    public void deletePatientAllergenicProductById(Long patientId, Long productId) throws EntityNotFoundException {
        if (!patientRepository.existsById(patientId)) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        PatientAllergenicProductId patientAllergenicProductId = new PatientAllergenicProductId(patientId, productId);

        if (!patientAllergenicProductRepository.existsById(patientAllergenicProductId)) {
            throw new EntityNotFoundException(PRODUCT_IS_NOT_ASSIGNED_TO_PATIENT_MESSAGE);
        }

        patientAllergenicProductRepository.deleteById(patientAllergenicProductId);
    }

}
