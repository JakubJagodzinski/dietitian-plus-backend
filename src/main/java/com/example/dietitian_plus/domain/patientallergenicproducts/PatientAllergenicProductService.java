package com.example.dietitian_plus.domain.patientallergenicproducts;

import com.example.dietitian_plus.common.constants.Messages;
import com.example.dietitian_plus.domain.patient.Patient;
import com.example.dietitian_plus.domain.patient.PatientRepository;
import com.example.dietitian_plus.domain.patientallergenicproducts.dto.AssignAllergenicProductToPatientRequestDto;
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

    @Transactional
    public List<ProductResponseDto> getPatientAllAllergenicProducts(Long patientId) throws EntityNotFoundException {
        if (!patientRepository.existsById(patientId)) {
            throw new EntityNotFoundException(Messages.PATIENT_NOT_FOUND);
        }

        List<PatientAllergenicProduct> patientAllergenicProductList = patientAllergenicProductRepository.findAllByPatient_Id(patientId);

        return patientAllergenicProductList.stream()
                .map(PatientAllergenicProduct::getProduct)
                .map(productDtoMapper::toDto)
                .toList();
    }

    @Transactional
    public PatientAllergenicProductResponseDto assignAllergenicProductToPatient(Long patientId, AssignAllergenicProductToPatientRequestDto assignAllergenicProductToPatientRequestDto) throws EntityNotFoundException, EntityExistsException {
        Patient patient = patientRepository.findById(patientId).orElse(null);

        if (patient == null) {
            throw new EntityNotFoundException(Messages.PATIENT_NOT_FOUND);
        }

        Long productId = assignAllergenicProductToPatientRequestDto.getProductId();
        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(Messages.PRODUCT_NOT_FOUND);
        }

        PatientAllergenicProductId patientAllergenicProductId = new PatientAllergenicProductId(patientId, productId);

        if (patientAllergenicProductRepository.existsById(patientAllergenicProductId)) {
            throw new EntityExistsException(Messages.PRODUCT_AlREADY_ASSIGNED_TO_PATIENT);
        }

        PatientAllergenicProduct patientAllergenicProduct = new PatientAllergenicProduct();

        patientAllergenicProduct.setId(patientAllergenicProductId);
        patientAllergenicProduct.setPatient(patient);
        patientAllergenicProduct.setProduct(product);

        return patientAllergenicProductDtoMapper.toDto(patientAllergenicProductRepository.save(patientAllergenicProduct));
    }

    @Transactional
    public void unassignAllergenicProductFromPatient(Long patientId, Long productId) throws EntityNotFoundException {
        if (!patientRepository.existsById(patientId)) {
            throw new EntityNotFoundException(Messages.PATIENT_NOT_FOUND);
        }

        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException(Messages.PRODUCT_NOT_FOUND);
        }

        PatientAllergenicProductId patientAllergenicProductId = new PatientAllergenicProductId(patientId, productId);

        if (!patientAllergenicProductRepository.existsById(patientAllergenicProductId)) {
            throw new EntityNotFoundException(Messages.PRODUCT_IS_NOT_ASSIGNED_TO_PATIENT);
        }

        patientAllergenicProductRepository.deleteById(patientAllergenicProductId);
    }

}
