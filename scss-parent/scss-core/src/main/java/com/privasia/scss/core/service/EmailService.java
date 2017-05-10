package com.privasia.scss.core.service;

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.privasia.scss.common.util.ApplicationConstants;

@Service("emailService")
public class EmailService {

  @Value("${mail.sender}")
  private String senderEmail;

  private JavaMailSender javaMailSender;
  private TemplateEngine emailTemplateEngine;
  private WDCGlobalSettingService wdcGlobalSettingService;

  public void setJavaMailSender(@Qualifier("javaMailSender") JavaMailSender javaMailSender) {
    this.javaMailSender = javaMailSender;
  }

  public void setEmailTemplateEngine(@Qualifier("emailTemplateEngine") TemplateEngine emailTemplateEngine) {
    this.emailTemplateEngine = emailTemplateEngine;
  }

  @Autowired
  public void setWdcGlobalSettingService(WDCGlobalSettingService wdcGlobalSettingService) {
    this.wdcGlobalSettingService = wdcGlobalSettingService;
  }

  @Async
  public void prepareAndSendEmail(String recipient, String subject, Context context, String templateName) {
    MimeMessagePreparator messagePreparator = mimeMessage -> {
      MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8"); // true =
      String senderEmailFromDB = wdcGlobalSettingService.getWDCGlobalSetting(ApplicationConstants.SMTP_FROM);
      if (StringUtils.isEmpty(senderEmailFromDB)) {
        senderEmailFromDB = senderEmail;
      }
      messageHelper.setFrom(new InternetAddress(senderEmailFromDB));
      messageHelper.setTo(new InternetAddress(recipient));
      messageHelper.setSubject(subject);
      String content = build(context, templateName);
      System.out.println("EMAIL CONTENT :" + content);
      messageHelper.setText(content, true);
    };
    javaMailSender.send(messagePreparator);
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

}
