package it.lucius.customers.bean.money;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.lucius.customers.models.money.MoneyCategory;
import it.lucius.customers.models.money.MoneyDashboard;

public class MDashboardBean {

	private int idDashboard;
	private String dashboard;
	private Date dtDashboard;
	private List<MCategoryBean> mCategories;
	
	private String Mese;
	private String Anno;
	
	private String etichetta;
	
	private BigDecimal saldoPartenza;
	private BigDecimal saldoPastMonth;
	
	private List<MEntrateBean> entrateList;
	private BigDecimal entrate;
	
	private BigDecimal totaleEntrate;
	
	private BigDecimal stimaBilancio;
	private BigDecimal bilancio;
	private BigDecimal stimaSaldo;
	private BigDecimal saldo;
	
	private List<MCategoryBean> categoriesList;
	
	private BigDecimal totaleBudget;
	private BigDecimal totaleSpese;
	private BigDecimal totaleBilancio;
	
	
	public MDashboardBean() {
		super();
	}

	public MDashboardBean(MoneyDashboard d) {
		this.idDashboard = d.getIdDashboard();
	}
	
	public MDashboardBean(MoneyDashboard d, LocalDate dtDashBoard) {
		super();
		this.saldoPartenza = new BigDecimal(0.0);
		this.entrate = new BigDecimal(0.0);
		this.totaleEntrate = new BigDecimal(0.0);
		this.stimaBilancio = new BigDecimal(0.0);
		this.bilancio = new BigDecimal(0.0);
		this.stimaSaldo = new BigDecimal(0.0);
		this.saldo = new BigDecimal(0.0);
		this.totaleBudget = new BigDecimal(0.0);
		this.totaleSpese = new BigDecimal(0.0);
		this.totaleBilancio = new BigDecimal(0.0);
		this.saldoPastMonth = new BigDecimal(0.0);
		this.idDashboard = d.getIdDashboard();
		this.dashboard = d.getDashboard();
		this.dtDashboard = d.getDtDashboard();
		
		mCategories = new ArrayList<MCategoryBean>();
		for(MoneyCategory c : d.getMoneyCategories()) {
			
			MCategoryBean cBean = new MCategoryBean(c, dtDashBoard);
			
			if(cBean.getMTransactions().size() > 0)
				mCategories.add(new MCategoryBean(c, dtDashBoard));
		}
	}

	public int getIdDashboard() {
		return idDashboard;
	}

	public void setIdDashboard(int idDashboard) {
		this.idDashboard = idDashboard;
	}

	public String getDashboard() {
		return dashboard;
	}

	public BigDecimal getSaldoPastMonth() {
		return saldoPastMonth;
	}

	public void setSaldoPastMonth(BigDecimal saldoPastMonth) {
		this.saldoPastMonth = saldoPastMonth;
	}

	public void setDashboard(String dashboard) {
		this.dashboard = dashboard;
	}

	public Date getDtDashboard() {
		return dtDashboard;
	}

	public void setDtDashboard(Date dtDashboard) {
		this.dtDashboard = dtDashboard;
	}

	public List<MCategoryBean> getmCategories() {
		return mCategories;
	}

	public void setmCategories(List<MCategoryBean> mCategories) {
		this.mCategories = mCategories;
	}

	public String getMese() {
		return Mese;
	}

	public void setMese(String mese) {
		Mese = mese;
	}

	public String getAnno() {
		return Anno;
	}

	public void setAnno(String anno) {
		Anno = anno;
	}

	public BigDecimal getSaldoPartenza() {
		return saldoPartenza;
	}

	public void setSaldoPartenza(BigDecimal saldoPartenza) {
		this.saldoPartenza = saldoPartenza;
	}

	public String getEtichetta() {
		return etichetta;
	}

	public void setEtichetta(String etichetta) {
		this.etichetta = etichetta;
	}

	public List<MEntrateBean> getEntrateList() {
		return entrateList;
	}

	public void setEntrateList(List<MEntrateBean> entrateList) {
		this.entrateList = entrateList;
	}

	public BigDecimal getEntrate() {
		return entrate;
	}

	public void setEntrate(BigDecimal entrate) {
		this.entrate = entrate;
	}

	public BigDecimal getTotaleEntrate() {
		return totaleEntrate;
	}

	public void setTotaleEntrate(BigDecimal totaleEntrate) {
		this.totaleEntrate = totaleEntrate;
	}

	public BigDecimal getStimaBilancio() {
		return stimaBilancio;
	}

	public void setStimaBilancio(BigDecimal stimaBilancio) {
		this.stimaBilancio = stimaBilancio;
	}

	public BigDecimal getBilancio() {
		return bilancio;
	}

	public void setBilancio(BigDecimal bilancio) {
		this.bilancio = bilancio;
	}

	public BigDecimal getStimaSaldo() {
		return stimaSaldo;
	}

	public void setStimaSaldo(BigDecimal stimaSaldo) {
		this.stimaSaldo = stimaSaldo;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public List<MCategoryBean> getCategoriesList() {
		return categoriesList;
	}

	public void setCategoriesList(List<MCategoryBean> categoriesList) {
		this.categoriesList = categoriesList;
	}


	public BigDecimal getTotaleBudget() {
		return totaleBudget;
	}

	public void setTotaleBudget(BigDecimal totaleBudget) {
		this.totaleBudget = totaleBudget;
	}

	public BigDecimal getTotaleSpese() {
		return totaleSpese;
	}

	public void setTotaleSpese(BigDecimal totaleSpese) {
		this.totaleSpese = totaleSpese;
	}

	public BigDecimal getTotaleBilancio() {
		return totaleBilancio;
	}

	public void setTotaleBilancio(BigDecimal totaleBilancio) {
		this.totaleBilancio = totaleBilancio;
	}
	
}
