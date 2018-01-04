package com.serv.testtask.scraper.pageprocessor;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.serv.testtask.scraper.model.Item;
import com.serv.testtask.scraper.model.ItemLink;
import com.serv.testtask.scraper.service.DBOperationsService;
import com.serv.testtask.scraper.utils.StringUtils;

public class ItemLinksPageProcessor implements IPageProcessor<ItemLink>{
	
	DBOperationsService dbService;
	
	String URL;
	
	public ItemLinksPageProcessor(DBOperationsService dbService, String url) {
		this.dbService = dbService;
		this.URL = url;
	}
	
	
	@Override
	public List<ItemLink> getPageItems(String URL) {
		List<ItemLink> links = new ArrayList<>();
		HtmlPage page = this.getPage(URL);
		if(page != null){
			List<HtmlAnchor> elements = (List<HtmlAnchor>) page.getByXPath("//h3[@class='r']/a");
			int i = 0;
			for(HtmlAnchor element :elements){
				ItemLink link = new ItemLink();
				link.setLink(StringUtils.getNormalLink(element.getAttribute("href")));
				links.add(link);
				i++;
				if (i == 2) //TODO: remove this hardcode for production. 
					break;
			}
		}
		
		return links;
	}

	@Override
	public void run() {
		int itemsprocessed = 0;
		int page = 0;
		while (true){
			//check flag. 
			if (GoogleThreadStopChecker.needsToStop == true){
				GoogleThreadStopChecker.needsToStop = false;
				break;
			}
			long itemsCount = this.dbService.getItemsCount();
			if (itemsprocessed == itemsCount)
				break;
		
			Page<Item> items = this.dbService.getItemsForGoogle(page);
			
			for(Item item: items){
				
				String itemName = item.getName();
				try{
					itemName = URLEncoder.encode(itemName, "UTF-8");
				}
				catch(Exception e){
					e.printStackTrace();
				}
				
				List<ItemLink> links = this.getPageItems(this.URL + itemName);
				
				
				// get Page Items is long operation and something could happen. 
				if (GoogleThreadStopChecker.needsToStop == true){
					GoogleThreadStopChecker.needsToStop = false;
					break;
				}
				
				dbService.saveItemLinks(item.getName(), links);
				try {
					Thread.sleep(5000); // to avoid google block captcha
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} //
				
			}
			
			page++;
			itemsprocessed = itemsprocessed + items.getSize();
		}
		
	}

}
