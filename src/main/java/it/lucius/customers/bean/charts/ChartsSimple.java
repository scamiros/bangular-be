package it.lucius.customers.bean.charts;

public class ChartsSimple implements Comparable<ChartsSimple> {

	private String label;
	private long value;
	
	public ChartsSimple(String label, long value) {
		super();
		this.label = label;
		this.value = value;
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public long getValue() {
		return value;
	}
	public void setValue(long value) {
		this.value = value;
	}

	@Override
	public int compareTo(ChartsSimple o) {
		
		return (int)(o.getValue() - this.value);
	}

}
