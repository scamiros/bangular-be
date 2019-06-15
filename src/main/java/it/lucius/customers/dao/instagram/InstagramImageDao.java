package it.lucius.customers.dao.instagram;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.lucius.customers.models.instagram.InstagramImage;
import it.lucius.customers.models.instagram.InstagramMedia;

public interface InstagramImageDao extends JpaRepository<InstagramImage, Integer> {

	List<InstagramImage> findByInstagramMediaOrderByOffSlide(InstagramMedia media);
	InstagramImage findByUri(String uri);
}
