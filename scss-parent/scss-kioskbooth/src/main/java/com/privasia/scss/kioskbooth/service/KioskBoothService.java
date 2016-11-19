package com.privasia.scss.kioskbooth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.ClientInfo;
import com.privasia.scss.common.enums.KioskLockStatus;
import com.privasia.scss.core.exception.BusinessException;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.KioskBoothRights;
import com.privasia.scss.core.model.KioskBoothRightsPK;
import com.privasia.scss.core.predicate.KioskBoothRightsPredicates;
import com.privasia.scss.core.repository.ClientRepository;
import com.privasia.scss.core.repository.KioskBoothRightsRepository;
import com.privasia.scss.kioskbooth.dto.KioskBoothRightInfo;
import com.querydsl.core.types.Predicate;

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



  public Iterable<KioskBoothRights> getKioskBoothInfoById(String kioskID) {
    Predicate kioskBoothInfoByKioskID = KioskBoothRightsPredicates.KioskBoothInfoByKioskID(kioskID);
    return kioskBoothRightsRepository.findAll(kioskBoothInfoByKioskID);
  }

  public java.util.function.Predicate<KioskBoothRights> getKioskLockStatusCombinedPredicate() {
    java.util.function.Predicate<KioskBoothRights> p1 = kb -> kb.getKioskLockStatus().equals(KioskLockStatus.ACTIVE)
        || kb.getKioskLockStatus().equals(KioskLockStatus.LOCK);
    return p1;
  }

  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, readOnly = false)
  public String activateBoothsByKioskId(KioskBoothRightInfo kioskBoothRightInfo) {
    String result = "ERROR";
    if (!(kioskBoothRightInfo == null || StringUtils.isEmpty(kioskBoothRightInfo.getKioskID()))) {
      Iterable<KioskBoothRights> allKiosks = getKioskBoothInfoById(kioskBoothRightInfo.getKioskID());
      Stream<KioskBoothRights> allKiosksStrem = stream(allKiosks);
      boolean isLockedOrActiveAvailable = allKiosksStrem.anyMatch(getKioskLockStatusCombinedPredicate());
      if (!(isLockedOrActiveAvailable)) {
        allKiosks.forEach(kiosk -> {
          KioskBoothRights updatedKioskBoothRights = updateKioskBoothRightsFromDTO(kiosk, kioskBoothRightInfo);
          kioskBoothRightsRepository.save(updatedKioskBoothRights);
        });
        result = "SUCCESS";
      } else {
        throw new BusinessException("Locked or Active records available!");
      }
    } else {
      throw new BusinessException("Kiosk Information is null!");
    }
    return result;
  }

  public KioskBoothRights updateKioskBoothRightsFromDTO(KioskBoothRights kiosk,
      KioskBoothRightInfo kioskBoothRightInfo) {
    if (!(kioskBoothRightInfo == null)) {
      KioskBoothRights kioskBoothRights = kioskBoothRightInfo.updateKioskBoothRights(kiosk);
      if (!(kioskBoothRightInfo.getContainer01() == null)) {
        kioskBoothRights.setContainer01(
            kioskBoothRightInfo.getContainer01().updateContainerAttribute(kioskBoothRights.getContainer01()));
      } else {
        kioskBoothRights.setContainer01(null);
      }
      if (!(kioskBoothRightInfo.getContainer02() == null)) {
        kioskBoothRights.setContainer02(
            kioskBoothRightInfo.getContainer02().updateContainerAttribute(kioskBoothRights.getContainer02()));
      } else {
        kioskBoothRights.setContainer02(null);
      }
      if (!(kioskBoothRightInfo.getContainer03() == null)) {
        kioskBoothRights.setContainer03(
            kioskBoothRightInfo.getContainer03().updateContainerAttribute(kioskBoothRights.getContainer03()));
      } else {
        kioskBoothRights.setContainer03(null);
      }
      if (!(kioskBoothRightInfo.getContainer04() == null)) {
        kioskBoothRights.setContainer04(
            kioskBoothRightInfo.getContainer04().updateContainerAttribute(kioskBoothRights.getContainer04()));
      } else {
        kioskBoothRights.setContainer04(null);
      }
      return kioskBoothRights;
    }
    return null;
  }



  public KioskBoothRights convertDtoToDomain(KioskBoothRightInfo kioskBoothRightInfo) {
    if (!(kioskBoothRightInfo == null)) {
      KioskBoothRightsPK pk = new KioskBoothRightsPK();
      pk.setBoothID(clientRepository.findOne(Long.parseLong(kioskBoothRightInfo.getBoothID())).orElse(null));
      pk.setKioskID(clientRepository.findOne(Long.parseLong(kioskBoothRightInfo.getKioskID())).orElse(null));
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

  public static <T> Stream<T> stream(Iterable<T> iterable) {
    return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterable.iterator(), Spliterator.ORDERED), false);
  }

}
