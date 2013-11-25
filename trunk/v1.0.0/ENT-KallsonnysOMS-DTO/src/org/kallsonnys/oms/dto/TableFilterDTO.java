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
	
	@SuppressWarnings("unchecked")
	public <T> T getVal(String key){
		Object value = filters.get(key);
		if(value == null)
			return null;
		return (T) filters.get(key);
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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((columnSorter == null) ? 0 : columnSorter.hashCode());
		result = prime * result + ((filters == null) ? 0 : filters.hashCode());
		result = prime * result + pageSize;
		result = prime * result
				+ ((sorterType == null) ? 0 : sorterType.hashCode());
		result = prime * result + start;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TableFilterDTO other = (TableFilterDTO) obj;
		if (columnSorter == null) {
			if (other.columnSorter != null)
				return false;
		} else if (!columnSorter.equals(other.columnSorter))
			return false;
		if (filters == null) {
			if (other.filters != null)
				return false;
		} else if (!filters.equals(other.filters))
			return false;
		if (pageSize != other.pageSize)
			return false;
		if (sorterType == null) {
			if (other.sorterType != null)
				return false;
		} else if (!sorterType.equals(other.sorterType))
			return false;
		if (start != other.start)
			return false;
		return true;
	}
	
	

}
