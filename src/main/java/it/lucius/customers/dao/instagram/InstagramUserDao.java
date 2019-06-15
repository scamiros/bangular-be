package it.lucius.customers.dao.instagram;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.lucius.customers.models.instagram.InstagramUser;

public interface InstagramUserDao extends JpaRepository<InstagramUser, Integer> {

	InstagramUser findByIdInstagramUser(Long id);
	InstagramUser findByUserIdAndUsername(Integer idUser, String username);
	List<InstagramUser> findByUserId(Integer idUser);
	Optional<InstagramUser> findById(Integer id);
}
