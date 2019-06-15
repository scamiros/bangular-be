package it.lucius.customers.bean.instagram;

import java.util.Date;

public class InstaPodChartBean {

	private Integer idPodGroup;
	private String metric;
	private String tipo;
	private Date dtFrom;
	private Date dtTo;
	
	public InstaPodChartBean() {
		super();
	}
	
	public Integer getIdPodGroup() {
		return idPodGroup;
	}
	public void setIdPodGroup(Integer idPodGroup) {
		this.idPodGroup = idPodGroup;
	}
	public String getMetric() {
		return metric;
	}
	public void setMetric(String metric) {
		this.metric = metric;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Date getDtFrom() {
		return dtFrom;
	}
	public void setDtFrom(Date dtFrom) {
		this.dtFrom = dtFrom;
	}
	public Date getDtTo() {
		return dtTo;
	}
	public void setDtTo(Date dtTo) {
		this.dtTo = dtTo;
	}
	
}
