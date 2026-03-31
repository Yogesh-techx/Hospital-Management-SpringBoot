package com.hospital.management.service.impl;

import com.hospital.management.dto.DepartmentRequestDTO;
import com.hospital.management.dto.DepartmentResponseDTO;
import com.hospital.management.exception.DuplicateResourceException;
import com.hospital.management.exception.InvalidOperationException;
import com.hospital.management.exception.ResourceNotFoundException;
import com.hospital.management.model.Department;
import com.hospital.management.repository.DepartmentRepository;
import com.hospital.management.repository.DoctorRepository;
import com.hospital.management.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {
	
	private final DepartmentRepository departmentRepository;
	private final DoctorRepository doctorRepository;
	
	public DepartmentServiceImpl(DepartmentRepository departmentRepository, DoctorRepository doctorRepository) {
		this.departmentRepository = departmentRepository;
		this.doctorRepository = doctorRepository;
	}
	
	// DTO to Entity
	private Department mapToEntity(DepartmentRequestDTO dto) {
		Department dept = new Department();
		dept.setDepartmentName(dto.getDepartmentName());
		return dept;
	}
	
	// Entity to DTO
	private DepartmentResponseDTO mapToDTO(Department dept) {
		DepartmentResponseDTO dto = new DepartmentResponseDTO();
		dto.setDepartmentId(dept.getDepartmentId());
		dto.setDepartmentName(dept.getDepartmentName());
		return dto;
	}
	
	// Create
	@Override
	public DepartmentResponseDTO createDepartment(DepartmentRequestDTO dto) {
		
		if (departmentRepository.existsByDepartmentName(dto.getDepartmentName())) {
			throw new DuplicateResourceException("Department already exists with name: " + dto.getDepartmentName());
		}
		
		Department saved = departmentRepository.save(mapToEntity(dto));
		return mapToDTO(saved);
	}
	
	// Get All
	@Override
	public List<DepartmentResponseDTO> getAllDepartments() {
		return departmentRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}
	
	// Get By ID
	@Override
	public DepartmentResponseDTO getDepartmentById(Long id) {
		Department dept = departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
		return mapToDTO(dept);
	}
	
	// Get By Name
	@Override
	public DepartmentResponseDTO getDepartmentByName(String name) {
		Department dept = departmentRepository.findByDepartmentName(name).orElseThrow(() -> new ResourceNotFoundException("Department not found with name: " + name));
		return mapToDTO(dept);
	}
	
	// Update
	@Override
	public DepartmentResponseDTO updateDepartment(Long id, DepartmentRequestDTO dto) {
		
		Department existing = departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
		
		if (!existing.getDepartmentName().equals(dto.getDepartmentName()) && departmentRepository.existsByDepartmentName(dto.getDepartmentName())) {
			throw new DuplicateResourceException("Department name already exists: " + dto.getDepartmentName());
		}
		
		existing.setDepartmentName(dto.getDepartmentName());
		
		return mapToDTO(departmentRepository.save(existing));
	}
	
	// Delete
	@Override
	public void deleteDepartment(Long id) {
		
		Department dept = departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
		
		if (!doctorRepository.findByDepartment_DepartmentId(id).isEmpty()) {
			throw new InvalidOperationException("Cannot delete department. Doctors are assigned.");
		}
		
		departmentRepository.delete(dept);
	}
}