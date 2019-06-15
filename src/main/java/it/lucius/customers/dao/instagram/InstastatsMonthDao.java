package it.lucius.customers.dao.instagram;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.lucius.customers.models.instagram.InstastatsMonth;

public interface InstastatsMonthDao extends JpaRepository<InstastatsMonth, Integer> {

	InstastatsMonth findByIdInstagramAndDtStats(Long idInstagram, Date dt);
	List<InstastatsMonth> findByIdInstagramAndDtStatsBetweenOrderByDtStats(Long idInstagram, Date from, Date to);
}
