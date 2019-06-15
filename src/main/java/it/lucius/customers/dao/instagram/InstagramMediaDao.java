package it.lucius.customers.dao.instagram;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.lucius.customers.models.instagram.InstagramMedia;

public interface InstagramMediaDao extends JpaRepository<InstagramMedia, Integer> {

	InstagramMedia findByIdInstagramMedia(String id);
	List<InstagramMedia> findByIdInstagram(Long idInstagram);
	List<InstagramMedia> findFirst10ByIdInstagramOrderByLikesCountDesc(Long idInstagram);
	List<InstagramMedia> findFirst10ByIdInstagramOrderByCommentsCountDesc(Long idInstagram);
	List<InstagramMedia> findFirst5ByIdInstagramOrderByLikesCountDesc(Long idInstagram);
	List<InstagramMedia> findFirst5ByIdInstagramOrderByCommentsCountDesc(Long idInstagram);
	List<InstagramMedia> findFirst12ByIdInstagramOrderByDtMediaDesc(Long idInstagram);
	InstagramMedia findFirst1ByIdInstagramOrderByDtMediaDesc(Long idInstagram);
}
