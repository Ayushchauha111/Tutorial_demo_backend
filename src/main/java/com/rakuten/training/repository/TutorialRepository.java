package com.rakuten.training.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rakuten.training.model.Tutorial;
public interface TutorialRepository extends JpaRepository<Tutorial , Long>{

	Tutorial findByTitle(String t);

	List<Tutorial> findByTitleContaining(String title);
	
}
