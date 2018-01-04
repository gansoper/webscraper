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
	
	
	@Transactional
	public void TestMethod(){
		
		categoryRepo.deleteAll();
		itemRepo.deleteAll();
		itemLinkRepo.deleteAll();
		
		Category cat1 = new Category();
		cat1.setName("TestCat1");
		cat1.setLink("catLink1");
		
		
		
		
		Item item1 = new Item();
		item1.setName("item1");
		item1.setCategory(cat1);
		
		Item item2 = new Item();
		item2.setName("item2");
		item2.setCategory(cat1);
		
		
		ItemLink itemlink1 = new ItemLink();
		ItemLink itemlink2 = new ItemLink();
		
		
		itemlink1.setLink("link1");
		itemlink1.setItem(item1);
		itemlink2.setLink("link2");
		itemlink2.setItem(item2);
		
		item1.getItemLinks().add(itemlink1);
		item2.getItemLinks().add(itemlink2);
		
		cat1.getItems().add(item1);
		cat1.getItems().add(item2);
		
		categoryRepo.save(cat1);
		
	}
	
}
