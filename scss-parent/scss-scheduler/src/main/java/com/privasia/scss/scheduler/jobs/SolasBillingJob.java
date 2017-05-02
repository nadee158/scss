package com.privasia.scss.scheduler.jobs;

import java.time.LocalDateTime;
import java.util.stream.Stream;

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

import com.privasia.scss.common.enums.ContainerFullEmptyType;
import com.privasia.scss.common.enums.SolasInstructionType;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.core.model.Exports;
import com.privasia.scss.core.predicate.ExportsPredicates;
import com.privasia.scss.core.repository.ExportsRepository;
import com.privasia.scss.scheduler.config.ConfigureQuartz;
import com.privasia.scss.scheduler.util.AppLogger;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;

@Component
@DisallowConcurrentExecution
public class SolasBillingJob implements Job {

  private final static AppLogger logger = AppLogger.getInstance();

  @Value("${cron.frequency.solasbillingjob}")
  private String frequency;

  @Autowired
  private ExportsRepository exportsRepository;

  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    LocalDateTime today = LocalDateTime.now(); // Start date
    LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
    generateSolasBilling(yesterday, today);
    logger.info("Running solasBillingJob | frequency {}", frequency);
  }

  private void generateSolasBilling(LocalDateTime yesterday, LocalDateTime today) {
    Predicate timeGateInBetween = ExportsPredicates.timeGateInBetween(yesterday, today);
    Predicate byTransactionStatus = ExportsPredicates.byTransactionStatus(TransactionStatus.APPROVED);
    Predicate bySolasInstructionTerminal =
        ExportsPredicates.bySolasInstructionType(SolasInstructionType.VGM_INSTRUCTION_TERMINAL);
    Predicate bySolasInstructionShipper =
        ExportsPredicates.bySolasInstructionType(SolasInstructionType.VGM_INSTRUCTION_SHIPPER);
    Predicate byWithinTolerance = ExportsPredicates.byWithinTolerance(false);
    Predicate byHpabBooking = ExportsPredicates.byHpabBooking(null);
    Predicate byContainerFullOrEmpty = ExportsPredicates.byContainerFullOrEmpty(ContainerFullEmptyType.FULL);
    OrderSpecifier<Long> orderByPrintEirAsc = ExportsPredicates.orderByPrintEirAsc();

//  @formatter:off
    Predicate condition =
        ExpressionUtils.allOf(
                timeGateInBetween,
                byTransactionStatus,
                ExpressionUtils.or(bySolasInstructionTerminal, bySolasInstructionShipper), 
                byWithinTolerance,
                byHpabBooking,
                byContainerFullOrEmpty                
         );
//  @formatter:on

    Iterable<Exports> exportsList = exportsRepository.findAll(condition, orderByPrintEirAsc);
    if (!(exportsList == null || Stream.of(exportsList).count() == 0)) {
      while (exportsList.iterator().hasNext()) {
        Exports exports = exportsList.iterator().next();
      }
    }
  }

//  @formatter:off
  /*
   * String sql = "SELECT exp.EXP_EXPORTNO_SEQ, exp.EXP_CONTAINERNO, exp.EXP_GATEINCLERKID, TO_CHAR(exp.EXP_TIMEGATEIN, 'DD-MM-YYYY HH24:MI:ss') AS EXP_TIMEGATEIN "
                + " , TO_CHAR(exp.EXP_TIMEGATEINOK, 'DD-MM-YYYY HH24:MI:ss') AS EXP_TIMEGATEINOK, exp.CLI_CLIENTID_GATEIN "
                + " , exp.CAL_VARIANCE, exp.SOLAS_DETAIL_NO, exp.WITHIN_TOLERANCE, exp.MGW, exp.SHIPPER_VGM, exp.EXP_NET_WEIGHT, exp.BACK_TO_BACK  " 
                + " , exp.BOOKING_ID, exp.EXP_HCTDID, exp.EXP_BOOKINGNO, exp.FA_LEDGER_CODE, exp.EXP_LINE, exp.EXP_CONT_ISO_CODE, exp.SOLAS_REF_NO, exp.COSMOS_GROSS_WEIGHT " 
                + " , exp.VGM_TYPE, exp.EXP_TRUCK_POS, exp.VESSEL_SCN, exp.SOLAS_CERT, exp.EXP_PRINT_EIR "
                + " FROM SCSS_EXPORTS exp " 
                + " WHERE exp.EXP_TIMEGATEIN BETWEEN to_date("+ SQL.format(startDate) +", 'dd/MM/YYYY') and to_date("+ SQL.format(endDate) +", 'dd/MM/YYYY')"
                + " AND exp.EXP_EIRSTATUS = 'A' "
                + " AND ( exp.VGM_TYPE  = " + SQL.format(SCSSConstant.VGM_INSTRUCTION_TERMINAL) + "
                       OR (exp.VGM_TYPE = " + SQL.format(SCSSConstant.VGM_INSTRUCTION_SHIPPER) + " 
                    AND exp.WITHIN_TOLERANCE = 'F')) "
                + " AND exp.BOOKING_ID is not null "
                + " AND exp.EXP_FULL_EMPTY_FLAG = 'F' "
                + " ORDER BY exp.EXP_PRINT_EIR ";
   * */



  @Bean(name = "solasBillingJobTrigger")
  public JobDetailFactoryBean sampleJob() {
    return ConfigureQuartz.createJobDetail(this.getClass());
  }

  @Bean(name = "solasBillingJobTriggerBeanTrigger")
  public CronTriggerFactoryBean sampleJobTrigger(@Qualifier("solasBillingJobTrigger") JobDetail jobDetail) {
    return ConfigureQuartz.createCronTrigger(jobDetail, frequency);
  }




}
