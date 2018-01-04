package com.serv.testtask.scraper.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DBSource {
	
	@Autowired
	AppConfig config;
	
	@Bean
	public DataSource dataSource(){
		DataSourceBuilder dsBuilder = DataSourceBuilder.create();
		dsBuilder.driverClassName(config.getDsDriver());
		dsBuilder.url(config.getDsURL());
		dsBuilder.username(config.getUsername());
		dsBuilder.password(config.getPassword());
		
		return dsBuilder.build();
	}
	
}
