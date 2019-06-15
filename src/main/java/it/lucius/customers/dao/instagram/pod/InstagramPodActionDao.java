package it.lucius.customers.dao.instagram.pod;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import it.lucius.customers.models.instagram.pod.InstagramPodAction;
import it.lucius.customers.models.instagram.pod.InstagramPodContent;

public interface InstagramPodActionDao extends JpaRepository<InstagramPodAction, Integer>, JpaSpecificationExecutor<InstagramPodAction> {

	List<InstagramPodAction> findByInstagramPodContent(InstagramPodContent content);
	InstagramPodAction findByIdPodAction(int id);
};
