package com.serv.testtask.scraper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.serv.testtask.scraper.model.ItemLink;

@Repository
public interface ItemLinkRepository extends JpaRepository<ItemLink, Long>{
	
	
	
}
