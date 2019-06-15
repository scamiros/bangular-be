package it.lucius.customers.bean.charts;

import java.util.List;

public class ChartsDatasBigdecimal {

	private List<ChartsDatasetBigdecimal> dataset;
	private List<String> labels;
	
	public ChartsDatasBigdecimal() {
		super();
	}

	public ChartsDatasBigdecimal(List<ChartsDatasetBigdecimal> dataset, List<String> labels) {
		super();
		this.dataset = dataset;
		this.labels = labels;
	}
	
	public List<String> getLabels() {
		return labels;
	}
	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public List<ChartsDatasetBigdecimal> getDataset() {
		return dataset;
	}

	public void setDataset(List<ChartsDatasetBigdecimal> dataset) {
		this.dataset = dataset;
	}
}
