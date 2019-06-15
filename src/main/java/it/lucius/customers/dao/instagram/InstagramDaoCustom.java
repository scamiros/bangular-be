package it.lucius.customers.dao.instagram;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import it.lucius.customers.config.ServicesConstant;
import it.lucius.customers.models.instagram.InstastatsDay;
import it.lucius.customers.models.instagram.InstastatsDay_;

@Repository
public class InstagramDaoCustom {

	@PersistenceContext
	private EntityManager em;
	
	public List<Tuple> getInstastats(
			Specification<InstastatsDay> r, String metric) {
        
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();
		Root<InstastatsDay> root = criteriaQuery.from(InstastatsDay.class);
	
		Predicate predicate = r.toPredicate(root, criteriaQuery, criteriaBuilder);
		criteriaQuery.where(predicate);
		
		Path<Date> datePath = root.get(InstastatsDay_.dtStats);
		Expression<Integer> month = criteriaBuilder.function("last_day", Integer.class, datePath);
		
		Expression<Integer> metricExpr = null;
		switch (metric) {
		
		case ServicesConstant.METRIC_LIKES:
			//Sum likes
			metricExpr = root.get(InstastatsDay_.likesCount).as(Integer.class);
			break;
			
		case ServicesConstant.METRIC_FOLLOWERS:
			//Sum followers
			metricExpr = root.get(InstastatsDay_.followingCount).as(Integer.class);
			break;

		default:
			break;
		}
		
		criteriaQuery.multiselect(month.alias("month"), metricExpr.alias(metric));
//		criteriaQuery.groupBy(year, month);
		criteriaQuery.orderBy(criteriaBuilder.desc(month));
		
		TypedQuery<Tuple> typedQuery = em.createQuery(criteriaQuery);
		List<Tuple> listActual = typedQuery.getResultList();

		return listActual;
	}
}
