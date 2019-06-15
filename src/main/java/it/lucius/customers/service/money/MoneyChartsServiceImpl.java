package it.lucius.customers.service.money;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.lucius.customers.bean.charts.ChartsData;
import it.lucius.customers.bean.charts.ChartsDatasBigdecimal;
import it.lucius.customers.bean.charts.ChartsDatasetBigdecimal;
import it.lucius.customers.bean.money.MCategoryBean;
import it.lucius.customers.bean.money.MDashboardBean;
import it.lucius.customers.dao.UserDao;
import it.lucius.customers.dao.money.MCategoriesDao;
import it.lucius.customers.dao.money.MDashboardDao;
import it.lucius.customers.dao.money.MDictionaryCategoriesDao;
import it.lucius.customers.dao.money.MTransactionsDao;
import it.lucius.customers.models.User;
import it.lucius.customers.models.money.MoneyCategory;
import it.lucius.customers.models.money.MoneyDashboard;
import it.lucius.customers.models.money.MoneyDictionaryCategory;
import it.lucius.customers.util.DateUtil;
   
@Service
public class MoneyChartsServiceImpl implements MoneyChartsService {
	
	public static final String SEMESTRALE = "SEMETRALE";
	public static final String ANNUALE = "ANNUALE";
	public static final String TRIMESTRALE = "TRIMESTRALE";
	
	@Autowired
	MoneyService moneyService;
	
	@Autowired
	MDashboardDao mDashDao;

	@Autowired
	MCategoriesDao mCatDao;
	
	@Autowired
	MDictionaryCategoriesDao mDicCatDao;
	
	@Autowired
	MTransactionsDao mTrDao;
	
	@Autowired
	UserDao userDao;
	
	@Override
	public ChartsData getChartsPieCategory(Integer idUser, LocalDate dtDashBoard) {
		
		ChartsData res = null;
		
		MDashboardBean d = moneyService.getDashboard(idUser, dtDashBoard);
		boolean noSpese = true;
		
		if(d != null) {
			
			
			res = new ChartsData();
			for(MCategoryBean cat : d.getCategoriesList()) {
				
				if(cat.getSpesa().doubleValue() > 0)
					noSpese = false;
				
				res.getData().add(cat.getSpesa());
				res.getLabels().add(cat.getCategory());
			}
		}
		
		if(noSpese)
			res = null;
		
		return res;
	}

	@Override
	public ChartsData getChartsBarCategory(Integer idUser, MoneyDictionaryCategory category) {
		
		ChartsData chart = new ChartsData();
		User u = userDao.findById(idUser);
		
		Date now = new Date();
		LocalDate current = now.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		current.atTime(0, 0, 0, 0);
		
		LocalDate prev = null;
		
		boolean noSpese = true;
		
		for(int i = 6; i >= 0; i--) {
			
			boolean found = false;
			prev = current.minus(i, ChronoUnit.MONTHS);
			Date dt = DateUtil.getStartDate(prev.getYear(), prev.getMonthValue());
			MoneyDashboard d = mDashDao.findByUserAndDtDashboard(u, dt);
			
			if(d != null) {
				found = false;
				for(MoneyCategory cat : d.getMoneyCategories()) {
					
					if(cat.getMoneyDictionaryCategory().getIdDictionaryCat() == category.getIdDictionaryCat()) {
						MCategoryBean bean = new MCategoryBean(cat, prev);
						chart.getData().add(bean.getSpesa());
						noSpese = false;
						found = true;
						break;
					}
				}
				
				if(!found) {
					chart.getData().add(new BigDecimal(0));
				}
				
			} else {
				chart.getData().add(new BigDecimal(0));
			}
			chart.getLabels().add(
					prev.getMonth().getDisplayName(TextStyle.FULL, Locale.ITALIAN)
					+ "/" + 
					prev.getYear());
		}
		
		if(noSpese)
			chart = null;
		
		return chart;
	}

