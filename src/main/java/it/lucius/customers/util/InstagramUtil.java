package it.lucius.customers.util;

import java.text.DecimalFormat;

import it.lucius.instagram.auth.InstagramAuthentication;
import it.lucius.instagram.exception.InstagramException;

public class InstagramUtil {

	private static String[] suffix = new String[]{""," MILA", " MLN", " B", " t"};
	private static int MAX_LENGTH = 4;

	public static String formatSocialNumber(long number) {
	    String r = new DecimalFormat("##0E0").format(number);
	    r = r.replaceAll("E[0-9]", suffix[Character.getNumericValue(r.charAt(r.length() - 1)) / 3]);
	    
	    return r;
	}
	
	public static InstagramAuthentication getAuthUrl() {
		String auth = "";
		InstagramAuthentication s = new InstagramAuthentication();
		try {
			s.setClientId("7957847266b843e5af8922fd8b8e7184")
				.setClientSecret("2818f29e4f524c6f91f7757e3710fc32")
				.setRedirectUri("http://www.studiodentisticopolaratraina.it/appes_call.php/");
			
			s.getAuthorizationUri();
			
		} catch (InstagramException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
}
