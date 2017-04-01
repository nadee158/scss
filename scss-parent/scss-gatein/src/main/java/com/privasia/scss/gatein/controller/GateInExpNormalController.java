package com.privasia.scss.gatein.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.common.dto.DamageCodeDTO;
import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.GateInForm;
import com.privasia.scss.common.dto.GateInfo;
import com.privasia.scss.common.enums.ButtonType;
import com.privasia.scss.common.util.CommonUtil;
import com.privasia.scss.common.util.DateUtil;
import com.privasia.scss.common.util.ReturnMsg;
import com.privasia.scss.core.service.WDCGlobalSettingService;
import com.privasia.scss.gatein.dto.ExportSSR;
import com.privasia.scss.gatein.service.ClientService;
import com.privasia.scss.gatein.service.IsoCodeService;
import com.privasia.scss.gatein.service.VesselOmitService;
import com.privasia.scss.gatein.util.DGContDesc;



@RestController
@RequestMapping("/in")
public class GateInExpNormalController {

  private static Logger logger = Logger.getLogger(GateInExpNormalController.class.getName());



  @Autowired
  private VesselOmitService vesselOmitService;


  @Autowired
  private ClientService clientService;

  @Autowired
  private WDCGlobalSettingService wDCGlobalSettingService;

  @Autowired
  private IsoCodeService isoCodeService;

