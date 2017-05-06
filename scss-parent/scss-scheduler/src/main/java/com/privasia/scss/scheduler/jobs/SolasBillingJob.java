package com.privasia.scss.scheduler.jobs;

import java.time.LocalDateTime;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.stereotype.Component;

import com.privasia.scss.scheduler.config.ConfigureQuartz;
import com.privasia.scss.scheduler.service.SolasBillingService;
import com.privasia.scss.scheduler.util.AppLogger;

@Component
@DisallowConcurrentExecution
public class SolasBillingJob implements Job {

	private final static AppLogger logger = AppLogger.getInstance();

	@Value("${cron.frequency.solasbillingjob}")
	private String frequency;
	
	private SolasBillingService solasBillingService;
	
	
	@Autowired
	public void setSolasBillingService(SolasBillingService solasBillingService) {
		this.solasBillingService = solasBillingService;
	}


	@Override
	public void execute(JobExecutionContext jobExecutionContext) {
		LocalDateTime today = LocalDateTime.now(); // Start date
		LocalDateTime yesterday = LocalDateTime.now().minusDays(10);
		solasBillingService.fetchSolasBilling(yesterday, today);
		logger.info("Running solasBillingJob | frequency {}", frequency);
	}

	@Bean(name = "solasBillingJobTrigger")
	public JobDetailFactoryBean sampleJob() {
		return ConfigureQuartz.createJobDetail(this.getClass());
	}

	@Bean(name = "solasBillingJobTriggerBeanTrigger")
	public CronTriggerFactoryBean sampleJobTrigger(@Qualifier("solasBillingJobTrigger") JobDetail jobDetail) {
		return ConfigureQuartz.createCronTrigger(jobDetail, frequency);
	}

}
