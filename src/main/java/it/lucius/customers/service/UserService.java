package it.lucius.customers.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.service.spi.ServiceException;

import it.lucius.customers.bean.ResponseBean;
import it.lucius.customers.bean.UserProfile;

public interface UserService {
	
	UserProfile getUserProfileByUsername(String username);
	UserProfile saveUserProfile(UserProfile user);
	UserProfile updateUserPassword(UserProfile user);
	ResponseBean signup(HttpServletRequest request, UserProfile bean) throws ServiceException;
	ResponseBean confirmSignup(String username, String token) throws ServiceException;
	List<UserProfile> getUsersList();
}
