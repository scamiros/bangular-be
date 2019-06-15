package it.lucius.customers.util;

import java.security.MessageDigest;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import it.lucius.customers.bean.UserProfile;
import it.lucius.customers.config.ServicesConstant;
import it.lucius.customers.dao.UserDao;
import it.lucius.customers.dao.instagram.InstagramUserDao;
import it.lucius.customers.models.User;
import it.lucius.customers.models.instagram.InstagramUser;
import it.lucius.customers.service.UserService;
import it.lucius.instagram.InstagramSession;
import it.lucius.instagram.auth.AccessToken;
import it.lucius.instagram.exception.InstagramException;

@Component
public class Cripto {
	
	private static final String ALPHA_CAPS  = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALPHA   = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUM     = "0123456789";
    private static final String SPL_CHARS   = "!@#$%^&*";

    @Autowired
	UserService userService;
    
    @Autowired
	UserDao userDao;
    
    @Autowired
	InstagramUserDao instDao;
    
    public static char[] generatePswd(int minLen, int maxLen, int noOfCAPSAlpha, 
            int noOfDigits, int noOfSplChars) {
    	
        if(minLen > maxLen)
            throw new IllegalArgumentException("Min. Length > Max. Length!");
        
        if( (noOfCAPSAlpha + noOfDigits + noOfSplChars) > minLen )
            throw new IllegalArgumentException
            ("Min. Length should be atleast sum of (CAPS, DIGITS, SPL CHARS) Length!");
        
        Random rnd = new Random();
        int len = rnd.nextInt(maxLen - minLen + 1) + minLen;
        char[] pswd = new char[len];
        int index = 0;
        
        for (int i = 0; i < noOfCAPSAlpha; i++) {
            index = getNextIndex(rnd, len, pswd);
            pswd[index] = ALPHA_CAPS.charAt(rnd.nextInt(ALPHA_CAPS.length()));
        }
        
        for (int i = 0; i < noOfDigits; i++) {
            index = getNextIndex(rnd, len, pswd);
            pswd[index] = NUM.charAt(rnd.nextInt(NUM.length()));
        }
        
        for (int i = 0; i < noOfSplChars; i++) {
            index = getNextIndex(rnd, len, pswd);
            pswd[index] = SPL_CHARS.charAt(rnd.nextInt(SPL_CHARS.length()));
        }
        
        for(int i = 0; i < len; i++) {
            if(pswd[i] == 0) {
                pswd[i] = ALPHA.charAt(rnd.nextInt(ALPHA.length()));
            }
        }
        return pswd;
    }
     
    private static int getNextIndex(Random rnd, int len, char[] pswd) {
        
    	int index = rnd.nextInt(len);
        
    	while(pswd[index = rnd.nextInt(len)] != 0);
        
    	return index;
    }
	
	public static String sha256(String base) {
	    try{
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] hash = digest.digest(base.getBytes("UTF-8"));
	        StringBuffer hexString = new StringBuffer();

	        for (int i = 0; i < hash.length; i++) {
	            String hex = Integer.toHexString(0xff & hash[i]);
	            if(hex.length() == 1) hexString.append('0');
	            hexString.append(hex);
	        }

	        return hexString.toString();
	    } catch(Exception ex){
	       throw new RuntimeException(ex);
	    }
	}
	
	public static String rpHash(String value) {
		int hash = 5381;
		value = value.toUpperCase();
		
		for(int i = 0; i < value.length(); i++) {
			hash = ((hash << 5) + hash) + value.charAt(i);
		}
		return String.valueOf(hash);
	}
	
	public static String calculateCheckDigit(String toCalulate) {
		
		int sommaDispari = 0;
		int sommaPari = 0;

		for(int i = 0; i < toCalulate.length(); i++) {
			Character character = toCalulate.charAt(i);
			
			if((i + 1) % 2 == 1) {
				sommaDispari += Character.getNumericValue(character);
			} else {
				sommaPari += Character.getNumericValue(character);
			}
		}
		
		sommaPari = sommaPari * 11;
		
		int checkDigitNumeric = sommaDispari + sommaPari;
		
		String checkSomma = checkDigitNumeric + "";
		
		int sommaTotale = 0;
		
		for(int y = 0; y < checkSomma.length(); y++) {
			Character character = checkSomma.charAt(y);
			sommaTotale += Character.getNumericValue(character);
		}
		
		int check = sommaTotale % 10;
		return check + "";
	}
	
	public User getSessionUser(HttpSession session) {
		
		User u = (User) session.getAttribute(ServicesConstant.USERAPP_CURRENT);
		
		if(u == null) {
			UserDetails us = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserProfile user = userService.getUserProfileByUsername(us.getUsername());
			u = userDao.findByUsername(user.getUsername());
			session.setAttribute(ServicesConstant.USERAPP_CURRENT, u);
			
			Optional<InstagramUser> inst = instDao.findById(user.getLastInstaUser());
			AccessToken token = new AccessToken(inst.get().getToken());
			InstagramSession sessione = new InstagramSession(token);
			it.lucius.instagram.model.User instaUser = null;
			try {
				instaUser = sessione.getUserSelf();
			} catch (InstagramException e) {
				e.printStackTrace();
			}
			session.setAttribute(ServicesConstant.INSTAGRAM_CURRENT, instaUser);
			session.setAttribute(ServicesConstant.INSTAGRAM_SESSION, sessione);
		}
		
		return u;
		
	}
}
