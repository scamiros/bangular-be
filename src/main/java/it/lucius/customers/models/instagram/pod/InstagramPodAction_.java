package it.lucius.customers.models.instagram.pod;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(InstagramPodAction.class)
public class InstagramPodAction_ {

	public static volatile SingularAttribute<InstagramPodAction, Integer> idPodAction;
	
	public static volatile SingularAttribute<InstagramPodAction, Integer> comment;
	public static volatile SingularAttribute<InstagramPodAction, Integer> like;
	public static volatile SingularAttribute<InstagramPodAction, InstagramPodContent> instagramPodContent;
	public static volatile SingularAttribute<InstagramPodAction, InstagramPodMember> instagramPodMember;
}
