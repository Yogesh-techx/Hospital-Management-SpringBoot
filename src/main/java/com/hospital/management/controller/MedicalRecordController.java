package com.hospital.management.controller;

import com.hospital.management.dto.MedicalRecordRequestDTO;
import com.hospital.management.dto.MedicalRecordResponseDTO;
import com.hospital.management.service.MedicalRecordService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/records")
public class MedicalRecordController {
	
	private final MedicalRecordService medicalRecordService;
	
	public MedicalRecordController(MedicalRecordService medicalRecordService) {
		this.medicalRecordService = medicalRecordService;
	}
	
	@PostMapping
	public MedicalRecordResponseDTO create(@Valid @RequestBody MedicalRecordRequestDTO dto) {
		return medicalRecordService.createRecord(dto);
	}
	
	@GetMapping
	public List<MedicalRecordResponseDTO> getAll() {
		return medicalRecordService.getAllRecords();
	}
	
	@GetMapping("/{id}")
	public MedicalRecordResponseDTO getById(@PathVariable Long id) {
		return medicalRecordService.getRecordById(id);
	}
	
	@GetMapping("/patient/{patientId}")
	public List<MedicalRecordResponseDTO> getByPatient(@PathVariable Long patientId) {
		return medicalRecordService.getRecordsByPatient(patientId);
	}
	
	@GetMapping("/date/{date}")
	public List<MedicalRecordResponseDTO> getByDate(@PathVariable String date) {
		return medicalRecordService.getRecordsByDate(LocalDate.parse(date));
	}
	
	@GetMapping("/doctor/{doctorId}")
	public List<MedicalRecordResponseDTO> getByDoctor(@PathVariable Long doctorId) {
		return medicalRecordService.getRecordsByDoctor(doctorId);
	}
	
	@PutMapping("/{id}")
	public MedicalRecordResponseDTO update(@PathVariable Long id, @Valid @RequestBody MedicalRecordRequestDTO dto) {
		return medicalRecordService.updateRecord(id, dto);
	}
}