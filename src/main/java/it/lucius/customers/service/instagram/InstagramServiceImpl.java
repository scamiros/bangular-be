package it.lucius.customers.service.instagram;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.lucius.customers.bean.charts.ChartsDatasLong;
import it.lucius.customers.bean.charts.ChartsDatasetLong;
import it.lucius.customers.bean.instagram.InstaSnapBean;
import it.lucius.customers.bean.instagram.InstaStatsself;
import it.lucius.customers.bean.instagram.InstagramImageBean;
import it.lucius.customers.bean.instagram.InstagramMediaBean;
import it.lucius.customers.bean.instagram.InstragramUserBean;
import it.lucius.customers.config.ServicesConstant;
import it.lucius.customers.dao.UserDao;
import it.lucius.customers.dao.instagram.InstagramDaoCustom;
import it.lucius.customers.dao.instagram.InstagramImageDao;
import it.lucius.customers.dao.instagram.InstagramMediaDao;
import it.lucius.customers.dao.instagram.InstagramUserDao;
import it.lucius.customers.dao.instagram.InstastatsDayDao;
import it.lucius.customers.dao.instagram.InstastatsMonthDao;
import it.lucius.customers.dao.instagram.InstastatsWeekDao;
import it.lucius.customers.models.User;
import it.lucius.customers.models.instagram.InstagramImage;
import it.lucius.customers.models.instagram.InstagramMedia;
import it.lucius.customers.models.instagram.InstagramUser;
import it.lucius.customers.models.instagram.InstastatsDay;
import it.lucius.customers.models.instagram.InstastatsMonth;
import it.lucius.customers.models.instagram.InstastatsWeek;
import it.lucius.customers.util.DateUtil;
import it.lucius.instagram.InstagramSession;
import it.lucius.instagram.auth.AccessToken;
import it.lucius.instagram.exception.InstagramException;
import it.lucius.instagram.model.Media;
import it.lucius.instagram.model.Media.Image;

@Service
public class InstagramServiceImpl implements InstagramService {

	public static final String DAY = "day";
	public static final String WEEK = "week";
	public static final String MONTH = "month";
	public static final String YEAR = "year";
	public static final String METRIC_FOLLOWERS = "followers";
	public static final String METRIC_LIKES = "likes";
	public static final String METRIC_COMMENTS = "comments";
	public static final String METRIC_MEDIA = "media";

	@Autowired
	InstagramUserDao instDao;

	@Autowired
	UserDao userDao;

	@Autowired
	InstastatsDayDao dayDao;

	@Autowired
	InstastatsMonthDao monthDao;

	@Autowired
	InstastatsWeekDao weekDao;
	
	@Autowired
	InstagramMediaDao mediaDao;
	
	@Autowired
	InstagramImageDao imageDao;
	
	@Autowired
	InstagramDaoCustom instaCustomDao;

	@Override
	public InstragramUserBean getInstagramUserByUsername(Integer idUser, String username) {

		InstragramUserBean instUser = null;
		InstagramUser inst = instDao.findByUserIdAndUsername(idUser, username);

		if (inst != null)
			instUser = new InstragramUserBean(inst);

		return instUser;
	}

