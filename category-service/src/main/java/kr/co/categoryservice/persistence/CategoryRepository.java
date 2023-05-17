package kr.co.categoryservice.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.categoryservice.dto.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>{

}
