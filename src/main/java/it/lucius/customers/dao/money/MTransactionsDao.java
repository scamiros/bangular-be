package it.lucius.customers.dao.money;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.lucius.customers.models.money.MoneyDashboard;
import it.lucius.customers.models.money.MoneyTransaction;

public interface MTransactionsDao extends JpaRepository<MoneyTransaction, Integer> {

	List<MoneyTransaction> findByMoneyCategoryMoneyDashboardAndDtTransactionBetweenOrderByDtTransactionDesc(MoneyDashboard d, Date from, Date to);
}
