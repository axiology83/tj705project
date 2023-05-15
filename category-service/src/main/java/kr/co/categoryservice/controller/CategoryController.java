package kr.co.categoryservice.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.categoryservice.dto.BoardResponse;
import kr.co.categoryservice.dto.CategoryDTO;
import kr.co.categoryservice.dto.CategoryEntity;
import kr.co.categoryservice.dto.CategoryRequest;
import kr.co.categoryservice.dto.CategoryResponse;
import kr.co.categoryservice.service.CategoryService;

@RestController
@RequestMapping("/category-service")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	// 보드쪽과 통신하는 cid , id 비교해서 카테고리 name 찾는 메서드
	@PostMapping("/category/name")
	public ResponseEntity<?> getNameByCid(@RequestBody Long id) {
		
		CategoryDTO categoryDTO = categoryService.getNameByCid(id);
		
		return ResponseEntity.status(HttpStatus.OK).body(categoryDTO);
	}
	
	// 카테고리 수정
	@PutMapping("/category/update")
	public ResponseEntity<?> update(@RequestBody CategoryResponse categoryResponse){
		
		CategoryDTO categoryDTO = categoryService.update(categoryResponse);
		
		categoryResponse = categoryDTO.toCategoryResponse();
		
		return ResponseEntity.status(HttpStatus.OK).body(categoryResponse);
		
		
		
	}
	// 카테고리 목록 가져오기
	@GetMapping("/category/all")
	public ResponseEntity<?> findAll() {
		
		List<CategoryDTO> list = categoryService.findAll();
		
		return ResponseEntity.status(HttpStatus.OK).body(list);
		
	}
	// 카테고리 자세히보기인데 굳이 필요할까 
	@GetMapping("/category/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		
		
		CategoryDTO dto =  categoryService.findById(id);		
		
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}
	// 카테고리 생성
	@PostMapping("/categorys")
	public ResponseEntity<?> createCategory(@RequestBody CategoryRequest cRequest) {
		
		CategoryDTO categoryDTO = CategoryDTO.toCategoryDTO(cRequest);
		
		categoryDTO = categoryService.createCategory(categoryDTO);
		
		CategoryResponse categoryResponse = categoryDTO.toCategoryResponse();
		
		return ResponseEntity.status(HttpStatus.CREATED).body(categoryResponse);
	}

}
