package it.lucius.customers.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.lucius.customers.models.User;

public interface UserDao extends JpaRepository<User, Integer> {

    User findByUsername(String username);
    User findByEmail(String email);
    User findById(Integer idUser);
    User findByUsernameAndToken(String username, String token);

}
