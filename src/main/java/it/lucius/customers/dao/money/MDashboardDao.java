package it.lucius.customers.dao.money;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.lucius.customers.models.User;
import it.lucius.customers.models.money.MoneyDashboard;

public interface MDashboardDao extends JpaRepository<MoneyDashboard, Integer>{

	List<MoneyDashboard> findByUser(User u);
	MoneyDashboard findByUserAndDtDashboard(User u, Date dt);
	MoneyDashboard findByUserAndMoneyCategoriesMoneyTransactionsDtTransactionBetween(User u, Date from, Date to);
}
