package it.lucius.customers.bean.charts;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ChartsData {

	private List<BigDecimal> data;
	private List<Long> dataLong;
	private List<String> labels;
	
	public ChartsData() {
		
		super();
		this.data = new ArrayList<BigDecimal>();
		this.labels = new  ArrayList<String>();
	}

	public List<BigDecimal> getData() {
		return data;
	}

	public List<Long> getDataLong() {
		return dataLong;
	}

	public void setDataLong(List<Long> dataLong) {
		this.dataLong = dataLong;
	}

	public void setData(List<BigDecimal> data) {
		this.data = data;
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}
	
}
