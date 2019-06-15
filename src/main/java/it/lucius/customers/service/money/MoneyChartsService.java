package it.lucius.customers.service.money;

import java.time.LocalDate;
import java.util.List;

import it.lucius.customers.bean.charts.ChartsData;
import it.lucius.customers.bean.charts.ChartsDatasBigdecimal;
import it.lucius.customers.models.money.MoneyDictionaryCategory;

public interface MoneyChartsService {

	ChartsData getChartsPieCategory(Integer idUser, LocalDate dtDashBoard);
	ChartsData getChartsBarCategory(Integer idUser, MoneyDictionaryCategory category);
	ChartsDatasBigdecimal getChartsBarEU(Integer idUser, String periodo);
	ChartsDatasBigdecimal getChartsBarRisparmio(Integer idUser, String periodo);
	List<String> getMoneyListPeriodo();
}
