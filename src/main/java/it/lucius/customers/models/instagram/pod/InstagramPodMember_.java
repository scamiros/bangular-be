package it.lucius.customers.models.instagram.pod;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(InstagramPodMember.class)
public class InstagramPodMember_ {

	public static volatile SingularAttribute<InstagramPodMember, Integer> idPodMember;
	public static volatile SingularAttribute<InstagramPodMember, String> profilePicture;
	public static volatile SingularAttribute<InstagramPodMember, String> username;
	public static volatile SingularAttribute<InstagramPodMember, InstagramPodGroup> instagramPodGroup;
}
