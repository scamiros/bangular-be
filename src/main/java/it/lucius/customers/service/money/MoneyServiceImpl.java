package it.lucius.customers.service.money;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.text.WordUtils;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import it.lucius.customers.bean.ResponseBean;
import it.lucius.customers.bean.UserProfile;
import it.lucius.customers.bean.money.MCategoryBean;
import it.lucius.customers.bean.money.MDashboardBean;
import it.lucius.customers.bean.money.MDictionaryCategoryBean;
import it.lucius.customers.bean.money.MEntrateBean;
import it.lucius.customers.bean.money.MTransactionBean;
import it.lucius.customers.bean.money.MoneyDashboardNew;
import it.lucius.customers.bean.money.MoneyMonthsBean;
import it.lucius.customers.config.ServicesConstant;
import it.lucius.customers.dao.UserDao;
import it.lucius.customers.dao.money.MCategoriesDao;
import it.lucius.customers.dao.money.MDashboardDao;
import it.lucius.customers.dao.money.MDictionaryCategoriesDao;
import it.lucius.customers.dao.money.MTransactionsDao;
import it.lucius.customers.enumeration.MoneyCategoryType;
import it.lucius.customers.models.User;
import it.lucius.customers.models.money.MoneyCategory;
import it.lucius.customers.models.money.MoneyDashboard;
import it.lucius.customers.models.money.MoneyDictionaryCategory;
import it.lucius.customers.models.money.MoneyTransaction;
import it.lucius.customers.service.UserService;
import it.lucius.customers.util.DateUtil;

@Service
public class MoneyServiceImpl implements MoneyService {

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
	
	@Autowired
	UserService userService;
	
	@Autowired
	TestService testService;
	
	@Override
	public MDashboardBean getDashboard(Integer idUser, LocalDate dtDashBoard) {
		
		MDashboardBean bean = null;
		
		User u = userDao.findById(idUser);
		
		Date dt = DateUtil.getStartDate(dtDashBoard.getYear(), dtDashBoard.getMonthValue());
		
		MoneyDashboard d = mDashDao.findByUserAndDtDashboard(u, dt);
		
		bean = new MDashboardBean(d, dtDashBoard);
		
		bean.setAnno(dtDashBoard.getYear() + "");
		bean.setMese(dtDashBoard.getMonth().toString() + "");
		
		String etichetta = "Stima " + WordUtils.capitalizeFully(bean.getMese() + " " + bean.getAnno());
		
		bean.setEtichetta(etichetta);
		
		Money totaleEntrate = Money.parse("EUR 0.00");
		bean.setEntrateList(new ArrayList<>());
		bean.setCategoriesList(new ArrayList<>());
		
		for(MoneyCategory c : d.getMoneyCategories()) {
			
			if(c.getMoneyDictionaryCategory().getType()
					.getMoneyCategoryType().equals(MoneyCategoryType.E.name())) {
				
				MEntrateBean e = new MEntrateBean();
				e.setDescrizione(c.getMoneyDictionaryCategory().getLabel());
				Money totaleCategoria = Money.parse("EUR 0.00");
				
				for(MoneyTransaction t : c.getMoneyTransactions()) {
					totaleCategoria = totaleCategoria.plus(Money.parse("EUR" + t.getAmount().doubleValue()));
				}
				
				e.setValore(totaleCategoria.getAmount().doubleValue());
				totaleEntrate = totaleEntrate.plus(totaleCategoria);
				
				bean.getEntrateList().add(e);
				
			} else if(c.getMoneyDictionaryCategory().getType()
					.getMoneyCategoryType().equals(MoneyCategoryType.U.name())) {
				
				MCategoryBean cat = new MCategoryBean(c, dtDashBoard);
				bean.getCategoriesList().add(cat);
				
				bean.setTotaleBudget(bean.getTotaleBudget().add(cat.getBudget()));
				bean.setTotaleSpese(bean.getTotaleSpese().add(cat.getSpesa()));
				bean.setTotaleBilancio(bean.getTotaleBilancio().add(cat.getBilancio()));
			}
		}
		
		//Solo in caso di mesi successivi all'attuale
		//Recupero il bilancio del mese precedente per il saldo
		BigDecimal saldo = d.getSaldo();
		
		LocalDate previous = dtDashBoard.minus(1, ChronoUnit.MONTHS);
		Date pastDt = DateUtil.getStartDate(previous.getYear(), previous.getMonthValue());
				
		MoneyDashboard pastDash = mDashDao.findByUserAndDtDashboard(u, pastDt);
		
		if(pastDash != null)
			saldo = pastDash.getStimaSaldo();
		
		bean.setSaldoPartenza(saldo);
		bean.setEntrate(totaleEntrate.getAmount());
		bean.setTotaleEntrate(totaleEntrate.plus(saldo).getAmount()); 
		bean.setStimaBilancio(bean.getEntrate().subtract(bean.getTotaleBudget()));
		bean.setBilancio(bean.getEntrate().subtract(bean.getTotaleSpese()));
		bean.setStimaSaldo(bean.getTotaleEntrate().subtract(bean.getTotaleBudget()));
		bean.setSaldo(bean.getTotaleEntrate().subtract(bean.getTotaleSpese()));

		d.setSaldo(saldo);
		d.setStimaSaldo(bean.getStimaSaldo());
		
		mDashDao.save(d);
		
		return bean;
	}
	
