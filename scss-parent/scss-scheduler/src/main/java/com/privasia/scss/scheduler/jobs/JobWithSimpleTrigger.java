/**
 * 
 */
package com.privasia.scss.scheduler.jobs;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import com.privasia.scss.scheduler.config.ConfigureQuartz;
import com.privasia.scss.scheduler.util.AppLogger;

/**
 * @author pavan.solapure
 *
 */
// @Component
// @DisallowConcurrentExecution
public class JobWithSimpleTrigger implements Job {

  private final static AppLogger logger = AppLogger.getInstance();

  // @Value("${cron.frequency.jobwithsimpletrigger}")
  private long frequency;

  @Override
  public void execute(JobExecutionContext jobExecutionContext) {
    logger.info("Running JobWithSimpleTrigger | frequency {}", frequency);
  }

  @Bean(name = "jobWithSimpleTriggerBean")
  public JobDetailFactoryBean sampleJob() {
    return ConfigureQuartz.createJobDetail(this.getClass());
  }

  @Bean(name = "jobWithSimpleTriggerBeanTrigger")
  public SimpleTriggerFactoryBean sampleJobTrigger(@Qualifier("jobWithSimpleTriggerBean") JobDetail jobDetail) {
    return ConfigureQuartz.createTrigger(jobDetail, frequency);
  }
}
