package it.lucius.customers.models.money;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import it.lucius.customers.enumeration.MoneyCategoryType;
import it.lucius.customers.models.User;


/**
 * The persistent class for the money_dictionary_categories database table.
 * 
 */
@Entity
@Table(name="money_dictionary_categories")
@NamedQuery(name="MoneyDictionaryCategory.findAll", query="SELECT m FROM MoneyDictionaryCategory m")
public class MoneyDictionaryCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idDictionaryCat;

	private String label;

	@Enumerated(EnumType.STRING)
	private MoneyCategoryType type;
	
	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="idUser")
	private User user;

	public MoneyDictionaryCategory() {
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public int getIdDictionaryCat() {
		return this.idDictionaryCat;
	}

	public void setIdDictionaryCat(int idDictionaryCat) {
		this.idDictionaryCat = idDictionaryCat;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public MoneyCategoryType getType() {
		return this.type;
	}

	public void setType(MoneyCategoryType type) {
		this.type = type;
	}

}