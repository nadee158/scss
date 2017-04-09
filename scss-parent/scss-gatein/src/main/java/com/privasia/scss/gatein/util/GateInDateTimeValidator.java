package com.privasia.scss.gatein.util;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.privasia.scss.common.dto.GateInRequest;

@Component
public class GateInDateTimeValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return GateInRequest.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    GateInRequest gateInRequest = (GateInRequest) target;
    System.out.println("gateInRequest.getGateInDateTime() " + gateInRequest.getGateInDateTime());
    if (gateInRequest.getGateInDateTime() == null) {
      errors.rejectValue("gateInDateTime", "NULL_DATE", "Please enter a date for Gate in date time");
    } else if (gateInRequest.getGateInDateTime().isAfter(LocalDateTime.now())) {
      errors.rejectValue("gateInDateTime", "FUTURE_DATE", "Gate in date time cannot be a future date/time");
    }

  }


}
