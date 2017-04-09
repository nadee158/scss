package com.privasia.scss.gateout.util;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.privasia.scss.common.dto.GateOutWriteRequest;
import com.privasia.scss.common.enums.ImpExpFlagStatus;

@Component
public class GateOutWriteRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return GateOutWriteRequest.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    GateOutWriteRequest gateOutWriteRequest = (GateOutWriteRequest) target;
    System.out.println("gateOutWriteRequest.getGateOUTDateTime() " + gateOutWriteRequest.getGateOUTDateTime());
    if (gateOutWriteRequest.getGateOUTDateTime() == null) {
      errors.rejectValue("gateOUTDateTime", "NULL_DATE", "gateOUTDateTime is required!");
    } else if (gateOutWriteRequest.getGateOUTDateTime().isAfter(LocalDateTime.now())) {
      errors.rejectValue("gateOUTDateTime", "FUTURE_DATE", "gateOUTDateTime cannot be a future date/time");
    }

    if (gateOutWriteRequest.getImpExpFlag() == null) {
      errors.rejectValue("impExpFlag", "NULL_ImpExpFlag", "ImpExpFlag is required!");
    } else {
      ImpExpFlagStatus impExpFlagStatus = ImpExpFlagStatus.fromValue(gateOutWriteRequest.getImpExpFlag());
      if (impExpFlagStatus == null) {
        errors.rejectValue("impExpFlag", "INVALID_ImpExpFlag", "ImpExpFlag is not valid!");
      }
    }

  }


}
