package com.hospital.management.model;

import com.hospital.management.util.AvailabilityDays;
import jakarta.persistence.*;
import java.util.Set;

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
    
    public Doctor() {
    }
    
    public Doctor(Long doctorId, String doctorName, String specialization,
                  Set<AvailabilityDays> availabilityDays, Department department) {
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