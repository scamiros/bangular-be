package it.lucius.customers.dao.instagram.pod;

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
import it.lucius.customers.models.instagram.pod.InstagramPodAction;
import it.lucius.customers.models.instagram.pod.InstagramPodAction_;
import it.lucius.customers.models.instagram.pod.InstagramPodContent;
import it.lucius.customers.models.instagram.pod.InstagramPodMember_;

@Repository
public class InstagramPodActionDaoCustom {

	@PersistenceContext
	private EntityManager em;
	
	public List<Tuple> getInstaPodStats(
			Specification<InstagramPodAction> r, String metric) {
        
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();
		Root<InstagramPodAction> root = criteriaQuery.from(InstagramPodAction.class);
	
		//Where specification
		Predicate predicate = r.toPredicate(root, criteriaQuery, criteriaBuilder);
		criteriaQuery.where(predicate);
		Path<Integer> pathMember = root.join(InstagramPodAction_.instagramPodMember).get(InstagramPodMember_.idPodMember);
		
		Expression<Integer> metricExpr = null;
		switch (metric) {
		
		case ServicesConstant.INST_POD_METRIC_LIKE:
			//Sum likes
			metricExpr = criteriaBuilder.sum(root.get(InstagramPodAction_.like).as(Integer.class));
			break;
			
		case ServicesConstant.INST_POD_METRIC_COMMENTS:
			//Sum comment
			metricExpr = criteriaBuilder.sum(root.get(InstagramPodAction_.comment).as(Integer.class));
			break;

		default:
			break;
		}
		
		criteriaQuery.multiselect(
				pathMember.alias(InstagramPodAction_.instagramPodMember.getName()), 
				metricExpr.alias(metric));
		criteriaQuery.groupBy(pathMember);
		criteriaQuery.orderBy(criteriaBuilder.desc(metricExpr));
		TypedQuery<Tuple> typedQuery = em.createQuery(criteriaQuery);
		List<Tuple> listActual = typedQuery.getResultList();

		return listActual;
	}
	
	public List<Tuple> getInstaPodStatsSumLike(
			Specification<InstagramPodAction> r, String metric) {
        
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();
		Root<InstagramPodAction> root = criteriaQuery.from(InstagramPodAction.class);
	
		//Where specification
		Predicate predicate = r.toPredicate(root, criteriaQuery, criteriaBuilder);
		criteriaQuery.where(predicate);
		Path<Integer> pathMember = root.join(InstagramPodAction_.instagramPodMember).get(InstagramPodMember_.idPodMember);
		
		Expression<Integer> metricExpr = null;
		switch (metric) {
		
		case ServicesConstant.INST_POD_METRIC_LIKE:
			//Sum likes
			metricExpr = criteriaBuilder.sum(root.get(InstagramPodAction_.like).as(Integer.class));
			break;
			
		case ServicesConstant.INST_POD_METRIC_COMMENTS:
			//Sum comment
			metricExpr = criteriaBuilder.sum(root.get(InstagramPodAction_.comment).as(Integer.class));
			break;

		default:
			break;
		}
		
		criteriaQuery.multiselect(
				pathMember.alias(InstagramPodAction_.instagramPodMember.getName()), 
				metricExpr.alias(metric));
		criteriaQuery.groupBy(pathMember);
		criteriaQuery.orderBy(criteriaBuilder.desc(metricExpr));
		TypedQuery<Tuple> typedQuery = em.createQuery(criteriaQuery);
		List<Tuple> listActual = typedQuery.getResultList();

		return listActual;
	}
	
	public Long getInstaPodActionCount(
			Specification<InstagramPodContent> r) {
	
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Long> queryTotal = criteriaBuilder.createQuery(Long.class);
		Root<InstagramPodContent> countRoot = queryTotal.from(InstagramPodContent.class);		
		Predicate predicate = r.toPredicate(countRoot, queryTotal, criteriaBuilder);	
		queryTotal.where(predicate);
		queryTotal.select(criteriaBuilder.count(countRoot));
		Long numero = em.createQuery(queryTotal).getSingleResult();
		
		return numero;
	}
	
	public List<Tuple> getInstaPodActionSum(
			Specification<InstagramPodAction> r, String metric) {
	
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();
		Root<InstagramPodAction> root = criteriaQuery.from(InstagramPodAction.class);
	
		//Where specification
		Predicate predicate = r.toPredicate(root, criteriaQuery, criteriaBuilder);
		criteriaQuery.where(predicate);

		Expression<Integer> metricExpr = null;
		switch (metric) {
		
		case ServicesConstant.INST_POD_METRIC_LIKE:
			//Sum likes
			metricExpr = criteriaBuilder.sum(root.get(InstagramPodAction_.like).as(Integer.class));
			break;
			
		case ServicesConstant.INST_POD_METRIC_COMMENTS:
			//Sum comment
			metricExpr = criteriaBuilder.sum(root.get(InstagramPodAction_.comment).as(Integer.class));
			break;

		default:
			break;
		}
		
		criteriaQuery.multiselect(metricExpr.alias(metric));
		
		TypedQuery<Tuple> typedQuery = em.createQuery(criteriaQuery);
		List<Tuple> listActual = typedQuery.getResultList();

		return listActual;
	}
}
