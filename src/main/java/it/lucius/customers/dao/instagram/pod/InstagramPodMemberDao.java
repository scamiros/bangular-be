package it.lucius.customers.dao.instagram.pod;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.lucius.customers.models.instagram.pod.InstagramPodMember;

public interface InstagramPodMemberDao extends JpaRepository<InstagramPodMember, Integer> {

	List<InstagramPodMember> findByInstagramPodGroupIdPodGroupOrderByInstagramPodUserUsernameAsc(Integer idGroup);
}
