package com.hospital.management.controller;

import com.hospital.management.dto.DepartmentRequestDTO;
import com.hospital.management.dto.DepartmentResponseDTO;
import com.hospital.management.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
	
	private final DepartmentService departmentService;
	
	public DepartmentController(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	
	@PostMapping
	public DepartmentResponseDTO create(@RequestBody DepartmentRequestDTO dto) {
		return departmentService.createDepartment(dto);
	}
	
	@GetMapping
	public List<DepartmentResponseDTO> getAll() {
		return departmentService.getAllDepartments();
	}
	
	@GetMapping("/{id}")
	public DepartmentResponseDTO getById(@PathVariable Long id) {
		return departmentService.getDepartmentById(id);
	}
	
	@PutMapping("/{id}")
	public DepartmentResponseDTO update(@PathVariable Long id, @RequestBody DepartmentRequestDTO dto) {
		return departmentService.updateDepartment(id, dto);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		departmentService.deleteDepartment(id);
	}
	
	@GetMapping("/name/{name}")
	public DepartmentResponseDTO getByName(@PathVariable String name) {
		return departmentService.getDepartmentByName(name);
	}
}