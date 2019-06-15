package it.lucius.customers.bean.charts;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ChartsDatasetBigdecimal {

	private List<BigDecimal> data;
	private String label;
	
	public ChartsDatasetBigdecimal() {
		
		super();
		this.data = new ArrayList<BigDecimal>();
	}
	
	public ChartsDatasetBigdecimal(List<BigDecimal> data, String label) {
		super();
		this.data = data;
		this.label = label;
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