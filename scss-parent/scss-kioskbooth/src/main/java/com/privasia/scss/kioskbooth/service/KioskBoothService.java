package com.privasia.scss.kioskbooth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.ClientDTO;
import com.privasia.scss.common.dto.ClientInfo;
import com.privasia.scss.common.dto.GateOutMessage;
import com.privasia.scss.common.dto.KioskBoothRightsDTO;
import com.privasia.scss.common.enums.KioskLockStatus;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.common.exception.ResultsNotFoundException;
import com.privasia.scss.common.util.CommonUtil;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.KioskBoothRights;
import com.privasia.scss.core.predicate.KioskBoothRightsPredicates;
import com.privasia.scss.core.repository.ClientRepository;
import com.privasia.scss.core.repository.KioskBoothRightsRepository;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;

@Service("kioskBoothService")
public class KioskBoothService {

  @Autowired
  private KioskBoothRightsRepository kioskBoothRightsRepository;

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE,
      readOnly = false)
  public String activateBoothsForTransaction(KioskBoothRightsDTO kioskBoothRightDTO) {

    List<KioskBoothRights> lockedKioskList = fetchLockReleasedKioskBoothByKiosk(kioskBoothRightDTO.getKioskClientID());
    if (!lockedKioskList.isEmpty())
      throw new BusinessException(
          "Transaction In Progress for the given Kiosk !" + kioskBoothRightDTO.getKioskClientID());

    List<KioskBoothRights> kioskListToActive = fetchKiosksToActivateTrx(kioskBoothRightDTO.getKioskClientID());

    if (kioskListToActive.isEmpty())
      throw new ResultsNotFoundException("No Kiosk Info Found to Update!");
    kioskListToActive.stream().forEach(KioskBoothRights -> {
      kioskBoothRightDTO.setKioskLockStatus(KioskLockStatus.ACTIVE.getValue());
      kioskBoothRightDTO.setBoothClientID(KioskBoothRights.getKioskBoothRightsID().getBooth().getClientID());
      modelMapper.map(kioskBoothRightDTO, KioskBoothRights);
      kioskBoothRightsRepository.save(KioskBoothRights);
    });

    return "SUCCESS";

  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE,
      readOnly = false)
  public String lockBoothTransaction(KioskBoothRightsDTO kioskBoothRightDTO) {

    if (kioskBoothRightDTO.getKioskClientID() == null || kioskBoothRightDTO.getBoothClientID() == null)
      throw new BusinessException("Kiosk Information and/or booth information is null!");

    List<KioskBoothRights> activeKioskBoothList = fetchActiveKioskBoothByKiosk(kioskBoothRightDTO.getKioskClientID());

    if (activeKioskBoothList.isEmpty())
      throw new ResultsNotFoundException("Invalid Kiosk ID !" + kioskBoothRightDTO.getKioskClientID());

    activeKioskBoothList.stream().forEach(KioskBoothRights -> {

      int retval = KioskBoothRights.getKioskBoothRightsID().getBooth().getClientID()
          .compareTo(kioskBoothRightDTO.getBoothClientID());

      if (retval != 0) {
        KioskBoothRights.setKioskLockStatus(KioskLockStatus.RELEASED);
        kioskBoothRightsRepository.save(KioskBoothRights);
      } else {
        kioskBoothRightDTO.setKioskLockStatus(KioskLockStatus.LOCK.getValue());
        modelMapper.map(kioskBoothRightDTO, KioskBoothRights);
        kioskBoothRightsRepository.save(KioskBoothRights);
      }

    });

    return "SUCCESS";

  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE,
      readOnly = false)
  public String completeBoothTransaction(KioskBoothRightsDTO kioskBoothRightDTO) {

    if (kioskBoothRightDTO.getKioskClientID() == null || kioskBoothRightDTO.getBoothClientID() == null)
      throw new BusinessException("Kiosk Information and/or booth information is null!");

    List<KioskBoothRights> kioskBoothList = fetchLockReleasedKioskBoothByKiosk(kioskBoothRightDTO.getKioskClientID());

    if (kioskBoothList.isEmpty())
      throw new ResultsNotFoundException("Invalid Kiosk ID !" + kioskBoothRightDTO.getKioskClientID());

    kioskBoothList.stream().forEach(KioskBoothRights -> {

      int retval = KioskBoothRights.getKioskBoothRightsID().getBooth().getClientID()
          .compareTo(kioskBoothRightDTO.getBoothClientID());

      if ((retval != 0) && KioskBoothRights.getKioskLockStatus().equals(KioskLockStatus.RELEASED)) {
        KioskBoothRights.setKioskLockStatus(KioskLockStatus.ACTIVE);
        kioskBoothRightsRepository.save(KioskBoothRights);
      } else {
        kioskBoothRightDTO.setKioskLockStatus(KioskLockStatus.COMPLETE.getValue());
        modelMapper.map(kioskBoothRightDTO, KioskBoothRights);
        kioskBoothRightsRepository.save(KioskBoothRights);
      }

    });

    return "SUCCESS";
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE,
      readOnly = true)
  public List<KioskBoothRights> fetchKiosksToActivateTrx(Long kioskID) {

    List<KioskLockStatus> kioskStatusList = new ArrayList<>();
    kioskStatusList.add(KioskLockStatus.ACTIVE);
    kioskStatusList.add(KioskLockStatus.COMPLETE);

    Predicate byStatus = KioskBoothRightsPredicates.KioskBoothStatusIN(kioskStatusList);
    Predicate byNullStatus = KioskBoothRightsPredicates.KioskBoothStatusNull();
    Predicate byKioskID = KioskBoothRightsPredicates.KioskBoothInfoByKioskID(kioskID);
    Predicate statusOR = ExpressionUtils.or(byStatus, byNullStatus);
    Predicate condition = ExpressionUtils.allOf(byKioskID, statusOR);

    Iterable<KioskBoothRights> boothIterator = kioskBoothRightsRepository.findAll(condition);

    return IteratorUtils.toList(boothIterator.iterator());
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE,
      readOnly = true)
  public List<KioskBoothRights> fetchActiveKioskBoothByKiosk(Long kioskID) {

    List<KioskLockStatus> kioskStatusList = new ArrayList<>();
    kioskStatusList.add(KioskLockStatus.ACTIVE);

    Predicate byStatus = KioskBoothRightsPredicates.KioskBoothStatusIN(kioskStatusList);
    Predicate byKioskID = KioskBoothRightsPredicates.KioskBoothInfoByKioskID(kioskID);
    Predicate condition = ExpressionUtils.allOf(byKioskID, byStatus);
    Iterable<KioskBoothRights> boothIterator = kioskBoothRightsRepository.findAll(condition);

    return IteratorUtils.toList(boothIterator.iterator());
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE,
      readOnly = true)
  public List<KioskBoothRights> fetchLockReleasedKioskBoothByKiosk(Long kioskID) {

    List<KioskLockStatus> kioskStatusList = new ArrayList<>();
    kioskStatusList.add(KioskLockStatus.LOCK);
    kioskStatusList.add(KioskLockStatus.RELEASED);

    Predicate byStatus = KioskBoothRightsPredicates.KioskBoothStatusIN(kioskStatusList);
    Predicate byKioskID = KioskBoothRightsPredicates.KioskBoothInfoByKioskID(kioskID);
    Predicate condition = ExpressionUtils.allOf(byKioskID, byStatus);
    Iterable<KioskBoothRights> boothIterator = kioskBoothRightsRepository.findAll(condition);

    return IteratorUtils.toList(boothIterator.iterator());
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public List<ClientDTO> getKioskListByBooth(Long boothID) {

    Optional<List<Client>> KioskListForBooth = clientRepository.getKioskListByBooth(boothID);
    List<Client> clientInfoList =
        KioskListForBooth.orElseThrow(() -> new BusinessException("Invalid boothID : " + boothID));
    if (clientInfoList.isEmpty())
      throw new ResultsNotFoundException("Kiosk has not assign for the given boothID " + boothID);
    return clientInfoList.stream().map(Client -> modelMapper.map(Client, ClientDTO.class)).collect(Collectors.toList());
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public ClientInfo getKioskLoginInfo(String ipAddress) {
    if (StringUtils.isNotEmpty(ipAddress)) {
      Optional<Client> clientOpt = clientRepository.findByWebIPAddress(ipAddress);
      if (clientOpt.isPresent()) {
        Client client = clientOpt.get();
        ClientInfo clientInfo = constructClientInfoFromClient(client);
        GateOutMessage gateOutMessage = new GateOutMessage();
        if (clientInfo != null) {
          gateOutMessage.setCode(GateOutMessage.OK);
          gateOutMessage.setDescription(CommonUtil.formatMessageCode("SCSS_KIOSK_LOGIN_CODE01", null));

        } else {
          gateOutMessage.setCode(GateOutMessage.NOK);
          gateOutMessage.setDescription(CommonUtil.formatMessageCode("SCSS_KIOSK_LOGIN_ERR_CODE02", null));
        }
        clientInfo.setMessageStatus(gateOutMessage);
        return clientInfo;
      } else {
        throw new ResultsNotFoundException("Client information could not be found!");
      }
    } else {
      throw new BusinessException("Web IP address was null!");
    }
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public KioskBoothRightsDTO getLockedKioskBoothInfo(Long kioskID) {

    if (kioskID == null)
      throw new BusinessException("Kiosk Information not Provided !");
    Optional<KioskBoothRights> optionalLockKiosk = fetchLockedKioskBoothInfoById(kioskID);
    KioskBoothRights lockKiosk = optionalLockKiosk
        .orElseThrow(() -> new ResultsNotFoundException("Kiosk Booth Not in lock status for kioskID " + kioskID));
    return modelMapper.map(lockKiosk, KioskBoothRightsDTO.class);

  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public List<KioskBoothRightsDTO> getBoothAccessRight(Long boothID, Long kioskID, Long cardNumber) {
    if (boothID != null) {
      Iterable<KioskBoothRights> kioskList = applyBoothAccessRightPredicate(boothID, kioskID, cardNumber);
      if (kioskList != null) {
        Stream<KioskBoothRights> kioskListStrem = stream(kioskList);
        List<KioskBoothRightsDTO> resultMap =
            kioskListStrem.map(kisokBooth -> convertDomainToDTO(kisokBooth)).collect(Collectors.toList());
        if (resultMap.isEmpty())
          throw new ResultsNotFoundException("Access Rights for Booth could not be found!");
        return resultMap;
      } else {
        throw new ResultsNotFoundException("Access Rights for Booth could not be found!");
      }
    } else {
      throw new BusinessException("booth ID was null!");
    }
  }

  private ClientInfo constructClientInfoFromClient(Client client) {
    ClientInfo clientInfo = new ClientInfo();
    clientInfo.setClientIdSeq(client.getClientID());
    clientInfo.setWebIPAddress(client.getWebIPAddress());
    clientInfo.setClientDescription(client.getDescription());
    clientInfo.setClientStaus(client.getStatus().getValue());
    clientInfo.setClientType(client.getType().getValue());
    clientInfo.setUnitNo(client.getUnitNo());
    clientInfo.setCsmControl(Boolean.toString(client.isCsmControl()));
    clientInfo.setCosmosPortNumber(Integer.toString(client.getCosmosPortNo()));
    clientInfo.setSortSeq(client.getSortSEQ());
    clientInfo.setCameraServerIPAddress(client.getCameraServerIPAddress());
    clientInfo.setCameraServerPort(Integer.toString(client.getCameraServerPortNo()));
    clientInfo.setLaneNO(client.getLaneNo());
    clientInfo.setFtpIP(client.getFtpIPAddress());
    clientInfo.setFtpPort(client.getFtpPort());
    clientInfo.setFtpProtocal(client.getFtpProtocol());
    clientInfo.setFtpUserName(client.getFtpUsername());
    clientInfo.setFtpPassword(client.getFtpPassword());
    clientInfo.setFtpDirectory(client.getFtpDirectory());
    clientInfo.setWithCameraImage(Boolean.toString(client.isWithCameraImage()));
    return clientInfo;
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public Optional<KioskBoothRights> fetchLockedKioskBoothInfoById(Long kioskID) {
    Predicate kioskBoothInfoByKioskID = KioskBoothRightsPredicates.KioskBoothInfoByKioskID(kioskID);
    Predicate kioskBoothStatus = KioskBoothRightsPredicates.KioskBoothStatus(KioskLockStatus.LOCK.getValue());
    Predicate finalPredicate = ExpressionUtils.allOf(kioskBoothInfoByKioskID, kioskBoothStatus);
    KioskBoothRights lockedKiosk = kioskBoothRightsRepository.findOne(finalPredicate);
    return Optional.ofNullable(lockedKiosk);
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public Iterable<KioskBoothRights> getKioskBoothInfoByKiosk(Long kioskID) {
    Predicate kioskBoothInfoByKioskID = KioskBoothRightsPredicates.KioskBoothInfoByKioskID(kioskID);
    return kioskBoothRightsRepository.findAll(kioskBoothInfoByKioskID);
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public Iterable<KioskBoothRights> applyBoothAccessRightPredicate(Long boothID, Long kioskID, Long cardNumber) {
    Predicate kioskBoothInfoByBoothID = null;
    Predicate kioskBoothInfoByKioskID = null;
    Predicate kioskBoothInfoByCardNo = null;
    List<Predicate> predicated = new ArrayList<Predicate>();

    kioskBoothInfoByBoothID = KioskBoothRightsPredicates.KioskBoothInfoByBoothID(boothID);
    predicated.add(kioskBoothInfoByBoothID);

    if (kioskID != null) {
      kioskBoothInfoByKioskID = KioskBoothRightsPredicates.KioskBoothInfoByKioskID(kioskID);
      predicated.add(kioskBoothInfoByKioskID);
    }
    if (cardNumber != null) {
      kioskBoothInfoByCardNo = KioskBoothRightsPredicates.KioskBoothInfoByCardNumber(cardNumber);
      predicated.add(kioskBoothInfoByCardNo);
    }
    Predicate finalPredicate = ExpressionUtils.allOf(predicated);
    return kioskBoothRightsRepository.findAll(finalPredicate);
  }

  public static <T> Stream<T> stream(Iterable<T> iterable) {
    return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterable.iterator(), Spliterator.ORDERED), false);
  }

  private KioskBoothRightsDTO convertDomainToDTO(KioskBoothRights kioskBoothRights) {
    KioskBoothRightsDTO kioskBoothRightsDTO = modelMapper.map(kioskBoothRights, KioskBoothRightsDTO.class);
    return kioskBoothRightsDTO;
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public List<KioskBoothRightsDTO> searchKioskBooth(Long cardNumber, List<String> kioskLockStatusList) {

    final List<KioskBoothRightsDTO> dtoList = new ArrayList<KioskBoothRightsDTO>();

    List<Predicate> orPredicated = new ArrayList<Predicate>();

    Predicate finalPredicate = null;

    if (!(kioskLockStatusList == null || kioskLockStatusList.isEmpty())) {
      kioskLockStatusList.forEach(status -> {
        orPredicated.add(KioskBoothRightsPredicates.KioskBoothStatus(status));
      });
      finalPredicate = ExpressionUtils.anyOf(orPredicated);
    }

    if (cardNumber != null) {
      Predicate kioskBoothInfoByCardNo = KioskBoothRightsPredicates.KioskBoothInfoByCardNumber(cardNumber);
      if (!(finalPredicate == null)) {
        finalPredicate = ExpressionUtils.and(finalPredicate, kioskBoothInfoByCardNo);
      } else {
        finalPredicate = kioskBoothInfoByCardNo;
      }
    }

    Iterable<KioskBoothRights> list = null;
    if (!(finalPredicate == null)) {
      list = kioskBoothRightsRepository.findAll(finalPredicate);
    }


    if (list == null) {
      throw new BusinessException("No kisokBooth items found for given details! cardNumber:" + cardNumber
          + " kioskLockStatusList:" + kioskLockStatusList);
    }
    list.forEach(KioskBoothRights -> {
      KioskBoothRightsDTO kioskBoothRightDTO = new KioskBoothRightsDTO();
      modelMapper.map(KioskBoothRights, kioskBoothRightDTO);
      dtoList.add(kioskBoothRightDTO);
    });
    if (dtoList.isEmpty()) {
      throw new BusinessException("No kisokBooth items found for given details! cardNumber:" + cardNumber
          + " kioskLockStatusList:" + kioskLockStatusList);
    }
    return dtoList;
  }

}
