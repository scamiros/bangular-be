package it.lucius.customers.dao.instagram.pod;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import it.lucius.customers.models.User;
import it.lucius.customers.models.instagram.pod.InstagramPodContent;
import it.lucius.customers.models.instagram.pod.InstagramPodContent_;
import it.lucius.customers.models.instagram.pod.InstagramPodGroup_;
import it.lucius.customers.models.instagram.pod.InstagramPodMember_;

public class InstagramPodContentSpec {

	public static Specification<InstagramPodContent> findByUserGroupAndDate(final User user, final Integer group, final Date from, final Date to){
		return new Specification<InstagramPodContent>() {

			@Override
			public Predicate toPredicate(Root<InstagramPodContent> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				Predicate mainPredicate = root.isNotNull();
				
				Predicate predUser = cb.equal(root.join(InstagramPodContent_.instagramPodMember)
						.join(InstagramPodMember_.instagramPodGroup)
						.join(InstagramPodGroup_.user), user);
				
				mainPredicate = cb.and(mainPredicate, predUser);
				
				Predicate predGroup = cb.equal(root.join(InstagramPodContent_.instagramPodMember)
						.join(InstagramPodMember_.instagramPodGroup)
						.get(InstagramPodGroup_.idPodGroup), group);
				
				mainPredicate = cb.and(mainPredicate, predGroup);
	
				if(from != null && to != null) {
					Predicate datePredicate = 
							cb.greaterThanOrEqualTo(root.get(InstagramPodContent_.dtPosted), from);
					Predicate upperBoundPredicate = 
							cb.lessThanOrEqualTo(root.get(InstagramPodContent_.dtPosted), to);
					datePredicate = cb.and(datePredicate, upperBoundPredicate);
					mainPredicate = cb.and(mainPredicate, datePredicate);
				}
				
				return mainPredicate;
			}
		};
	}
}
