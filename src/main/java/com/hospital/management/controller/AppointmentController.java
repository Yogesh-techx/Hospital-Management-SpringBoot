package com.hospital.management.controller;

import com.hospital.management.dto.AppointmentRequestDTO;
import com.hospital.management.dto.AppointmentResponseDTO;
import com.hospital.management.service.AppointmentService;
import com.hospital.management.util.AppointmentStatus;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
	
	private final AppointmentService appointmentService;
	
	public AppointmentController(AppointmentService appointmentService) {
		this.appointmentService = appointmentService;
	}
	
	@PostMapping
	public AppointmentResponseDTO book(@Valid @RequestBody AppointmentRequestDTO dto) {
		return appointmentService.bookAppointment(dto);
	}
	
	@GetMapping
	public List<AppointmentResponseDTO> getAll() {
		return appointmentService.getAllAppointments();
	}
	
	@GetMapping("/{id}")
	public AppointmentResponseDTO getById(@PathVariable Long id) {
		return appointmentService.getAppointmentById(id);
	}
	
	@PutMapping("/{id}/cancel")
	public AppointmentResponseDTO cancel(@PathVariable Long id) {
		return appointmentService.cancelAppointment(id);
	}
	
	@PutMapping("/{id}/status")
	public AppointmentResponseDTO updateStatus(@PathVariable Long id, @RequestParam AppointmentStatus status) {
		return appointmentService.updateAppointmentStatus(id, status);
	}
	
	@GetMapping("/date/{date}")
	public List<AppointmentResponseDTO> getByDate(@PathVariable String date) {
		return appointmentService.getAppointmentsByDate(LocalDate.parse(date));
	}
	
	@GetMapping("/status/{status}")
	public List<AppointmentResponseDTO> getByStatus(@PathVariable AppointmentStatus status) {
		return appointmentService.getAppointmentsByStatus(status);
	}
	
	@GetMapping("/patient/{patientId}")
	public List<AppointmentResponseDTO> getByPatient(@PathVariable Long patientId) {
		return appointmentService.getAppointmentsByPatient(patientId);
	}
	
	@GetMapping("/doctor/{doctorId}")
	public List<AppointmentResponseDTO> getByDoctor(@PathVariable Long doctorId) {
		return appointmentService.getAppointmentsByDoctor(doctorId);
	}
}