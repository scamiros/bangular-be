package it.lucius.customers.models.instagram.pod;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import it.lucius.customers.models.User;

@StaticMetamodel(InstagramPodUser.class)
public class InstagramPodUser_ {

	public static volatile SingularAttribute<InstagramPodUser, Integer> idPodUser;
	public static volatile SingularAttribute<InstagramPodUser, String> profilePicture;
	public static volatile SingularAttribute<InstagramPodUser, String> username;
	public static volatile SingularAttribute<InstagramPodUser, User> user;
}
