package it.lucius.customers.service.instagram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.Tuple;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import it.lucius.customers.bean.ResponseBean;
import it.lucius.customers.bean.SearchBean;
import it.lucius.customers.bean.charts.ChartsDatasLong;
import it.lucius.customers.bean.charts.ChartsDatasetLong;
import it.lucius.customers.bean.charts.ChartsSimple;
import it.lucius.customers.bean.instagram.InstaPodAction;
import it.lucius.customers.bean.instagram.InstaPodChartBean;
import it.lucius.customers.bean.instagram.InstaPodContent;
import it.lucius.customers.bean.instagram.InstaPodGroup;
import it.lucius.customers.bean.instagram.InstaPodMember;
import it.lucius.customers.bean.instagram.InstaPodUser;
import it.lucius.customers.bean.instagram.InstagramPodGroupBean;
import it.lucius.customers.config.ServicesConstant;
import it.lucius.customers.dao.UserDao;
import it.lucius.customers.dao.instagram.pod.InstagramPodActionDao;
import it.lucius.customers.dao.instagram.pod.InstagramPodActionDaoCustom;
import it.lucius.customers.dao.instagram.pod.InstagramPodActionSpec;
import it.lucius.customers.dao.instagram.pod.InstagramPodContentDao;
import it.lucius.customers.dao.instagram.pod.InstagramPodContentSpec;
import it.lucius.customers.dao.instagram.pod.InstagramPodGroupDao;
import it.lucius.customers.dao.instagram.pod.InstagramPodMemberDao;
import it.lucius.customers.dao.instagram.pod.InstagramPodUserDao;
import it.lucius.customers.dao.instagram.pod.InstagramPodUserSpec;
import it.lucius.customers.models.User;
import it.lucius.customers.models.instagram.pod.InstagramPodAction;
import it.lucius.customers.models.instagram.pod.InstagramPodAction_;
import it.lucius.customers.models.instagram.pod.InstagramPodContent;
import it.lucius.customers.models.instagram.pod.InstagramPodGroup;
import it.lucius.customers.models.instagram.pod.InstagramPodMember;
import it.lucius.customers.models.instagram.pod.InstagramPodUser;

@Service
public class InstagramPodServiceImpl {
	
	@Autowired
	InstagramPodGroupDao podGroupDao;
	
	@Autowired
	InstagramPodMemberDao podMemberDao;
	
	@Autowired
	InstagramPodContentDao podContentDao;
	
	@Autowired
	InstagramPodActionDao podActionDao;
	
	@Autowired
	InstagramPodUserDao podUserDao;
	
	@Autowired
	InstagramPodActionDaoCustom podActionDaoCustom;
	
	@Autowired
	UserDao userDao;

	public List<InstaPodGroup> getPodListGroups(User user) {
		
		List<InstagramPodGroup> list = podGroupDao.findByUserAndEnabledTrue(user);
		List<InstaPodGroup> listBean = new ArrayList<InstaPodGroup>();
		
		for(InstagramPodGroup g : list) {
			InstaPodGroup bean = new InstaPodGroup(g);
			listBean.add(bean);
		}
		
		return listBean;
	}
	
	public InstagramPodGroupBean getPodGroup(InstaPodGroup group) {
		
		InstagramPodGroupBean ret = new InstagramPodGroupBean();
		ret.setGroup(group);
		
		List<InstaPodMember> members = new ArrayList<InstaPodMember>();
		for(InstagramPodMember m : podMemberDao.findByInstagramPodGroupIdPodGroupOrderByInstagramPodUserUsernameAsc(group.getIdPodGroup())) {
			
			InstaPodMember bean = new InstaPodMember(m);
			members.add(bean);
		}
		
		ret.setMembers(members);
		
		List<InstaPodContent> contents = new ArrayList<InstaPodContent>();
		for(InstagramPodContent c : 
			podContentDao.findByInstagramPodMemberInstagramPodGroupIdPodGroupOrderByDtPostedDesc(group.getIdPodGroup())) {
			
			InstaPodContent bean = new InstaPodContent(c);
			int likes = 0;
			int comments = 0;
			
			List<InstagramPodAction> actions = podActionDao.findByInstagramPodContent(c);
			List<InstaPodAction> actionsBean = new ArrayList<InstaPodAction>();
			
			for(InstagramPodAction act : actions) {
				actionsBean.add(new InstaPodAction(act));
				likes = (act.getLike() == 1) ? likes + 1 : likes;
				comments = (act.getComment() == 1) ? comments + 1 : comments;
			}
			bean.setActions(actionsBean);
			bean.setComments(comments);
			bean.setLikes(likes);
			contents.add(bean);
		}
		
		ret.setContents(contents);
		
		return ret;
	}
	
