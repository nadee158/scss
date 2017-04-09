package com.privasia.scss.gateout.util;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.privasia.scss.common.dto.GateOutRequest;

@Component
public class GateOutRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return GateOutRequest.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    GateOutRequest gateOutRequest = (GateOutRequest) target;
    System.out.println("gateOutRequest.getGateOUTDateTime() " + gateOutRequest.getGateOUTDateTime());
    if (gateOutRequest.getGateOUTDateTime() == null) {
      errors.rejectValue("gateOUTDateTime", "NULL_DATE", "gateOUTDateTime is required!");
    } else if (gateOutRequest.getGateOUTDateTime().isAfter(LocalDateTime.now())) {
      errors.rejectValue("gateOUTDateTime", "FUTURE_DATE", "gateOUTDateTime cannot be a future date/time");
    }

  }


}
