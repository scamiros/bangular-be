package it.lucius.customers.bean.charts;

import java.util.ArrayList;
import java.util.List;

public class ChartsDatasetLong {

	private List<Long> data;
	private String label;
	
	public ChartsDatasetLong() {
		
		super();
		this.data = new ArrayList<Long>();
	}
	
	public ChartsDatasetLong(List<Long> data, String label) {
		super();
		this.data = data;
		this.label = label;
	}

	public List<Long> getData() {
		return data;
	}

	public void setData(List<Long> data) {
		this.data = data;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
}