package it.lucius.customers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.lucius.customers.bean.UserPrincipal;
import it.lucius.customers.dao.UserDao;
import it.lucius.customers.models.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserDao userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByUsername(username);
	
		if (user == null) {
			throw new UsernameNotFoundException("No user found. Username tried: " + username);
		}
		
		return new UserPrincipal(user);
	}
}