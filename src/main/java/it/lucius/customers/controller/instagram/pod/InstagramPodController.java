package it.lucius.customers.controller.instagram.pod;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.lucius.customers.bean.ResponseBean;
import it.lucius.customers.bean.SearchBean;
import it.lucius.customers.bean.charts.ChartsDatasLong;
import it.lucius.customers.bean.instagram.InstaPodAction;
import it.lucius.customers.bean.instagram.InstaPodChartBean;
import it.lucius.customers.bean.instagram.InstaPodContent;
import it.lucius.customers.bean.instagram.InstaPodGroup;
import it.lucius.customers.bean.instagram.InstaPodMember;
import it.lucius.customers.bean.instagram.InstaPodUser;
import it.lucius.customers.bean.instagram.InstagramPodGroupBean;
import it.lucius.customers.service.UserService;
import it.lucius.customers.service.instagram.InstagramPodServiceImpl;
import it.lucius.customers.util.Cripto;

@RestController
@RequestMapping("/pr/instpod")
public class InstagramPodController {

	@Autowired
	Cripto cripto;
	
	@Autowired
	InstagramPodServiceImpl podService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping("/getInstaPodGroups")
	public ResponseEntity<List<InstaPodGroup>> podGroups(HttpSession session) {
		
		it.lucius.customers.models.User user = cripto.getSessionUser(session);
		ResponseEntity<List<InstaPodGroup>> res = ResponseEntity.ok(podService.getPodListGroups(user));
		return res;
	}
	
	@RequestMapping("/getInstaPodGroup")
	public ResponseEntity<InstagramPodGroupBean> podMembers(@RequestBody InstaPodGroup group) {
		
		ResponseEntity<InstagramPodGroupBean> res = ResponseEntity.ok(podService.getPodGroup(group));
		
		return res;
	}
	
	@RequestMapping("/createInstaPodGroup")
	public ResponseEntity<ResponseBean> podCreateGroup(@RequestBody InstaPodGroup group, HttpSession session) {
		
		it.lucius.customers.models.User user = cripto.getSessionUser(session);
		
		ResponseBean d = podService.createPodGroup(
				user, group);
		
		return ResponseEntity.ok(d);
	}
	
	@RequestMapping("/createInstaPodMember")
	public ResponseEntity<ResponseBean> podCreateMember(@RequestBody InstaPodMember m) {
		
		ResponseBean d = podService.createPodMember(m);
		
		return ResponseEntity.ok(d);
	}
	
	@RequestMapping("/removeInstaPodMember")
	public ResponseEntity<ResponseBean> podRemoveMember(@RequestBody InstaPodMember m) {
		
		ResponseBean d = podService.removePodMember(m);
		
		return ResponseEntity.ok(d);
	}
	
	@RequestMapping("/postInstaPodUser")
	public ResponseEntity<ResponseBean> postPodInstaPodUser(@RequestBody InstaPodUser m, HttpSession session) {
		
		it.lucius.customers.models.User user = cripto.getSessionUser(session);
		ResponseBean d = podService.managePodUser(user, m);
		
		return ResponseEntity.ok(d);
	}
	
	@RequestMapping("/createInstaPodContent")
	public ResponseEntity<ResponseBean> podCreateContent(@RequestBody InstaPodContent m) {
		
		ResponseBean d = podService.createPodContent(m);
		
		return ResponseEntity.ok(d);
	}
	
	@RequestMapping("/postInstaPodAction")
	public ResponseEntity<ResponseBean> podPostAction(@RequestBody InstaPodAction action) {
		
		ResponseEntity<ResponseBean> res = ResponseEntity.ok(podService.postPodAction(action));
		
		return res;
	}
	
	@RequestMapping("/getInstaPodActions")
	public ResponseEntity<List<InstaPodAction>> podActions(@RequestBody InstaPodContent content) {
		
		ResponseEntity<List<InstaPodAction>> res = ResponseEntity.ok(podService.getPodActions(content));
		
		return res;
	}
	
	@RequestMapping("/getInstaPodStats")
    public ResponseEntity<ChartsDatasLong> getInstaPodStats(
    		HttpServletRequest req, HttpServletResponse resp, HttpSession session, 
    		@RequestBody InstaPodChartBean search) {
        
		cripto.getSessionUser(session);
		ResponseEntity<ChartsDatasLong> res = ResponseEntity.ok(
				podService.getInstaPodStats(session, search));
		
		return res;
				
    }
	
	@RequestMapping("/getInstaPodStars")
    public ResponseEntity<ChartsDatasLong> getInstaPodStars(
    		HttpServletRequest req, HttpServletResponse resp, HttpSession session, 
    		@RequestBody InstaPodChartBean search) {
        
		cripto.getSessionUser(session);
		ResponseEntity<ChartsDatasLong> res = ResponseEntity.ok(
				podService.getInstaPodStars(session, search));
		
		return res;
				
    }
	
	@RequestMapping("/getInstaPodUsers")
	public ResponseEntity<Page<InstaPodUser>> podUsers(@RequestBody SearchBean search, HttpSession session) {
		
		it.lucius.customers.models.User user = cripto.getSessionUser(session);
		ResponseEntity<Page<InstaPodUser>> res = ResponseEntity.ok(podService.getInstaPodUsers(user, search));
		return res;
	}
	
	@RequestMapping("/getInstaPodUsersList")
	public ResponseEntity<List<InstaPodUser>> podUsers(HttpSession session) {
		
		it.lucius.customers.models.User user = cripto.getSessionUser(session);
		ResponseEntity<List<InstaPodUser>> res = ResponseEntity.ok(podService.getInstaPodUsersList(user));
		return res;
	}
}
