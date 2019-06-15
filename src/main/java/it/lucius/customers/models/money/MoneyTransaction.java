package it.lucius.customers.models.money;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;


/**
 * The persistent class for the money_transactions database table.
 * 
 */
@Entity
@Table(name="money_transactions")
@NamedQuery(name="MoneyTransaction.findAll", query="SELECT m FROM MoneyTransaction m")
public class MoneyTransaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idTransaction;

	private BigDecimal amount;

	@Temporal(TemporalType.DATE)
	private Date dtTransaction;

	private String transaction;

	//bi-directional many-to-one association to MoneyCategory
	@ManyToOne
	@JoinColumn(name="idCategory")
	private MoneyCategory moneyCategory;

	public MoneyTransaction() {
	}

	public int getIdTransaction() {
		return this.idTransaction;
	}

	public void setIdTransaction(int idTransaction) {
		this.idTransaction = idTransaction;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getDtTransaction() {
		return this.dtTransaction;
	}

	public void setDtTransaction(Date dtTransaction) {
		this.dtTransaction = dtTransaction;
	}

	public String getTransaction() {
		return this.transaction;
	}

	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}

	public MoneyCategory getMoneyCategory() {
		return this.moneyCategory;
	}

	public void setMoneyCategory(MoneyCategory moneyCategory) {
		this.moneyCategory = moneyCategory;
	}
	
	public static Comparator<MoneyTransaction> DtComparator = new Comparator<MoneyTransaction>() {

		public int compare(MoneyTransaction s1, MoneyTransaction s2) {
			Date d1 = s1.getDtTransaction();
			Date d2 = s2.getDtTransaction();
			
			return d1.compareTo(d2);
	    }
	};

}