	public ResponseBean createPodGroup(User user, InstaPodGroup group) {
		
		ResponseBean res = new ResponseBean();
		res.setCode(ResponseBean.OK_CODE);
		res.setMessage(ResponseBean.OK_CREATE_MSG);
		
		User u = userDao.findById(user.getId());
		InstagramPodGroup g = new InstagramPodGroup();
		
		g.setUser(u);
		g.setGroupName(group.getGroupName());
		g.setEnabled(true);
		g = podGroupDao.save(g);
		
		return res;
	}
	
	public ResponseBean createPodMember(InstaPodMember m) {
		
		ResponseBean res = new ResponseBean();
		res.setCode(ResponseBean.OK_CODE);
		res.setMessage(ResponseBean.OK_CREATE_MSG);
		
		InstagramPodMember g = new InstagramPodMember();
		
		InstagramPodGroup group = podGroupDao.findOne(m.getInstagramPodGroup().getIdPodGroup());
		InstagramPodUser user = podUserDao.findOne(m.getInstagramPodUser().getIdPodUser());
		
		g.setInstagramPodGroup(group);
		g.setInstagramPodUser(user);
		podMemberDao.save(g);
		
		return res;
	}
	
	public ResponseBean removePodMember(InstaPodMember m) {
		
		ResponseBean res = new ResponseBean();
		res.setCode(ResponseBean.OK_CODE);
		res.setMessage(ResponseBean.OK_DELETE_MSG);
		
		InstagramPodMember g = podMemberDao.findOne(m.getIdPodMember());
		
		if(g != null)
			podMemberDao.delete(g);
		
		return res;
	}
	
	public ResponseBean managePodUser(User user, InstaPodUser m) {
		
		ResponseBean res = new ResponseBean();
		res.setCode(ResponseBean.OK_CODE);
		res.setMessage(ResponseBean.OK_CREATE_MSG);
		
		InstagramPodUser g = null;
		
		if(m.getIdPodUser() != null) {
			g = podUserDao.findOne(m.getIdPodUser());
		} else 
			g = new InstagramPodUser();
		
		g.setProfilePicture(m.getProfilePicture());
		g.setUsername(m.getUsername());
		g.setUser(user);
		podUserDao.save(g);
		
		return res;
	}
	
	public ResponseBean createPodContent(InstaPodContent m) {
		
		ResponseBean res = new ResponseBean();
		res.setCode(ResponseBean.OK_CODE);
		res.setMessage(ResponseBean.OK_CREATE_MSG);
		
		InstagramPodContent g = new InstagramPodContent();
		
		InstagramPodMember member = podMemberDao.findOne(m.getInstagramPodMember().getIdPodMember());
		
		g.setContentUri(m.getContentUri());
		g.setDtPosted(m.getDtPosted());
		g.setInstagramPodMember(member);
		
		podContentDao.save(g);
		
		for(InstagramPodMember ms : 
			podMemberDao.findByInstagramPodGroupIdPodGroupOrderByInstagramPodUserUsernameAsc(member.getInstagramPodGroup().getIdPodGroup())) {
			
			if(ms.getIdPodMember() != member.getIdPodMember()) {
				InstagramPodAction action = new InstagramPodAction();
				action.setComment(0);
				action.setLike(0);
				action.setInstagramPodContent(g);
				action.setInstagramPodMember(ms);
				
				podActionDao.save(action);
			}
		}
		
		return res;
	}
	
