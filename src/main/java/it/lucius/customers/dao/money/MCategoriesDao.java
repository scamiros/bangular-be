package it.lucius.customers.dao.money;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.lucius.customers.models.User;
import it.lucius.customers.models.money.MoneyCategory;
import it.lucius.customers.models.money.MoneyDashboard;


public interface MCategoriesDao extends JpaRepository<MoneyCategory, Integer> {

	List<MoneyCategory> findByMoneyDashboard(MoneyDashboard dashboard);
}
