package it.lucius.customers.service.mail;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import it.lucius.customers.bean.UserProfile;

public interface SmtpMailSender {
	
	void sendConfirmationLink(HttpServletRequest request, UserProfile user) throws MessagingException;

}