	@Override
	public List<MCategoryBean> getCategoryList(Integer idDashboard, LocalDate currentDate) {
		
		
		List<MoneyCategory> list = mCatDao.findByMoneyDashboard(mDashDao.findOne(idDashboard));
		
		Collections.sort(list, MoneyCategory.ordineComparator);
		
		List<MCategoryBean> listBean = new ArrayList<MCategoryBean>();
		
		for(MoneyCategory c : list) {
			
			MCategoryBean bean = new MCategoryBean(c, currentDate);
			listBean.add(bean);
		}
		
		return listBean;
	}
	
	@Override
	public List<MoneyMonthsBean> getMoneyMonths() {
		
		UserDetails u = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserProfile user = userService.getUserProfileByUsername(u.getUsername());
		User us = userDao.findById(user.getId());
		
		//User us = userDao.findByUsername("admin");
		
		List<MoneyMonthsBean> listBean = new ArrayList<MoneyMonthsBean>();
		
		Date now = new Date();
		
		LocalDate current = now.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		current.atTime(0, 0, 0, 0);
		
		Date dt = DateUtil.getStartDate(current.getYear(), current.getMonthValue());
		
		LocalDate previous = current.minus(1, ChronoUnit.MONTHS);
		Date pastDt = DateUtil.getStartDate(previous.getYear(), previous.getMonthValue());
				
		MoneyDashboard pastDash = mDashDao.findByUserAndDtDashboard(us, pastDt);
		
		if(pastDash != null) {
			
			MoneyMonthsBean bean = new MoneyMonthsBean();
			dt = DateUtil.getStartDate(previous.getYear(), previous.getMonthValue());
			bean.setMonth(DateUtil.getSlashDate(dt));
			bean.setOption(previous.getMonthValue() + "/" + previous.getYear());
			
			listBean.add(bean);
			
			bean = new MoneyMonthsBean();
			dt = DateUtil.getStartDate(current.getYear(), current.getMonthValue());
			bean.setMonth(DateUtil.getSlashDate(dt));
			bean.setOption(current.getMonthValue() + "/" + current.getYear());
			
			for(int i = 1; i <= ServicesConstant.MONEY_MONTHS_MAX; i++) {
				
				LocalDate next = previous.plus(i, ChronoUnit.MONTHS);
				
				bean = new MoneyMonthsBean();
				dt = DateUtil.getStartDate(next.getYear(), next.getMonthValue());
				bean.setMonth(DateUtil.getSlashDate(dt));
				bean.setOption(next.getMonthValue() + "/" + next.getYear());
				
				listBean.add(bean);
			}
			
		} else {
			
			MoneyMonthsBean bean = new MoneyMonthsBean();
			dt = DateUtil.getStartDate(current.getYear(), current.getMonthValue());
			bean.setMonth(DateUtil.getSlashDate(dt));
			
			bean.setOption(current.getMonthValue() + "/" + current.getYear());
			listBean.add(bean);
			
			for(int i = 1; i <= ServicesConstant.MONEY_MONTHS_MAX; i++) {
				
				LocalDate next = current.plus(i, ChronoUnit.MONTHS);
				dt = DateUtil.getStartDate(next.getYear(), next.getMonthValue());
				
				bean = new MoneyMonthsBean();
				bean.setMonth(DateUtil.getSlashDate(dt));
				bean.setOption(next.getMonthValue() + "/" + next.getYear());
				
				listBean.add(bean);
			}
		} 
		
		int i = 0;
		for(MoneyMonthsBean bean : listBean) {
			
			MoneyDashboard dash = mDashDao.findByUserAndDtDashboard(us, DateUtil.getDatefromSlash(bean.getMonth()));
			
			if(dash == null) {
				dash = new MoneyDashboard();
				dash.setDtDashboard(DateUtil.getDatefromSlash(bean.getMonth()));
				dash.setUser(us);
				
				if(i == 0) {
					dash.setSaldo(new BigDecimal("0"));
					dash.setStimaSaldo(new BigDecimal("0"));
				} else {
					MoneyDashboard pastDashboard = mDashDao.findByUserAndDtDashboard(
							us, DateUtil.getDatefromSlash(listBean.get(i-1).getMonth()));
					dash.setSaldo(calculateStimaSaldo(pastDashboard));
				}
				
				dash = mDashDao.saveAndFlush(dash);
			}
			
			i++;
		}
		
		return listBean;
	}
	
