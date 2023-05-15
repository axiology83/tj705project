package kr.co.categoryservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.categoryservice.dto.CategoryDTO;
import kr.co.categoryservice.dto.CategoryEntity;
import kr.co.categoryservice.dto.CategoryResponse;
import kr.co.categoryservice.jpa.CategoryRepository;


@Service

public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	
	@Transactional
	public CategoryDTO createCategory(CategoryDTO categoryDTO) {
		
		CategoryEntity categoryEntity = categoryDTO.toCategoryEntity();
		
		categoryEntity = categoryRepository.save(categoryEntity);
		
		
		
		return categoryDTO.toCategoryDTO2(categoryEntity);
	}
	@Transactional
	public CategoryDTO findById(Long id) {
		Optional<CategoryEntity> optional = categoryRepository.findById(id);
		
		if(!optional.isPresent()) {
			throw new RuntimeException(" ID를 입력해주세요 ");
		}
		
		CategoryEntity entity = optional.get();
		
		return CategoryDTO.toCategoryDTO(entity);
	}
	@Transactional
	public List<CategoryDTO> findAll() {
		
		List<CategoryEntity> list = categoryRepository.findAll();
		List<CategoryDTO> dtoList = new ArrayList<>();
		
		for(CategoryEntity entity : list) {
			dtoList.add(CategoryDTO.toCategoryDTO(entity));
		}
		
		return dtoList;
	}
	@Transactional
	public CategoryDTO update(CategoryResponse categoryResponse) {
		Optional<CategoryEntity> optional = categoryRepository.findById(categoryResponse.getId());
		
		if(!optional.isPresent()) {
			throw new RuntimeException("없는 ID입니다.");
		}
		
		CategoryEntity categoryEntity = optional.get();
		
		categoryEntity.setName(categoryResponse.getName());
		categoryEntity = categoryRepository.save(categoryEntity);
		
		return CategoryDTO.toCategoryDTO(categoryEntity);
	}
	
	@Transactional
	public CategoryDTO getNameByCid(Long cid) {
		 
		Optional<CategoryEntity> optional = categoryRepository.findById(cid);
		
		
		CategoryEntity entity = optional.get();
	
		
		return CategoryDTO.toCategoryDTO(entity);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
