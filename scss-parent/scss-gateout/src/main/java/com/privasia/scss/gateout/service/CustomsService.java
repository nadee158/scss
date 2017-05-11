package com.privasia.scss.gateout.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.CustomsDTO;
import com.privasia.scss.common.enums.ContainerFullEmptyType;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.common.enums.TransactionType;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.common.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.CustomContainer;
import com.privasia.scss.core.model.Customs;
import com.privasia.scss.core.model.CustomsReport;
import com.privasia.scss.core.model.Exports;
import com.privasia.scss.core.predicate.ExportsPredicates;
import com.privasia.scss.core.repository.ClientRepository;
import com.privasia.scss.core.repository.CustomsReportRepository;
import com.privasia.scss.core.repository.CustomsRepository;
import com.privasia.scss.core.repository.ExportsRepository;
import com.privasia.scss.core.repository.GatePassRepository;
import com.privasia.scss.core.repository.ODDRepository;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;

@Service("customsService")
public class CustomsService {

  private ExportsRepository exportsRepository;

  private GatePassRepository gatePassRepository;

  private ODDRepository oddRepository;

  private CustomsReportRepository customsReportRepository;

  private CustomsRepository customsRepository;

  private ClientRepository clientRepository;

  private ModelMapper modelMapper;

  @Autowired
  public void setModelMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  @Autowired
  public void setClientRepository(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Autowired
  public void setCustomsReportRepository(CustomsReportRepository customsReportRepository) {
    this.customsReportRepository = customsReportRepository;
  }

  @Autowired
  public void setCustomsRepository(CustomsRepository customsRepository) {
    this.customsRepository = customsRepository;
  }

  @Autowired
  public void setExportsRepository(ExportsRepository exportsRepository) {
    this.exportsRepository = exportsRepository;
  }

  @Autowired
  public void setGatePassRepository(GatePassRepository gatePassRepository) {
    this.gatePassRepository = gatePassRepository;
  }

  @Autowired
  public void setOddRepository(ODDRepository oddRepository) {
    this.oddRepository = oddRepository;
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
  public String updateCustoms(CustomsDTO customsDTO) {
    String returnValue = null;

    if (customsDTO.getExportIDSeq01().isPresent() || customsDTO.getExportIDSeq02().isPresent()) {
      returnValue = updateCustomsExport(customsDTO);
    }
    if (customsDTO.getGatePassIDSeq01().isPresent() || customsDTO.getGatePassIDSeq02().isPresent()) {
      returnValue = updateCustomsImport(customsDTO);
    }
    if (customsDTO.getOddIdSeq01().isPresent() || customsDTO.getOddIdSeq02().isPresent()) {
      returnValue = updateCustomsODD(customsDTO);
    }
    return null;
  }

  public String updateCustomsODD(CustomsDTO customsDTO) {
    return null;
  }

  public String updateCustomsImport(CustomsDTO customsDTO) {
    // TODO Auto-generated method stub
    return null;
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
  public String updateCustomsExport(CustomsDTO customsDTO) {
    List<Long> exportsIds = new ArrayList<Long>();
    if (customsDTO.getExportIDSeq01().isPresent()) {
      exportsIds.add(customsDTO.getExportIDSeq01().get());
    }
    if (customsDTO.getExportIDSeq02().isPresent()) {
      exportsIds.add(customsDTO.getExportIDSeq02().get());
    }
    Predicate byContainerFullOrEmpty = ExportsPredicates.byContainerFullOrEmpty(ContainerFullEmptyType.FULL);
    Predicate byGateInStatus = ExportsPredicates.byEirStatus(TransactionStatus.APPROVED);
    Predicate byExportsIDList = ExportsPredicates.byExportsIDList(exportsIds);

    Predicate condition = ExpressionUtils.allOf(byContainerFullOrEmpty, byGateInStatus, byExportsIDList);
    Iterable<Exports> exportsList = exportsRepository.findAll(condition);
    if ((exportsList != null && exportsList.iterator().hasNext())) {
      final Customs customs = constructCustoms(customsDTO, TransactionType.EXPORT);
      deleteCustomsByGateOutClientId(customsDTO.getGateOutClientId());
      exportsList.forEach(exports -> {
        if (customs.getContainer01() == null) {
          customs.setContainer01(constructCustomContainer(exports));
        } else {
          customs.setContainer02(constructCustomContainer(exports));
        }
      });
      customsRepository.save(customs);
      if (!(customs.getCustomsID() == null || customs.getCustomsID() <= 0)) {
        CustomsReport customsReport = new CustomsReport();
        modelMapper.map(customs, customsReport);
        customsReportRepository.save(customsReport);
      }
    }
    return null;
  }

  public CustomContainer constructCustomContainer(Exports exports) {
    CustomContainer customContainer = new CustomContainer();
    customContainer.setContainerFullOrEmpty(exports.getContainer().getContainerFullOrEmpty());
    customContainer.setContainerNumber(exports.getContainer().getContainerNumber());
    customContainer.setCustomEirStatus(exports.getBaseCommonGateInOutAttribute().getEirStatus());
    customContainer.setCustomRejection(exports.getCommonGateInOut().getRejectReason());
    customContainer.setExportID(exports);
    // sql += ", csm_containerno2" //
    // + ", csm_fullempty_flagC2"
    // + ", exp_exportno_seq2"
    // + ", csm_rejectreason2"
    // + ", csm_eirstatus2";
    return customContainer;
  }

  public Customs constructCustoms(CustomsDTO customsDTO, TransactionType transactionType) {
    Customs customs = new Customs();
    customs.setCsmFlag(transactionType);
    Optional<Client> clientOpt = clientRepository.findOne(customsDTO.getGateOutClientId());
    Client client = clientOpt
        .orElseThrow(() -> new ResultsNotFoundException("Invalid lane ID ! " + customsDTO.getGateOutClientId()));
    customs.setCsmGateOutClient(client);
    customs.setTimeGateOut(LocalDateTime.now());
    return customs;
  }


  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
  public Optional<List<Customs>> deleteCustomsByGateOutClientId(Long gateOutClientId) {
    if (gateOutClientId == null || gateOutClientId <= 0) {
      throw new BusinessException("Gate out client id is not available!");
    }
    Optional<List<Customs>> deletedCustoms = customsRepository.deleteByCsmGateOutClient_ClientID(gateOutClientId);
    return deletedCustoms;
  }
}
