package com.hospital.management.service;

import com.hospital.management.model.MedicalRecord;

import java.time.LocalDate;
import java.util.List;

public interface MedicalRecordService {
	
	MedicalRecord createRecord(MedicalRecord record);
	
	List<MedicalRecord> getAllRecords();
	
	MedicalRecord getRecordById(Long id);
	
	List<MedicalRecord> getRecordsByPatient(Long patientId);
	
	List<MedicalRecord> getRecordsByDate(LocalDate date);
	
	List<MedicalRecord> getRecordsByDoctor(Long doctorId);
	
	MedicalRecord updateRecord(Long id, MedicalRecord record);
}