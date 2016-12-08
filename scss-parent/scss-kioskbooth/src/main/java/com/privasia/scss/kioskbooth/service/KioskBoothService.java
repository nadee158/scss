package com.privasia.scss.kioskbooth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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
import com.privasia.scss.common.util.CommonUtil;
import com.privasia.scss.core.exception.BusinessException;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.KioskBoothRights;
import com.privasia.scss.core.model.KioskBoothRightsPK;
import com.privasia.scss.core.predicate.KioskBoothRightsPredicates;
import com.privasia.scss.core.repository.ClientRepository;
import com.privasia.scss.core.repository.KioskBoothRightsRepository;
import com.privasia.scss.kioskbooth.dto.KioskBoothRightInfo;
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


  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, readOnly = false)
  public String activateBoothsByKioskId(KioskBoothRightInfo kioskBoothRightInfo) {
    String result = "ERROR";
    if (!(kioskBoothRightInfo == null || kioskBoothRightInfo.getKioskID() == null)) {
      Iterable<KioskBoothRights> allKiosks = getKioskBoothInfoById(kioskBoothRightInfo.getKioskID());
      if (!(allKiosks == null)) {
        Stream<KioskBoothRights> allKiosksStrem = stream(allKiosks);
        if (allKiosksStrem.count() > 0) {
          boolean isLockedOrActiveAvailable = allKiosksStrem.anyMatch(getKioskLockStatusCombinedPredicate());
          if (!(isLockedOrActiveAvailable)) {
            allKiosks.forEach(kiosk -> {
              KioskBoothRights updatedKioskBoothRights = updateKioskBoothRightsFromDTO(kiosk, kioskBoothRightInfo);
              updatedKioskBoothRights.setKioskLockStatus(KioskLockStatus.ACTIVE);
              kioskBoothRightsRepository.save(updatedKioskBoothRights);
            });
            result = "SUCCESS";
          } else {
            throw new ResultsNotFoundException("Locked or Active records available!");
          }
        } else {
          throw new ResultsNotFoundException("Kiosk Information could not be found!");
        }
      } else {
        throw new ResultsNotFoundException("Kiosk Information could not be found!");
      }
    } else {
      throw new BusinessException("Kiosk Information is null!");
    }
    return result;
  }

  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, readOnly = false)
  public String lockBoothForKiosk(KioskBoothRightInfo kioskBoothRightInfo) {
    String result = "ERROR";
    if (!(kioskBoothRightInfo == null)) {
      if (kioskBoothRightInfo.getKioskID() !=null || kioskBoothRightInfo.getBoothID() != null) {
        Iterable<KioskBoothRights> allKiosks = getKioskBoothInfoById(kioskBoothRightInfo.getKioskID());
        if (!(allKiosks == null)) {
          Stream<KioskBoothRights> allKiosksStrem = stream(allKiosks);
          if (allKiosksStrem.count() > 0) {
            allKiosks.forEach(kioskBoothright -> {
              long boothIDl = kioskBoothRightInfo.getBoothID();
              if (boothIDl == kioskBoothright.getKioskBoothRightsID().getBooth().getClientID()) {
                kioskBoothright = updateKioskBoothRightsFromDTO(kioskBoothright, kioskBoothRightInfo);
                kioskBoothright.setKioskLockStatus(KioskLockStatus.LOCK);
              } else {
                kioskBoothright.setKioskLockStatus(KioskLockStatus.RELEASED);
              }
              kioskBoothRightsRepository.save(kioskBoothright);
            });
            result = "SUCCESS";
          } else {
            throw new ResultsNotFoundException("Kiosk Information could not be found!");
          }
        } else {
          throw new ResultsNotFoundException("Kiosk Information could not be foundl!");
        }
      } else {
        throw new BusinessException("Kiosk Information and/or booth information is null!");
      }
    } else {
      throw new BusinessException("Kiosk Information is null!");
    }

    return result;
  }

  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, readOnly = false)
  public String completekioskbooth(KioskBoothRightInfo kioskBoothRightInfo) {
    String result = "ERROR";
    if (!(kioskBoothRightInfo == null)) {
      if (!(kioskBoothRightInfo.getKioskID() == null || kioskBoothRightInfo.getBoothID() == null)) {
        Optional<KioskBoothRights> completedKioskOpt =
            kioskBoothRightsRepository.findByKioskBoothRightsID_Kiosk_ClientIDAndKioskBoothRightsID_Booth_ClientID(
                kioskBoothRightInfo.getKioskID(), kioskBoothRightInfo.getBoothID());
        if ((completedKioskOpt.isPresent())) {
          KioskBoothRights completedKiosk = completedKioskOpt.get();
          completedKiosk = updateKioskBoothRightsFromDTO(completedKiosk, kioskBoothRightInfo); 
          completedKiosk.setKioskLockStatus(KioskLockStatus.COMPLETE);
          kioskBoothRightsRepository.save(completedKiosk);
          result = "SUCCESS";
        } else {
          throw new ResultsNotFoundException("Kiosk Information could not be found!");
        }
      } else {
        throw new BusinessException("Kiosk Information is null!");
      }
    } else {
      throw new BusinessException("Kiosk Information is null!");
    }

    return result;
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<ClientDTO> getKioskListByBooth(Long boothID) {
	 
   Optional<List<Client>> KioskListForBooth = clientRepository.getKioskListByBooth(boothID);  
    
   List<Client> clientInfoList = KioskListForBooth.orElseThrow(() -> new BusinessException("Invalid boothID : "+boothID));
   
   if(clientInfoList.isEmpty())
	   throw new ResultsNotFoundException("Kiosk has not assign for the given boothID "+ boothID);
   
   return clientInfoList.stream().map(Client -> modelMapper.map(Client, ClientDTO.class)).collect(Collectors.toList());
  }



  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
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

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public KioskBoothRightInfo getLockedKioskBoothInfo(Long kioskID) {
    if (kioskID != null) {
      Iterable<KioskBoothRights> kioskList = getKioskBoothInfoByIdAndStatus(kioskID, KioskLockStatus.LOCK.getValue());
      if (!(kioskList == null)) {
        Stream<KioskBoothRights> kioskListStrem = stream(kioskList);
        if (kioskListStrem.count() > 0) {
          Optional<KioskBoothRights> kioskBoothRightsOpt = kioskListStrem.findFirst();
          if (kioskBoothRightsOpt.isPresent()) {
            KioskBoothRights boothRights = kioskBoothRightsOpt.get();
            if (!(boothRights == null)) {
              return new KioskBoothRightInfo(boothRights);
            } else {
              throw new ResultsNotFoundException("Kiosk Booth information could not be found!");
            }
          } else {
            throw new ResultsNotFoundException("Kiosk Booth information could not be found!");
          }
        } else {
          throw new ResultsNotFoundException("Kiosk Booth information could not be found!");
        }
      } else {
        throw new ResultsNotFoundException("Kiosk Booth information could not be found!");
      }
    } else {
      throw new BusinessException("Kiosk ID was null!");
    }
  }


  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<KioskBoothRightsDTO> getBoothAccessRight(Long boothID, Long kioskID, Integer cardNumber) {
    if (boothID != null) {
      Iterable<KioskBoothRights> kioskList = applyBoothAccessRightPredicate(boothID, kioskID, cardNumber);
      if (kioskList != null) {
        Stream<KioskBoothRights> kioskListStrem = stream(kioskList);
        List<KioskBoothRightsDTO> resultMap = kioskListStrem.map(kisokBooth -> convertDomainToDto(kisokBooth)).collect(Collectors.toList());
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


  public Iterable<KioskBoothRights> getKioskBoothInfoByIdAndStatus(Long kioskID, String status) {
    Predicate kioskBoothInfoByKioskID = KioskBoothRightsPredicates.KioskBoothInfoByKioskID(kioskID);
    Predicate kioskBoothStatus = KioskBoothRightsPredicates.KioskBoothStatus(status);
    Predicate finalPredicate = ExpressionUtils.allOf(kioskBoothInfoByKioskID, kioskBoothStatus);
    return kioskBoothRightsRepository.findAll(finalPredicate);
  }

  public Iterable<KioskBoothRights> getKioskBoothInfoById(Long kioskID) {
    Predicate kioskBoothInfoByKioskID = KioskBoothRightsPredicates.KioskBoothInfoByKioskID(kioskID);
    return kioskBoothRightsRepository.findAll(kioskBoothInfoByKioskID);
  }

  public Iterable<KioskBoothRights> applyBoothAccessRightPredicate(Long boothID, Long kioskID, Integer cardNumber) {
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

  public java.util.function.Predicate<KioskBoothRights> getKioskLockStatusCombinedPredicate() {
    java.util.function.Predicate<KioskBoothRights> p1 = kb -> kb.getKioskLockStatus().equals(KioskLockStatus.ACTIVE)
        || kb.getKioskLockStatus().equals(KioskLockStatus.LOCK);
    return p1;
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
      pk.setBooth(clientRepository.findOne(kioskBoothRightInfo.getBoothID()).orElse(null));
      pk.setKiosk(clientRepository.findOne(kioskBoothRightInfo.getKioskID()).orElse(null));
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
  
  private KioskBoothRightsDTO convertDomainToDto(KioskBoothRights kioskBoothRights) {
	  KioskBoothRightsDTO kioskBoothRightsDTO = modelMapper.map(kioskBoothRights, KioskBoothRightsDTO.class);
	  System.out.println("kioskBoothRightsDTO.getKioskLockStatus() "+ kioskBoothRightsDTO.getKioskLockStatus());
	  return kioskBoothRightsDTO;
  }



}
