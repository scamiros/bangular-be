package it.lucius.customers.controller.money;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.lucius.customers.bean.UserProfile;
import it.lucius.customers.bean.charts.ChartsData;
import it.lucius.customers.bean.charts.ChartsDatasBigdecimal;
import it.lucius.customers.bean.money.MoneyMonthsBean;
import it.lucius.customers.models.money.MoneyDictionaryCategory;
import it.lucius.customers.service.UserService;
import it.lucius.customers.service.money.MoneyChartsService;
import it.lucius.customers.service.money.MoneyService;
import it.lucius.customers.util.DateUtil;

@RestController
@RequestMapping("/pr")
public class MoneyChartsController {

	@Autowired
	UserService userService;  
	
	@Autowired
	MoneyService moneyService;
	
	@Autowired
	MoneyChartsService moneyChartsService;
	
	@RequestMapping("/getMoneyPieChartsCategory")
    public ResponseEntity<ChartsData> moneyChartsPieCategory(@RequestBody MoneyMonthsBean dtDashboard) {
        
		UserDetails u = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserProfile user = userService.getUserProfileByUsername(u.getUsername());
		
		LocalDate dt = DateUtil.getDatefromSlash(dtDashboard.getMonth()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		ResponseEntity<ChartsData> res = ResponseEntity.ok(moneyChartsService.getChartsPieCategory(user.getId(), dt));
		
		return res;
				
    }
	
	@RequestMapping("/getMoneyBarChartsCategory")
    public ResponseEntity<ChartsData> moneyChartsBarCategory(@RequestBody MoneyDictionaryCategory category) {
        
		UserDetails u = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserProfile user = userService.getUserProfileByUsername(u.getUsername());
		
		ResponseEntity<ChartsData> res = ResponseEntity.ok(moneyChartsService.getChartsBarCategory(user.getId(), category));
		
		return res;
				
    }
	
	@RequestMapping("/getMoneyBarChartsEU")
    public ResponseEntity<ChartsDatasBigdecimal> moneyChartsBarEU(@RequestBody String periodo) {
        
		UserDetails u = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserProfile user = userService.getUserProfileByUsername(u.getUsername());
		
		ResponseEntity<ChartsDatasBigdecimal> res = ResponseEntity.ok(
				moneyChartsService.getChartsBarEU(user.getId(), periodo));
		
		return res;
				
    }
	
	@RequestMapping("/getMoneyBarChartsRisparmio")
    public ResponseEntity<ChartsDatasBigdecimal> moneyChartsBarRisparmio(@RequestBody String periodo) {
        
		UserDetails u = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserProfile user = userService.getUserProfileByUsername(u.getUsername());
		
		ResponseEntity<ChartsDatasBigdecimal> res = ResponseEntity.ok(
				moneyChartsService.getChartsBarRisparmio(user.getId(), periodo));
		
		return res;
				
    }
	
	@RequestMapping("/getMoneyListPeriodo")
    public ResponseEntity<List<String>> moneyListPeriodo() {
        
		ResponseEntity<List<String>> res = ResponseEntity.ok(
				moneyChartsService.getMoneyListPeriodo());
		
		return res;
				
    }
}
