package com.pms.patientservice.controller;

import com.pms.patientservice.dto.PatientRequestDto;
import com.pms.patientservice.dto.PatientResponseDto;
import com.pms.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patient", description = "Operations related to patient management")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    @Operation(summary = "Get Patients")
    public ResponseEntity<List<PatientResponseDto>> getPatients() {
        List<PatientResponseDto> response = patientService.getPatients();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/create")
    @Operation(summary = "Create Patient")
    public ResponseEntity<PatientResponseDto> createPatient(@Valid @RequestBody PatientRequestDto requestDto) {
        PatientResponseDto response = patientService.CreatePatient(requestDto);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "update patient")
    public ResponseEntity<PatientResponseDto> updatePatient(@PathVariable UUID id, @Valid @RequestBody PatientRequestDto requestDto) {
        PatientResponseDto response = patientService.UpdatePatientRecord(id, requestDto);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "delete patient")
    public ResponseEntity<String> deletePatient(@PathVariable UUID id) {
        String response = patientService.DeletePatientRecord(id);
        return ResponseEntity.ok().body(response);
    }
}
