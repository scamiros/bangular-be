package it.lucius.customers.batch;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import it.lucius.customers.bean.UserProfile;
import it.lucius.customers.dao.instagram.InstagramUserDao;
import it.lucius.customers.dao.instagram.InstastatsDayDao;
import it.lucius.customers.dao.instagram.InstastatsMonthDao;
import it.lucius.customers.models.instagram.InstagramUser;
import it.lucius.customers.models.instagram.InstastatsDay;
import it.lucius.customers.models.instagram.InstastatsMonth;
import it.lucius.customers.service.UserService;
import it.lucius.customers.service.instagram.InstagramService;
import it.lucius.customers.util.DateUtil;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class InstaStatsMonth extends QuartzJobBean {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	InstagramService instService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	InstagramUserDao instDao;
	
	@Autowired
	InstastatsDayDao dayDao;
	
	@Autowired
	InstastatsMonthDao monthDao;

	protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException {
		
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		log.info(format.format(now) + ": executing job " + ctx.getJobDetail().getKey().toString().toUpperCase());
		
		List<UserProfile> userList = userService.getUsersList();
		
		for(UserProfile user : userList) {
			
			List<InstagramUser> instaUser = instDao.findByUserId(user.getId());
			for(InstagramUser instaU : instaUser) {
				
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
				
				
				int div = calToday.get(Calendar.DAY_OF_MONTH);
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
		}
		now = new Date();
		log.info(format.format(now) + ": finished job " + ctx.getJobDetail().getKey().toString().toUpperCase());
	}
}
