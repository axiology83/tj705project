package kr.co.categoryservice.dto;

import java.io.Serializable;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoryDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String name;
	
	public static CategoryDTO toCategoryDTO(CategoryRequest categoryRequest) {
		
		return CategoryDTO.builder()
				.name(categoryRequest.getName())
				.build();
	}
	
	public CategoryResponse toCategoryResponse() {
		return CategoryResponse.builder()
				.id(id)
				.name(name)
				.build();
	}
	
	public CategoryEntity toCategoryEntity() {
		return CategoryEntity.builder()
				.id(id)
				.name(name)
				.build();
		}
	public  CategoryDTO toCategoryDTO2(CategoryEntity entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		
		return this;
	} 
	
	
public static CategoryDTO toCategoryDTO(CategoryEntity categoryEntity) {
		
		return CategoryDTO.builder()
				.id(categoryEntity.getId())
				.name(categoryEntity.getName())
				.build();
	}


}
