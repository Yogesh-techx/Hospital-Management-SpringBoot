package com.hospital.management.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.management.dto.ApiResponse;
import com.hospital.management.dto.MedicalRecordRequestDTO;
import com.hospital.management.dto.MedicalRecordResponseDTO;
import com.hospital.management.service.MedicalRecordService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/records")
public class MedicalRecordController {
	
	private final MedicalRecordService medicalRecordService;
	
	public MedicalRecordController(MedicalRecordService medicalRecordService) {
		this.medicalRecordService = medicalRecordService;
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse<MedicalRecordResponseDTO>> create(@Valid @RequestBody MedicalRecordRequestDTO dto) {
		return new ResponseEntity<>(new ApiResponse<>(HttpStatus.CREATED, medicalRecordService.createRecord(dto)), HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<MedicalRecordResponseDTO>>> getAll() {
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, medicalRecordService.getAllRecords()));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<MedicalRecordResponseDTO>> getById(@PathVariable Long id) {
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, medicalRecordService.getRecordById(id)));
	}
	
	@GetMapping("/patient/{patientId}")
	public ResponseEntity<ApiResponse<List<MedicalRecordResponseDTO>>> getByPatient(@PathVariable Long patientId) {
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, medicalRecordService.getRecordsByPatient(patientId)));
	}
	
	@GetMapping("/date/{date}")
    public ResponseEntity<ApiResponse<List<MedicalRecordResponseDTO>>> getByDate(@PathVariable String date) {
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, medicalRecordService.getRecordsByDate(LocalDate.parse(date))));
	}
	
	@GetMapping("/doctor/{doctorId}")
	public ResponseEntity<ApiResponse<List<MedicalRecordResponseDTO>>> getByDoctor(@PathVariable Long doctorId) {
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, medicalRecordService.getRecordsByDoctor(doctorId)));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<MedicalRecordResponseDTO>> update(@PathVariable Long id, @Valid @RequestBody MedicalRecordRequestDTO dto) {
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, medicalRecordService.updateRecord(id, dto)));
	}
}