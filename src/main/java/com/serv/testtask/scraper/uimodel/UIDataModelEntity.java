package com.serv.testtask.scraper.uimodel;

import java.util.ArrayList;
import java.util.List;

public class UIDataModelEntity {
	
	String categoryName;
	
	String companyName;
	
	List<String> googleLinks = new ArrayList<>();

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public List<String> getGoogleLinks() {
		return googleLinks;
	}

	public void setGoogleLinks(List<String> googleLinks) {
		this.googleLinks = googleLinks;
	}
	
	
	
}
