package com.privasia.scss.gatein.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

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
import com.privasia.scss.common.util.DateUtil;
import com.privasia.scss.common.util.MessageCode;
import com.privasia.scss.common.util.ReturnMsg;
import com.privasia.scss.common.util.UserIpAddressUtil;
import com.privasia.scss.core.dto.GateInForm;
import com.privasia.scss.core.dto.GateInfo;
import com.privasia.scss.core.util.constant.ButtonType;
import com.privasia.scss.gatein.dto.Container;
import com.privasia.scss.gatein.dto.ExportSSR;
import com.privasia.scss.gatein.dto.SCUInfo;
import com.privasia.scss.gatein.dto.VesselOmitDto;
import com.privasia.scss.gatein.service.CardService;
import com.privasia.scss.gatein.service.CardUsageService;
import com.privasia.scss.gatein.service.ClientService;
import com.privasia.scss.gatein.service.ContainerService;
import com.privasia.scss.gatein.service.VesselOmitService;


@RestController
@RequestMapping("/in")
public class GateInExpNormalController {

  private static Logger logger = Logger.getLogger(GateInExpNormalController.class.getName());

  @Autowired
  private CardUsageService cardUsageService;

  @Autowired
  private ContainerService containerService;

  @Autowired
  private VesselOmitService vesselOmitService;

  @Autowired
  private CardService cardService;

  @Autowired
  private ClientService clientService;