	public ResponseBean postPodAction(InstaPodAction action) {
		
		ResponseBean res = new ResponseBean();
		res.setCode(ResponseBean.OK_CODE);
		res.setMessage(ResponseBean.OK_UPDATE_MSG);
		
		InstagramPodAction a = podActionDao.findOne(action.getIdPodAction());
		
		a.setComment(action.getComment());
		a.setLike(action.getLike());
		
		podActionDao.save(a);
		
		return res;
	}

	public List<InstaPodAction> getPodActions(InstaPodContent content) {
		
		InstagramPodContent cont = podContentDao.findOne(content.getIdPodContent());
		List<InstagramPodAction> list = podActionDao.findByInstagramPodContent(cont);
		
		List<InstaPodAction> listBean = new ArrayList<InstaPodAction>();
		
		for(InstagramPodAction g : list) {
			InstaPodAction bean = new InstaPodAction(g);
			listBean.add(bean);
		}
		
		return listBean;
	}
	
	public ChartsDatasLong getInstaPodStats(
			HttpSession session, InstaPodChartBean search) {
		
		ChartsDatasLong res = new ChartsDatasLong();
		List<ChartsDatasetLong> dataset = new ArrayList<ChartsDatasetLong>();
		List<String> label = new ArrayList<String>();
		
		ChartsDatasetLong chart = new ChartsDatasetLong();
		List<Long> dataLong = new ArrayList<Long>();
		
		chart.setData(dataLong);
		chart.setLabel(search.getMetric().toUpperCase());
		
		dataset.add(chart);
		res.setDataset(dataset);
		res.setLabels(label);
		
		User current = (User) session.getAttribute(ServicesConstant.USERAPP_CURRENT);
		
		Specification<InstagramPodAction> spec = InstagramPodActionSpec.findByUserGroupAndDate(
				current, search.getIdPodGroup(), search.getDtFrom(), search.getDtTo(), null);
		Specification<InstagramPodContent> specC = InstagramPodContentSpec.findByUserGroupAndDate(
				current, search.getIdPodGroup(), search.getDtFrom(), search.getDtTo());
		Long c = podActionDaoCustom.getInstaPodActionCount(specC);
		
		if(c != null) {
			List<Tuple> renderList = podActionDaoCustom.getInstaPodStats(spec, search.getMetric());
			
			for(Tuple t : renderList) {
				
				Integer idM = t.get(InstagramPodAction_.instagramPodMember.getName(), Integer.class);
				Long count = t.get(search.getMetric(), Long.class);
				
				InstagramPodMember m = podMemberDao.findOne(idM);
				label.add(m.getInstagramPodUser().getUsername());
				
				switch (search.getTipo()) {
				case ServicesConstant.METRIC_TYPE_COUNTER:
					dataLong.add(count*100/c);
					break;
					
				case ServicesConstant.METRIC_TYPE_GAP:
					
					List<InstagramPodContent> contents = null;
					
					if(search.getDtFrom() != null && search.getDtTo() == null)
						contents = podContentDao.findByInstagramPodMemberIdPodMemberAndDtPostedAfter(
								idM, search.getDtFrom());
					else if(search.getDtFrom() == null && search.getDtTo() != null)
						contents = podContentDao.findByInstagramPodMemberIdPodMemberAndDtPostedBefore(
								idM, search.getDtTo());
					else if(search.getDtFrom() != null && search.getDtTo() != null)
						contents = podContentDao.findByInstagramPodMemberIdPodMemberAndDtPostedBetween(
								idM, search.getDtFrom(), search.getDtTo());
					else
						contents = podContentDao.findByInstagramPodMemberIdPodMember(idM);
					
					Integer numMembri = podMemberDao.findByInstagramPodGroupIdPodGroupOrderByInstagramPodUserUsernameAsc(
							m.getInstagramPodGroup().getIdPodGroup()).size();
					
					Long sumTotal = new Long(0);
					for(InstagramPodContent co : contents) {
						
						Specification<InstagramPodAction> specContent = 
								InstagramPodActionSpec.findByUserGroupAndDate(
										current, search.getIdPodGroup(), search.getDtFrom(), search.getDtTo(), co.getIdPodContent());
						List<Tuple> li = podActionDaoCustom.getInstaPodActionSum(specContent, search.getMetric());
						for(Tuple tt : li) {
							sumTotal = sumTotal + tt.get(search.getMetric(), Long.class);
						}
						
					}
					
					Long media = new Long(0);
					Long percentContent = new Long(0);
					
					if(contents.size() != 0) {
						media = sumTotal/contents.size();
						percentContent = media*100/numMembri;
					}
					
					dataLong.add(count*100/c - percentContent);
					
					break;

				default:
					break;
				}
			}
		}
		
		return res;
	}
	
