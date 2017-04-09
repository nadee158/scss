package com.privasia.scss.gatein.util;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.enums.ImpExpFlagStatus;

@Component
public class GateInWriteRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return GateInWriteRequest.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    GateInWriteRequest gateInWriteRequest = (GateInWriteRequest) target;
    System.out.println("gateInWriteRequest.getGateInDateTime() " + gateInWriteRequest.getGateInDateTime());
    if (gateInWriteRequest.getGateInDateTime() == null) {
      errors.rejectValue("gateInDateTime", "NULL_DATE", "gateInDateTime is required!");
    } else if (gateInWriteRequest.getGateInDateTime().isAfter(LocalDateTime.now())) {
      errors.rejectValue("gateInDateTime", "FUTURE_DATE", "gateInDateTime cannot be a future date/time");
    }

    if (gateInWriteRequest.getImpExpFlag() == null) {
      errors.rejectValue("impExpFlag", "NULL_ImpExpFlag", "ImpExpFlag is required!");
    } else {
      ImpExpFlagStatus impExpFlagStatus = ImpExpFlagStatus.fromValue(gateInWriteRequest.getImpExpFlag());
      if (impExpFlagStatus == null) {
        errors.rejectValue("impExpFlag", "INVALID_ImpExpFlag", "ImpExpFlag is not valid!");
      }
    }

  }


}
