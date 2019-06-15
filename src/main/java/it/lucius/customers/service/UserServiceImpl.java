package it.lucius.customers.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import it.lucius.customers.bean.ResponseBean;
import it.lucius.customers.bean.RoleBean;
import it.lucius.customers.bean.UserProfile;
import it.lucius.customers.dao.RoleDao;
import it.lucius.customers.dao.UserDao;
import it.lucius.customers.models.Role;
import it.lucius.customers.models.User;
import it.lucius.customers.models.money.MoneyTransaction;
import it.lucius.customers.service.mail.SmtpMailSender;
import it.lucius.customers.util.Cripto;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;
	
	@Autowired
	RoleDao roleDao;
	
	@Autowired
	SmtpMailSender sender;
	
	@Override
	public UserProfile getUserProfileByUsername(String username) {
		
		UserProfile u = new UserProfile(userDao.findByUsername(username));
		return u;
	}

	@Override
	public UserProfile saveUserProfile(UserProfile user) {
		
		User u = userDao.findByUsername(user.getUsername());
		
		if(user != null) {
			
			u.setFirstname(user.getFirstname());
			u.setLastname(user.getLastname());
			u.setEmail(user.getEmail());
			
			Role role = roleDao.findOne(user.getRole().getIdRole());
			
			u.setRole(role);
			
			u = userDao.saveAndFlush(u);
		
		}
		return new UserProfile(u);
	}

	@Override
	public UserProfile updateUserPassword(UserProfile user) {
		
		User u = userDao.findByUsername(user.getUsername());
		
		if(user != null) {
			
			BCryptPasswordEncoder p = new BCryptPasswordEncoder();
			String has = p.encode(user.getPassword());
			
			u.setPassword(has);
			
			u = userDao.saveAndFlush(u);
		
		}
		return new UserProfile(u);
	}

	@Override
	public ResponseBean signup(HttpServletRequest request, UserProfile bean) throws ServiceException {
		
		ResponseBean res = new ResponseBean();
		res.setMessage(ResponseBean.OK_CREATE_USER_MSG);
		
		User model = userDao.findByEmail(bean.getEmail());
		
		if(model != null) {
			res.setCode(ResponseBean.ERROR_CODE);
			res.setMessage(ResponseBean.ERROR_USER_EMAIL_EXISTS_MSG);
			return res;
		}
		
		model = userDao.findByUsername(bean.getUsername());
		
		if(model != null) {
			res.setCode(ResponseBean.ERROR_CODE);
			res.setMessage(ResponseBean.ERROR_USER_USERNAME_EXISTS_MSG);
			return res;
		}
		
		User user = new User();
		user.setEmail(bean.getEmail());
		user.setUsername(bean.getUsername());
		user.setEnabled(false);
		user.setExpired(false);
		Role r = roleDao.findByRole(RoleBean.ROLE_USER);
		
		user.setRole(r);
		
		BCryptPasswordEncoder p = new BCryptPasswordEncoder();
		String has = p.encode(bean.getPassword());
		
		user.setPassword(has);
		
		String token = Cripto.sha256(Cripto.generatePswd(8, 8, 1, 1, 1).toString());
		
		user.setToken(token);
		user.setEnabled(true);
		bean.setToken(token);
		user = userDao.saveAndFlush(user);
		
		if(user == null) {
			res.setCode(ResponseBean.ERROR_CODE);
			res.setMessage(ResponseBean.ERROR_CREATE_MSG);
			return res;
		}
		
		/*try {
			sender.sendConfirmationLink(request, bean);
		} catch (MessagingException e) {
			e.printStackTrace();
		}*/
		
		return res;
		
	}

	@Override
	public ResponseBean confirmSignup(String username, String token) throws ServiceException {
		
		ResponseBean res = new ResponseBean();
		res.setMessage(ResponseBean.OK_CONFIRM_SIGNUP_MSG);
		
		User user = userDao.findByUsernameAndToken(username, token);
		
		if(user != null) {
			user.setToken("");
			user.setEnabled(true);
			userDao.saveAndFlush(user);
		}
		return res;
	}

	@Override
	public List<UserProfile> getUsersList() {
		
		List<UserProfile> beanList = new ArrayList<UserProfile>();
		
		List<User> list = userDao.findAll();
		
		for(User u : list) {
			UserProfile p = new UserProfile(u);
			beanList.add(p);
		}
		
		return beanList;
		
	}

}
