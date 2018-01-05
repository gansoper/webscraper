package com.serv.testtask.scraper.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.serv.testtask.scraper.pageprocessor.GoogleThreadStopChecker;
import com.serv.testtask.scraper.service.ScraperService;
import com.serv.testtask.scraper.uimodel.UIData;

@Controller
public class MainController {
	
	@Autowired
	ScraperService scraper;
	
	
	@RequestMapping(value="/scrape", method = RequestMethod.GET)
	@ResponseBody
	public UIData testData(){
		this.scraper.scrapePage();
		UIData data  = this.scraper.getDataforUI(0);
		GoogleThreadStopChecker.needsToStop = false;
		this.scraper.googleLinksUpdate();
		return data;
	}
	
	@RequestMapping(value={"/home","/"}, method = RequestMethod.GET)
	public String home(){
		return "index";
	}
	
	@RequestMapping(value="/getData", method=RequestMethod.GET)
	@ResponseBody
	public UIData getData(@RequestParam int page){
		UIData data  = this.scraper.getDataforUI(page);
		return data;
	}
	
}
