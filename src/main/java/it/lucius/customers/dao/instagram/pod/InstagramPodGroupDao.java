package it.lucius.customers.dao.instagram.pod;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.lucius.customers.models.User;
import it.lucius.customers.models.instagram.pod.InstagramPodGroup;

public interface InstagramPodGroupDao extends JpaRepository<InstagramPodGroup, Integer> {

	List<InstagramPodGroup> findByUserAndEnabledTrue(User users);
}
