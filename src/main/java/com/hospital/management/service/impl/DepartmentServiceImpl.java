package com.hospital.management.service.impl;

import com.hospital.management.exception.DuplicateResourceException;
import com.hospital.management.exception.InvalidOperationException;
import com.hospital.management.exception.ResourceNotFoundException;
import com.hospital.management.model.Department;
import com.hospital.management.repository.DepartmentRepository;
import com.hospital.management.repository.DoctorRepository;
import com.hospital.management.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
	
	private final DepartmentRepository departmentRepository;
	private final DoctorRepository doctorRepository;
	
	public DepartmentServiceImpl(DepartmentRepository departmentRepository, DoctorRepository doctorRepository) {
		this.departmentRepository = departmentRepository;
		this.doctorRepository = doctorRepository;	
	}
	
	// Create Department
	@Override
	public Department createDepartment(Department department) {
		
		if (departmentRepository.existsByDepartmentName(department.getDepartmentName())) {
			throw new DuplicateResourceException("Department already exists with name: " + department.getDepartmentName());
        }
		
		return departmentRepository.save(department);
		
	}
	
	// Get All Departments
	@Override
	public List<Department> getAllDepartments() {
		return departmentRepository.findAll();	
	}
	
	// Get Department By ID
	@Override
	public Department getDepartmentById(Long id) {
		return departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
	}
	
	// Get Department By Name
	@Override
	public Department getDepartmentByName(String name) {
		return departmentRepository.findByDepartmentName(name).orElseThrow(() -> new ResourceNotFoundException("Department not found with name: " + name));	
	}
	
	// Update Department
	@Override
	public Department updateDepartment(Long id, Department updatedDepartment) {
		
		Department existing = getDepartmentById(id);
		
		if (!existing.getDepartmentName().equals(updatedDepartment.getDepartmentName()) && departmentRepository.existsByDepartmentName(updatedDepartment.getDepartmentName())) {
			throw new DuplicateResourceException("Department name already exists: " + updatedDepartment.getDepartmentName());	
		}
		
		existing.setDepartmentName(updatedDepartment.getDepartmentName());
		
		return departmentRepository.save(existing);	
	}
	
	// Delete Department
	@Override
	public void deleteDepartment(Long id) {
		
		Department department = getDepartmentById(id);
		
		if (!doctorRepository.findByDepartment_DepartmentId(id).isEmpty()) {
			throw new InvalidOperationException("Cannot delete department. Doctors are assigned to it.");	
		}
		
		departmentRepository.delete(department);	
	}
}