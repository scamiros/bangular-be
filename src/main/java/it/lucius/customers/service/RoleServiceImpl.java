package it.lucius.customers.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.lucius.customers.bean.RoleBean;
import it.lucius.customers.dao.RoleDao;
import it.lucius.customers.models.Role;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleDao roleDao;
	
	@Override
	public List<RoleBean> getRoles() {

		List<Role> roles =  roleDao.findAll();
		List<RoleBean> rolesBean = new ArrayList<>();
		
		for(Role role : roles) {
			rolesBean.add(new RoleBean(role));
		}
		return rolesBean;
	}

}
