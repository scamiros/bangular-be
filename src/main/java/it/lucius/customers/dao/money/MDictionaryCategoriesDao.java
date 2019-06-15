package it.lucius.customers.dao.money;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.lucius.customers.enumeration.MoneyCategoryType;
import it.lucius.customers.models.User;
import it.lucius.customers.models.money.MoneyDictionaryCategory;

public interface MDictionaryCategoriesDao extends JpaRepository<MoneyDictionaryCategory, Integer> {

	List<MoneyDictionaryCategory> findByUser(User u);
	MoneyDictionaryCategory findByUserAndLabelIgnoreCaseAndType(User u, String label, MoneyCategoryType type);
}
