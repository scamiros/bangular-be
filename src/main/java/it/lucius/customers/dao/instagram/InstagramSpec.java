package it.lucius.customers.dao.instagram;
import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import it.lucius.customers.models.instagram.InstastatsDay;
import it.lucius.customers.models.instagram.InstastatsDay_;

public class InstagramSpec {

	public static Specification<InstastatsDay> findByDate(final Long idInstagram, final Date from, final Date to){
		return new Specification<InstastatsDay>() {

			@Override
			public Predicate toPredicate(Root<InstastatsDay> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				Predicate mainPredicate = root.isNotNull();
				
				Predicate predUser = cb.equal(root.get(InstastatsDay_.idInstagram), idInstagram);
				
				mainPredicate = cb.and(mainPredicate, predUser);
				
				if(from != null) {
					Predicate datePredicate = 
							cb.greaterThanOrEqualTo(root.get(InstastatsDay_.dtStats), from);
					
					mainPredicate = cb.and(mainPredicate, datePredicate);
				}
				
				if(to != null) {
					Predicate upperBoundPredicate = 
							cb.lessThanOrEqualTo(root.get(InstastatsDay_.dtStats), to);
					mainPredicate = cb.and(mainPredicate, upperBoundPredicate);
				}
				return mainPredicate;
			}
		};
	}
}