	public ChartsDatasLong getInstaPodStars(
			HttpSession session, InstaPodChartBean search) {
		
		ChartsDatasLong res = new ChartsDatasLong();
		List<ChartsDatasetLong> dataset = new ArrayList<ChartsDatasetLong>();
		List<String> label = new ArrayList<String>();
		
		ChartsDatasetLong chart = new ChartsDatasetLong();
		List<Long> dataLong = new ArrayList<Long>();
		
		chart.setData(dataLong);
		chart.setLabel(search.getMetric().toUpperCase());
		
		dataset.add(chart);
		res.setDataset(dataset);
		res.setLabels(label);
		
		List<ChartsSimple> tempCharts = new ArrayList<ChartsSimple>();
		
		User current = (User) session.getAttribute(ServicesConstant.USERAPP_CURRENT);
		
		List<InstagramPodMember> members = podMemberDao.findByInstagramPodGroupIdPodGroupOrderByInstagramPodUserUsernameAsc(search.getIdPodGroup());
		
		for(InstagramPodMember m : members) {
			
			String lab = m.getInstagramPodUser().getUsername(); 
			
			long totalLikes = 0;
			
			List<InstagramPodContent> contents = 
					podContentDao.findByInstagramPodMemberIdPodMemberAndDtPostedBetween(m.getIdPodMember(), search.getDtFrom(), search.getDtTo());
			
			for(InstagramPodContent c : contents) {
				Specification<InstagramPodAction> specContent = 
						InstagramPodActionSpec.findByUserGroupAndDate(
								current, search.getIdPodGroup(), search.getDtFrom(), search.getDtTo(), c.getIdPodContent());
				List<Tuple> li = podActionDaoCustom.getInstaPodActionSum(specContent, search.getMetric());
				for(Tuple tt : li) {
					totalLikes = totalLikes + tt.get(search.getMetric(), Long.class);
				}
			}
			
			long value = new Long(0);
			if(contents.size() > 0)
				value = totalLikes*100/(members.size()*contents.size());
			
			ChartsSimple simple = new ChartsSimple(lab, value);
			tempCharts.add(simple);
		}
		
		Collections.sort(tempCharts);
		
		for(ChartsSimple c : tempCharts) {
			label.add(c.getLabel());
			dataLong.add(c.getValue());
		}
		
		return res;
	}
	
	public Page<InstaPodUser> getInstaPodUsers(User user, SearchBean search) {
		
		Direction direction = (search.getDirection() != null) ? Direction.fromString(search.getDirection()) : Direction.ASC;
		Specification<InstagramPodUser> users = InstagramPodUserSpec.findByUserAndCriteria(user, search);
		
		PageRequest pageable = new PageRequest(search.getPageNumber(), search.getPageSize(), direction, 
				search.getOrderField());
		
		Page<InstagramPodUser> page = podUserDao.findAll(users, pageable);
		List<InstaPodUser> listUsers = new ArrayList<InstaPodUser>(page.getNumberOfElements());
		
		for(InstagramPodUser g : page.getContent()) {
			InstaPodUser bean = new InstaPodUser(g);
			listUsers.add(bean);
		}
		
		Page<InstaPodUser> result = new PageImpl<InstaPodUser>(listUsers, pageable, page.getTotalElements());
		
		return result;
	}
	
	public List<InstaPodUser> getInstaPodUsersList(User user) {
		
		List<InstagramPodUser> page = podUserDao.findAll();
		List<InstaPodUser> listUsers = new ArrayList<InstaPodUser>();
		
		for(InstagramPodUser g : page) {
			InstaPodUser bean = new InstaPodUser(g);
			listUsers.add(bean);
		}
		
		return listUsers;
	}
}
