package it.lucius.customers.bean.money;

public class MoneyDashboardNew {

	private Integer idDashboard;
	private MoneyMonthsBean copyMonth;
	
	
	public MoneyDashboardNew() {
		super();
	}
	
	public Integer getIdDashboard() {
		return idDashboard;
	}

	public void setIdDashboard(Integer idDashboard) {
		this.idDashboard = idDashboard;
	}


	public MoneyMonthsBean getCopyMonth() {
		return copyMonth;
	}
	public void setCopyMonth(MoneyMonthsBean copyMonth) {
		this.copyMonth = copyMonth;
	}
	
	
}
