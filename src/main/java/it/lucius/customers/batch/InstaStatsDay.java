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
import it.lucius.customers.dao.instagram.InstagramImageDao;
import it.lucius.customers.dao.instagram.InstagramMediaDao;
import it.lucius.customers.dao.instagram.InstagramUserDao;
import it.lucius.customers.dao.instagram.InstastatsDayDao;
import it.lucius.customers.models.instagram.InstagramImage;
import it.lucius.customers.models.instagram.InstagramMedia;
import it.lucius.customers.models.instagram.InstagramUser;
import it.lucius.customers.models.instagram.InstastatsDay;
import it.lucius.customers.service.UserService;
import it.lucius.customers.service.instagram.InstagramService;
import it.lucius.customers.util.DateUtil;
import it.lucius.instagram.InstagramSession;
import it.lucius.instagram.auth.AccessToken;
import it.lucius.instagram.exception.InstagramException;
import it.lucius.instagram.model.Media;
import it.lucius.instagram.model.Media.Image;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class InstaStatsDay extends QuartzJobBean {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	InstagramService instService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	InstagramUserDao instDao;
	
	@Autowired
	InstastatsDayDao follDao;
	
	@Autowired
	InstagramMediaDao mediaDao;
	
	@Autowired
	InstagramImageDao imageDao;

	protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException {

		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		log.info(format.format(now) + ": executing job " + ctx.getJobDetail().getKey().toString().toUpperCase());
		
		List<UserProfile> userList = userService.getUsersList();
		
		for(UserProfile user : userList) {
			
			List<InstagramUser> instaUser = instDao.findByUserId(user.getId());
			for(InstagramUser instaU : instaUser) {
				
				AccessToken token = new AccessToken(instaU.getToken());
				InstagramSession sessione = new InstagramSession(token);
				
				it.lucius.instagram.model.User instaModel = null;
				
				try {
					instaModel = sessione.getUserSelf();
					
					if(instaModel != null) {
						
						instaU.setUsername(instaModel.getUserName());
						instaU.setBio(instaModel.getBio());
						instaU.setFollowerCountString(instaModel.getFollowerCountString());
						instaU.setFollowingCountString(instaModel.getFollowingCountString());
						instaU.setFullName(instaModel.getFullName());
						instaU.setMediaCountString(instaModel.getMediaCount().toString());
						instaU.setProfilePictureURI(instaModel.getProfilePictureURI());
						instaU.setWebsite(instaModel.getWebsite());
						instDao.save(instaU);
						
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
						
						InstastatsDay use = follDao.findByIdInstagramAndDtStats(instaModel.getIdLong(), dt);
						
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
						follDao.save(use);
					}
					
				} catch (InstagramException e) {
				}
			}
		}
		now = new Date();
		log.info(format.format(now) + ": finished job " + ctx.getJobDetail().getKey().toString().toUpperCase());
	}
}
