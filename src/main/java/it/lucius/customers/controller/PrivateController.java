package it.lucius.customers.controller;

import java.util.List;

import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.lucius.customers.bean.RoleBean;
import it.lucius.customers.bean.UserProfile;
import it.lucius.customers.service.RoleService;
import it.lucius.customers.service.UserService;

@RestController
@RequestMapping("/pr")
public class PrivateController {

	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	@RequestMapping("/getUserProfile")
    public ResponseEntity<UserProfile> userProfile() {
        
		UserDetails u = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserProfile user = userService.getUserProfileByUsername(u.getUsername());
		user.setUsername(WordUtils.capitalizeFully(user.getUsername()));
		ResponseEntity<UserProfile> res = ResponseEntity.ok(user);
		
		return res;
				
    }
	
	@RequestMapping("/getRoles")
    public ResponseEntity<List<RoleBean>> roles() {
        
		List<RoleBean> respList = roleService.getRoles();
		ResponseEntity<List<RoleBean>> res = ResponseEntity.ok(respList);
		
		return res;
				
    }
	
	@RequestMapping("/userProfileSave")
	public ResponseEntity<UserProfile> saveUser(@RequestBody UserProfile user) {
		
		return ResponseEntity.ok(userService.saveUserProfile(user));
	}
	
	@RequestMapping("/updateUserPassword")
	public ResponseEntity<UserProfile> updateUserPassword(@RequestBody UserProfile user) {
		
		return ResponseEntity.ok(userService.updateUserPassword(user));
	}
}
