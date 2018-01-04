package com.serv.testtask.scraper.pageprocessor;

import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.serv.testtask.scraper.model.Category;
import com.serv.testtask.scraper.model.Item;
import com.serv.testtask.scraper.service.DBOperationsService;

public class ItemsPageProcessor implements IPageProcessor<Item>{

	Category category;
	
	String URL;
	
	DBOperationsService dbService;
	
	public  ItemsPageProcessor(Category cat, String url, DBOperationsService dbService) {
		this.category = cat;
		this.URL = url;
		this.dbService = dbService;
	}
	
	@Override
	public List<Item> getPageItems(String URL) {
		List<Item> items = new ArrayList<>();
		HtmlPage page = this.getPage(URL);
		if(page != null){
			List<HtmlAnchor> elements = (List<HtmlAnchor>) page.getByXPath("//div[@class='company-name']/a");
			for(HtmlAnchor element :elements){
				Item item = new Item();
				item.setName(element.getTextContent());
				if (this.category !=null)
					item.setCategory(this.category);
				items.add(item);
			}
		}
		
		return items;
	}

	@Override
	public void run() {
		List<Item> items = this.getPageItems(this.URL);
		this.category.getItems().addAll(items);
		synchronized (this.dbService) {
			this.dbService.saveCategory(this.category);
		}
	}

	
	
	
	
}
