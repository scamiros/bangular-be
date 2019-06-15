package it.lucius.customers.controller.instagram;

import java.io.IOException;
import java.net.URI;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.lucius.customers.bean.UserProfile;
import it.lucius.customers.bean.charts.ChartsDatasLong;
import it.lucius.customers.bean.instagram.InstaSnapBean;
import it.lucius.customers.bean.instagram.InstaStatsself;
import it.lucius.customers.bean.instagram.InstragramUserBean;
import it.lucius.customers.config.ServicesConstant;
import it.lucius.customers.models.instagram.InstagramMedia;
import it.lucius.customers.service.instagram.InstagramService;
import it.lucius.customers.util.Cripto;
import it.lucius.customers.util.InstagramUtil;
import it.lucius.instagram.auth.InstagramAuthentication;
import it.lucius.instagram.model.Media;

@RestController
@RequestMapping("/pr/inst")
public class InstragramController {

	@Autowired
	InstagramService instService;
	
	@Autowired
	private Cripto cripto;

	@RequestMapping("/addInstaUser")
	public void addInstaUser(HttpServletRequest req, HttpServletResponse resp,
			HttpSession session) throws ServletException, IOException {

		UserProfile u = (UserProfile) req.getSession().getAttribute(ServicesConstant.USERAPP_CURRENT);
		InstagramAuthentication auth = InstagramUtil.getAuthUrl();
		session.setAttribute("" + u.getId(), auth);
//		UserProfile u = (UserProfile) req.getSession().getAttribute(ServicesConstant.USERAPP_CURRENT);
//		InstragramUserBean user = instService.getInstagramUserByUsername(u.getId(), username);
//		InstagramAuthentication s = null;
//		AccessToken token = null;
//
//		if (user.getToken() != null) {
//			token = new AccessToken(user.getToken());
//			
//			InstagramSession session = new InstagramSession(token);
//			try {
//				User my = session.getUserSelf();
//				int id = my.getId();
//			} catch (InstagramException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			  
//		} else {
//			InstagramUtil.getAuthUrl(user.getUsername());
//			try {
//				Thread.sleep(5000);
//			} catch (InterruptedException e1) {
//				e1.printStackTrace();
//			}
//			
//			URL obj = new URL(UriFactory.Auth.GET_ACCESS_TOKEN_INTERNAL + "/?user=" + user.getUsername());
//			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//			// optional default is GET
//			con.setRequestMethod("GET");
//			// add request header
//			con.setRequestProperty("User-Agent", "Mozilla/5.0");
//			int responseCode = con.getResponseCode();
//			System.out.println("\nSending 'GET' request to URL : " + obj);
//			System.out.println("Response Code : " + responseCode);
//			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//			String inputLine;
//			StringBuffer response = new StringBuffer();
//			while ((inputLine = in.readLine()) != null) {
//				response.append(inputLine);
//			}
//			in.close();
//			// print in String
//			System.out.println(response.toString());
//			// Read JSON response and print
//			JSONObject myResponse = null;
//			try {
//				myResponse = new JSONObject(response.toString());
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			System.out.println("result after Reading JSON Response");
//			try {
//				String access_token = myResponse.getString("access_token");
//				new AccessToken(access_token);
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//
//		return ResponseEntity.ok(user);
	}

	@RequestMapping("/addInstaUserCallBack")
	public ResponseEntity<Void> addInstaUserCallBack(HttpServletRequest req, HttpServletResponse resp,
			HttpSession session, @RequestParam("token") String token) throws ServletException, IOException {
		
		UserProfile u = (UserProfile) req.getSession().getAttribute(ServicesConstant.USERAPP_CURRENT);
		instService.saveUserToken(u.getId(), token);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("/private/insta.profile"));
		return new ResponseEntity<Void>(headers, HttpStatus.MOVED_PERMANENTLY);
		
	}
	
	@RequestMapping("/getInstaProfile")
	public ResponseEntity<InstragramUserBean> getInstaProfile(HttpServletRequest req, HttpServletResponse resp,
			HttpSession session) throws ServletException, IOException {

		cripto.getSessionUser(session);
		InstragramUserBean user = instService.getInstaProfile(session);

		return ResponseEntity.ok(user);
	}
	
	@RequestMapping("/getInstaMedia/{idMedia}")
	public ResponseEntity<Media> getInstaMedia(HttpServletRequest req, HttpServletResponse resp,
			HttpSession session, @PathVariable String idMedia) throws ServletException, IOException {

		cripto.getSessionUser(session);
		Media media = instService.getInstaMedia(session, idMedia);

		return ResponseEntity.ok(media);
	}
	
	@RequestMapping("/getInstaSnap")
	public ResponseEntity<InstaSnapBean> getInstaSnap(HttpServletRequest req, HttpServletResponse resp,
			HttpSession session) throws ServletException, IOException {

		it.lucius.customers.models.User user = cripto.getSessionUser(session);
		
		InstaSnapBean bean = instService.getInstaSnap(session, user);

		return ResponseEntity.ok(bean);

	}
	
	@RequestMapping("/getInstaUserself/{period}")
	public ResponseEntity<InstaStatsself> getInstaUserself(HttpServletRequest req, HttpServletResponse resp,
			HttpSession session, @PathVariable String period) throws ServletException, IOException {

		cripto.getSessionUser(session);
		
		InstaStatsself bean = instService.getInstaStasself(session, period);
		return ResponseEntity.ok(bean);

	}
	
	@RequestMapping("/getInstaStats/{metric}/{period}")
    public ResponseEntity<ChartsDatasLong> getInstaStats(HttpServletRequest req, HttpServletResponse resp,
			HttpSession session, @PathVariable String metric, @PathVariable String period) {
        
		cripto.getSessionUser(session);
		ResponseEntity<ChartsDatasLong> res = ResponseEntity.ok(instService.getInstaStats(session, metric, period));
		
		return res;
				
    }
	
	@RequestMapping("/getInstaTopTen/{metric}")
	public ResponseEntity<InstragramUserBean> getInstaTopTen(HttpServletRequest req, HttpServletResponse resp,
			HttpSession session, @PathVariable String metric) throws ServletException, IOException {

		cripto.getSessionUser(session);
		InstragramUserBean user = instService.getInstaTopTen(session, metric);

		return ResponseEntity.ok(user);
	}
	
	@RequestMapping("/getInstaLastMedia")
	public ResponseEntity<InstagramMedia> getInstaLastMedia(HttpServletRequest req, HttpServletResponse resp,
			HttpSession session) throws ServletException, IOException {

		cripto.getSessionUser(session);
		InstagramMedia media = instService.getInstaLastMedia(session);

		return ResponseEntity.ok(media);
	}
}
