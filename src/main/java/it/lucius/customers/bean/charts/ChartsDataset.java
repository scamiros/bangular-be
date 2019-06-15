package it.lucius.customers.bean.charts;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ChartsDataset {

	private List<BigDecimal> data;
	private List<Long> dataLong;
	private String label;
	
	public ChartsDataset() {
		
		super();
		this.data = new ArrayList<BigDecimal>();
	}
	
	public ChartsDataset(List<BigDecimal> data, String label) {
		super();
		this.data = data;
		this.label = label;
	}

	public List<Long> getDataLong() {
		return dataLong;
	}

	public void setDataLong(List<Long> dataLong) {
		this.dataLong = dataLong;
	}

	public List<BigDecimal> getData() {
		return data;
	}

	public void setData(List<BigDecimal> data) {
		this.data = data;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
}
