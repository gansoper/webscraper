package com.serv.testtask.scraper.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {
	
	@Value("${processing.base.url}")
	String scraperBaseURL;
	
	@Value("${processing.service.url}")
	String scraperCategoriesURL;
	

	@Value("${processing.google.url}")
	String scraperGoogleURL;
	
	@Value("${spring.datasource.url}")
	String dsURL;
	
	@Value("${spring.datasource.driver-class-name}")
	String dsDriver;
	
	@Value("${spring.datasource.username}")
	String username;
	
	@Value("${spring.datasource.password}")
	String password;
	
	public String getScraperBaseURL() {
		return scraperBaseURL;
	}

	public void setScraperBaseURL(String scraperBaseURL) {
		this.scraperBaseURL = scraperBaseURL;
	}

	public String getDsURL() {
		return dsURL;
	}

	public void setDsURL(String dsURL) {
		this.dsURL = dsURL;
	}

	public String getDsDriver() {
		return dsDriver;
	}

	public void setDsDriver(String dsDriver) {
		this.dsDriver = dsDriver;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getScraperCategoriesURL() {
		return scraperCategoriesURL;
	}

	public void setScraperCategoriesURL(String scraperCategoriesURL) {
		this.scraperCategoriesURL = scraperCategoriesURL;
	}

	public String getScraperGoogleURL() {
		return scraperGoogleURL;
	}

	public void setScraperGoogleURL(String scraperGoogleURL) {
		this.scraperGoogleURL = scraperGoogleURL;
	}
	
	
}
