package it.lucius.customers.service.instagram;

import java.util.List;

import javax.servlet.http.HttpSession;

import it.lucius.customers.bean.charts.ChartsDatasLong;
import it.lucius.customers.bean.instagram.InstaSnapBean;
import it.lucius.customers.bean.instagram.InstaStatsself;
import it.lucius.customers.bean.instagram.InstragramUserBean;
import it.lucius.customers.models.User;
import it.lucius.customers.models.instagram.InstagramMedia;
import it.lucius.instagram.model.Media;

public interface InstagramService {

	InstragramUserBean getInstagramUserByUsername(Integer idUser, String username);
	List<InstragramUserBean> getListInstagramUsers(Integer idUser);
	void saveUserToken(Integer idUser, String token);
	InstaSnapBean getInstaSnap(HttpSession session, User user);
	InstragramUserBean getInstaProfile(HttpSession session);
	Media getInstaMedia(HttpSession session, String idMedia);
	InstaStatsself getInstaStasself(HttpSession session, String period);
	ChartsDatasLong getInstaStats(HttpSession session, String metric, String period);
	InstragramUserBean getInstaTopTen(HttpSession session, String type);
	InstagramMedia getInstaLastMedia(HttpSession session);
}
