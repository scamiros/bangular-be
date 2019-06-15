package it.lucius.customers.bean.money;

import java.math.BigDecimal;
import java.util.Date;

import it.lucius.customers.models.money.MoneyTransaction;

public class MTransactionBean {

	private int idTransaction;
	private Date dtTransaction;
	private BigDecimal amount;
	private String transaction;
	private int idCategory;
	private MCategoryBean mCategory;
	private String category;
	
	public MTransactionBean() {
		super();
	}

	public MTransactionBean(MoneyTransaction t) {
		super();
		
		this.idTransaction = t.getIdTransaction();
		this.transaction = t.getTransaction();
		this.amount = t.getAmount();
		this.dtTransaction = t.getDtTransaction();
		this.category = t.getMoneyCategory().getMoneyDictionaryCategory().getLabel();
		this.idCategory = t.getMoneyCategory().getIdCategory();
		
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public MCategoryBean getmCategory() {
		return mCategory;
	}

	public void setmCategory(MCategoryBean mCategory) {
		this.mCategory = mCategory;
	}

	public int getIdTransaction() {
		return idTransaction;
	}

	public void setIdTransaction(int idTransaction) {
		this.idTransaction = idTransaction;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Date getDtTransaction() {
		return dtTransaction;
	}

	public void setDtTransaction(Date dtTransaction) {
		this.dtTransaction = dtTransaction;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getTransaction() {
		return transaction;
	}

	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}

	public int getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
	}

}
