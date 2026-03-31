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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.management.dto.ApiResponse;
import com.hospital.management.dto.AppointmentRequestDTO;
import com.hospital.management.dto.AppointmentResponseDTO;
import com.hospital.management.service.AppointmentService;
import com.hospital.management.util.AppointmentStatus;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
	
	private final AppointmentService appointmentService;
	
	public AppointmentController(AppointmentService appointmentService) {
		this.appointmentService = appointmentService;
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse<AppointmentResponseDTO>> book(@Valid @RequestBody AppointmentRequestDTO dto) {
		return new ResponseEntity<>(new ApiResponse<>(HttpStatus.CREATED, appointmentService.bookAppointment(dto)), HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<AppointmentResponseDTO>>> getAll() {
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, appointmentService.getAllAppointments()));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<AppointmentResponseDTO>> getById(@PathVariable Long id) {
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, appointmentService.getAppointmentById(id)));
	}
	
	@PutMapping("/{id}/cancel")
	public ResponseEntity<ApiResponse<AppointmentResponseDTO>> cancel(@PathVariable Long id) {
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, appointmentService.cancelAppointment(id)));
	}
	
	@PutMapping("/{id}/status")
	public ResponseEntity<ApiResponse<AppointmentResponseDTO>> updateStatus(@PathVariable Long id, @RequestParam AppointmentStatus status) {
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, appointmentService.updateAppointmentStatus(id, status)));
	}
	
	@GetMapping("/date/{date}")
	public ResponseEntity<ApiResponse<List<AppointmentResponseDTO>>> getByDate(@PathVariable String date) {
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, appointmentService.getAppointmentsByDate(LocalDate.parse(date))));
	}
	
	@GetMapping("/status/{status}")
	public ResponseEntity<ApiResponse<List<AppointmentResponseDTO>>> getByStatus(@PathVariable AppointmentStatus status) {
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, appointmentService.getAppointmentsByStatus(status)));
	}
	
	@GetMapping("/patient/{patientId}")
	public ResponseEntity<ApiResponse<List<AppointmentResponseDTO>>> getByPatient(@PathVariable Long patientId) {
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, appointmentService.getAppointmentsByPatient(patientId)));
	}
	@GetMapping("/doctor/{doctorId}")
	public ResponseEntity<ApiResponse<List<AppointmentResponseDTO>>> getByDoctor(@PathVariable Long doctorId) {
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, appointmentService.getAppointmentsByDoctor(doctorId)));
	}
}