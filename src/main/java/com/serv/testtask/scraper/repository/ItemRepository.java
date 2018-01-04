package com.serv.testtask.scraper.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.serv.testtask.scraper.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>{
	
	@Query("Select count(*) from Item")
	Long getCount();
	
	List<Item> findByName(String name);
}