	@Override
	public List<InstragramUserBean> getListInstagramUsers(Integer idUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveUserToken(Integer idUser, String token) {

		User u = userDao.findById(idUser);

		AccessToken accessToken = new AccessToken(token);

		InstagramSession sessione = new InstagramSession(accessToken);

		it.lucius.instagram.model.User instaUser = null;

		try {
			instaUser = sessione.getUserSelf();

		} catch (InstagramException e) {
		}

		InstagramUser instUser = new InstagramUser();
		instUser.setUser(u);
		instUser.setToken(token);
		instUser.setUsername(instaUser.getUserName());
		instUser.setIdInstagramUser(instaUser.getIdLong());
		instUser = instDao.save(instUser);

	}

//	@Override
//	public InstaSnapBean getInstaSnap(HttpSession session, User user) {
//
//		List<InstagramUser> list = instDao.findByUserId(user.getId());
//		List<it.lucius.instagram.model.User> listInsta = new ArrayList<>();
//		int current = -1;
//
//		for (InstagramUser inst : list) {
//
//			AccessToken token = new AccessToken(inst.getToken());
//			InstagramSession sessione = new InstagramSession(token);
//
//			it.lucius.instagram.model.User instaUser = null;
//
//			try {
//				instaUser = sessione.getUserSelf();
//
//				inst.setIdInstagramUser(instaUser.getIdLong());
//				inst.setUsername(instaUser.getUserName());
//				instDao.save(inst);
//
//				listInsta.add(instaUser);
//
//				if (user.getLastInstaUser() == inst.getId()) {
//					current = listInsta.indexOf(instaUser);
//					session.setAttribute(ServicesConstant.INSTAGRAM_CURRENT, instaUser);
//					session.setAttribute(ServicesConstant.INSTAGRAM_SESSION, sessione);
//				}
//
//			} catch (InstagramException e) {
//				e.printStackTrace();
//				throw new ApplicationContextException("Instagram temporary not available");
//			}
//		}
//
//		InstaSnapBean bean = new InstaSnapBean();
//		bean.setCurrentIndex(current);
//		bean.setList(listInsta);
//		return bean;
//	}
	
	@Override
	public InstaSnapBean getInstaSnap(HttpSession session, User user) {

		List<InstagramUser> list = instDao.findByUserId(user.getId());
		List<InstragramUserBean> beanList = new ArrayList<InstragramUserBean>();
		int current = -1;

		for (InstagramUser inst : list) {
			
			InstragramUserBean bean = new InstragramUserBean(inst);
			beanList.add(bean);
			if (user.getLastInstaUser() == inst.getId()) {
				
				current = list.indexOf(inst);
				AccessToken token = new AccessToken(inst.getToken());
				InstagramSession sessione = new InstagramSession(token);
				session.setAttribute(ServicesConstant.INSTAGRAM_USER, inst);
				session.setAttribute(ServicesConstant.INSTAGRAM_CURRENT, inst);
				session.setAttribute(ServicesConstant.INSTAGRAM_SESSION, sessione);
			}
		}

		InstaSnapBean bean = new InstaSnapBean();
		bean.setCurrentIndex(current);
		bean.setList(beanList);
		return bean;
	}

	@Override
	public InstragramUserBean getInstaProfile(HttpSession session) {

		InstagramUser current = (InstagramUser) session.getAttribute(ServicesConstant.INSTAGRAM_USER);
		InstragramUserBean bean = new InstragramUserBean(current);
		
		List<InstagramMedia> listMedia = mediaDao.findFirst12ByIdInstagramOrderByDtMediaDesc(current.getIdInstagramUser());
		List<InstagramMediaBean> listMediaBean = new ArrayList<InstagramMediaBean>();
		
		for(InstagramMedia media : listMedia) {
			InstagramMediaBean mediaBean = new InstagramMediaBean(media);
			if(media.getType().equalsIgnoreCase("carousel")) {
				List<InstagramImageBean> listImageBean = new ArrayList<InstagramImageBean>();
				List<InstagramImage> listImage = imageDao.findByInstagramMediaOrderByOffSlide(media);
				
				for(InstagramImage im : listImage) {
					
					InstagramImageBean imageBean = new InstagramImageBean(im);
					listImageBean.add(imageBean);
				}
				
				mediaBean.setInstagramImage(listImageBean);
			}
			listMediaBean.add(mediaBean);
		}
		
		bean.setMedia(listMediaBean);
		
		return bean;
	}

	@Override
	public Media getInstaMedia(HttpSession session, String idMedia) {

		InstagramSession sessione = (InstagramSession) session.getAttribute(ServicesConstant.INSTAGRAM_SESSION);
		Media media = null;
		try {
			media = sessione.getMedia(idMedia);
		} catch (InstagramException e) {
			e.printStackTrace();
		}
		return media;
	}

	@Override
	public InstaStatsself getInstaStasself(HttpSession session, String period) {
		InstagramUser current = (InstagramUser) session.getAttribute(ServicesConstant.INSTAGRAM_USER);
		
		InstaStatsself res = null;
		
		Date today = new Date();
		Calendar t = Calendar.getInstance();
		t.setTime(today);
		Date dt = DateUtil.getDate(t.get(Calendar.YEAR), t.get(Calendar.MONTH), t.get(Calendar.DAY_OF_MONTH));
		
		InstastatsDay use = dayDao.findByIdInstagramAndDtStats(current.getIdInstagramUser(), dt);
		
		if(use == null) {
			updateInstaself(session);
			use = dayDao.findByIdInstagramAndDtStats(current.getIdInstagramUser(), dt);
		}
		
		String lastUpdated = DateUtil.getTimestamp(use.getLastUpdated()); 
		
		switch (period) {
		case DAY:
			
			t.add(Calendar.DAY_OF_YEAR, -1);
			dt = DateUtil.getDate(t.get(Calendar.YEAR), t.get(Calendar.MONTH), t.get(Calendar.DAY_OF_MONTH));
			InstastatsDay diff = dayDao.findByIdInstagramAndDtStats(current.getIdInstagramUser(), dt);
			
			if(diff == null)
				res = new InstaStatsself(use);
			else
				res = new InstaStatsself(use, diff);
			
			break;
			
		case WEEK:
			
			t.add(Calendar.DAY_OF_YEAR, -7);
			dt = DateUtil.getDate(t.get(Calendar.YEAR), t.get(Calendar.MONTH), t.get(Calendar.DAY_OF_MONTH));
			diff = dayDao.findByIdInstagramAndDtStats(current.getIdInstagramUser(), dt);
			
			if(diff == null)
				res = new InstaStatsself(use);
			else
				res = new InstaStatsself(use, diff);
			
			break;
			
//			Date last = new Date();
//			Calendar calLast = Calendar.getInstance();
//			calLast.setTime(last);
//			calLast.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
//			last = DateUtil.getDateNoHour(calLast.getTime());
//			InstastatsWeek week = weekDao.findByIdInstagramAndDtStats(current.getIdInstagramUser(), last);
//			
//			if(week == null) {
//				updateWeekStats(session);
//				week = weekDao.findByIdInstagramAndDtStats(current.getIdInstagramUser(), last);
//			}
//			
//			Date prev = new Date();
//			Calendar calPrev = Calendar.getInstance();
//			calPrev.setTime(prev);
//			calPrev.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
//			calPrev.add(Calendar.WEEK_OF_YEAR, -1);
//			prev = DateUtil.getDateNoHour(calPrev.getTime());
//			InstastatsWeek diffWeek = weekDao.findByIdInstagramAndDtStats(current.getIdInstagramUser(), prev);
//			
//			if(diffWeek == null)
//				res = new InstaStatsself(week);
//			else
//				res = new InstaStatsself(week, diffWeek);
//			
//			break;
		case MONTH:
			
			t.add(Calendar.DAY_OF_YEAR, -30);
			dt = DateUtil.getDate(t.get(Calendar.YEAR), t.get(Calendar.MONTH), t.get(Calendar.DAY_OF_MONTH));
			diff = dayDao.findByIdInstagramAndDtStats(current.getIdInstagramUser(), dt);
			
			if(diff == null)
				res = new InstaStatsself(use);
			else
				res = new InstaStatsself(use, diff);
			
			break;
//			Date lastM = new Date();
//			Calendar calLastM = Calendar.getInstance();
//			calLastM.setTime(lastM);
//			calLastM.set(Calendar.DAY_OF_MONTH, calLastM.getActualMaximum(Calendar.DAY_OF_MONTH));
//			lastM = DateUtil.getDateNoHour(calLastM.getTime());
//			InstastatsMonth month = monthDao.findByIdInstagramAndDtStats(current.getIdInstagramUser(), lastM);
//			
//			if(month == null) {
//				updateMonthStats(session);
//				month = monthDao.findByIdInstagramAndDtStats(current.getIdInstagramUser(), lastM);
//			}
//			
//			Date prevM = new Date();
//			Calendar calPrevM = Calendar.getInstance();
//			calPrevM.setTime(prevM);
//			calPrevM.add(Calendar.MONTH, -1);
//			calPrevM.set(Calendar.DAY_OF_MONTH, calPrevM.getActualMaximum(Calendar.DAY_OF_MONTH));
//			prevM = DateUtil.getDateNoHour(calPrevM.getTime());
//			InstastatsMonth diffMonth = monthDao.findByIdInstagramAndDtStats(current.getIdInstagramUser(), prevM);
//			
//			if(diffMonth == null)
//				res = new InstaStatsself(month);
//			else
//				res = new InstaStatsself(month, diffMonth);
//			
//			break;
		default:
			break;
		}
		
		res.setLastUpdated(lastUpdated);
		return res;
	}

	private void updateInstaself(HttpSession session) {
		
		InstagramSession sessione = (InstagramSession) session.getAttribute(ServicesConstant.INSTAGRAM_SESSION);
		it.lucius.instagram.model.User instaModel = null;
		
		try {
			instaModel = sessione.getUserSelf();
			
			if(instaModel != null) {
				
				List<Media> listMedia = sessione.getRecentPublishedMedia(instaModel.getIdLong(), 1, false);
				Long totalLikes = new Long(0);
				Long totalComments = new Long(0);
				
				for(Media m : listMedia) {
					
					InstagramMedia media = mediaDao.findByIdInstagramMedia(m.getId());
					if(media == null)
						media = new InstagramMedia();
					
					media = mediaDao.save(media);
					
					media.setCaption((m.getCaption() != null) ? m.getCaption().getText() : "");
					media.setCommentsCount(m.getCommentsCount());
					media.setCreatedTimestamp(m.getCreatedTimestamp());
					media.setFilter(m.getFilter());
					media.setIdInstagram(instaModel.getIdLong());
					media.setIdInstagramMedia(m.getId());
					media.setLikesCount(m.getLikesCount());
					media.setLink(m.getLink());
					media.setLocation((m.getLocation() != null) ? m.getLocation().getName() :  "");
					media.setType(m.getType());
					
					if(media.getType().equalsIgnoreCase("video"))
						media.setUriVideo(m.getStandardResolution().getUrl());
					else if(media.getType().equalsIgnoreCase("carousel")) {
						
						int i = 0;
						for(Image im : m.getCarousel()) {
							InstagramImage image = imageDao.findByUri(im.getUri());
							if(image == null) 
								image = new InstagramImage();
							
							image.setUri(im.getUri());
							image.setInstagramMedia(media);
							image.setOffSlide(i);
							i++;
							imageDao.save(image);
						}
						
					}
					media.setUriLow(m.getLowResolutionImage().getUri());
					media.setUriStandard(m.getStandardResolutionImage().getUri());
					media.setUriThumb(m.getThumbnailImage().getUri());
					
					media.setDtMedia(m.getCreatedDate());
					mediaDao.save(media);
				}
				
				Date today = new Date();
				Calendar t = Calendar.getInstance();
				t.setTime(today);
				Date dt = DateUtil.getDate(t.get(Calendar.YEAR), t.get(Calendar.MONTH), t.get(Calendar.DAY_OF_MONTH));
				
				InstastatsDay use = dayDao.findByIdInstagramAndDtStats(instaModel.getIdLong(), dt);
				
				if(use == null)
					use = new InstastatsDay();
				
				use.setDtStats(dt);
				use.setIdInstagram(instaModel.getIdLong());
				use.setFollowersCount(new Long(instaModel.getFollowerCount()));
				use.setFollowingCount(new Long(instaModel.getFollowingCount()));
				use.setMediaCount(new Long(instaModel.getMediaCount()));
				
				List<InstagramMedia> medias = mediaDao.findByIdInstagram(instaModel.getIdLong());
				for(InstagramMedia media : medias) {
					totalLikes += media.getLikesCount();
					totalComments += media.getCommentsCount();
				}
				
				use.setLikesCount(totalLikes);
				use.setCommentsCount(totalComments);
				use.setLastUpdated(new Date());
				dayDao.save(use);
			}
		} catch (InstagramException e) {
		
		}
	}
	
	private void updateWeekStats(HttpSession session) {
		
		InstagramUser user = (InstagramUser) session.getAttribute(ServicesConstant.INSTAGRAM_USER);
		
		InstagramUser us = instDao.findByIdInstagramUser(user.getIdInstagramUser());
		
		Date today = DateUtil.getDateNoHour(new Date());
		Calendar calToday = Calendar.getInstance();
		calToday.setTime(today);
		
		Date first = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(first);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		first = DateUtil.getDateNoHour(cal.getTime());
		
		List<InstastatsDay> list = dayDao.findByIdInstagramAndDtStatsBetweenOrderByDtStats(
				us.getIdInstagramUser(), first, today);
		
		Long totalFollowers = new Long(0);
		Long totalFollowing = new Long(0);
		Long totalMedia = new Long(0);
		Long totalLikes = new Long(0);
		Long totalComments = new Long(0);
		
		for(InstastatsDay day : list) {
			
			totalFollowers += day.getFollowersCount();
			totalFollowing += day.getFollowingCount();
			totalMedia += day.getMediaCount();
			totalLikes += day.getLikesCount();
			totalComments += day.getCommentsCount();
		}
		
		Date last = new Date();
		Calendar calLast = Calendar.getInstance();
		calLast.setTime(last);
		calLast.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		last = DateUtil.getDateNoHour(calLast.getTime());
		InstastatsWeek week = weekDao.findByIdInstagramAndDtStats(
				us.getIdInstagramUser(), last);
		
		int tod = calToday.get(Calendar.DAY_OF_WEEK);
		int las = cal.get(Calendar.DAY_OF_WEEK);
		int div = tod - las + 1;
		if(week == null)
			week = new InstastatsWeek();
		
		week.setDtStats(last);
		week.setFollowersCount(totalFollowers/div);
		week.setFollowingCount(totalFollowing/div);
		week.setLikesCount(totalLikes/div);
		week.setCommentsCount(totalComments/div);
		week.setMediaCount(totalMedia/div);
		week.setIdInstagram(us.getIdInstagramUser());
		weekDao.save(week);
	}
	
	private void updateMonthStats(HttpSession session) {
		
		it.lucius.instagram.model.User user = 
				(it.lucius.instagram.model.User) session.getAttribute(ServicesConstant.INSTAGRAM_CURRENT);
		
		InstagramUser instaU = instDao.findByIdInstagramUser(user.getIdLong());
		
		Date today = DateUtil.getDateNoHour(new Date());
		Calendar calToday = Calendar.getInstance();
		calToday.setTime(today);
		
		Date first = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(first);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		first = DateUtil.getDateNoHour(cal.getTime());
		
		List<InstastatsDay> list = dayDao.findByIdInstagramAndDtStatsBetweenOrderByDtStats(
				instaU.getIdInstagramUser(), first, today);
		
		Long totalFollowers = new Long(0);
		Long totalFollowing = new Long(0);
		Long totalMedia = new Long(0);
		Long totalLikes = new Long(0);
		Long totalComments = new Long(0);
		
		for(InstastatsDay day : list) {
			
			totalFollowers += day.getFollowersCount();
			totalFollowing += day.getFollowingCount();
			totalMedia += day.getMediaCount();
			totalLikes += day.getLikesCount();
			totalComments += day.getCommentsCount();
		}
		
		Date last = new Date();
		Calendar calLast = Calendar.getInstance();
		calLast.setTime(last);
		calLast.set(Calendar.DAY_OF_MONTH, calLast.getActualMaximum(Calendar.DAY_OF_MONTH));
		last = DateUtil.getDateNoHour(calLast.getTime());
		InstastatsMonth month = monthDao.findByIdInstagramAndDtStats(
				instaU.getIdInstagramUser(), last);
		
		int tod = calToday.get(Calendar.DAY_OF_MONTH);
		int las = cal.get(Calendar.DAY_OF_MONTH);
		int div = tod - las + 1;
		if(month == null)
			month = new InstastatsMonth();
		
		month.setDtStats(last);
		month.setFollowersCount(totalFollowers/div);
		month.setFollowingCount(totalFollowing/div);
		month.setLikesCount(totalLikes/div);
		month.setCommentsCount(totalComments/div);
		month.setMediaCount(totalMedia/div);
		month.setIdInstagram(instaU.getIdInstagramUser());
		monthDao.save(month);
	}

	@Override
	public ChartsDatasLong getInstaStats(HttpSession session, String metric, String period) {
		
		ChartsDatasLong res = new ChartsDatasLong();
		List<ChartsDatasetLong> dataset = new ArrayList<ChartsDatasetLong>();
		List<String> label = new ArrayList<String>();
		
		ChartsDatasetLong chart = new ChartsDatasetLong();
		List<Long> dataLong = new ArrayList<Long>();
		
		chart.setData(dataLong);
		chart.setLabel(metric.toUpperCase());
		
		dataset.add(chart);
		res.setDataset(dataset);
		res.setLabels(label);
		
		InstagramUser current = 
				(InstagramUser) session.getAttribute(ServicesConstant.INSTAGRAM_USER);
		
//		System.out.println("****************** Session " + current);
		
		Date today = new Date();
		Date dtDayTo = DateUtil.getDateNoHour(today);
		List<InstastatsDay> use = null;
		Date dtDayFrom = DateUtil.getSevenDaysAgoNoHour(today);
		
		switch (period) {
		case DAY:
			
//			System.out.println("****************** GET DAY METRIC " + metric);
//			System.out.println("****************** Current IG " + current.getIdInstagramUser());
			use = dayDao.findByIdInstagramAndDtStatsBetweenOrderByDtStats(current.getIdInstagramUser(), dtDayFrom, dtDayTo);
//			System.out.println("****************** list size " + use.size());
			for(int i = 1; i < use.size(); i++) {
				
				switch (metric) { 
				case METRIC_FOLLOWERS:
					dataLong.add(use.get(i).getFollowersCount() - use.get(i - 1).getFollowersCount());
					break;
				case METRIC_COMMENTS:
					dataLong.add(use.get(i).getCommentsCount() - use.get(i - 1).getCommentsCount());
					break;
				case METRIC_LIKES:
					dataLong.add(use.get(i).getLikesCount() - use.get(i - 1).getLikesCount());
					break;
				case METRIC_MEDIA:
					dataLong.add(use.get(i).getMediaCount());
					break;
				default:
					break;
				}
				
				label.add(DateUtil.getSlashDate(use.get(i).getDtStats()));
			}
			
			break;
			
		case MONTH:
			
			Date dtLastDayFrom = DateUtil.getLastDayPreviousMonth(today);
			
			use = dayDao.findByIdInstagramAndDtStatsBetweenOrderByDtStats(current.getIdInstagramUser(), dtLastDayFrom, dtDayTo);
			
			for(int i = 1; i < use.size(); i++) {
				
				switch (metric) { 
				case METRIC_FOLLOWERS:
					dataLong.add(use.get(i).getFollowersCount() - use.get(i - 1).getFollowersCount());
					break;
				case METRIC_LIKES:
					dataLong.add(use.get(i).getLikesCount() - use.get(i - 1).getLikesCount());
					break;
				case METRIC_COMMENTS:
					dataLong.add(use.get(i).getCommentsCount() - use.get(i - 1).getCommentsCount());
					break;
				case METRIC_MEDIA:
					dataLong.add(use.get(i).getMediaCount());
					break;
				default:
					break;
				}
				
				label.add(DateUtil.getSlashDate(use.get(i).getDtStats()));
			}
			
			break;
		case YEAR:
			 
			Date dtMonthTo = DateUtil.getLastMonthDay(today);
			
			Date dtMonthFrom = DateUtil.getFirstPrecYearMonth(today);
			
			List<InstastatsMonth> useMonth = 
					monthDao.findByIdInstagramAndDtStatsBetweenOrderByDtStats(current.getIdInstagramUser(), dtMonthFrom, dtMonthTo);
			
			for(int i = 1; i < useMonth.size(); i++) {
				
				switch (metric) { 
				case METRIC_FOLLOWERS:
					dataLong.add(useMonth.get(i).getFollowersCount() - useMonth.get(i - 1).getFollowersCount());
					break;
				case METRIC_LIKES:
					dataLong.add(useMonth.get(i).getLikesCount() - useMonth.get(i - 1).getLikesCount());
					break;
				case METRIC_COMMENTS:
					dataLong.add(useMonth.get(i).getCommentsCount() - useMonth.get(i - 1).getCommentsCount());
					break;
				case METRIC_MEDIA:
					dataLong.add(useMonth.get(i).getMediaCount());
					break;
				default:
					break;
				}
				
				label.add(StringUtils.capitalize(DateUtil.getSlashDateWithoutDay(useMonth.get(i).getDtStats())));
			}
			
			break;
		default:
			break;
		}
		
		return res;
	}

	@Override
	public InstragramUserBean getInstaTopTen(HttpSession session, String type) {
		
		InstagramUser current = (InstagramUser) session.getAttribute(ServicesConstant.INSTAGRAM_USER);
		InstragramUserBean bean = new InstragramUserBean(current);
		
		List<InstagramMedia> listMedia = null;
		List<InstagramMediaBean> listMediaBean = new ArrayList<InstagramMediaBean>();
		
		switch (type) {
		case METRIC_LIKES:
			listMedia = mediaDao.findFirst5ByIdInstagramOrderByLikesCountDesc(current.getIdInstagramUser());
			break;
		case METRIC_COMMENTS:
			listMedia = mediaDao.findFirst5ByIdInstagramOrderByCommentsCountDesc(current.getIdInstagramUser());
			break;
		default:
			break;
		}
		
		for(InstagramMedia m : listMedia) {
			InstagramMediaBean mbean = new InstagramMediaBean(m);
			listMediaBean.add(mbean);
		}
		
		bean.setMedia(listMediaBean);
		
		return bean;
	}

	@Override
	public InstagramMedia getInstaLastMedia(HttpSession session) {
		
		InstagramUser current = (InstagramUser) session.getAttribute(ServicesConstant.INSTAGRAM_USER);
		
		InstagramMedia lastMedia = mediaDao.findFirst1ByIdInstagramOrderByDtMediaDesc(current.getIdInstagramUser());
		
		return lastMedia;
	}
}
