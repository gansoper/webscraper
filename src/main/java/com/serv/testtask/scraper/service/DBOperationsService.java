package com.serv.testtask.scraper.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.serv.testtask.scraper.model.Category;
import com.serv.testtask.scraper.model.Item;
import com.serv.testtask.scraper.model.ItemLink;
import com.serv.testtask.scraper.repository.CategoryRepository;
import com.serv.testtask.scraper.repository.ItemLinkRepository;
import com.serv.testtask.scraper.repository.ItemRepository;
import com.serv.testtask.scraper.uimodel.UIDataModelEntity;

@Service
public class DBOperationsService {
	
	@Autowired
	CategoryRepository categoryRepo;
	
	@Autowired
	ItemRepository itemRepo;
	
	@Autowired
	ItemLinkRepository itemLinkRepo;
	
	
	public static final int PAGE_OFFSET = 10;
	
	/**
	 * Method for creation of the UI Model Entities of DB data. 
	 * @param startItem  - page start searching from 
	 * @return List<UIDataModelEntity>
	 */
	@Transactional
	public List<UIDataModelEntity>getLinks(int startItem){
		PageRequest page = new PageRequest(startItem, PAGE_OFFSET);
		Page<Item> items = itemRepo.findAll(page);
		List<UIDataModelEntity> uiEntitiesList = new ArrayList<>();
		for(Item item : items){
			item.getItemLinks().size();
			Set<ItemLink> links = item.getItemLinks();
			Category category = item.getCategory();
			UIDataModelEntity modelEntity = new UIDataModelEntity();
			modelEntity.setCategoryName(category.getName());
			modelEntity.setCompanyName(item.getName());
			links.stream().forEach(e->{modelEntity.getGoogleLinks().add(e.getLink());});
			uiEntitiesList.add(modelEntity);
		}
		
		return uiEntitiesList;
		
	}
	
	@Transactional
	public Page<Item> getItemsForGoogle(int page){
		Page<Item> items = itemRepo.findAll(new PageRequest(page, PAGE_OFFSET));
		return items;
	}
	
	@Transactional
	public long getItemsCount(){
		return itemRepo.getCount();
	}
	
	
	@Transactional
	public void deleteAll(){
		this.categoryRepo.deleteAll();
		this.itemRepo.deleteAll();
		this.itemLinkRepo.deleteAll();
	}
	
	
	@Transactional
	public boolean saveCategory(Category cat){
		this.categoryRepo.save(cat);
		return true;
	}
	
	@Transactional
	public boolean saveItem(Item item){
		this.itemRepo.save(item);
		return true;
	}
	
	@Transactional
	public boolean saveItemLinks(String name, List<ItemLink> links){
		Item itemtosave = this.itemRepo.findByName(name).get(0);
		
		for(ItemLink link: links){
			link.setItem(itemtosave);
		}
		itemtosave.getItemLinks().addAll(links);
		this.itemRepo.save(itemtosave);
		return true;
	}
	
	
}
