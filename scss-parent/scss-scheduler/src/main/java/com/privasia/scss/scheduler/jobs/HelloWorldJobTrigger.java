package com.privasia.scss.scheduler.jobs;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import com.privasia.scss.scheduler.config.ConfigureQuartz;
import com.privasia.scss.scheduler.util.AppLogger;

// @Component
// @DisallowConcurrentExecution
public class HelloWorldJobTrigger implements Job {

  private final static AppLogger logger = AppLogger.getInstance();

  // @Value("${cron.frequency.jobwithsimpletrigger}")
  private long frequency;

  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    //System.out.println("HELLO WORLD!");
    //logger.info("HELLO WORLD | frequency {}", frequency);
  }

  @Bean(name = "helloWorldJobTriggerBean")
  public JobDetailFactoryBean sampleJob() {
    return ConfigureQuartz.createJobDetail(this.getClass());
  }

  @Bean(name = "helloWorldJobTriggerBeanTrigger")
  public SimpleTriggerFactoryBean sampleJobTrigger(@Qualifier("helloWorldJobTriggerBean") JobDetail jobDetail) {
    return ConfigureQuartz.createTrigger(jobDetail, frequency);
  }

}
