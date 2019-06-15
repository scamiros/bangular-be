package it.lucius.customers.dao.instagram.pod;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.lucius.customers.models.instagram.pod.InstagramPodContent;

public interface InstagramPodContentDao extends JpaRepository<InstagramPodContent, Integer> {

	InstagramPodContent findByIdPodContent(int id);
	List<InstagramPodContent> findByInstagramPodMemberInstagramPodGroupIdPodGroupOrderByDtPostedDesc(Integer idGroup);
	List<InstagramPodContent> findByInstagramPodMemberIdPodMember(Integer idMember);
	List<InstagramPodContent> findByInstagramPodMemberIdPodMemberAndDtPostedBetween(Integer idMember, Date from, Date to);
	List<InstagramPodContent> findByInstagramPodMemberIdPodMemberAndDtPostedAfter(Integer idMember, Date from);
	List<InstagramPodContent> findByInstagramPodMemberIdPodMemberAndDtPostedBefore(Integer idMember, Date to);
}
