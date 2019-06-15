package it.lucius.customers.models.instagram.pod;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(InstagramPodContent.class)
public class InstagramPodContent_ {

	public static volatile SingularAttribute<InstagramPodContent, Integer> idPodContent;
	public static volatile SingularAttribute<InstagramPodContent, Date> dtPosted;
	public static volatile SingularAttribute<InstagramPodContent, String> contentUri;
	public static volatile SingularAttribute<InstagramPodContent, InstagramPodMember> instagramPodMember;
}
