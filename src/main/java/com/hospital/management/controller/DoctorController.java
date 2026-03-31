package com.hospital.management.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.management.dto.ApiResponse;
import com.hospital.management.dto.DoctorRequestDTO;
import com.hospital.management.dto.DoctorResponseDTO;
import com.hospital.management.service.DoctorService;
import com.hospital.management.util.AvailabilityDays;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
	
	private final DoctorService doctorService;
	
	public DoctorController(DoctorService doctorService) {
		this.doctorService = doctorService;
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse<DoctorResponseDTO>> saveDoctor(@RequestBody DoctorRequestDTO dto) {
		return new ResponseEntity<>(new ApiResponse<>(HttpStatus.CREATED, doctorService.saveDoctor(dto)), HttpStatus.CREATED);
	}
	
	@PostMapping("/bulk")
	public ResponseEntity<ApiResponse<List<DoctorResponseDTO>>> saveAll(@RequestBody List<DoctorRequestDTO> dtos) {
		return new ResponseEntity<>(new ApiResponse<>(HttpStatus.CREATED, doctorService.saveAllDoctors(dtos)), HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<DoctorResponseDTO>>> getAll() {
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, doctorService.getAllDoctors()));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<DoctorResponseDTO>> getById(@PathVariable Long id) {
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, doctorService.getDoctorById(id)));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
		doctorService.deleteDoctor(id);
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "Doctor deleted successfully"));
	}
	
	@GetMapping("/department/{deptId}")
	public ResponseEntity<ApiResponse<List<DoctorResponseDTO>>> getByDepartment(@PathVariable Long deptId) {
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, doctorService.getDoctorsByDepartment(deptId)));
	}
	
	@GetMapping("/specialization/{spec}")
	public ResponseEntity<ApiResponse<List<DoctorResponseDTO>>> getBySpecialization(@PathVariable String spec) {
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, doctorService.getDoctorsBySpecialization(spec)));
	}
	
	@GetMapping("/appointment/{appointmentId}")
	public ResponseEntity<ApiResponse<List<DoctorResponseDTO>>> getByAppointment(@PathVariable Long appointmentId) {
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, doctorService.getDoctorsByAppointment(appointmentId)));
	}
	
	@GetMapping("/patient/{patientId}")
	public ResponseEntity<ApiResponse<List<DoctorResponseDTO>>> getByPatient(@PathVariable Long patientId) {
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, doctorService.getDoctorsByPatient(patientId)));
	}
	
	@GetMapping("/availability/{day}")
	public ResponseEntity<ApiResponse<List<DoctorResponseDTO>>> getByAvailability(@PathVariable AvailabilityDays day) {
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, doctorService.getDoctorsByAvailabilityDay(day)));
	}
}