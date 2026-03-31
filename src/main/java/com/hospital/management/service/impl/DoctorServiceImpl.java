package com.hospital.management.service.impl;

import com.hospital.management.dto.DoctorRequestDTO;
import com.hospital.management.dto.DoctorResponseDTO;
import com.hospital.management.exception.ResourceNotFoundException;
import com.hospital.management.model.Department;
import com.hospital.management.model.Doctor;
import com.hospital.management.repository.DepartmentRepository;
import com.hospital.management.repository.DoctorRepository;
import com.hospital.management.service.DoctorService;
import com.hospital.management.util.AvailabilityDays;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {
	
	private final DoctorRepository doctorRepository;
	private final DepartmentRepository departmentRepository;
	
	public DoctorServiceImpl(DoctorRepository doctorRepository, DepartmentRepository departmentRepository) {
		this.doctorRepository = doctorRepository;
		this.departmentRepository = departmentRepository;
	}
	
	// DTO to Entity
	private Doctor mapToEntity(DoctorRequestDTO dto) {
		
		Department department = departmentRepository.findById(dto.getDepartmentId()).orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + dto.getDepartmentId()));
		
		Doctor doctor = new Doctor();
		doctor.setDoctorName(dto.getDoctorName());
		doctor.setSpecialization(dto.getSpecialization());
		doctor.setAvailabilityDays(dto.getAvailabilityDays());
		doctor.setDepartment(department);
		
		return doctor;
	}
	
	// Entity to DTO
	private DoctorResponseDTO mapToDTO(Doctor doctor) {
		
		DoctorResponseDTO dto = new DoctorResponseDTO();
		dto.setDoctorId(doctor.getDoctorId());
		dto.setDoctorName(doctor.getDoctorName());
		dto.setSpecialization(doctor.getSpecialization());
		dto.setAvailabilityDays(doctor.getAvailabilityDays());
		dto.setDepartmentName(doctor.getDepartment().getDepartmentName());
		
		return dto;
	}
	
	// Save Doctor
	@Override
	public DoctorResponseDTO saveDoctor(DoctorRequestDTO dto) {
		Doctor saved = doctorRepository.save(mapToEntity(dto));
		return mapToDTO(saved);
	}
	
	// Save All Doctors
	@Override
	public List<DoctorResponseDTO> saveAllDoctors(List<DoctorRequestDTO> dtos) {
		
		List<Doctor> doctors = dtos.stream().map(this::mapToEntity).collect(Collectors.toList());
		
		return doctorRepository.saveAll(doctors).stream().map(this::mapToDTO).collect(Collectors.toList());
	}
	
	// Get All Doctors
	@Override
	public List<DoctorResponseDTO> getAllDoctors() {
		return doctorRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}
	
	// Get Doctor By ID
	@Override
	public DoctorResponseDTO getDoctorById(Long id) {
		
		Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));
		
		return mapToDTO(doctor);
	}
	
	// Delete Doctor
	@Override
	public void deleteDoctor(Long id) {
		Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));
		doctorRepository.delete(doctor);
	}
	
	// Get by Department
	@Override
	public List<DoctorResponseDTO> getDoctorsByDepartment(Long departmentId) {
		
		if (!departmentRepository.existsById(departmentId)) {
			throw new ResourceNotFoundException("Department not found with id: " + departmentId);
		}
		
		return doctorRepository.findByDepartment_DepartmentId(departmentId).stream().map(this::mapToDTO).collect(Collectors.toList());
	}
	
	// Get by Specialization
	@Override
	public List<DoctorResponseDTO> getDoctorsBySpecialization(String specialization) {
		return doctorRepository.findBySpecialization(specialization).stream().map(this::mapToDTO).collect(Collectors.toList());
    }
	
	// Get by Appointment
	@Override
	public List<DoctorResponseDTO> getDoctorsByAppointment(Long appointmentId) {
		return doctorRepository.findByAppointments_AppointmentId(appointmentId).stream().map(this::mapToDTO).collect(Collectors.toList());
	}
	
	// Get by Patient
	@Override
	public List<DoctorResponseDTO> getDoctorsByPatient(Long patientId) {
		return doctorRepository.findByMedicalRecords_Patient_PatientId(patientId).stream().map(this::mapToDTO).collect(Collectors.toList());
	}
	
	// Get by Availability
	@Override
	public List<DoctorResponseDTO> getDoctorsByAvailabilityDay(AvailabilityDays day) {
		return doctorRepository.findByAvailabilityDaysContaining(day).stream().map(this::mapToDTO).collect(Collectors.toList());
	}
}