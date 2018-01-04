package com.serv.testtask.scraper.pageprocessor;

import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public interface IPageProcessor<E> extends Runnable {
	
	List<E> getPageItems(String URL);
	
	public default HtmlPage getPage(String URL){
		WebClient client = new WebClient();
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);
		HtmlPage page  = null;
		try{
			page = client.getPage(URL);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		client.close();
		
		return page;
	}
	
}
