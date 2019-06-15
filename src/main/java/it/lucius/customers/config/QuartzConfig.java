package it.lucius.customers.config;

import java.util.HashMap;
import java.util.Map;

import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import it.lucius.customers.batch.AutowiringSpringBeanJobFactory;
import it.lucius.customers.batch.InstaStatsDay;
import it.lucius.customers.batch.InstaStatsMonth;
import it.lucius.customers.batch.InstaStatsWeek;

@Configuration
@ComponentScan("it.lucius")
@EnableAutoConfiguration
public class QuartzConfig {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Bean
	public JobDetailFactoryBean instaStatsDayFactory() {
		JobDetailFactoryBean factory = new JobDetailFactoryBean();
		factory.setJobClass(InstaStatsDay.class);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "DayStats");
		factory.setJobDataAsMap(map);
		factory.setGroup("instagram");
		factory.setName("instaStatsDay");
		return factory;
	}
	
	@Bean
	public JobDetailFactoryBean instaStatsWeekFactory() {
		JobDetailFactoryBean factory = new JobDetailFactoryBean();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "WeekStats");
		factory.setJobDataAsMap(map);
		factory.setJobClass(InstaStatsWeek.class);
		factory.setGroup("instagram");
		factory.setName("instaStatsWeek");
		return factory;
	}
	
	@Bean
	public JobDetailFactoryBean instaStatsMonthFactory() {
		JobDetailFactoryBean factory = new JobDetailFactoryBean();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "MonthStats");
		factory.setJobDataAsMap(map);
		factory.setJobClass(InstaStatsMonth.class);
		factory.setGroup("instagram");
		factory.setName("instaStatsMonth");
		return factory;
	}

	@Bean
	public CronTriggerFactoryBean dayTriggerFactoryBean() {
		
		CronTriggerFactoryBean dayFactory = new CronTriggerFactoryBean();
		JobDetail det = instaStatsDayFactory().getObject();
		dayFactory.setJobDetail(det);
		dayFactory.setStartDelay(3000);
		dayFactory.setName("instaStatsDayTrigger");
		dayFactory.setGroup("instaGroup");
		//dayFactory.setCronExpression("0 44 23 ? * *");
		dayFactory.setCronExpression("0 */2 * ? * *");
		
		return dayFactory;
	}
	
	@Bean
	public CronTriggerFactoryBean weekTriggerFactoryBean() {
		
		CronTriggerFactoryBean weekFactory = new CronTriggerFactoryBean();
		weekFactory.setJobDetail(instaStatsWeekFactory().getObject());
		weekFactory.setStartDelay(3000);
		weekFactory.setName("instaStatsWeekTrigger");
		weekFactory.setGroup("instaGroup");
		weekFactory.setCronExpression("0 */10 * ? * *");
		
		return weekFactory;
	}
	
	@Bean
	public CronTriggerFactoryBean monthTriggerFactoryBean() {
		
		CronTriggerFactoryBean monthFactory = new CronTriggerFactoryBean();
		monthFactory.setJobDetail(instaStatsMonthFactory().getObject());
		monthFactory.setStartDelay(3000); 
		monthFactory.setName("instaStatsMonthTrigger");
		monthFactory.setGroup("instaGroup");
		monthFactory.setCronExpression("0 */10 * ? * *");
		
		
		return monthFactory;
	}

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() {
		SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
		AutowiringSpringBeanJobFactory autowiringSpringBeanJobFactory = new AutowiringSpringBeanJobFactory();
	    autowiringSpringBeanJobFactory.setApplicationContext(applicationContext);
	    scheduler.setJobFactory(autowiringSpringBeanJobFactory);
	    
	    scheduler.setTriggers(
	    		dayTriggerFactoryBean().getObject(), 
	    		weekTriggerFactoryBean().getObject(), 
	    		monthTriggerFactoryBean().getObject());
	    
		return scheduler;
	}
}
