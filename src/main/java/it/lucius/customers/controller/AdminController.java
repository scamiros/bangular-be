package it.lucius.customers.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.lucius.customers.bean.UserProfile;
import it.lucius.customers.bean.money.MDictionaryCategoryBean;
import it.lucius.customers.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	UserService userService;
	
	@RequestMapping("/getAdmin")
    public ResponseEntity<UserProfile> user() {
        
		UserDetails u = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserProfile user = userService.getUserProfileByUsername(u.getUsername());
		
		return ResponseEntity.ok(user);
				
    }
	
	@RequestMapping("/getAdmin2")
    public ResponseEntity<User> user2() {
        
		User user = (User)SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
		
		ResponseEntity<User> res = ResponseEntity.ok(user);
		return res;
				
    }
	
	@RequestMapping("/getMenu")
    public ResponseEntity<String> getMenu(HttpServletRequest request) {
        
		HttpSession session = request.getSession();
		
		String test = (String) session.getAttribute("test");
		ResponseEntity<String> res = ResponseEntity.ok(test);
		return res;
				
    }
	
	/***************** PEOPLE ******************/
	@RequestMapping("/getPeopleUsersList")
    public ResponseEntity<List<UserProfile>> peopleUserList() {
        
		UserDetails u = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserProfile user = userService.getUserProfileByUsername(u.getUsername());
		
		ResponseEntity<List<UserProfile>> res = ResponseEntity.ok(
				userService.getUsersList());
		
		return res;
				
    }
}
