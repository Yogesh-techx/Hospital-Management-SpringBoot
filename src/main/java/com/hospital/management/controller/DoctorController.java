package com.hospital.management.controller;

import com.hospital.management.dto.DoctorRequestDTO;
import com.hospital.management.dto.DoctorResponseDTO;
import com.hospital.management.service.DoctorService;
import com.hospital.management.util.AvailabilityDays;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
	
	private final DoctorService doctorService;
	
	public DoctorController(DoctorService doctorService) {
		this.doctorService = doctorService;
	}
	
	// Save Doctor
	@PostMapping
	public DoctorResponseDTO saveDoctor(@RequestBody DoctorRequestDTO dto) {
		return doctorService.saveDoctor(dto);
	}
	
	// Save All Doctors
	@PostMapping("/bulk")
	public List<DoctorResponseDTO> saveAllDoctors(@RequestBody List<DoctorRequestDTO> dtos) {
		return doctorService.saveAllDoctors(dtos);
	}
	
	// Get All Doctors
	@GetMapping
	public List<DoctorResponseDTO> getAllDoctors() {
		return doctorService.getAllDoctors();
	}
	
	// Get Doctor By ID
	@GetMapping("/{id}")
	public DoctorResponseDTO getDoctorById(@PathVariable Long id) {
		return doctorService.getDoctorById(id);
	}
	
	// Delete Doctor
	@DeleteMapping("/{id}")
	public void deleteDoctor(@PathVariable Long id) {
		doctorService.deleteDoctor(id);
	}
	
	// Get by Department
	@GetMapping("/department/{deptId}")
	public List<DoctorResponseDTO> getByDepartment(@PathVariable Long deptId) {
		return doctorService.getDoctorsByDepartment(deptId);
	}
	
	// Get by Specialization
	@GetMapping("/specialization/{spec}")
	public List<DoctorResponseDTO> getBySpecialization(@PathVariable String spec) {
		return doctorService.getDoctorsBySpecialization(spec);
	}
	
	// Get by Appointment
	@GetMapping("/appointment/{appointmentId}")
	public List<DoctorResponseDTO> getByAppointment(@PathVariable Long appointmentId) {
		return doctorService.getDoctorsByAppointment(appointmentId);
	}
	
	// Get by Patient
	@GetMapping("/patient/{patientId}")
	public List<DoctorResponseDTO> getByPatient(@PathVariable Long patientId) {
		return doctorService.getDoctorsByPatient(patientId);
	}
	
	// Get by Availability Day
	@GetMapping("/availability/{day}")
	public List<DoctorResponseDTO> getByAvailability(@PathVariable AvailabilityDays day) {
		return doctorService.getDoctorsByAvailabilityDay(day);
	}
}