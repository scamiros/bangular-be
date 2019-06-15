package it.lucius.customers.models.instagram;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(InstastatsDay.class)
public class InstastatsDay_ {

	public static volatile SingularAttribute<InstastatsDay, Integer> idStats;
	public static volatile SingularAttribute<InstastatsDay, Date> dtStats;
	public static volatile SingularAttribute<InstastatsDay, Long> followersCount;
	public static volatile SingularAttribute<InstastatsDay, Long> followingCount;
	public static volatile SingularAttribute<InstastatsDay, Long> mediaCount;
	public static volatile SingularAttribute<InstastatsDay, Long> likesCount;
	public static volatile SingularAttribute<InstastatsDay, Long> commentsCount;
	public static volatile SingularAttribute<InstastatsDay, Long> idInstagram;
}
