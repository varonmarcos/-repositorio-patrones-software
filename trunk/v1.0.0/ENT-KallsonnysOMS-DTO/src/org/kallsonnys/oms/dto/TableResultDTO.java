package org.kallsonnys.oms.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TableResultDTO<T>  implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	private List<T> result;
	private int totalOfRecords;
	
	public TableResultDTO() {
		setResult(new ArrayList<T>());
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public int getTotalOfRecords() {
		return totalOfRecords;
	}

	public void setTotalOfRecords(int totalOfRecords) {
		this.totalOfRecords = totalOfRecords;
	}
	
}
