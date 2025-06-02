package com.pms.patientservice.service;

import com.pms.patientservice.dto.PatientRequestDto;
import com.pms.patientservice.dto.PatientResponseDto;
import com.pms.patientservice.exception.childexception.EmailAlreadyExitsException;
import com.pms.patientservice.exception.childexception.PatientNotFoundException;
import com.pms.patientservice.exception.childexception.UnderAgedArgumentException;
import com.pms.patientservice.mapper.PatientMapper;
import com.pms.patientservice.model.Patient;
import com.pms.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDto> getPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(PatientMapper::toDto).toList();

    }

    // check if the user doesnt al;ready exists
    // patients below age 18 shouldnt be registered

    public PatientResponseDto CreatePatient(PatientRequestDto requestDto) {

        boolean emailExits = patientRepository.existsByEmail(requestDto.getEmail());
        if (emailExits) throw new EmailAlreadyExitsException("A patient with this email already exits");

        LocalDate today = LocalDate.now();
        Period age = Period.between(requestDto.getDateOfBirth(), today);
        Period minAge = Period.ofYears(11);
        if (age.minus(minAge).isNegative())
            throw new UnderAgedArgumentException("Patient must be at least 11 years old");

        Patient newPatient = patientRepository.save(PatientMapper.toModel(requestDto));

        return PatientMapper.toDto(newPatient);

    }

    public PatientResponseDto UpdatePatientRecord(UUID id, PatientRequestDto requestDto) {

        Patient patient = patientRepository.findById(id).orElseThrow(
                () -> new PatientNotFoundException("Patient not found with ID:", id));

        boolean emailExits = patientRepository.existsByEmail(requestDto.getEmail());
        if (emailExits) throw new EmailAlreadyExitsException("A patient with this email already exits");

        patient.setDateOfBirth(requestDto.getDateOfBirth());
        patient.setAddress(requestDto.getAddress());
        patient.setName(requestDto.getName());
        patient.setName(requestDto.getName());

        var updatedPatient = patientRepository.save(patient);
        return PatientMapper.toDto(updatedPatient);
    }

    public String DeletePatientRecord(UUID id) {
        Patient patient = patientRepository.findById(id).orElseThrow(
                () -> new PatientNotFoundException("Patient not found with ID:", id));

        patientRepository.delete(patient);
        return "Patient with id: " + id + " deleted successfully";
    }
}
