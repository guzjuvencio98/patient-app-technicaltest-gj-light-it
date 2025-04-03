package com.lightit.patientapp.service;

import com.lightit.patientapp.dto.PatientDTO;
import com.lightit.patientapp.entity.Patient;
import com.lightit.patientapp.exception.PatientAlreadyExistsException;
import com.lightit.patientapp.mapper.PatientMapper;
import com.lightit.patientapp.repository.PatientRepository;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    private INotificationService notificationService;

    private final PatientMapper patientMapper;

    public Long registerPatient(PatientDTO dto) {
        Patient patient = patientMapper.toEntity(dto, null);
        try {
            patientRepository.save(patient);
        } catch (DataIntegrityViolationException ex) {
            Throwable cause = ex.getCause();
            if (cause instanceof org.hibernate.exception.ConstraintViolationException constraintEx &&
                    constraintEx.getSQLException().getMessage().contains("Duplicate entry")) {
                throw new PatientAlreadyExistsException(dto.getEmail());
            }
            throw ex;
        }

        notificationService.notify(patient.getEmail(), patient.getName());
        return patient.getId();
    }


    public void uploadDocument(Long id, MultipartFile file) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        String path = saveDocument(file);
        patient.setDocumentPath(path);
        patientRepository.save(patient);
    }

    private String saveDocument(MultipartFile document) {
        if (document == null || document.isEmpty()) return null;

        String fileName = UUID.randomUUID() + "_" + document.getOriginalFilename();
        String folder = System.getProperty("java.io.tmpdir");
        File file = new File(folder + File.separator + fileName);

        try {
            document.transferTo(file);
            return file.getAbsolutePath();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save document", e);
        }
    }

}