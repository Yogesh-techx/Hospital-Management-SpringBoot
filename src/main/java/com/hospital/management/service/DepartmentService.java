package com.hospital.management.service;

import com.hospital.management.dto.DepartmentRequestDTO;
import com.hospital.management.dto.DepartmentResponseDTO;
import java.util.List;

public interface DepartmentService {
	
	DepartmentResponseDTO createDepartment(DepartmentRequestDTO dto);
	
	List<DepartmentResponseDTO> getAllDepartments();
	
	DepartmentResponseDTO getDepartmentById(Long id);
	
	DepartmentResponseDTO updateDepartment(Long id, DepartmentRequestDTO dto);
	
	void deleteDepartment(Long id);
	
	DepartmentResponseDTO getDepartmentByName(String name);
}