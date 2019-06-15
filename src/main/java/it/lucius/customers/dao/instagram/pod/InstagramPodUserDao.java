package it.lucius.customers.dao.instagram.pod;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import it.lucius.customers.models.User;
import it.lucius.customers.models.instagram.pod.InstagramPodUser;

public interface InstagramPodUserDao extends JpaRepository<InstagramPodUser, Integer>, 
	JpaSpecificationExecutor<InstagramPodUser> {

	List<InstagramPodUser> findByUser(User users);
}
