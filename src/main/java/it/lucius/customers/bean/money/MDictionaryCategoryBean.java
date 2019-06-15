package it.lucius.customers.bean.money;

import it.lucius.customers.models.money.MoneyDictionaryCategory;

public class MDictionaryCategoryBean {

	private int idDictionaryCat;

	private String label;

	private String tipo;
	
	public MDictionaryCategoryBean() {
		super();
	}

	public MDictionaryCategoryBean(MoneyDictionaryCategory m) {
		super();
		this.idDictionaryCat = m.getIdDictionaryCat();
		this.label = m.getLabel();
		this.tipo = m.getType().getMoneyCategoryType();
	}

	public int getIdDictionaryCat() {
		return idDictionaryCat;
	}

	public void setIdDictionaryCat(int idDictionaryCat) {
		this.idDictionaryCat = idDictionaryCat;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
