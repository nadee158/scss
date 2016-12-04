package com.privasia.scss.scheduler.jobs;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.stereotype.Component;

import com.privasia.scss.core.service.NotificationService;
import com.privasia.scss.scheduler.config.ConfigureQuartz;
import com.privasia.scss.scheduler.util.AppLogger;

@Component
@DisallowConcurrentExecution
public class KioskHealthCheckMailJob implements Job {

  private final static AppLogger logger = AppLogger.getInstance();

  @Value("${cron.frequency.kioskhealthcheckmailjob}")
  private String frequency;

  @Autowired
  private NotificationService notificationService;

  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    // NotificationSender notificationSender = NotificationSender.getInstance();
    // notificationSender.sendToQueue(SCSSConstant.SCHEDULER_HEALTH_CHECK_NOTIFICATION,
    // SCSSConstant.QUEUE_EMAIL);
    notificationService.sendKioskHealthCheckMails();
    logger.info("Running KioskHealthCheckMailJob | frequency {}", frequency);
  }

  @Bean(name = "kioskHealthCheckMailJobTrigger")
  public JobDetailFactoryBean sampleJob() {
    return ConfigureQuartz.createJobDetail(this.getClass());
  }

  @Bean(name = "kioskHealthCheckMailJobTriggerBeanTrigger")
  public CronTriggerFactoryBean sampleJobTrigger(@Qualifier("kioskHealthCheckMailJobTrigger") JobDetail jobDetail) {
    return ConfigureQuartz.createCronTrigger(jobDetail, frequency);
  }



}
