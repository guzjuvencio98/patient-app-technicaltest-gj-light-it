package com.lightit.patientapp.mapper;

import com.lightit.patientapp.dto.PatientDTO;
import com.lightit.patientapp.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(getCurrentTime())")
    @Mapping(target = "documentPath", source = "documentPath")
    Patient toEntity(PatientDTO dto, String documentPath);

    default LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }

    PatientDTO toDTO(Patient patient);
}
