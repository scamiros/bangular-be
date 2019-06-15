package it.lucius.customers.bean.money;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import it.lucius.customers.models.money.MoneyCategory;
import it.lucius.customers.models.money.MoneyTransaction;

public class MCategoryBean {

	private int idCategory;

	private String category;

	private int idDashboard;
	
	private String tipo;
	
	private int ordine;
	
	private String dtDashboard;
	
	private BigDecimal budget;
	
	private BigDecimal spesa;
	
	private BigDecimal bilancio;
	
	private List<MTransactionBean> MTransactions;

	
	public MCategoryBean() {
		super();
	}

	public MCategoryBean(MoneyCategory c, LocalDate currentDate) {
		super();

		this.idCategory = c.getIdCategory();
		this.category = c.getMoneyDictionaryCategory().getLabel();
		this.idDashboard = c.getMoneyDashboard().getIdDashboard();
		this.tipo = c.getMoneyDictionaryCategory().getType().getMoneyCategoryType();
		this.ordine = c.getOrdine();
		this.budget = c.getBudget();
		this.spesa = new BigDecimal(0.0);
		
		MTransactions = new ArrayList<MTransactionBean>();
		
		Collections.sort(c.getMoneyTransactions(), MoneyTransaction.DtComparator);
		
		for(MoneyTransaction t : c.getMoneyTransactions()) {
			
			Long ci = t.getDtTransaction().getTime();
			
			LocalDate currentT = Instant.ofEpochMilli(ci).atZone(ZoneId.systemDefault()).toLocalDate();
			
			YearMonth y = YearMonth.from(currentDate);
			YearMonth yT = YearMonth.from(currentT);
			
			if(y.equals(yT)) {
				
				MTransactions.add(new MTransactionBean(t));
				this.spesa = this.spesa.add(t.getAmount());
			}
		}
		
		this.bilancio = this.budget.subtract(this.spesa);
	}

	public int getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
	}

	public String getCategory() {
		return category;
	}

	public BigDecimal getSpesa() {
		return spesa;
	}

	public void setSpesa(BigDecimal spesa) {
		this.spesa = spesa;
	}

	public BigDecimal getBilancio() {
		return bilancio;
	}

	public void setBilancio(BigDecimal bilancio) {
		this.bilancio = bilancio;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getIdDashboard() {
		return idDashboard;
	}

	public void setIdDashboard(int idDashboard) {
		this.idDashboard = idDashboard;
	}

	public List<MTransactionBean> getMTransactions() {
		return MTransactions;
	}

	public void setMTransactions(List<MTransactionBean> mTransactions) {
		MTransactions = mTransactions;
	}

	public BigDecimal getBudget() {
		return budget;
	}

	public void setBudget(BigDecimal budget) {
		this.budget = budget;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getOrdine() {
		return ordine;
	}

	public String getDtDashboard() {
		return dtDashboard;
	}

	public void setDtDashboard(String dtDashboard) {
		this.dtDashboard = dtDashboard;
	}

	public void setOrdine(int ordine) {
		this.ordine = ordine;
	}
	
	
}
