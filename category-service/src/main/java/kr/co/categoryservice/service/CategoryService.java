package kr.co.categoryservice.service;

import java.util.List;

import kr.co.categoryservice.dto.CategoryDTO;
import kr.co.categoryservice.dto.CategoryResponse;

public interface CategoryService {
	
	CategoryDTO createCategory(CategoryDTO categoryDTO);
	CategoryDTO findById(Long id);
	List<CategoryDTO> findAll();
	CategoryDTO update(CategoryResponse categoryResponse);
	CategoryDTO getNameByCid(Long cid);

}