  @RequestMapping(value = "/gateInImpNormal", method = RequestMethod.GET)
  public ResponseEntity<String> gateInImpNormal(@RequestBody GateInForm f, HttpServletRequest request) {

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

        int pressedButton = f.getPressedButton();
        ButtonType buttonType = ButtonType.fromCode(pressedButton);

        Container c1 = null;
        Container c2 = null;

        switch (buttonType) {

          case CONTINUE_BTN_PRESSED:

            c1 = findContainer(f.getContainerNoC1());
            c2 = findContainer(f.getContainerNoC2());

            if (StringUtils.isNotBlank(f.getContainerNoC1()) && StringUtils.isNotBlank(f.getContainerNoC2())) {
              f.setBackToBack("Y");
            } else {
              f.setBackToBack("N");
            }

            // validating container 1
            String returnMessage = validateContainer(c1, f, gateInInfo);

            // validating container 2
            returnMessage = returnMessage + validateContainer(c2, f, gateInInfo);

            if (StringUtils.isNotEmpty(returnMessage)) {
              returnMessage = ReturnMsg.trim(returnMessage);
              returnedView = "INPUT";
            } else {

              f.setBookingNoC1(c1.getBookingNo());
              f.setSeq1(c1.getSeq());
              if (c2 != null) {
                f.setBookingNoC2(c2.getBookingNo());
                f.setSeq2(c2.getSeq());
              }

              // to determine whether the smart card is Mastercard?
              // if yes then redirect to Gate In Export Bypass page
              // else redirect to Gate In Export page

              if (gateInInfo.isMCByPass()) {
                f.setMCBypass(true);
                /*
                 * could not find a master card modal, ignoring the line
                 */
                // dao.selectMCInfo(cardIdSeq, f);
                returnedView = "MC_BYPASS";

              } else {
                // no place to use this info - previously assigned to the UI form
                SCUInfo scuInfo = cardService.selectSCUInfo(gateInInfo.getCardIdSeq());
                // no place to use this info - previously assigned to the UI form
                String laneNo = clientService.getLaneNo(gateInInfo.getClientId());
              }

            }



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

  private String validateContainer(Container c, GateInForm f, GateInfo gateInInfo) throws Exception {
    String returnmsg = "";

    if (c != null) {
      /**
       * WPTSCSSSUP-163: check multiple booking
       */
      if (c.getTotalBooking() > 1) {
        returnmsg += MessageCode.format("ERR_MSG_095", new Object[] {c.getContainerNo()}) + ReturnMsg.SEPARATOR;
      }
      if (!c.isBookingNoExist()) {
        returnmsg += MessageCode.format("ERR_MSG_015", new Object[] {c.getContainerNo()}) + ReturnMsg.SEPARATOR;
      }

      returnmsg += validateVesselOmitDto(c);

      returnmsg += validateContainerAllowedIn(c);

      if (c.isInternalBlock()) {
        f.setInternalBlock(true);
        returnmsg += MessageCode.format("ERR_MSG_082", new Object[] {c.getContainerNo(), c.getInternalBlockDesc()})
            + ReturnMsg.SEPARATOR;
      }

      ExportSSR ssrC1 = new ExportSSR(gateInInfo.getTimeGateIn(), c.getVesselDateEta_ddMMyyyyHHmmss());
      f.setExpHasReplanSSRC1(ssrC1.getHasReplan());
      f.setExpHasOverClosingSSRC1(ssrC1.getHasOverClosing());
      if (ssrC1.getSSRBlockStatus()) {
        f.setExpSSRBlockStatusC1("BLK");
      } else {
        f.setExpSSRBlockStatusC1("RLS");
      }
      if (StringUtils.isNotEmpty(gateInInfo.getWeightBridge())) {
        f.setNetWeightC1(gateInInfo.getWeightBridge());
      }
    }

    return returnmsg;
  }

  private String validateContainerAllowedIn(Container c) throws Exception {
    String returnmsg = null;
    boolean isContainerAllowedIn = containerService.isAllowIn(c);
    if (!isContainerAllowedIn) {
      if (c.isEarlyEntry()) {
        final Date now = new Date();
        final SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        final SimpleDateFormat dateTime = new SimpleDateFormat("dd/MM/yyyy h:mm a");
        final SimpleDateFormat time = new SimpleDateFormat("h:mm a");
        String dateNow = date.format(now);
        String startFullDate = dateNow + " " + c.getStartEarlyEntry();
        String endFullDate = dateNow + " " + c.getEndEarlyEntry();
        Date strtFullDate = dateTime.parse(startFullDate);
        Date edFullDate = dateTime.parse(endFullDate);
        if (strtFullDate.after(edFullDate)) {
          edFullDate = DateUtil.addDate(edFullDate, 1);
        }
        returnmsg += MessageCode.format("ERR_MSG_100",
            new Object[] {c.getContainerNo(), time.format(strtFullDate), time.format(edFullDate)})
            + ReturnMsg.SEPARATOR;
      } else {
        returnmsg += MessageCode.format("ERR_MSG_064", new Object[] {c.getContainerNo()}) + ReturnMsg.SEPARATOR;
      }
    }
    return returnmsg;
  }

  private String validateVesselOmitDto(Container c) throws Exception {
    String returnmsg = null;
    if (StringUtils.isNotBlank(c.getAgentCode()) && StringUtils.isNotBlank(c.getLineCode())) {
      /*
       * Vessel OMIT Container 1
       */
      VesselOmitDto vesselOmitDto = vesselOmitService.getVesselOmit(c.getLineCode(), c.getAgentCode());
      if (vesselOmitDto != null) {
        if (StringUtils.isNotBlank(vesselOmitDto.getVesselVoyIn())) {
          if (StringUtils.contains(c.getVesselVoyage(), vesselOmitDto.getVesselVoyIn())) {
            returnmsg +=
                MessageCode.format("ERR_MSG_081", new Object[] {c.getContainerNo(), vesselOmitDto.getLineCode(),
                    vesselOmitDto.getAgentCode(), vesselOmitDto.getVesselVoyIn()}) + ReturnMsg.SEPARATOR;
          }
        } else if (StringUtils.isNotBlank(vesselOmitDto.getVesselVoyOut())) {
          if (StringUtils.contains(c.getVesselVoyageOut(), vesselOmitDto.getVesselVoyOut())) {
            returnmsg +=
                MessageCode.format("ERR_MSG_081", new Object[] {c.getContainerNo(), vesselOmitDto.getLineCode(),
                    vesselOmitDto.getAgentCode(), vesselOmitDto.getVesselVoyOut()}) + ReturnMsg.SEPARATOR;
          }
        }
      }
    }
    return returnmsg;
  }

  private Container findContainer(String containerNo) throws Exception {
    if (StringUtils.isNotEmpty(containerNo)) {
      return containerService.getContainerByContainerNo(containerNo);
    }
    return null;
  }

}
