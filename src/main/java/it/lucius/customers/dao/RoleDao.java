package it.lucius.customers.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import it.lucius.customers.models.Role;

public interface RoleDao extends JpaRepository<Role, Integer> {

	Role findByRole(String role);
}
