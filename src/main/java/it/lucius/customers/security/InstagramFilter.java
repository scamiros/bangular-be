package it.lucius.customers.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.filter.GenericFilterBean;


public class InstagramFilter extends GenericFilterBean {
 
    @Override
    public void doFilter(
      ServletRequest request, 
      ServletResponse response,
      FilterChain chain) throws IOException, ServletException {
    	
    	HttpServletRequest req = (HttpServletRequest) request;

//		User u = (User) req.getSession().getAttribute(INSTAGRAM_CURRENT);
//		
//		if(u == null) {
//			
//			InstagramAuthentication s = new InstagramAuthentication();
//			try {
//				String auth = s.setClientId("7957847266b843e5af8922fd8b8e7184")
//					.setClientSecret("2818f29e4f524c6f91f7757e3710fc32")
//					.setRedirectUri("http://www.studiodentisticopolaratraina.it/apprun/controller.php")
//					.getAuthorizationUri();
//				
//			} catch (InstagramException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} 
		
        chain.doFilter(request, response);
    }
}