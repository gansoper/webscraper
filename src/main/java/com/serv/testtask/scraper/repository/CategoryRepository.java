package com.serv.testtask.scraper.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.serv.testtask.scraper.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
	
	List<Category> findByLink(String link);
	
}
