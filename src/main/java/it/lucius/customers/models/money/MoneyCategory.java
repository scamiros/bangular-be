package it.lucius.customers.models.money;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the money_categories database table.
 * 
 */
@Entity
@Table(name="money_categories")
@NamedQuery(name="MoneyCategory.findAll", query="SELECT m FROM MoneyCategory m")
public class MoneyCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idCategory; 

	private BigDecimal budget;

	private int ordine;

	//bi-directional many-to-one association to MoneyDashboard
	@ManyToOne
	@JoinColumn(name="idDashboard")
	private MoneyDashboard moneyDashboard;

	//bi-directional many-to-one association to MoneyDictionaryCategory
	@ManyToOne
	@JoinColumn(name="type")
	private MoneyDictionaryCategory moneyDictionaryCategory;
		
	//bi-directional many-to-one association to MoneyTransaction
	@OneToMany(mappedBy="moneyCategory", fetch=FetchType.EAGER)
	private List<MoneyTransaction> moneyTransactions;

	public MoneyCategory() {
	}

	public int getIdCategory() {
		return this.idCategory;
	}

	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
	}
	
	public MoneyDictionaryCategory getMoneyDictionaryCategory() {
		return this.moneyDictionaryCategory;
	}

	public void setMoneyDictionaryCategory(MoneyDictionaryCategory moneyDictionaryCategory) {
		this.moneyDictionaryCategory = moneyDictionaryCategory;
	}

	public BigDecimal getBudget() {
		return this.budget;
	}

	public void setBudget(BigDecimal budget) {
		this.budget = budget;
	}

	public int getOrdine() {
		return ordine;
	}

	public void setOrdine(int ordine) {
		this.ordine = ordine;
	}

	public MoneyDashboard getMoneyDashboard() {
		return this.moneyDashboard;
	}

	public void setMoneyDashboard(MoneyDashboard moneyDashboard) {
		this.moneyDashboard = moneyDashboard;
	}

	public List<MoneyTransaction> getMoneyTransactions() {
		return this.moneyTransactions;
	}

	public void setMoneyTransactions(List<MoneyTransaction> moneyTransactions) {
		this.moneyTransactions = moneyTransactions;
	}

	public MoneyTransaction addMoneyTransaction(MoneyTransaction moneyTransaction) {
		getMoneyTransactions().add(moneyTransaction);
		moneyTransaction.setMoneyCategory(this);

		return moneyTransaction;
	}

	public MoneyTransaction removeMoneyTransaction(MoneyTransaction moneyTransaction) {
		getMoneyTransactions().remove(moneyTransaction);
		moneyTransaction.setMoneyCategory(null);

		return moneyTransaction;
	}
	
	public static Comparator<MoneyCategory> ordineComparator = new Comparator<MoneyCategory>() {

		public int compare(MoneyCategory s1, MoneyCategory s2) {
			int d1 = s1.getOrdine();
			int d2 = s2.getOrdine();
			
			return d1-d2;
	    }
	};

}