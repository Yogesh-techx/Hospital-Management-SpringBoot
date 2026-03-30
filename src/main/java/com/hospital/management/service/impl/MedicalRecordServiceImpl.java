package com.hospital.management.service.impl;

import com.hospital.management.exception.InvalidOperationException;
import com.hospital.management.exception.ResourceNotFoundException;
import com.hospital.management.model.Appointment;
import com.hospital.management.model.Doctor;
import com.hospital.management.model.MedicalRecord;
import com.hospital.management.model.Patient;
import com.hospital.management.repository.AppointmentRepository;
import com.hospital.management.repository.DoctorRepository;
import com.hospital.management.repository.MedicalRecordRepository;
import com.hospital.management.repository.PatientRepository;
import com.hospital.management.service.MedicalRecordService;
import com.hospital.management.util.AppointmentStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {
	private final MedicalRecordRepository medicalRecordRepository;
	private final AppointmentRepository appointmentRepository;
	private final DoctorRepository doctorRepository;
	private final PatientRepository patientRepository;
	
	public MedicalRecordServiceImpl(MedicalRecordRepository medicalRecordRepository, AppointmentRepository appointmentRepository, DoctorRepository doctorRepository, PatientRepository patientRepository) {
		this.medicalRecordRepository = medicalRecordRepository;
		this.appointmentRepository = appointmentRepository;
		this.doctorRepository = doctorRepository;
		this.patientRepository = patientRepository;
	}
	
	// Create Medical Record
	@Override
	public MedicalRecord createRecord(MedicalRecord record) {
		
		Long appointmentId = record.getAppointment().getAppointmentId();
		
		// Validate appointment exists
		Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + appointmentId));
		
		// Appointment must be COMPLETED
		
		if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
			throw new InvalidOperationException("Medical record can only be created for COMPLETED appointments");
		}
		
		// Extract doctor and patient from appointment
		Doctor doctor = appointment.getDoctor();
		Patient patient = appointment.getPatient();
		
		// Set relationships properly
		record.setAppointment(appointment);
		record.setDoctor(doctor);
		record.setPatient(patient);
		
		// Set visiting date (optional: auto-set)
		record.setVisitingDate(LocalDate.now());
		
		return medicalRecordRepository.save(record);
    }
	
	// Get All Records
	@Override
	public List<MedicalRecord> getAllRecords() {
		return medicalRecordRepository.findAll();
	}
	
	// Get Record By ID
	@Override
	public MedicalRecord getRecordById(Long id) {
		return medicalRecordRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Medical record not found with id: " + id));
	}
	
	// Get Records By Patient
	@Override
	public List<MedicalRecord> getRecordsByPatient(Long patientId) {
		
		if (!patientRepository.existsById(patientId)) {
			throw new ResourceNotFoundException("Patient not found with id: " + patientId);
		}
		
		return medicalRecordRepository.findByPatient_PatientId(patientId);
	}
	
	// Get Records By Date
	@Override
	public List<MedicalRecord> getRecordsByDate(LocalDate date) {
		return medicalRecordRepository.findByVisitingDate(date);
	}
	
	// Get Records By Doctor
	@Override
	public List<MedicalRecord> getRecordsByDoctor(Long doctorId) {
		
		if (!doctorRepository.existsById(doctorId)) {
			throw new ResourceNotFoundException("Doctor not found with id: " + doctorId);
		}
		
		return medicalRecordRepository.findByDoctor_DoctorId(doctorId);
	}
	
	// Update Medical Record
	@Override
	public MedicalRecord updateRecord(Long id, MedicalRecord updatedRecord) {
		
		MedicalRecord existing = getRecordById(id);
		existing.setDiagnosis(updatedRecord.getDiagnosis());
		existing.setTreatment(updatedRecord.getTreatment());
		existing.setVisitingDate(updatedRecord.getVisitingDate());
		
		return medicalRecordRepository.save(existing);
	}
}