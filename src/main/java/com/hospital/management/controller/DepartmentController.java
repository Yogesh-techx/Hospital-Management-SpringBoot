package com.hospital.management.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.management.dto.ApiResponse;
import com.hospital.management.dto.DepartmentRequestDTO;
import com.hospital.management.dto.DepartmentResponseDTO;
import com.hospital.management.service.DepartmentService;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
	
	private final DepartmentService departmentService;
	
	public DepartmentController(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse<DepartmentResponseDTO>> create(@RequestBody DepartmentRequestDTO dto) {
		
		DepartmentResponseDTO response = departmentService.createDepartment(dto);
		
		return new ResponseEntity<>(new ApiResponse<>(HttpStatus.CREATED, response), HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<DepartmentResponseDTO>>> getAll() {
		
		List<DepartmentResponseDTO> list = departmentService.getAllDepartments();
		
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, list));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<DepartmentResponseDTO>> getById(@PathVariable Long id) {
		
		DepartmentResponseDTO dto = departmentService.getDepartmentById(id);
		
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, dto));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<DepartmentResponseDTO>> update(@PathVariable Long id, @RequestBody DepartmentRequestDTO dto) {
		
		DepartmentResponseDTO updated = departmentService.updateDepartment(id, dto);
		
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, updated));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
		
		departmentService.deleteDepartment(id);
		
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "Department deleted successfully"));
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<ApiResponse<DepartmentResponseDTO>> getByName(@PathVariable String name) {
		
		DepartmentResponseDTO dto = departmentService.getDepartmentByName(name);
		
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, dto));
	}
}