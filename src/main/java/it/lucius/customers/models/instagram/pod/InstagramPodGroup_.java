package it.lucius.customers.models.instagram.pod;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import it.lucius.customers.models.User;

@StaticMetamodel(InstagramPodGroup.class)
public class InstagramPodGroup_ {

	public static volatile SingularAttribute<InstagramPodGroup, Integer> idPodGroup;
	public static volatile SingularAttribute<InstagramPodGroup, String> groupName;
	public static volatile SingularAttribute<InstagramPodGroup, User> user;
}
