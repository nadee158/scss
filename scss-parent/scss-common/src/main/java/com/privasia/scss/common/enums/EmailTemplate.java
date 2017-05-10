package com.privasia.scss.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum EmailTemplate implements Enumable {

  CONTAINER_SIZE_TEMPLATE("containerSizeTemplate"), DSO_SEAL_TEMPLATE(
      "dsosealTemplate"), KIOSK_HEALTH_CHECK_MAIL_TEMPLATE("kioskHealthCheckMailTemplate"), MAIL_TEMPLATE(
          "mailTemplate"), NON_STANDARD_SEAL_LINE_CODE_TEMPLATE("nonsStandardSealLineCodeTemplate"), WEIGHT_TEMPLATE(
              "weightTemplate"), WRONG_DOOR_TEMPLATE("wrongdoorTemplate");

  private final String emailTemplate;

  private EmailTemplate(String emailTemplate) {
    this.emailTemplate = emailTemplate;
  }

  /**
   * @return the emailTemplate
   */
  public String getValue() {
    return emailTemplate;
  }

  public Enum<?> getEnumFromValue(String value) {
    return EnumableHelper.getEnumFromValue(this, value, null);
  }

  private static final Map<String, EmailTemplate> LOOKUP = new HashMap<String, EmailTemplate>();

  static {
    for (EmailTemplate emailTemplate : EnumSet.allOf(EmailTemplate.class)) {
      LOOKUP.put(emailTemplate.getValue(), emailTemplate);
    }
  }

  public static EmailTemplate fromValue(String value) {
    return LOOKUP.get(value);
  }

}
