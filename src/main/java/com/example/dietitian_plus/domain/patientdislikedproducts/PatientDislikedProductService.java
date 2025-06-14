package com.example.dietitian_plus.domain.patientdislikedproducts;

import com.example.dietitian_plus.common.constants.messages.PatientMessages;
import com.example.dietitian_plus.common.constants.messages.ProductMessages;
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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientDislikedProductService {

    private final PatientDislikedProductRepository patientDislikedProductRepository;
    private final PatientRepository patientRepository;
    private final ProductRepository productRepository;

    private final PatientDislikedProductDtoMapper patientDislikedProductDtoMapper;
    private final ProductDtoMapper productDtoMapper;

    @Transactional
    public List<ProductResponseDto> getPatientAllDislikedProducts(UUID patientId) throws EntityNotFoundException {
        if (!patientRepository.existsById(patientId)) {
            throw new EntityNotFoundException(PatientMessages.PATIENT_NOT_FOUND);
        }

        List<PatientDislikedProduct> patientDislikedProductList = patientDislikedProductRepository.findAllByPatient_UserId(patientId);

        return patientDislikedProductList.stream()
                .map(PatientDislikedProduct::getProduct)
                .map(productDtoMapper::toDto)
                .toList();
    }

    @Transactional
    public PatientDislikedProductResponseDto assignDislikedProductToPatient(UUID patientId, AssignDislikedProductToPatientRequestDto assignDislikedProductToPatientRequestDto) throws EntityNotFoundException, EntityExistsException {
        Patient patient = patientRepository.findById(patientId).orElse(null);

        if (patient == null) {
            throw new EntityNotFoundException(PatientMessages.PATIENT_NOT_FOUND);
        }

        Long productId = assignDislikedProductToPatientRequestDto.getProductId();
        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(ProductMessages.PRODUCT_NOT_FOUND);
        }

        PatientDislikedProductId patientDislikedProductId = new PatientDislikedProductId(patientId, productId);

        if (patientDislikedProductRepository.existsById(patientDislikedProductId)) {
            throw new EntityExistsException(ProductMessages.PRODUCT_AlREADY_ASSIGNED_TO_PATIENT);
        }

        PatientDislikedProduct patientDislikedProduct = new PatientDislikedProduct();

        patientDislikedProduct.setId(patientDislikedProductId);
        patientDislikedProduct.setPatient(patient);
        patientDislikedProduct.setProduct(product);

        return patientDislikedProductDtoMapper.toDto(patientDislikedProductRepository.save(patientDislikedProduct));
    }

    @Transactional
    public void unassignDislikedProductFromPatient(UUID patientId, Long productId) throws EntityNotFoundException {
        if (!patientRepository.existsById(patientId)) {
            throw new EntityNotFoundException(PatientMessages.PATIENT_NOT_FOUND);
        }

        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException(ProductMessages.PRODUCT_NOT_FOUND);
        }

        PatientDislikedProductId patientDislikedProductId = new PatientDislikedProductId(patientId, productId);

        if (!patientDislikedProductRepository.existsById(patientDislikedProductId)) {
            throw new EntityNotFoundException(ProductMessages.PRODUCT_IS_NOT_ASSIGNED_TO_PATIENT);
        }

        patientDislikedProductRepository.deleteById(patientDislikedProductId);
    }

}
