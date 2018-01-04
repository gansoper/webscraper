package com.serv.testtask.scraper.uimodel;

import java.util.ArrayList;
import java.util.List;

public class UIData {
	List<UIDataModelEntity> data = new ArrayList<>();
	long totalCount = 0;
	
	public List<UIDataModelEntity> getData() {
		return data;
	}
	public void setData(List<UIDataModelEntity> data) {
		this.data = data;
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	
	
}
