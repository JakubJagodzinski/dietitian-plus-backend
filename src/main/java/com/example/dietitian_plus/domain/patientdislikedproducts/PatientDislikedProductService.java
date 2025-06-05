package com.example.dietitian_plus.domain.patientdislikedproducts;

import com.example.dietitian_plus.domain.patient.Patient;
import com.example.dietitian_plus.domain.patient.PatientRepository;
import com.example.dietitian_plus.domain.patientdislikedproducts.dto.CreatePatientDislikedProductRequestDto;
import com.example.dietitian_plus.domain.patientdislikedproducts.dto.PatientDislikedProductDtoMapper;
import com.example.dietitian_plus.domain.patientdislikedproducts.dto.PatientDislikedProductResponseDto;
import com.example.dietitian_plus.domain.product.Product;
import com.example.dietitian_plus.domain.product.ProductRepository;
import com.example.dietitian_plus.domain.product.dto.ProductDtoMapper;
import com.example.dietitian_plus.domain.product.dto.ProductResponseDto;
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
    public List<ProductResponseDto> getPatientDislikedProductsByPatientId(Long patientId) throws EntityNotFoundException {
        if (!patientRepository.existsById(patientId)) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        List<PatientDislikedProduct> patientDislikedProductList = patientDislikedProductRepository.findAllById_PatientId(patientId);

        return patientDislikedProductList.stream()
                .map(PatientDislikedProduct::getProduct)
                .map(productDtoMapper::toDto)
                .toList();
    }

    @Transactional
    public PatientDislikedProductResponseDto createPatientDislikedProduct(CreatePatientDislikedProductRequestDto createPatientDislikedProductRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Long patientId = createPatientDislikedProductRequestDto.getPatientId();
        Patient patient = patientRepository.findById(createPatientDislikedProductRequestDto.getPatientId()).orElse(null);

        if (!patientRepository.existsById(patientId)) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        Long productId = createPatientDislikedProductRequestDto.getProductId();
        Product product = productRepository.findById(createPatientDislikedProductRequestDto.getProductId()).orElse(null);

        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        PatientDislikedProductId patientDislikedProductId = new PatientDislikedProductId(patientId, productId);

        if (patientDislikedProductRepository.existsById(patientDislikedProductId)) {
            throw new IllegalArgumentException(PRODUCT_AlREADY_ASSIGNED_TO_PATIENT_MESSAGE);
        }

        PatientDislikedProduct patientDislikedProduct = new PatientDislikedProduct();

        patientDislikedProduct.setId(patientDislikedProductId);
        patientDislikedProduct.setPatient(patient);
        patientDislikedProduct.setProduct(product);

        PatientDislikedProduct savedPatientDislikedProduct = patientDislikedProductRepository.save(patientDislikedProduct);

        return patientDislikedProductDtoMapper.toDto(savedPatientDislikedProduct);
    }

    @Transactional
    public void deletePatientDislikedProductById(Long patientId, Long productId) throws EntityNotFoundException, IllegalArgumentException {
        if (!patientRepository.existsById(patientId)) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        PatientDislikedProductId patientDislikedProductId = new PatientDislikedProductId(patientId, productId);

        if (!patientDislikedProductRepository.existsById(patientDislikedProductId)) {
            throw new IllegalArgumentException(PRODUCT_IS_NOT_ASSIGNED_TO_PATIENT_MESSAGE);
        }

        patientDislikedProductRepository.deleteById(patientDislikedProductId);
    }

}
