package it.lucius.customers.service.money;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import it.lucius.customers.bean.ResponseBean;
import it.lucius.customers.bean.money.MCategoryBean;
import it.lucius.customers.bean.money.MDashboardBean;
import it.lucius.customers.bean.money.MDictionaryCategoryBean;
import it.lucius.customers.bean.money.MTransactionBean;
import it.lucius.customers.bean.money.MoneyDashboardNew;
import it.lucius.customers.bean.money.MoneyMonthsBean;

public interface MoneyService {

	MDashboardBean getDashboard(Integer idUser, LocalDate dtDashBoard);
	ResponseBean createMoneyDashboard(Integer user, MoneyDashboardNew bean);
	ResponseBean updateDashboard(Integer user, MDashboardBean bean);
	
	List<MoneyMonthsBean> getMoneyMonths();
	List<MoneyMonthsBean> getMoneyMonthsCopy(Integer idUser, LocalDate currentDate);
	
	/***** CATEGORY *****/
	List<MCategoryBean> getCategoryList(Integer idDashboard, LocalDate currentDate);
	
	ResponseBean createCategoryDashboard(Integer user, Date dtDashBoard,
			Integer idCat, BigDecimal budget);
	ResponseBean updateCategory(Integer user, MCategoryBean bean);
	ResponseBean deleteCategory(Integer user, MCategoryBean bean);
	
	/***** TRANSACTION *****/
	ResponseBean createTransactionDashboard(Integer user, MTransactionBean t);
	ResponseBean deleteTransaction(Integer user, MTransactionBean bean);
	List<MTransactionBean> getTransactionList(Integer idUser, LocalDate dtDashBoard);
	
	/**** CATEGORY DICTIONARY ****/
	List<MDictionaryCategoryBean> getDicCategoryList(Integer idUser);
	
	ResponseBean createDictionaryCategory(Integer user, MDictionaryCategoryBean bean);
	ResponseBean updateDictionaryCategory(Integer user, MDictionaryCategoryBean bean);
	ResponseBean deleteDictionaryCategory(Integer user, MDictionaryCategoryBean bean);
	
	List<MDictionaryCategoryBean> getListCategories(Integer idUser);
}
