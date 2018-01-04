package com.serv.testtask.scraper.pageprocessor;

import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.serv.testtask.scraper.model.Category;

public class CategoryPageProcessor implements IPageProcessor<Category>{

	
	@Override
	public List<Category> getPageItems(String URL) {
		List<Category> categories = new ArrayList<>();
		HtmlPage page = this.getPage(URL);
		if(page != null){
			List<HtmlAnchor> elements = (List<HtmlAnchor>) page.getByXPath("//a[@class='service_list_title']");
			for(HtmlAnchor element :elements){
				Category cat  = new Category();
				cat.setName(element.getTextContent());
				cat.setLink(element.getAttribute("href"));
				categories.add(cat);
				
			}
		}
		
		return categories;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