	private BigDecimal calculateStimaSaldo(MoneyDashboard dash) {
		
		Money totaleEntrate = Money.parse("EUR 0.00");
		Money totaleUscite = Money.parse("EUR 0.00");
		
		if(dash.getMoneyCategories() != null) {
			for(MoneyCategory c : dash.getMoneyCategories()) {
				
				if(c.getMoneyDictionaryCategory().getType()
						.getMoneyCategoryType().equals(MoneyCategoryType.E.name())) {
					
					Money totaleCategoria = Money.parse("EUR 0.00");
					
					if(c.getMoneyTransactions() != null) {
						for(MoneyTransaction t : c.getMoneyTransactions()) {
							totaleCategoria = totaleCategoria.plus(Money.parse("EUR" + t.getAmount().doubleValue()));
						}
						
						totaleEntrate = totaleEntrate.plus(totaleCategoria);
					}
					
				} else if(c.getMoneyDictionaryCategory().getType()
						.getMoneyCategoryType().equals(MoneyCategoryType.U.name())) {
					
					totaleUscite = totaleUscite.plus(c.getBudget());
				}
			}
		}
		
		return totaleEntrate.minus(totaleUscite).plus(dash.getSaldo()).getAmount();
	}
	
//	@Override
//	public List<MoneyMonthsBean> getMoneyMonths() {
//		
//		List<MoneyMonthsBean> listBean = new ArrayList<MoneyMonthsBean>();
//		
//		Date now = new Date();
//		LocalDate current = now.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//		
//		current.atTime(0, 0, 0, 0);
//		
//		for(int i = 3; i >= 1; i--) {
//			
//			LocalDate previous = current.plus(i, ChronoUnit.MONTHS);
//			
//			MoneyMonthsBean bean = new MoneyMonthsBean();
//			bean.setMonth(Date.from(previous.atStartOfDay(ZoneId.systemDefault()).toInstant()));
//			bean.setOption(previous.getMonthValue() + "/" + previous.getYear());
//			
//			listBean.add(bean);
//		}
//
//		for(int i = 0; i < 5; i++) {
//			
//			LocalDate previous = current.minus(i, ChronoUnit.MONTHS);
//			
//			MoneyMonthsBean bean = new MoneyMonthsBean();
//			bean.setMonth(Date.from(previous.atStartOfDay(ZoneId.systemDefault()).toInstant()));
//			bean.setOption(previous.getMonthValue() + "/" + previous.getYear());
//			
//			listBean.add(bean);
//		}
//		
//		return listBean;
//	}

