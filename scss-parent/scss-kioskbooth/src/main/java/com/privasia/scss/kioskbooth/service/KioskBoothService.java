package com.privasia.scss.kioskbooth.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.ClientInfo;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.KioskBoothRights;
import com.privasia.scss.core.model.KioskBoothRightsPK;
import com.privasia.scss.core.repository.ClientRepository;
import com.privasia.scss.core.repository.KioskBoothRightsRepository;
import com.privasia.scss.kioskbooth.dto.KioskBoothRightInfo;

@Service("kioskBoothService")
public class KioskBoothService {

  @Autowired
  private KioskBoothRightsRepository kioskBoothRightsRepository;

  @Autowired
  private ClientRepository clientRepository;

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
        // clientInfo.setCsmControl(kioskID.isCsmControl());
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

  public String activateBoothsByKioskId(KioskBoothRightInfo kioskBoothRightInfo) {
    // TODO Auto-generated method stub
    return null;
  }

  public KioskBoothRights convertDtoToDomain(KioskBoothRightInfo kioskBoothRightInfo) {
    if (!(kioskBoothRightInfo == null)) {
      KioskBoothRightsPK pk = new KioskBoothRightsPK();
      pk.setBoothID(clientRepository.findOne(kioskBoothRightInfo.getBoothID()).orElse(null));
      pk.setKioskID(clientRepository.findOne(kioskBoothRightInfo.getKioskID()).orElse(null));
      KioskBoothRights kioskBoothRights = kioskBoothRightInfo.constructKioskBoothRights(pk);
      if (!(kioskBoothRightInfo.getContainer01() == null)) {
        kioskBoothRights.setContainer01(kioskBoothRightInfo.getContainer01().constructContainerAttribute());
      }
      if (!(kioskBoothRightInfo.getContainer02() == null)) {
        kioskBoothRights.setContainer02(kioskBoothRightInfo.getContainer02().constructContainerAttribute());
      }
      if (!(kioskBoothRightInfo.getContainer03() == null)) {
        kioskBoothRights.setContainer03(kioskBoothRightInfo.getContainer03().constructContainerAttribute());
      }
      if (!(kioskBoothRightInfo.getContainer04() == null)) {
        kioskBoothRights.setContainer04(kioskBoothRightInfo.getContainer04().constructContainerAttribute());
      }
      return kioskBoothRights;
    }
    return null;
  }

}
