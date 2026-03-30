package com.hospital.management.service.impl;

import com.hospital.management.exception.ResourceNotFoundException;
import com.hospital.management.model.Department;
import com.hospital.management.model.Doctor;
import com.hospital.management.repository.DepartmentRepository;
import com.hospital.management.repository.DoctorRepository;
import com.hospital.management.service.DoctorService;
import com.hospital.management.util.AvailabilityDays;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {
	
	private final DoctorRepository doctorRepository;
	private final DepartmentRepository departmentRepository;
	
	public DoctorServiceImpl(DoctorRepository doctorRepository, DepartmentRepository departmentRepository) {
		this.doctorRepository = doctorRepository;
		this.departmentRepository = departmentRepository;	
	}
	
	// Save Doctor
	@Override
	public Doctor saveDoctor(Doctor doctor) {
		
		Long departmentId = doctor.getDepartment().getDepartmentId();
		
		Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + departmentId));
		
		doctor.setDepartment(department);
		
		return doctorRepository.save(doctor);
	}
	
	// Save All Doctors
	@Override
	public List<Doctor> saveAllDoctors(List<Doctor> doctors) {
		
		for (Doctor doctor : doctors) {
			Long departmentId = doctor.getDepartment().getDepartmentId();
			
			Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + departmentId));
			
			doctor.setDepartment(department);
		}
		
		return doctorRepository.saveAll(doctors);
	}
	
	// Get All Doctors
	@Override
	public List<Doctor> getAllDoctors() {
		return doctorRepository.findAll();
	}
	
	// Get Doctor By ID
	@Override
	public Doctor getDoctorById(Long id) {
		return doctorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));
	}
	
	// Delete Doctor
	@Override
	public void deleteDoctor(Long id) {
		Doctor doctor = getDoctorById(id);
		doctorRepository.delete(doctor);
	}
	
	// Get Doctors by Department
	@Override
	public List<Doctor> getDoctorsByDepartment(Long departmentId) {
		if (!departmentRepository.existsById(departmentId)) {
			throw new ResourceNotFoundException("Department not found with id: " + departmentId);
		}
		
		return doctorRepository.findByDepartment_DepartmentId(departmentId);
	}

    // Get Doctors by Specialization
	@Override
	public List<Doctor> getDoctorsBySpecialization(String specialization) {
		return doctorRepository.findBySpecialization(specialization);
	}
	
	// Get Doctors by Appointment
	@Override
	public List<Doctor> getDoctorsByAppointment(Long appointmentId) {
		return doctorRepository.findByAppointments_AppointmentId(appointmentId);
	}
	
	// Get Doctors by Patient
	@Override
	public List<Doctor> getDoctorsByPatient(Long patientId) {
		return doctorRepository.findByMedicalRecords_Patient_PatientId(patientId);
	}
	
	// Get Doctors by Availability Day
	@Override
	public List<Doctor> getDoctorsByAvailabilityDay(AvailabilityDays day) {
		return doctorRepository.findByAvailabilityDaysContaining(day);
	}
}