package it.lucius.customers.dao.instagram;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.lucius.customers.models.instagram.InstastatsDay;

public interface InstastatsDayDao extends JpaRepository<InstastatsDay, Integer> {

	InstastatsDay findByIdInstagramAndDtStats(Long idInstagram, Date dt);
	List<InstastatsDay> findByIdInstagramAndDtStatsBetweenOrderByDtStats(Long idInstagram, Date from, Date to);
}
