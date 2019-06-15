package it.lucius.customers.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.util.RedirectUrlBuilder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import it.lucius.customers.bean.ResponseBean;
import it.lucius.customers.bean.UserProfile;
import it.lucius.customers.bean.money.MTransactionBean;
import it.lucius.customers.config.ServicesConstant;
import it.lucius.customers.dao.UserDao;
import it.lucius.customers.models.AuthToken;
import it.lucius.customers.models.User;
import it.lucius.customers.security.TokenHelper;
import it.lucius.customers.service.UserService;
import it.lucius.customers.util.Cripto;

@RestController
@RequestMapping("/pb")
public class AuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenHelper jwtTokenUtil;

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private Cripto cripto;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<AuthToken> login(@RequestBody User loginUser, HttpSession session) throws AuthenticationException {
		
		ResponseEntity<AuthToken> res = null;
		
		try {
			
			final Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			final User user = userDao.findByUsername(loginUser.getUsername());
			final String token = jwtTokenUtil.generateToken(user.getUsername());
			
			session.setAttribute(ServicesConstant.USERAPP_CURRENT, user);
			res = ResponseEntity.ok(new AuthToken(token, user));
			
		} catch (AuthenticationException e) {
			
			res = ResponseEntity.ok(new AuthToken(null, null));
		}
		
		return res;
	}
	
	@RequestMapping("/signup")
    public ResponseEntity<ResponseBean> signup(HttpServletRequest request,
    		@RequestBody UserProfile bean) {
        
		ResponseBean d = userService.signup(request, bean);
		
		return ResponseEntity.ok(d);
				
    }
	
	@RequestMapping("/confirmsignup")
    public ModelAndView confirmSignup(HttpServletRequest request,
    		@RequestParam(value = "username", required = true) String username,
    		@RequestParam(value = "token", required = true) String token,
    		ModelMap model) {
        
		ResponseBean d = userService.confirmSignup(username, token);
		model.addAttribute("attribute", "confirmSignup");
		model.addAttribute("message", d.getMessage());
		
		String url = "/http:localhost:4200/#/login";
		
		return new ModelAndView("redirect:" + url, model);
				
    }

}
