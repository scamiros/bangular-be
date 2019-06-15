package it.lucius.customers.dao.instagram.pod;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import it.lucius.customers.bean.SearchBean;
import it.lucius.customers.models.User;
import it.lucius.customers.models.instagram.pod.InstagramPodUser;
import it.lucius.customers.models.instagram.pod.InstagramPodUser_;
import it.lucius.customers.util.SearchUtil;

public class InstagramPodUserSpec {

	public static Specification<InstagramPodUser> findByUserAndCriteria(
			final User user, final SearchBean search){
		return new Specification<InstagramPodUser>() {

			@Override
			public Predicate toPredicate(Root<InstagramPodUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				Predicate mainPredicate = root.isNotNull();
				
				Predicate predUser = cb.equal(root.get(InstagramPodUser_.user), user);
				
				mainPredicate = cb.and(mainPredicate, predUser);
				
				Predicate predField = cb.like(root.get(InstagramPodUser_.username), 
						SearchUtil.fullSearch(search.getSearch()));
				
				mainPredicate = cb.and(mainPredicate, predField);
	
				return mainPredicate;
			}
		};
	}
}
