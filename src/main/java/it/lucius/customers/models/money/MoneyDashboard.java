package it.lucius.customers.models.money;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import it.lucius.customers.models.User;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the money_dashboard database table.
 * 
 */
@Entity
@Table(name="money_dashboard")
@NamedQuery(name="MoneyDashboard.findAll", query="SELECT m FROM MoneyDashboard m")
public class MoneyDashboard implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idDashboard;

	private String dashboard;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dtDashboard;
	
	private BigDecimal saldo;
	private BigDecimal stimaSaldo;
	
	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="idUser")
	private User user;

	//bi-directional many-to-one association to MoneyCategory
	@OneToMany(mappedBy="moneyDashboard", fetch=FetchType.EAGER)
	private List<MoneyCategory> moneyCategories;

	public MoneyDashboard() {
	}

	public int getIdDashboard() {
		return this.idDashboard;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public BigDecimal getStimaSaldo() {
		return stimaSaldo;
	}

	public void setStimaSaldo(BigDecimal stimaSaldo) {
		this.stimaSaldo = stimaSaldo;
	}

	public void setIdDashboard(int idDashboard) {
		this.idDashboard = idDashboard;
	}

	public String getDashboard() {
		return this.dashboard;
	}

	public void setDashboard(String dashboard) {
		this.dashboard = dashboard;
	}

	public Date getDtDashboard() {
		return this.dtDashboard;
	}

	public void setDtDashboard(Date dtDashboard) {
		this.dtDashboard = dtDashboard;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<MoneyCategory> getMoneyCategories() {
		return this.moneyCategories;
	}

	public void setMoneyCategories(List<MoneyCategory> moneyCategories) {
		this.moneyCategories = moneyCategories;
	}

	public MoneyCategory addMoneyCategory(MoneyCategory moneyCategory) {
		getMoneyCategories().add(moneyCategory);
		moneyCategory.setMoneyDashboard(this);

		return moneyCategory;
	}

	public MoneyCategory removeMoneyCategory(MoneyCategory moneyCategory) {
		getMoneyCategories().remove(moneyCategory);
		moneyCategory.setMoneyDashboard(null);

		return moneyCategory;
	}

}