package com.privasia.scss.gatein.controller;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.common.security.AuditContext;
import com.privasia.scss.common.security.SecurityContext;
import com.privasia.scss.common.security.SecurityHelper;
import com.privasia.scss.common.util.UserIpAddressUtil;
import com.privasia.scss.core.dto.GateInForm;
import com.privasia.scss.core.dto.GateInfo;
import com.privasia.scss.core.util.constant.ButtonType;
import com.privasia.scss.gatein.service.CardUsageService;


@RestController
@RequestMapping("/in")
public class GateInExpNormalController {

  private static Logger logger = Logger.getLogger(GateInExpNormalController.class.getName());

  @Autowired
  private CardUsageService cardUsageService;

  @RequestMapping(value = "/gateInImpNormal", method = RequestMethod.GET)
  public ResponseEntity<String> scanCard(@RequestBody GateInForm f, HttpServletRequest request) {

    String returnedView = null;
    String menuId = (String) request.getParameter("menuId");

    SecurityContext securityContext = SecurityHelper.getSecurityContext();
    AuditContext auditContext = SecurityHelper.getAuditContext();
    GateInfo gateInInfo = null;

    try {
      gateInInfo = cardUsageService.lookupGateInfo(UserIpAddressUtil.getUserIp(request));
      boolean cardStatus = cardUsageService.doCheckCardStatus(gateInInfo.getCardIdSeq(), LocalDateTime.now());
      if (!cardStatus) {
        returnedView = "CARD_EXPIRED";
      }
    } catch (Exception e) {
      e.printStackTrace();
      returnedView = "SCAN_CARD";
    }

    if (StringUtils.isEmpty(returnedView)) {
      try {
        // get container numbers from UI - getting by using same old GateInForm
        // keep seperate two container objects in the objects passeed back to ui
        f.setCardIdSeq(gateInInfo.getCardIdSeq());
        f.setClientId(gateInInfo.getClientId());
        f.setTimeIn(gateInInfo.getTimeGateIn());
        f.setExpWeightBridge(gateInInfo.getWeightBridge());
        if (StringUtils.isNotBlank(gateInInfo.getCugIdSeq())) {
          f.setCugIdSeq(gateInInfo.getCugIdSeq());
        }
        if (StringUtils.isNotBlank(menuId)) {
          f.setMenuId(menuId);
        }

        int pressedButton = f.getPressedButton();
        ButtonType buttonType = ButtonType.fromCode(pressedButton);

        switch (buttonType) {

          case CONTINUE_BTN_PRESSED:


            break;
          case OK_BTN_PRESSED:
            break;
          case PRINT_BTN_PRESSED:
            break;
          case PRINT_BAK_BTN_PRESSED:
            break;
          default:

            break;
        }

      } catch (Exception e) {
        e.printStackTrace();
      }
    }



    return new ResponseEntity<String>(returnedView, HttpStatus.OK);
  }

}
