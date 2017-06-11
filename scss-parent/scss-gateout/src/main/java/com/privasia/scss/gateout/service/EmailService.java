package com.privasia.scss.gateout.service;

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.privasia.scss.common.util.ApplicationConstants;
import com.privasia.scss.core.model.SendMailQueue;
import com.privasia.scss.core.repository.SendMailQueueRepository;
import com.privasia.scss.core.service.WDCGlobalSettingService;

@Service("emailService")
public class EmailService {

  @Value("${mail.sender}")
  private String senderEmail;

  private String emailContent = null;

  private JavaMailSender javaMailSender;
  private TemplateEngine emailTemplateEngine;
  private WDCGlobalSettingService wdcGlobalSettingService;
  private SendMailQueueRepository sendMailQueueRepository;


  @Autowired
  public void setSendMailQueueRepository(SendMailQueueRepository sendMailQueueRepository) {
    this.sendMailQueueRepository = sendMailQueueRepository;
  }

  @Autowired
  public void setJavaMailSender(@Qualifier("javaMailSender") JavaMailSender javaMailSender) {
    this.javaMailSender = javaMailSender;
  }

  @Autowired
  public void setEmailTemplateEngine(@Qualifier("emailTemplateEngine") TemplateEngine emailTemplateEngine) {
    this.emailTemplateEngine = emailTemplateEngine;
  }

  @Autowired
  public void setWdcGlobalSettingService(WDCGlobalSettingService wdcGlobalSettingService) {
    this.wdcGlobalSettingService = wdcGlobalSettingService;
  }



  public String prepareAndSendEmail(String recipient, String subject, Context context, String templateName) {
    System.out.println("prepareAndSendEmail ");
    emailContent = null;
    MimeMessagePreparator messagePreparator = mimeMessage -> {
      MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8"); // true =
      String senderEmailFromDB = wdcGlobalSettingService.getWDCGlobalSetting(ApplicationConstants.SMTP_FROM);
      if (StringUtils.isEmpty(senderEmailFromDB)) {
        senderEmailFromDB = senderEmail;
      }
      if (!(context == null || context.getVariables() == null)) {
        if (context.getVariables().get(ApplicationConstants.EMAIL_BCC) != null) {
          messageHelper
              .setBcc(new InternetAddress((String) context.getVariables().get(ApplicationConstants.EMAIL_BCC)));
        }
        if (context.getVariables().get(ApplicationConstants.EMAIL_CC) != null) {
          messageHelper.setCc(new InternetAddress((String) context.getVariables().get(ApplicationConstants.EMAIL_CC)));
        }
      }
      messageHelper.setFrom(new InternetAddress(senderEmailFromDB));
      messageHelper.setTo(new InternetAddress(recipient));
      messageHelper.setSubject(subject);
      emailContent = build(context, templateName);
      System.out.println("EMAIL CONTENT :" + emailContent);
      messageHelper.setText(emailContent, true);
    };
    System.out.println("messagePreparator " + messagePreparator);
    javaMailSender.send(messagePreparator);
    return emailContent;
  }

  public void sendEmail(String recipient, String subject, String message) {
    MimeMessagePreparator messagePreparator = mimeMessage -> {
      MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8"); // true
      String senderEmailFromDB = wdcGlobalSettingService.getWDCGlobalSetting(ApplicationConstants.SMTP_FROM);
      if (StringUtils.isEmpty(senderEmailFromDB)) {
        senderEmailFromDB = senderEmail;
      }
      messageHelper.setFrom(senderEmailFromDB);
      messageHelper.setTo(recipient);
      messageHelper.setSubject(subject);
      messageHelper.setText(message, true);
    };
    javaMailSender.send(messagePreparator);
  }


  public String build(Context context, String templateName) {
    return emailTemplateEngine.process(templateName, context);
  }


  public String prepareAndSaveEmail(String emailTo, String subject, Context context, String templateName) {
    emailContent = build(context, templateName);
    String senderEmailFromDB = wdcGlobalSettingService.getWDCGlobalSetting(ApplicationConstants.SMTP_FROM);
    if (StringUtils.isEmpty(senderEmailFromDB)) {
      senderEmailFromDB = senderEmail;
    }

    SendMailQueue sendMailQueue = new SendMailQueue();
    sendMailQueue.setTimeStampId(Long.toString(System.currentTimeMillis()));
    sendMailQueue.setEmailTo(emailTo);
    if (!(context == null || context.getVariables() == null)) {
      if (context.getVariables().get(ApplicationConstants.EMAIL_BCC) != null) {
        sendMailQueue.setEmailBCC(((String) context.getVariables().get(ApplicationConstants.EMAIL_BCC)));
      }
      if (context.getVariables().get(ApplicationConstants.EMAIL_CC) != null) {
        sendMailQueue.setEmailCC((String) context.getVariables().get(ApplicationConstants.EMAIL_CC));
      }
    }
    sendMailQueue.setEmailFrom(senderEmail);
    sendMailQueue.setEmailSubject(subject);
    sendMailQueue.setEmailMsgClob(emailContent);
    sendMailQueueRepository.save(sendMailQueue);
    return emailContent;
  }

}