  @RequestMapping(value = "/gateInImpNormal", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> gateInImpNormal(@RequestBody GateInForm f, HttpServletRequest request) {

    String returnedView = null;
    String menuId = (String) request.getParameter("menuId");


    GateInfo gateInInfo = null;

    try {
      // gateInInfo = cardUsageService.lookupGateInfo(UserIpAddressUtil.getUserIp(request));
      // boolean cardStatus = cardUsageService.doCheckCardStatus(gateInInfo.getCardIdSeq(),
      // LocalDateTime.now());
      // if (!cardStatus) {
      // returnedView = "CARD_EXPIRED";
      // }
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

        ExportContainer c1 = null;
        ExportContainer c2 = null;

        switch (buttonType) {

          case CONTINUE_BTN_PRESSED:

            String containerNoC1 = StringUtils.EMPTY;
            if (!(f.getContainer1() == null
                || StringUtils.isEmpty(f.getContainer1().getContainer().getContainerNumber()))) {
              containerNoC1 = f.getContainer1().getContainer().getContainerNumber();
            }
            String containerNoC2 = StringUtils.EMPTY;
            if (!(f.getContainer2() == null
                || StringUtils.isEmpty(f.getContainer2().getContainer().getContainerNumber()))) {
              containerNoC2 = f.getContainer1().getContainer().getContainerNumber();
            }

            c1 = findContainer(containerNoC1);
            c2 = findContainer(containerNoC2);

            if (StringUtils.isNotBlank(containerNoC1) && StringUtils.isNotBlank(containerNoC2)) {
              f.setBackToBack("Y");
            } else {
              f.setBackToBack("N");
            }

            // validating container 1
            String returnMessage = validateContainer(c1, gateInInfo);

            // validating container 2
            returnMessage = returnMessage + validateContainer(c2, gateInInfo);

            if (StringUtils.isNotEmpty(returnMessage)) {
              returnMessage = ReturnMsg.trim(returnMessage);
              returnedView = "INPUT";
            } else {

              // if yes then redirect to Gate In Export Bypass page
              // else redirect to Gate In Export page


              // no place to use this info - previously assigned to the UI form
              // SCUInfo scuInfo = cardService.selectSCUInfo(gateInInfo.getCardIdSeq());
              // no place to use this info - previously assigned to the UI form
              // String laneNo = clientService.getLaneNo(gateInInfo.getClientId());

              /**
               * Query Export Information
               */
              // f = containerService.selectContainerNoInfo(f);

              /**
               * Check if container 1 is a DG container
               */
              returnMessage = returnMessage + checkIfDGContainer(c1, returnMessage);
              /**
               * Check if container 2 is a DG container
               */
              returnMessage = returnMessage + checkIfDGContainer(c2, returnMessage);

              /**
               * final step: Check if user is allowed to bypass DG validation
               */
              // f.setAllowBypassDgVal(super.log(request, AccessRight.GATE_BYPASS_DG_VALIDATION));

              // calculateWeightBridge(c1, c2, gateInInfo.getWeightBridge());

              returnedView = "VIEW.NORMAL";



              /**
               * Query Export Information
               */
              // f = containerService.selectContainerNoInfo(f);

              /**
               * Check if container 1 is a DG container
               */
              returnMessage = returnMessage + checkIfDGContainer(c1, returnMessage);
              /**
               * Check if container 2 is a DG container
               */
              returnMessage = returnMessage + checkIfDGContainer(c2, returnMessage);

              /**
               * final step: Check if user is allowed to bypass DG validation
               */
              // f.setAllowBypassDgVal(super.log(request, AccessRight.GATE_BYPASS_DG_VALIDATION));



              returnedView = "VIEW.NORMAL";


            }
            break;
          case OK_BTN_PRESSED:
            returnMessage = "";
            f.setGateImpOrExp("Exports.EXP_FLAG");


            /**
             * Damage ExportContainer 1
             */
            List<String> clearedDamageCodesC1 = getClearedDamageCodes(c1, returnMessage, returnedView);

            /**
             * Damage ExportContainer 2
             */
            List<String> clearedDamageCodesC2 = getClearedDamageCodes(c1, returnMessage, returnedView);

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

  private List<String> getClearedDamageCodes(ExportContainer c, String returnMessage, String returnedView) {
    List<DamageCodeDTO> damages = c.getDamages();
    if (!(damages == null || damages.isEmpty())) {

      List<String> originalDamagedCodes =
          damages.stream().map(DamageCodeDTO::getDamageCode).collect(Collectors.toList());

      long duplicateCount =
          originalDamagedCodes.stream().filter(i -> Collections.frequency(originalDamagedCodes, i) > 1).count();

      if (duplicateCount == 0) {
        return originalDamagedCodes;
      }
      List<String> clearedDamageCodes = originalDamagedCodes.stream()
          .filter(i -> Collections.frequency(originalDamagedCodes, i) == 1).collect(Collectors.toList());
      return clearedDamageCodes;

    }
    return null;

  }

  private String checkIfDGContainer(ExportContainer c, String returnMessage) throws Exception {

    if (returnMessage == null) {
      returnMessage = StringUtils.EMPTY;
    }

    String globalCode = "LPK_EDI";
    String globalSetting = wDCGlobalSettingService.getWDCGlobalSetting(globalCode);
    if ("Y".equalsIgnoreCase(globalSetting)) {

      c.setLpkEdiEnabled("Y");
      /**
       * Step 1: Check if container 1 is a DG container
       */
      if (StringUtils.isNotBlank(c.getImdg())) {
        /**
         * Step 1.5: Check if container 1 has approval to bypass
         */
        if (!c.isBypassDg()) {
          /**
           * Step 2: Check if there is any approval
           */
          if (StringUtils.isBlank(c.getKpaApproval())) {
            /**
             * No approval from LPK
             */
            returnMessage = returnMessage + " DG container " + c.getContainer().getContainerNumber()
                + " approval record not found<br/>";

            c.setDgWithinWindowEntry(false);

          } else {
            /**
             * DG is approve. Check class to verify entry. class 1 has to wait till vessel status is
             * ACT. class 2 and 3 get hours from LPI remarks. Step 3: validate LPK class and timing
             */

            if ("1".equals(c.getKpaClass())) {

              returnMessage =
                  returnMessage + "Class 1 block. Please call supervisor for entry confirmation for container no:"
                      + c.getContainer().getContainerNumber();

              c.setDgWithinWindowEntry(false);

            } else if ("2".equals(c.getKpaClass())) {
              /**
               * default is 72 if no hours found
               */
              // int hours = parseHoursToGateInForDG(f.getGoodsHdlDescC1());
              DGContDesc dgContDesc = new DGContDesc();
              int hours = dgContDesc.parseHoursToGateInForDG(c.getDgDescription());

              LocalDateTime newEta = getNewEta(c.getVesselETADate());
              LocalDateTime allowedGateInDate = newEta.plusHours(-hours);
              LocalDateTime now = LocalDateTime.now();
              // Class 2 block. CC not within TT hours allowed window (XX - YY)
              if (now.isBefore(allowedGateInDate) || now.isAfter(newEta)) {

                returnMessage =
                    returnMessage + "Class " + c.getKpaClass() + " block. " + c.getContainer().getContainerNumber()
                        + " not within " + hours + " hours allowed window (" + allowedGateInDate + " - " + newEta + ")";
                c.setDgWithinWindowEntry(false);

              } else {

                returnMessage = returnMessage + "<div style='color: green;'>ExportContainer  "
                    + c.getContainer().getContainerNumber() + "  has arrived within the assigned entry slot</div>";

              }

            } else if ("3".equals(c.getKpaClass())) {

              int hours = 72;

              LocalDateTime newEta = getNewEta(c.getVesselETADate());
              LocalDateTime allowedGateInDate = newEta.plusHours(-hours);
              LocalDateTime now = LocalDateTime.now();
              // Class 2 block. CC not within TT hours allowed window (XX - YY)
              if (now.isBefore(allowedGateInDate) || now.isAfter(newEta)) {
                if (c.isRegisteredInEarlyEntry()) {

                  if (!c.isBypassDg()) {
                    /**
                     * did not tick bypass early entry window
                     */
                    boolean inEarlyEntryWindow = false; // containerService.inEarlyEntryWindow();
                    if (!inEarlyEntryWindow) {

                      final SimpleDateFormat time = new SimpleDateFormat("h:mm a");
                      Date strtFullDate = getParsedFullDate(c.getStartEarlyEntry());
                      Date edFullDate = getParsedFullDate(c.getEndEarlyEntry());

                      if (strtFullDate.after(edFullDate)) {
                        edFullDate = DateUtil.addDate(edFullDate, 1);
                      }
                      /**
                       * generate message for early entry window
                       */
                      returnMessage = returnMessage + CommonUtil.formatMessageCode("ERR_MSG_101", new Object[] {
                          c.getContainer().getContainerNumber(), time.format(strtFullDate), time.format(edFullDate)})
                          + ReturnMsg.SEPARATOR;

                      c.setDgWithinWindowEntry(false);
                      // return mapping.findForward(VIEW.INPUT);
                    }
                  }
                } else {
                  /*
                   * did not register as early entry
                   */
                  returnMessage = returnMessage + CommonUtil.formatMessageCode("ERR_MSG_094",
                      new Object[] {c.getContainer().getContainerNumber()}) + ReturnMsg.SEPARATOR;
                  c.setDgWithinWindowEntry(false);
                  // return mapping.findForward(VIEW.INPUT);
                }

              } else {
                returnMessage = returnMessage + "<div style='color: green;'>ExportContainer  "
                    + c.getContainer().getContainerNumber() + "  has arrived within the assigned entry slot</div>";
              }
            } // end of dg class type
          }
        } else {
          c.setAllowBypassDgValRemote(true);
        } // end check if container has bypass approval
      } // end check if container is DG
    }
    return returnMessage;
  }

  private Date getParsedFullDate(String entry) throws ParseException {
    Date now = new Date();
    final SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
    final SimpleDateFormat dateTime = new SimpleDateFormat("dd/MM/yyyy h:mm a");
    String dateNow = date.format(now);
    String startFullDate = dateNow + " " + entry;
    return dateTime.parse(startFullDate);
  }

  private LocalDateTime getNewEta(LocalDateTime eta) throws ParseException {
    // SimpleDateFormat sdf_ddMMyyyyHHmmss = new SimpleDateFormat("ddMMyyyyHHmmss");
    // SimpleDateFormat out_msg = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    // Date eta = sdf_ddMMyyyyHHmmss.parse(vesselDateEta);
    return eta.plusHours(2l);
  }

  private String validateContainer(ExportContainer c, GateInfo gateInInfo) throws Exception {
    String returnmsg = "";

    if (c != null) {
      /**
       * WPTSCSSSUP-163: check multiple booking
       */
      if (c.getTotalBooking() > 1) {
        returnmsg += CommonUtil.formatMessageCode("ERR_MSG_095", new Object[] {c.getContainer().getContainerNumber()})
            + ReturnMsg.SEPARATOR;
      }
      if (!c.isBookingNoExist()) {
        returnmsg += CommonUtil.formatMessageCode("ERR_MSG_015", new Object[] {c.getContainer().getContainerNumber()})
            + ReturnMsg.SEPARATOR;
      }



      returnmsg += validateContainerAllowedIn(c);

      if (c.isInternalBlock()) {
        c.setInternalBlock(true);
        returnmsg += CommonUtil.formatMessageCode("ERR_MSG_082",
            new Object[] {c.getContainer().getContainerNumber(), c.getInternalBlockDesc()}) + ReturnMsg.SEPARATOR;
      }


      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
      String formatDateTime = c.getVesselETADate().format(formatter);

      ExportSSR ssr = new ExportSSR("", formatDateTime);
      c.setReplanSSR(Boolean.parseBoolean(ssr.getHasReplan()));
      c.setOverClosingSSR(Boolean.parseBoolean(ssr.getHasOverClosing()));
      if (ssr.getSSRBlockStatus()) {
        c.setSsrBlockStatus("BLK");
      } else {
        c.setSsrBlockStatus("RLS");
      }
      if (StringUtils.isNotEmpty("")) {
        // c.setNetWeight(gateInInfo.getWeightBridge());
      }
    }

    return returnmsg;
  }

  private String validateContainerAllowedIn(ExportContainer c) throws Exception {
    String returnmsg = null;
    boolean isContainerAllowedIn = false;// containerService.isAllowIn(c);
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
        returnmsg += CommonUtil.formatMessageCode("ERR_MSG_100",
            new Object[] {c.getContainer().getContainerNumber(), time.format(strtFullDate), time.format(edFullDate)})
            + ReturnMsg.SEPARATOR;
      } else {
        returnmsg += CommonUtil.formatMessageCode("ERR_MSG_064", new Object[] {c.getContainer().getContainerNumber()})
            + ReturnMsg.SEPARATOR;
      }
    }
    return returnmsg;
  }



  private ExportContainer findContainer(String containerNo) throws Exception {
    if (StringUtils.isNotEmpty(containerNo)) {
      // return containerService.getContainerByContainerNo(containerNo);
    }
    return null;
  }

}
