package com.privasia.scss.core.util.service;

import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import com.privasia.scss.common.dto.NotificationDTO;
import com.privasia.scss.common.util.ApplicationConstants;
import com.privasia.scss.common.util.NotificationSentStatus;
import com.privasia.scss.core.service.WDCGlobalSettingService;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Component("MailUtil")
public class MailUtil implements Serializable {

  private static final long serialVersionUID = 649816335599665740L;
  private static final Logger LOG = Logger.getLogger(MailUtil.class);

  @Autowired
  private JavaMailSender javaMailService;

  @Autowired
  private Configuration freemarkerConfiguration;

  @Value("${mail.sender}")
  private String senderEmail;

  @Value("${mail.to}")
  private String recieverEmail;

  @Autowired
  private WDCGlobalSettingService wdcGlobalSettingService;


  /**
   * @param emailMessage
   * @param recieverEmailAddresses
   */
  public NotificationSentStatus sendEmail(Map<String, List<NotificationDTO>> resultsMap) throws MailException {

    NotificationSentStatus sentStatus = new NotificationSentStatus();

    if (!(resultsMap == null || resultsMap.isEmpty())) {

      try {

        javaMailService.send(new MimeMessagePreparator() {

          @Override
          public void prepare(MimeMessage mimeMessage) throws Exception {

            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            String mailSubject = "Notification From Kiosk Health Check";
            message.setSubject(mailSubject);


            String senderEmailFromDB = wdcGlobalSettingService.getWDCGlobalSetting(ApplicationConstants.SMTP_FROM);
            if (StringUtils.isEmpty(senderEmailFromDB)) {
              senderEmailFromDB = senderEmail;
            }
            message.setFrom(new InternetAddress(senderEmailFromDB));

            LOG.info("senderEmailFromDB: " + senderEmailFromDB);

            // message.setTo(to);
            InternetAddress toAddress = new InternetAddress(StringUtils.trim(recieverEmail));

            message.setTo(toAddress);

            Template template = freemarkerConfiguration.getTemplate("mailTemplate.ftl");

            Writer out = new StringWriter();
            Map<String, Object> freeMarkerMap = new HashMap<String, Object>();
            freeMarkerMap.put("resultMap", resultsMap);
            template.process(freeMarkerMap, out);

            String text = out.toString();

            message.setText(text, true);

          }

        });
      } catch (Exception e) {
        e.printStackTrace();
        sentStatus.setStatus(ApplicationConstants.ERROR);
        sentStatus.setExceptionMesssage(e.getMessage());
        sentStatus.setException(e);
      }
    }
    sentStatus.setStatus(ApplicationConstants.SUCCESS);
    return sentStatus;
  }



}