	@Override
	public ChartsDatasBigdecimal getChartsBarEU(Integer idUser, String periodo) {
		
		List<ChartsDatasetBigdecimal> list = new ArrayList<ChartsDatasetBigdecimal>();
		
		User u = userDao.findById(idUser);
		
		Date now = new Date();
		LocalDate current = now.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		current.atTime(0, 0, 0, 0);
		
		LocalDate prev = null;
		
		int upper = 0;
		
		switch (periodo.toUpperCase()) {
		case TRIMESTRALE:
			upper = 2;
			break;
		case SEMESTRALE:
			upper = 5;
			break;
		case ANNUALE:
			upper = 11;
			break;
		default:
			break;
		}
		
		List<BigDecimal> entrate = new ArrayList<BigDecimal>();
		List<BigDecimal> uscite = new ArrayList<BigDecimal>();
		List<String> labels = new ArrayList<String>();
		
		for(int i = upper; i >= 0; i--) {
			
			prev = current.minus(i, ChronoUnit.MONTHS);
			Date dt = DateUtil.getStartDate(prev.getYear(), prev.getMonthValue());
			MoneyDashboard d = mDashDao.findByUserAndDtDashboard(u, dt);
			
			if(d != null) {
				
				MDashboardBean bean = moneyService.getDashboard(idUser, prev);
				entrate.add(bean.getEntrate());
				uscite.add(bean.getTotaleSpese());
				
			} else {
				entrate.add(new BigDecimal(0));
				uscite.add(new BigDecimal(0));
			}
			
			labels.add(
					prev.getMonth().getDisplayName(TextStyle.FULL, Locale.ITALIAN)
					+ "/" + 
					prev.getYear());
		}
		
		list.add(new ChartsDatasetBigdecimal(entrate, "Entrate"));
		list.add(new ChartsDatasetBigdecimal(uscite, "Uscite"));
		
		
		
		ChartsDatasBigdecimal chartBarEU = new ChartsDatasBigdecimal(list, labels);
		return chartBarEU;
	}

	@Override
	public List<String> getMoneyListPeriodo() {
		
		return new ArrayList<String>(
			Arrays.asList(
			    WordUtils.capitalizeFully(TRIMESTRALE), 
			    WordUtils.capitalizeFully(SEMESTRALE), 
			    WordUtils.capitalizeFully(ANNUALE)));
	}

	@Override
	public ChartsDatasBigdecimal getChartsBarRisparmio(Integer idUser, String periodo) {
		
		List<ChartsDatasetBigdecimal> list = new ArrayList<ChartsDatasetBigdecimal>();
		
		User u = userDao.findById(idUser);
		
		Date now = new Date();
		LocalDate current = now.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		current.atTime(0, 0, 0, 0);
		
		LocalDate prev = null;
		
		int upper = 0;
		
		switch (periodo.toUpperCase()) {
		case TRIMESTRALE:
			upper = 2;
			break;
		case SEMESTRALE:
			upper = 5;
			break;
		case ANNUALE:
			upper = 11;
			break;
		default:
			break;
		}
		
		List<BigDecimal> bilancio = new ArrayList<BigDecimal>();
		List<String> labels = new ArrayList<String>();
		
		for(int i = upper; i >= 0; i--) {
			
			prev = current.minus(i, ChronoUnit.MONTHS);
			Date dt = DateUtil.getStartDate(prev.getYear(), prev.getMonthValue());
			MoneyDashboard d = mDashDao.findByUserAndDtDashboard(u, dt);
			
			if(d != null) {
				
				MDashboardBean bean = moneyService.getDashboard(idUser, prev);
				bilancio.add(bean.getEntrate().subtract(bean.getTotaleSpese()));
				
			} else {
				bilancio.add(new BigDecimal(0));
			}
			
			labels.add(
					prev.getMonth().getDisplayName(TextStyle.FULL, Locale.ITALIAN)
					+ "/" + 
					prev.getYear());
		}
		
		list.add(new ChartsDatasetBigdecimal(bilancio, "Risparmio"));
		
		ChartsDatasBigdecimal chartBarEU = new ChartsDatasBigdecimal(list, labels);
		return chartBarEU;
	}

}
