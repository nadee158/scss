package com.privasia.scss.kioskbooth.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.ClientInfo;
import com.privasia.scss.common.dto.KioskBoothRightInfo;
import com.privasia.scss.common.enums.DBTransactionStatus;
import com.privasia.scss.common.enums.KioskLockStatus;
import com.privasia.scss.common.enums.TransactionType;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.KioskBoothContainerAttribute;
import com.privasia.scss.core.model.KioskBoothRights;
import com.privasia.scss.core.model.KioskBoothRightsPK;
import com.privasia.scss.core.repository.KioskBoothRightsRepository;

@Service("kioskBoothService")
public class KioskBoothService {

  @Autowired
  private KioskBoothRightsRepository kioskBoothRightsRepository;

  public List<ClientInfo> getKioskInfoByBooth(String boothID) {
    List<ClientInfo> clientInfoList = new ArrayList<ClientInfo>();
    Client clientBoothID = new Client();
    clientBoothID.setClientID(Long.parseLong(boothID));
    List<KioskBoothRights> KioskBoothRightList =
        kioskBoothRightsRepository.findByKioskBoothRightsID_BoothID(clientBoothID);
    if (!(KioskBoothRightList == null || KioskBoothRightList.isEmpty())) {
      for (KioskBoothRights kioskBoothRights : KioskBoothRightList) {

        KioskBoothRightsPK kioskBoothRightsID = kioskBoothRights.getKioskBoothRightsID();

        Client kioskID = kioskBoothRightsID.getKioskID();

        ClientInfo clientInfo = new ClientInfo();

        clientInfo.setClientIdSeq(kioskID.getClientID());
        clientInfo.setWebIPAddress(kioskID.getWebIPAddress());
        clientInfo.setClientDescription(kioskID.getDescription());
        clientInfo.setClientStaus(kioskID.getStatus().getValue());
        clientInfo.setClientType(kioskID.getType().getValue());
        clientInfo.setUnitNo(kioskID.getUnitNo());
        //clientInfo.setCsmControl(kioskID.isCsmControl());
        clientInfo.setCosmosPortNumber(Integer.toString(kioskID.getCosmosPortNo()));
        clientInfo.setSortSeq(kioskID.getSortSEQ());
        clientInfo.setCameraServerIPAddress(kioskID.getCameraServerIPAddress());
        clientInfo.setCameraServerPort(Integer.toString(kioskID.getCameraServerPortNo()));
        clientInfo.setDisplayScreenId(Integer.toString(kioskBoothRights.getDisplayScreenID()));
        clientInfo.setKioskLockStatus(kioskBoothRights.getKioskLockStatus().getValue());
        clientInfo.setLaneNO(kioskID.getLaneNo());
        clientInfo.setFtpIP(kioskID.getFtpIPAddress());
        clientInfo.setFtpPort(kioskID.getFtpPort());
        clientInfo.setFtpProtocal(kioskID.getFtpProtocol());
        clientInfo.setFtpUserName(kioskID.getFtpUsername());
        clientInfo.setFtpPassword(kioskID.getFtpPassword());
        clientInfo.setFtpDirectory(kioskID.getFtpDirectory());
        clientInfo.setWithCameraImage(Boolean.toString(kioskID.isWithCameraImage()));

        clientInfoList.add(clientInfo);


      }
    }
    return clientInfoList;
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public int updateKioskBoothInfo(KioskBoothRightInfo kioskBoothRightInfo) {
    boolean islock = false;
    int rs = 0;
    try {

      long kioskID = 0;
      if (StringUtils.isNotEmpty(kioskBoothRightInfo.getKioskID())) {
        kioskID = Long.parseLong(kioskBoothRightInfo.getKioskID());
      }
      long boothID = 0;
      if (StringUtils.isNotEmpty(kioskBoothRightInfo.getBoothID())) {
        boothID = Long.parseLong(kioskBoothRightInfo.getBoothID());
      }
      int cardNumber = 0;
      if (StringUtils.isNotEmpty(kioskBoothRightInfo.getCardNumber())) {
        cardNumber = Integer.parseInt(kioskBoothRightInfo.getCardNumber());
      }

      if (StringUtils.equals(kioskBoothRightInfo.getKioskLockStatus(),
          DBTransactionStatus.LOCK.getValue())) {
        List<KioskBoothRights> list = kioskBoothRightsRepository.findByKioskIDAndKioskLockStatusAndBoothID(kioskID,
            KioskLockStatus.LOCK, boothID);
        if (!(list == null || list.isEmpty())) {
          islock = true;
        }
      }

      if (!islock) {
        Client clKioskID = new Client();
        clKioskID.setClientID(kioskID);
        List<KioskBoothRights> list = null;

        if (boothID != 0) {
          Client clBoothID = new Client();
          clBoothID.setClientID(boothID);
          if (cardNumber != 0) {
            list =
                kioskBoothRightsRepository.findByKioskBoothRightsID_KioskIDAndKioskBoothRightsID_BoothIDAndCardNumber(
                    clKioskID, clBoothID, cardNumber);
          } else {
            list = kioskBoothRightsRepository.findByKioskBoothRightsID_KioskIDAndKioskBoothRightsID_BoothID(clKioskID,
                clBoothID);
          }
        } else {
          if (cardNumber != 0) {
            list = kioskBoothRightsRepository.findByKioskBoothRightsID_KioskIDAndCardNumber(clKioskID, cardNumber);
          } else {
            list = kioskBoothRightsRepository.findByKioskBoothRightsID_KioskID(clKioskID);
          }
        }


        if (!(list == null || list.isEmpty())) {
          for (KioskBoothRights kioskBoothRights : list) {

            int newCardNumber = 0;
            if (StringUtils.isNotEmpty(kioskBoothRightInfo.getNewCardNumber())) {
              newCardNumber = Integer.parseInt(kioskBoothRightInfo.getNewCardNumber());
            }
            kioskBoothRights.setCardNumber(newCardNumber);

            KioskBoothContainerAttribute container01 = kioskBoothRights.getContainer01();
            if (container01 == null) {
              container01 = new KioskBoothContainerAttribute(kioskBoothRightInfo, 1);
            } else {
              container01.updateFromKioskBoothRightInfo(kioskBoothRightInfo, 1);
            }
            kioskBoothRights.setContainer01(container01);

            KioskBoothContainerAttribute container02 = kioskBoothRights.getContainer02();
            if (container02 == null) {
              container02 = new KioskBoothContainerAttribute(kioskBoothRightInfo, 2);
            } else {
              container02.updateFromKioskBoothRightInfo(kioskBoothRightInfo, 2);
            }
            kioskBoothRights.setContainer02(container02);

            KioskBoothContainerAttribute container03 = kioskBoothRights.getContainer03();
            if (container03 == null) {
              container03 = new KioskBoothContainerAttribute(kioskBoothRightInfo, 3);
            } else {
              container03.updateFromKioskBoothRightInfo(kioskBoothRightInfo, 3);
            }
            kioskBoothRights.setContainer03(container03);

            KioskBoothContainerAttribute container04 = kioskBoothRights.getContainer04();
            if (container04 == null) {
              container04 = new KioskBoothContainerAttribute(kioskBoothRightInfo, 4);
            } else {
              container04.updateFromKioskBoothRightInfo(kioskBoothRightInfo, 4);
            }
            kioskBoothRights.setContainer04(container04);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy HH:mm:ss", Locale.ENGLISH);

            if (StringUtils.isNotEmpty(kioskBoothRightInfo.getCardScanTime())) {
              LocalDateTime cardScanTime = LocalDateTime.parse(kioskBoothRightInfo.getCardScanTime(), formatter);
              kioskBoothRights.setCardScanTime(cardScanTime);
            }

            if (StringUtils.isNotEmpty(kioskBoothRightInfo.getKioskSelectTime())) {
              LocalDateTime kioskSelectedTime =
                  LocalDateTime.parse(kioskBoothRightInfo.getKioskSelectTime(), formatter);
              kioskBoothRights.setKioskSelectedTime(kioskSelectedTime);
            }

            if (StringUtils.isNotEmpty(kioskBoothRightInfo.getDisplayScreenID())) {
              kioskBoothRights.setDisplayScreenID(Integer.parseInt(kioskBoothRightInfo.getDisplayScreenID()));
            }

            kioskBoothRights.setKioskLockStatus(KioskLockStatus.fromCode(kioskBoothRightInfo.getKioskLockStatus()));

            kioskBoothRights.setTransactionIDList(kioskBoothRightInfo.getTransID());

            kioskBoothRights.setDriverName(kioskBoothRightInfo.getDriverName());
            kioskBoothRights.setPmHeadNo(kioskBoothRightInfo.getPmHead());
            kioskBoothRights.setTruckCompany(kioskBoothRightInfo.getTruckCO());
            kioskBoothRights.setDriverIC(kioskBoothRightInfo.getDriverIC());
            kioskBoothRights.setPlateNo(kioskBoothRightInfo.getPlateNo());
            kioskBoothRights.setTransactionType(TransactionType.fromCode(kioskBoothRightInfo.getTransactionType()));

            kioskBoothRights.setReviseHeadNo(kioskBoothRightInfo.getReviseHeadNo());
            kioskBoothRights.setReviseHeadNoRemarks(kioskBoothRightInfo.getReviseHeadNoRemark());
            kioskBoothRights.setRetakePhoto(Boolean.getBoolean(kioskBoothRightInfo.getReTakePhoto()));

            if (StringUtils.isNotEmpty(kioskBoothRightInfo.getTrxCompleateTime())) {
              LocalDateTime trxCompleateTime =
                  LocalDateTime.parse(kioskBoothRightInfo.getTrxCompleateTime(), formatter);
              kioskBoothRights.setTrxCompleteTime(trxCompleateTime);
            }

            kioskBoothRights.setLockUserID(kioskBoothRightInfo.getLockUserID());
            kioskBoothRights.setLockUserName(kioskBoothRightInfo.getLockUserName());
            kioskBoothRights.setReferReason01List(kioskBoothRightInfo.getReferReasonList01());
            kioskBoothRights.setReferReason02List(kioskBoothRightInfo.getReferReasonList02());

            kioskBoothRightsRepository.save(kioskBoothRights);

          }
        }

        if (kioskBoothRightInfo.getKioskLockStatus().equals(DBTransactionStatus.LOCK.getValue())
            && StringUtils.isNotEmpty(kioskBoothRightInfo.getBoothID())) {

          List<KioskBoothRights> updatingList = kioskBoothRightsRepository.findByKioskIDAndNotBoothID(kioskID, boothID);
          if (!(updatingList == null || updatingList.isEmpty())) {
            for (KioskBoothRights kioskBoothRights : updatingList) {
              kioskBoothRights.setCardNumber(0);
              KioskBoothContainerAttribute container01 = kioskBoothRights.getContainer01();
              if (container01 == null) {
                container01 = new KioskBoothContainerAttribute(null, 1);
              } else {
                container01.updateFromKioskBoothRightInfo(null, 1);
              }
              kioskBoothRights.setContainer01(container01);

              KioskBoothContainerAttribute container02 = kioskBoothRights.getContainer02();
              if (container02 == null) {
                container02 = new KioskBoothContainerAttribute(null, 2);
              } else {
                container02.updateFromKioskBoothRightInfo(null, 2);
              }
              kioskBoothRights.setContainer02(container02);

              KioskBoothContainerAttribute container03 = kioskBoothRights.getContainer03();
              if (container03 == null) {
                container03 = new KioskBoothContainerAttribute(null, 3);
              } else {
                container03.updateFromKioskBoothRightInfo(null, 3);
              }
              kioskBoothRights.setContainer03(container03);

              KioskBoothContainerAttribute container04 = kioskBoothRights.getContainer04();
              if (container04 == null) {
                container04 = new KioskBoothContainerAttribute(null, 4);
              } else {
                container04.updateFromKioskBoothRightInfo(null, 4);
              }
              kioskBoothRights.setContainer04(container04);
              kioskBoothRights.setCardScanTime(null);
              kioskBoothRights.setKioskSelectedTime(null);
              kioskBoothRights.setDisplayScreenID(0);
              kioskBoothRights.setKioskLockStatus(null);
              kioskBoothRights.setTransactionIDList(null);
              kioskBoothRights.setDriverName(null);
              kioskBoothRights.setPmHeadNo(null);
              kioskBoothRights.setTruckCompany(null);
              kioskBoothRights.setDriverIC(null);
              kioskBoothRights.setPlateNo(null);
              kioskBoothRights.setTransactionType(null);
              kioskBoothRights.setReviseHeadNo(null);
              kioskBoothRights.setReviseHeadNoRemarks(null);
              kioskBoothRights.setRetakePhoto(false);
              kioskBoothRights.setTrxCompleteTime(null);
              kioskBoothRights.setLockUserID(null);
              kioskBoothRights.setLockUserName(null);
              kioskBoothRights.setReferReason01List(null);
              kioskBoothRights.setReferReason02List(null);
              kioskBoothRightsRepository.save(kioskBoothRights);
            }
          }

        }

      } else {
        rs = -1;
      }


    } catch (Exception e) {
      e.printStackTrace();
    }


    return rs;
  }


  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public KioskBoothRights getLockedKioskBoothInfo(String kioskID) {
    // Predicate kioskBoothInfoByKioskID =
    // KioskBoothRightsPredicates.KioskBoothInfoByKioskID(kioskID);
    // Predicate KioskBoothStatus =
    // KioskBoothRightsPredicates.KioskBoothStatus(KioskLockStatus.LOCK.name());
    // Predicate condition = ExpressionUtils.and(kioskBoothInfoByKioskID, KioskBoothStatus);
    // return kioskBoothRightsRepository.findOne(condition);
    return null;

  }



}
