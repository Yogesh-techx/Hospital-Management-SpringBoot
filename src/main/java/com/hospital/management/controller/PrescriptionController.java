package com.hospital.management.controller;

import com.hospital.management.dto.PrescriptionRequestDTO;
import com.hospital.management.dto.PrescriptionResponseDTO;
import com.hospital.management.service.PrescriptionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {
	
	private final PrescriptionService prescriptionService;
	
	public PrescriptionController(PrescriptionService prescriptionService) {
		this.prescriptionService = prescriptionService;
	}
	
	@PostMapping
	public PrescriptionResponseDTO create(@Valid @RequestBody PrescriptionRequestDTO dto) {
		return prescriptionService.createPrescription(dto);
	}
	
	@GetMapping
	public List<PrescriptionResponseDTO> getAll() {
		return prescriptionService.getAllPrescriptions();
	}
	
	@GetMapping("/{id}")
	public PrescriptionResponseDTO getById(@PathVariable Long id) {
		return prescriptionService.getPrescriptionById(id);
	}
	
	@GetMapping("/record/{recordId}")
	public PrescriptionResponseDTO getByRecord(@PathVariable Long recordId) {
		return prescriptionService.getPrescriptionByMedicalRecord(recordId);
	}
	
	@GetMapping("/patient/{patientId}")
	public List<PrescriptionResponseDTO> getByPatient(@PathVariable Long patientId) {
		return prescriptionService.getPrescriptionsByPatient(patientId);
	}
}