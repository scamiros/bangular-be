package it.lucius.customers.controller.money;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.lucius.customers.bean.ResponseBean;
import it.lucius.customers.bean.UserProfile;
import it.lucius.customers.bean.money.MCategoryBean;
import it.lucius.customers.bean.money.MDashboardBean;
import it.lucius.customers.bean.money.MDictionaryCategoryBean;
import it.lucius.customers.bean.money.MTransactionBean;
import it.lucius.customers.bean.money.MoneyDashboardNew;
import it.lucius.customers.bean.money.MoneyMonthsBean;
import it.lucius.customers.service.UserService;
import it.lucius.customers.service.money.MoneyService;
import it.lucius.customers.util.Cripto;
import it.lucius.customers.util.DateUtil;

@RestController
@RequestMapping("/pr")
public class MoneyController {

	@Autowired
	UserService userService;
	
	@Autowired
	MoneyService moneyService;
	
	@Autowired
	Cripto cripto;
	
	@RequestMapping("/getMoneyDashboard")
    public ResponseEntity<MDashboardBean> moneyDashboard(@RequestBody MoneyMonthsBean dtDashboard,
    		HttpSession session) {
        
		it.lucius.customers.models.User user = cripto.getSessionUser(session);
		
		LocalDate dt = DateUtil.getDatefromSlash(dtDashboard.getMonth()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		ResponseEntity<MDashboardBean> res = ResponseEntity.ok(moneyService.getDashboard(user.getId(), dt));
		
		return res;
				
    }
	
	@RequestMapping("/getDashboardCategories")
    public ResponseEntity<List<MCategoryBean>> moneyCategories(@RequestBody MoneyMonthsBean current, HttpSession session) {
        
		it.lucius.customers.models.User user = cripto.getSessionUser(session);
		
		LocalDate dt = DateUtil.getDatefromSlash(current.getMonth()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		MDashboardBean d = moneyService.getDashboard(user.getId(), dt);
		ResponseEntity<List<MCategoryBean>> res = null;
		
		if(d != null)
			res = ResponseEntity.ok(moneyService.getCategoryList(d.getIdDashboard(), dt));
		
		return res;
				
    }
	
	@RequestMapping("/getMoneyMonths")
    public ResponseEntity<List<MoneyMonthsBean>> moneyMonths() {
        
		ResponseEntity<List<MoneyMonthsBean>> res = ResponseEntity.ok(
				moneyService.getMoneyMonths());
		
		return res;
				
    }
	
	@RequestMapping("/getMoneyMonthsCopy")
    public ResponseEntity<List<MoneyMonthsBean>> moneyMonthsCopy(@RequestBody MoneyMonthsBean current, HttpSession session) {
        
		it.lucius.customers.models.User user = cripto.getSessionUser(session);
		
		LocalDate dt = DateUtil.getDatefromSlash(current.getMonth()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		ResponseEntity<List<MoneyMonthsBean>> res = ResponseEntity.ok(
				moneyService.getMoneyMonthsCopy(user.getId(), dt)); 
		
		return res;
				
    }
	
	@RequestMapping("/getCategories")
    public ResponseEntity<List<MDictionaryCategoryBean>> moneyCategories(HttpSession session) {
        
		it.lucius.customers.models.User user = cripto.getSessionUser(session);
		
		ResponseEntity<List<MDictionaryCategoryBean>> res = ResponseEntity.ok(
				moneyService.getListCategories(user.getId()));
		
		return res;
				
    }
	
	@RequestMapping("/createMoneyDashboard")
    public ResponseEntity<ResponseBean> createMoneyDashboard(@RequestBody MoneyDashboardNew bean, HttpSession session) {
        
		it.lucius.customers.models.User user = cripto.getSessionUser(session);
		
		ResponseBean d = moneyService.createMoneyDashboard(user.getId(), bean);
		
		return ResponseEntity.ok(d);
				
    }
	
	@RequestMapping("/createCategoryDashboard")
    public ResponseEntity<ResponseBean> createMoneyCategory(
    		@RequestBody MCategoryBean cat) {
        
		UserDetails u = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserProfile user = userService.getUserProfileByUsername(u.getUsername());
		
		ResponseBean d = moneyService.createCategoryDashboard(
				user.getId(), 
				DateUtil.getDatefromSlash(cat.getDtDashboard()), 
				Integer.decode(cat.getTipo()).intValue(), 
				cat.getBudget());
		
		return ResponseEntity.ok(d);
				
    }
	
	@RequestMapping("/createTransaction")
    public ResponseEntity<ResponseBean> createTransactionCategory(
    		@RequestBody MTransactionBean bean) {
        
		UserDetails u = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserProfile user = userService.getUserProfileByUsername(u.getUsername());
		
		ResponseBean d = moneyService.createTransactionDashboard(user.getId(), bean);
		
		return ResponseEntity.ok(d);
				
    }
	
	@RequestMapping("/updateMoneyDashboard")
    public ResponseEntity<ResponseBean> updateDashboard(
    		@RequestBody MDashboardBean bean) {
        
		UserDetails u = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserProfile user = userService.getUserProfileByUsername(u.getUsername());
		
		ResponseBean d = moneyService.updateDashboard(user.getId(), bean);
		
		return ResponseEntity.ok(d);
				
    }
	
	@RequestMapping("/updateMoneyCategory")
    public ResponseEntity<ResponseBean> updateCategory(
    		@RequestBody MCategoryBean bean) {
        
		UserDetails u = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserProfile user = userService.getUserProfileByUsername(u.getUsername());
		
		ResponseBean d = moneyService.updateCategory(user.getId(), bean);
		
		return ResponseEntity.ok(d);
				
    }
	
	/***************** Dictionay Categories ******************/
	@RequestMapping("/getMoneyDictionaryCategories")
    public ResponseEntity<List<MDictionaryCategoryBean>> moneyDicCats() {
        
		UserDetails u = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserProfile user = userService.getUserProfileByUsername(u.getUsername());
		
		ResponseEntity<List<MDictionaryCategoryBean>> res = ResponseEntity.ok(
				moneyService.getDicCategoryList(user.getId()));
		
		return res;
				
    }
	
	@RequestMapping("/createMoneyDictionaryCategories")
    public ResponseEntity<ResponseBean> createDictionaryCategory(
    		@RequestBody MDictionaryCategoryBean bean) {
        
		UserDetails u = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserProfile user = userService.getUserProfileByUsername(u.getUsername());
		
		ResponseBean d = moneyService.createDictionaryCategory(user.getId(), bean);
		
		return ResponseEntity.ok(d);
				
    }
	
	@RequestMapping("/deleteCategory")
    public ResponseEntity<ResponseBean> deleteCategory(
    		@RequestBody MCategoryBean bean) {
        
		UserDetails u = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserProfile user = userService.getUserProfileByUsername(u.getUsername());
		
		ResponseBean d = moneyService.deleteCategory(user.getId(), bean);
		
		return ResponseEntity.ok(d);
				
    }
	
	@RequestMapping("/deleteTransaction")
    public ResponseEntity<ResponseBean> deleteTransaction(
    		@RequestBody MTransactionBean bean) {
        
		UserDetails u = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserProfile user = userService.getUserProfileByUsername(u.getUsername());
		
		ResponseBean d = moneyService.deleteTransaction(user.getId(), bean);
		
		return ResponseEntity.ok(d);
				
    }
	
	@RequestMapping("/getTransactionList")
    public ResponseEntity<List<MTransactionBean>> getTransactionList(
    		@RequestBody MoneyMonthsBean current, HttpSession session) {
        
		it.lucius.customers.models.User user = cripto.getSessionUser(session);
		
		LocalDate dt = DateUtil.getDatefromSlash(current.getMonth()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		ResponseEntity<List<MTransactionBean>> res = ResponseEntity.ok(moneyService.getTransactionList(user.getId(), dt));
		
		return res;
				
    }
}