	@Override
	public ResponseBean createMoneyDashboard(Integer user, MoneyDashboardNew bean) {
		
		ResponseBean res = new ResponseBean();
		res.setCode(ResponseBean.OK_CODE);
		
		User u = userDao.findById(user);
		
		MoneyDashboard dash = mDashDao.findOne(bean.getIdDashboard());
			
		LocalDate localDt = DateUtil.getDatefromSlash(bean.getCopyMonth().getMonth()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		Date dat = DateUtil.getStartDate(localDt.getYear(), localDt.getMonthValue());
		MoneyDashboard dashCopy = mDashDao.findByUserAndDtDashboard(u, dat);
		
		for(MoneyCategory cat : dashCopy.getMoneyCategories()) {
			
			MoneyCategory c = new MoneyCategory();
			c.setBudget(cat.getBudget());
			c.setMoneyDashboard(dash);
			c.setMoneyDictionaryCategory(cat.getMoneyDictionaryCategory());
			
			mCatDao.save(c);
		}
		res.setMessage(ResponseBean.OK_CREATE_MSG);
		
		return res;
	}

	@Override
	public List<MDictionaryCategoryBean> getDicCategoryList(Integer idUser) {
		
		List<MDictionaryCategoryBean> list = new ArrayList<MDictionaryCategoryBean>();
		
		User u = userDao.findById(idUser);
		
		List<MoneyDictionaryCategory> r = mDicCatDao.findByUser(u);
		
		for(MoneyDictionaryCategory m : r) {
			
			MDictionaryCategoryBean bean = new MDictionaryCategoryBean(m);
			list.add(bean);
		}
		return list;
	}

	@Override
	public ResponseBean createCategoryDashboard(Integer user, Date dtDashBoard, Integer idCat,
			BigDecimal budget) {
		
		ResponseBean res = new ResponseBean();
		res.setCode(ResponseBean.OK_CODE);
		res.setMessage(ResponseBean.OK_CREATE_MSG);
		
		LocalDate dt = dtDashBoard.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		Date dt2 = DateUtil.getStartDate(dt.getYear(), dt.getMonthValue());
		
		User u = userDao.findById(user);
		
		MoneyDashboard dash = mDashDao.findByUserAndDtDashboard(u, dt2);
		
		if(dash == null) {
			res.setCode(ResponseBean.ERROR_CODE);
			res.setMessage(ResponseBean.ERROR_DASHBOARD_NOTEXISTS_MSG);
		} else {
			
			MoneyDictionaryCategory cat = mDicCatDao.findOne(idCat);
			
			for(MoneyCategory c : dash.getMoneyCategories()) {
				
				if(c.getMoneyDictionaryCategory().getIdDictionaryCat() == cat.getIdDictionaryCat()) {
					
					//Category exists
					res.setCode(ResponseBean.ERROR_CODE);
					res.setMessage(ResponseBean.ERROR_CATEGORY_EXISTS_MSG);
					return res;
				}
			}
			
			MoneyCategory category = new MoneyCategory();
			category.setBudget((budget != null) ? budget : new BigDecimal(0));
			category.setMoneyDashboard(dash);
			category.setMoneyDictionaryCategory(cat);
			
			category  = mCatDao.saveAndFlush(category);
			
			if(category == null) {
				res.setCode(ResponseBean.ERROR_CODE);
				res.setMessage(ResponseBean.ERROR_CREATE_MSG);
			}
		}
		
		return res;
	}
	
	Money getSaldoFromDashboard(User u, MoneyDashboard dash) {
		
		Money saldo = Money.parse("EUR 0.00");
		
		if(dash.getSaldo() != null)
			saldo = saldo.plus(dash.getSaldo());
		
		Money saldoMoney = Money.parse("EUR 0.00");
		Money totaleEntrate = Money.parse("EUR 0.00");
		Money totaleUscite = Money.parse("EUR 0.00");
		
		for(MoneyCategory c : dash.getMoneyCategories()) {
			
			if(c.getMoneyDictionaryCategory().getType()
					.getMoneyCategoryType().equals(MoneyCategoryType.E.name())) {
				
				Money totaleCategoria = Money.parse("EUR 0.00");
				
				for(MoneyTransaction t : c.getMoneyTransactions()) {
					totaleCategoria = totaleCategoria.plus(Money.parse("EUR" + t.getAmount().doubleValue()));
				}
				
				totaleEntrate = totaleEntrate.plus(totaleCategoria);
				
			} else if(c.getMoneyDictionaryCategory().getType()
					.getMoneyCategoryType().equals(MoneyCategoryType.U.name())) {
				
				/*Money totaleCategoria = Money.parse("EUR 0.00");
				for(MoneyTransaction t : c.getMoneyTransactions()) {
					totaleCategoria = totaleCategoria.plus(Money.parse("EUR" + t.getAmount().doubleValue()));
				}*/
				
				totaleUscite = totaleUscite.plus(c.getBudget());
			}
		}
		
		saldoMoney = totaleEntrate.minus(totaleUscite).plus(saldo);
		
		return saldoMoney;
	}

	@Override
	public ResponseBean createTransactionDashboard(Integer user, MTransactionBean t) {
		
		ResponseBean res = new ResponseBean();
		res.setMessage(ResponseBean.OK_CREATE_MSG);
		
		MoneyCategory cat = mCatDao.findOne(t.getmCategory().getIdCategory());
		
		MoneyTransaction tr = new MoneyTransaction();
		tr.setAmount(t.getAmount());
		tr.setDtTransaction(t.getDtTransaction());
		tr.setMoneyCategory(cat);
		tr.setTransaction(t.getTransaction());
		
		tr = mTrDao.saveAndFlush(tr);
		
		if(tr == null) {
			res.setCode(ResponseBean.ERROR_CODE);
			res.setMessage(ResponseBean.ERROR_CREATE_MSG);
		}
		
		return res;
	}

	@Override
	public ResponseBean updateDashboard(Integer user, MDashboardBean bean) {
		
		ResponseBean res = new ResponseBean();
		res.setMessage(ResponseBean.OK_CREATE_MSG);
		
		User u = userDao.findById(user);
		
		MoneyDashboard dash = mDashDao.findByUserAndDtDashboard(u, bean.getDtDashboard());
		
		if(dash == null) {
			res.setCode(ResponseBean.ERROR_CODE);
			res.setMessage(ResponseBean.ERROR_DASHBOARD_NOTEXISTS_MSG);
		} else {
			
			dash.setSaldo(bean.getSaldo());
			dash.setStimaSaldo(calculateStimaSaldo(dash));
			dash = mDashDao.saveAndFlush(dash);
			
			if(dash == null) {
				res.setCode(ResponseBean.ERROR_CODE);
				res.setMessage(ResponseBean.ERROR_UPDATE_MSG);
			}
		}
		
		return res;
	}

	@Override
	public ResponseBean updateCategory(Integer user, MCategoryBean bean) {
		
		ResponseBean res = new ResponseBean();
		res.setMessage(ResponseBean.OK_CREATE_MSG);
		
		MoneyCategory model = mCatDao.findOne(bean.getIdCategory());
		
		if(model == null) {
			res.setCode(ResponseBean.ERROR_CODE);
			res.setMessage(ResponseBean.ERROR_CATEGORY_NOTEXISTS_MSG);
		} else {
			
			model.setBudget(new BigDecimal(bean.getBudget().doubleValue()));
			model = mCatDao.saveAndFlush(model);
			
			if(model == null) {
				res.setCode(ResponseBean.ERROR_CODE);
				res.setMessage(ResponseBean.ERROR_UPDATE_MSG);
			}
		}
		
		return res;
	}

	@Override
	public ResponseBean createDictionaryCategory(Integer user, MDictionaryCategoryBean bean) {
		
		ResponseBean res = new ResponseBean();
		res.setMessage(ResponseBean.OK_CREATE_MSG);
		
		User u = userDao.findById(user);
		MoneyCategoryType e = MoneyCategoryType.valueOf(bean.getTipo());
		MoneyDictionaryCategory model = mDicCatDao.findByUserAndLabelIgnoreCaseAndType(u, bean.getLabel(), e);
		
		if(model != null) {
			res.setCode(ResponseBean.ERROR_CODE);
			res.setMessage(ResponseBean.ERROR_CATEGORY_EXISTS_MSG);
		} else {
			
			model = new MoneyDictionaryCategory();
			model.setLabel(bean.getLabel());
			model.setType(e);
			model.setUser(u);
			model = mDicCatDao.saveAndFlush(model);
			
			if(model == null) {
				res.setCode(ResponseBean.ERROR_CODE);
				res.setMessage(ResponseBean.ERROR_UPDATE_MSG);
			}
		}
		
		return res;
	}

	@Override
	public ResponseBean updateDictionaryCategory(Integer user, MDictionaryCategoryBean bean) {
		
		ResponseBean res = new ResponseBean();
		res.setMessage(ResponseBean.OK_UPDATE_MSG);
		
		MoneyDictionaryCategory model = mDicCatDao.findOne(bean.getIdDictionaryCat());
		
		if(model == null) {
			res.setCode(ResponseBean.ERROR_CODE);
			res.setMessage(ResponseBean.ERROR_CATEGORY_NOTEXISTS_MSG);
		} else {
			
			model.setLabel(bean.getLabel());
			model = mDicCatDao.saveAndFlush(model);
			
			if(model == null) {
				res.setCode(ResponseBean.ERROR_CODE);
				res.setMessage(ResponseBean.ERROR_UPDATE_MSG);
			}
		}
		
		return res;
	}

	@Override
	public ResponseBean deleteDictionaryCategory(Integer user, MDictionaryCategoryBean bean) {
		
		ResponseBean res = new ResponseBean();
		res.setMessage(ResponseBean.OK_DELETE_MSG);
		
		MoneyDictionaryCategory model = mDicCatDao.findOne(bean.getIdDictionaryCat());
		
		if(model == null) {
			res.setCode(ResponseBean.ERROR_CODE);
			res.setMessage(ResponseBean.ERROR_CATEGORY_NOTEXISTS_MSG);
		} else {
			
			mDicCatDao.delete(model);
			
		}
		
		return res;
	}

	@Override
	public List<MDictionaryCategoryBean> getListCategories(Integer idUser) {
		
		List<MDictionaryCategoryBean> list = new ArrayList<MDictionaryCategoryBean>();
		
		User u = userDao.findById(idUser);
		List<MoneyDictionaryCategory> listCat = mDicCatDao.findByUser(u);
		
		for(MoneyDictionaryCategory cat : listCat) {
			
			MDictionaryCategoryBean bean = new MDictionaryCategoryBean(cat);
			list.add(bean);
		}
		return list;
	}

	@Override
	public ResponseBean deleteCategory(Integer user, MCategoryBean bean) {
		
		ResponseBean res = new ResponseBean();
		res.setMessage(ResponseBean.OK_DELETE_MSG);
		
		MoneyCategory model = mCatDao.findOne(bean.getIdCategory());
		
		if(model == null) {
			res.setCode(ResponseBean.ERROR_CODE);
			res.setMessage(ResponseBean.ERROR_CATEGORY_NOTEXISTS_MSG);
		} else {
			
			mCatDao.delete(model);
			
		}
		
		return res;
	}

	@Override
	public ResponseBean deleteTransaction(Integer user, MTransactionBean bean) {
		
		ResponseBean res = new ResponseBean();
		res.setMessage(ResponseBean.OK_DELETE_MSG);
		
		MoneyTransaction model = mTrDao.findOne(bean.getIdTransaction());
		
		if(model == null) {
			res.setCode(ResponseBean.ERROR_CODE);
			res.setMessage(ResponseBean.ERROR_CATEGORY_NOTEXISTS_MSG);
		} else {
			
			mTrDao.delete(model);
			
		}
		
		return res;
	}

	@Override
	public List<MoneyMonthsBean> getMoneyMonthsCopy(Integer idUser, LocalDate currentDate) {
		
		User u = userDao.findById(idUser);
		
		List<MoneyMonthsBean> listBean = new ArrayList<MoneyMonthsBean>();
		
		currentDate.atTime(0, 0, 0, 0);
		
		for(int i = 1; i < 5; i++) {
			
			LocalDate previous = currentDate.minus(i, ChronoUnit.MONTHS);
			
			Date dt = DateUtil.getStartDate(previous.getYear(), previous.getMonthValue());
			MoneyDashboard dash = mDashDao.findByUserAndDtDashboard(u, dt);
			if(dash != null && dash.getMoneyCategories().size() > 0) {
				MoneyMonthsBean bean = new MoneyMonthsBean();
				bean.setMonth(DateUtil.getSlashDate(Date.from(previous.atStartOfDay(ZoneId.systemDefault()).toInstant())));
				bean.setOption(previous.getMonthValue() + "/" + previous.getYear());
				
				listBean.add(bean);
			} else 
				break;
		}
		
		return listBean;
	}
	
	public String findUser(String username) {
		
		User user = userDao.findByUsername(username);
		String res = testService.getMessage(user.getFirstname());
		
		return res;
	}

	@Override
	public List<MTransactionBean> getTransactionList(Integer idUser, LocalDate dtDashBoard) {
		
		List<MTransactionBean> beanList = new ArrayList<MTransactionBean>();
		
		User u = userDao.findById(idUser);
		
		Date dt = DateUtil.getStartDate(dtDashBoard.getYear(), dtDashBoard.getMonthValue());
		MoneyDashboard d = mDashDao.findByUserAndDtDashboard(u, dt);
		
		Date today = new Date();
		Date dtDayTo = DateUtil.getLastMonthDay(today);
		Date dtDayFrom = DateUtil.getFirstMonthDay(today);
		
		List<MoneyTransaction> list = 
				mTrDao.findByMoneyCategoryMoneyDashboardAndDtTransactionBetweenOrderByDtTransactionDesc(d, dtDayFrom, dtDayTo);
		
		for(MoneyTransaction t : list) {
			MTransactionBean bean = new MTransactionBean(t);
			beanList.add(bean);
		}
		return beanList;
	}

}
