package it.lucius.customers.dao.instagram.pod;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import it.lucius.customers.models.User;
import it.lucius.customers.models.instagram.pod.InstagramPodAction;
import it.lucius.customers.models.instagram.pod.InstagramPodAction_;
import it.lucius.customers.models.instagram.pod.InstagramPodContent_;
import it.lucius.customers.models.instagram.pod.InstagramPodGroup_;
import it.lucius.customers.models.instagram.pod.InstagramPodMember_;

public class InstagramPodActionSpec {

	public static Specification<InstagramPodAction> findByUserGroupAndDate(
			final User user, final Integer group, final Date from, final Date to, final Integer idPodContent){
		return new Specification<InstagramPodAction>() {

			@Override
			public Predicate toPredicate(Root<InstagramPodAction> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				Predicate mainPredicate = root.isNotNull();
				
				Predicate predUser = cb.equal(root.join(InstagramPodAction_.instagramPodMember)
						.join(InstagramPodMember_.instagramPodGroup)
						.join(InstagramPodGroup_.user), user);
				
				mainPredicate = cb.and(mainPredicate, predUser);
				
				Predicate predGroup = cb.equal(root.join(InstagramPodAction_.instagramPodMember)
						.join(InstagramPodMember_.instagramPodGroup)
						.get(InstagramPodGroup_.idPodGroup), group);
				
				mainPredicate = cb.and(mainPredicate, predGroup);
	
				if(idPodContent != null) {
					
					Predicate predContent = cb.equal(root.join(InstagramPodAction_.instagramPodContent)
							.get(InstagramPodContent_.idPodContent), idPodContent);
					
					mainPredicate = cb.and(mainPredicate, predContent);
				}
				
				if(from != null) {
					Predicate datePredicate = 
							cb.greaterThanOrEqualTo(root.join(InstagramPodAction_.instagramPodContent)
							.get(InstagramPodContent_.dtPosted), from);
					
					mainPredicate = cb.and(mainPredicate, datePredicate);
				}
				
				if(to != null) {
					Predicate upperBoundPredicate = 
							cb.lessThanOrEqualTo(root.join(InstagramPodAction_.instagramPodContent)
							.get(InstagramPodContent_.dtPosted), to);
					mainPredicate = cb.and(mainPredicate, upperBoundPredicate);
				}
				return mainPredicate;
			}
		};
	}
}
