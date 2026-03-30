package com.hospital.management.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(
    name = "department",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "department_name")
    }
)
public class Department {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long departmentId;
	
	@Column(name = "department_name", nullable = false)
	private String departmentName;
	
	@OneToMany(mappedBy = "department")
	private List<Doctor> doctors;
	
	public Department() {
    }
	
	public Department(Long departmentId, String departmentName) {
		this.departmentId = departmentId;
		this.departmentName = departmentName;
	}
	
    public Long getDepartmentId() {
    	return departmentId;
    }
    
    public void setDepartmentId(Long departmentId) {
    	this.departmentId = departmentId;
    }
    
    public String getDepartmentName() {
    	return departmentName;
    }
    
    public void setDepartmentName(String departmentName) {
    	this.departmentName = departmentName;
    }
    
    public List<Doctor> getDoctors() {
    	return doctors;
    }
    
    public void setDoctors(List<Doctor> doctors) {
    	this.doctors = doctors;
    }
}