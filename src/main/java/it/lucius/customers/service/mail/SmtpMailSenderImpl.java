package it.lucius.customers.service.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import it.lucius.customers.bean.UserProfile;
import it.lucius.customers.util.Cripto;

@Service
public class SmtpMailSenderImpl implements SmtpMailSender {

	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendConfirmationLink(HttpServletRequest request, UserProfile user) throws MessagingException {
		
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper;
		// SSL Certhificate.
		helper = new MimeMessageHelper(message, true);
		// Multipart messages.
		helper.setSubject("Confirm registration");
		helper.setTo(user.getEmail());
		
		//String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		String url = "http://localhost:8080/customers/pb";
		
		String link = "<a style='text-decoration: none; color: #0DB8DF; font-weight: bold;' href=\"" + url + 
    			"/confirmsignup?username=" + user.getUsername() + 
				"&token=" + user.getToken() + "\" target=\"_blank\">Confirm account</a>";
    	
    	message.setContent(link, "text/html");
		
		javaMailSender.send(message);
    }
}
