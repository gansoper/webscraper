package com.serv.testtask.scraper.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.serv.testtask.scraper.config.AppConfig;
import com.serv.testtask.scraper.model.Category;
import com.serv.testtask.scraper.model.Item;
import com.serv.testtask.scraper.model.ItemLink;
import com.serv.testtask.scraper.pageprocessor.CategoryPageProcessor;
import com.serv.testtask.scraper.pageprocessor.GoogleThreadStopChecker;
import com.serv.testtask.scraper.pageprocessor.IPageProcessor;
import com.serv.testtask.scraper.pageprocessor.ItemLinksPageProcessor;
import com.serv.testtask.scraper.pageprocessor.ItemsPageProcessor;
import com.serv.testtask.scraper.uimodel.UIData;
import com.serv.testtask.scraper.uimodel.UIDataModelEntity;

@Service
public class ScraperService {
	
	@Autowired
	AppConfig config;
	
	@Autowired
	DBOperationsService dbService;
	
	public boolean scrapePage(){
		// for simplicity every time when page is being scrapped all data in repo are deleted. 
		GoogleThreadStopChecker.needsToStop = true;
		dbService.deleteAll();

		IPageProcessor<Category> categoryProcessor = new CategoryPageProcessor();
		List<Category> categories = categoryProcessor.getPageItems(config.getScraperBaseURL() + config.getScraperCategoriesURL());
		
		for(Category cat: categories){
			IPageProcessor<Item>  itemsProcessor = new ItemsPageProcessor(cat, config.getScraperBaseURL() + cat.getLink(), dbService );
			Thread thread = new Thread(itemsProcessor);
			thread.start();
			try {
				thread.join(); // this needs in order to have not locked DB when read. 
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
		
		return true;
	}
	
	public void googleLinksUpdate(){
		IPageProcessor<ItemLink> linksPageProcessor = new ItemLinksPageProcessor(this.dbService, config.getScraperGoogleURL());
		Thread thread = new Thread(linksPageProcessor);
		thread.start();
		
	}
	
	
	public UIData getDataforUI(int page){
		List<UIDataModelEntity> entities = dbService.getLinks(page);
		long items = dbService.getItemsCount();
		UIData data = new UIData();
		data.setData(entities);
		data.setTotalCount(items);
		return data;
	}
	
	
	
	
}
