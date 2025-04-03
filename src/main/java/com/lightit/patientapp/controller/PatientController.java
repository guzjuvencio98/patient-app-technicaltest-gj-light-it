package com.lightit.patientapp.controller;


import com.lightit.patientapp.dto.PatientDTO;
import com.lightit.patientapp.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<String> registerPatient(@RequestBody @Valid PatientDTO dto) {
        Long id = patientService.registerPatient(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Patient created successfully with ID " + id);
    }

    @PostMapping("/{id}/document")
    public ResponseEntity<String> uploadDocument(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        patientService.uploadDocument(id, file);
        return ResponseEntity.ok("Document uploaded successfully.");
    }

}