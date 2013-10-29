package org.kallsonnys.oms.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TableFilterDTO implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	public static final String ASC = "ASC";
	public static final String DESC = "DESC";
	
	
	private Map<String, Object> filters;
	private int start;
	private int pageSize;
	private String sorterType;
	private String columnSorter;
	
	
	public TableFilterDTO() {
		setFilters(new HashMap<String, Object>());
	}


	public Map<String, Object> getFilters() {
		if(filters == null)
			filters = new HashMap<String, Object>();
		return filters;
	}


	public void setFilters(Map<String, Object> filters) {
		this.filters = filters;
	}
	
	
	public String getStringVal(String key){
		Object value = filters.get(key);
		if(value == null)
			return null;
		return (String) filters.get(key);
	}
	
	public void addFilter(String key, Object value){
		filters.put(key, value);
	}


	public int getStart() {
		return start;
	}


	public void setStart(int start) {
		this.start = start;
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public String getSorterType() {
		return sorterType;
	}


	public void setSorterType(String sorterType) {
		this.sorterType = sorterType;
	}


	public String getColumnSorter() {
		return columnSorter;
	}


	public void setColumnSorter(String columnSorter) {
		this.columnSorter = columnSorter;
	}

}
