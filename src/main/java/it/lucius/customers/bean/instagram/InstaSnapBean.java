package it.lucius.customers.bean.instagram;

import java.util.List;

public class InstaSnapBean {

	private List<InstragramUserBean> list;
	private int currentIndex;
	
	public List<InstragramUserBean> getList() {
		return list;
	}
	public void setList(List<InstragramUserBean> list) {
		this.list = list;
	}
	public int getCurrentIndex() {
		return currentIndex;
	}
	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}
	
	
}
