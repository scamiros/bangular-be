package it.lucius.customers.bean.charts;

import java.util.List;

public class ChartsDatasLong {

	private List<ChartsDatasetLong> dataset;
	private List<String> labels;
	
	public ChartsDatasLong() {
		super();
	}

	public ChartsDatasLong(List<ChartsDatasetLong> dataset, List<String> labels) {
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

	public List<ChartsDatasetLong> getDataset() {
		return dataset;
	}

	public void setDataset(List<ChartsDatasetLong> dataset) {
		this.dataset = dataset;
	}
}
