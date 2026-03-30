package com.hospital.management.model;

import java.util.List;
import java.util.Set;

import com.hospital.management.util.AvailabilityDays;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "doctor")
public class Doctor {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doctorId;
    
    @Column(name = "doctor_name", nullable = false)
    private String doctorName;
    
    @Column(nullable = false)
    private String specialization;
    
    @ElementCollection(targetClass = AvailabilityDays.class)
    @CollectionTable(
    		name = "doctor_availability",
    		joinColumns = @JoinColumn(name = "doctor_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "available_day")
    private Set<AvailabilityDays> availabilityDays;
    
    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;
    
    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;
    
    @OneToMany(mappedBy = "doctor")
    private List<MedicalRecord> medicalRecords;
    
    public Doctor() {
    }
    
    public Doctor(Long doctorId, String doctorName, String specialization, Set<AvailabilityDays> availabilityDays, Department department) {
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.specialization = specialization;
        this.availabilityDays = availabilityDays;
        this.department = department;
    }
    
    public Long getDoctorId() {
    	return doctorId;
    }
    
    public void setDoctorId(Long doctorId) {
    	this.doctorId = doctorId;
    }
    
    public String getDoctorName() {
    	return doctorName;
    }
    
    public void setDoctorName(String doctorName) {
    	this.doctorName = doctorName;
    }
    
    public String getSpecialization() {
    	return specialization;
    }
    
    public void setSpecialization(String specialization) {
    	this.specialization = specialization;
    }
    
    public Set<AvailabilityDays> getAvailabilityDays() {
    	return availabilityDays;
    }
    
    public void setAvailabilityDays(Set<AvailabilityDays> availabilityDays) {
    	this.availabilityDays = availabilityDays;
    }
    
    public Department getDepartment() {
    	return department;
    }
    
    public void setDepartment(Department department) {
    	this.department = department;
    }
}