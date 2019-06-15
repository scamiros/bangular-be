package it.lucius.customers.dao.instagram;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import it.lucius.customers.models.instagram.InstastatsWeek;

public interface InstastatsWeekDao extends JpaRepository<InstastatsWeek, Integer> {

	InstastatsWeek findByIdInstagramAndDtStats(Long idInstagram, Date dt);
}
