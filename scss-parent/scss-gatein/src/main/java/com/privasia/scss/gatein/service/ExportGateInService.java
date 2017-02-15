package com.privasia.scss.gatein.service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.GateInReponse;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.dto.ShipSCNDTO;
import com.privasia.scss.common.enums.ShipStatus;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Exports;
import com.privasia.scss.core.model.ExportsQ;
import com.privasia.scss.core.model.ShipCode;
import com.privasia.scss.core.model.ShipSCN;
import com.privasia.scss.core.repository.ExportsQRepository;
import com.privasia.scss.core.repository.ExportsRepository;
import com.privasia.scss.core.repository.ShipCodeRepository;
import com.privasia.scss.core.repository.ShipSCNRepository;

@Service("exportGateInService")
public class ExportGateInService {

  private ModelMapper modelMapper;

  private ShipCodeRepository shipCodeRepository;

  private ShipSCNRepository shipSCNRepository;

  private ExportsRepository exportsRepository;

  private ExportsQRepository exportsQRepository;


  @Autowired
  public void setModelMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  @Autowired
  public void setShipCodeRepository(ShipCodeRepository shipCodeRepository) {
    this.shipCodeRepository = shipCodeRepository;
  }

  @Autowired
  public void setShipSCNRepository(ShipSCNRepository shipSCNRepository) {
    this.shipSCNRepository = shipSCNRepository;
  }

  @Autowired
  public void setExportsRepository(ExportsRepository exportsRepository) {
    this.exportsRepository = exportsRepository;
  }

  @Autowired
  public void setExportsQRepository(ExportsQRepository exportsQRepository) {
    this.exportsQRepository = exportsQRepository;
  }


  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public GateInReponse populateGateInExports(GateInReponse gateInReponse) {
	  
	setStoragePeriod(gateInReponse.getExportContainers());
	setSCN(gateInReponse.getExportContainers());
    return gateInReponse;

  }


  public List<ExportContainer> fetchContainerInfo(List<String> exportContainerNumbers) {

    return null;

  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public void setStoragePeriod(List<ExportContainer> exportContainers) {
    if (!(exportContainers == null || exportContainers.isEmpty())) {
      List<String> shippingCodes =
          exportContainers.stream().map(ExportContainer::getShipCode).collect(Collectors.toList());

      Optional<List<ShipCode>> optionalShipCodelist = shipCodeRepository.findByShipStatusAndShippingCodeIn(ShipStatus.ACTIVE, shippingCodes);
      
      List<ShipCode> shipCodelist  =  optionalShipCodelist.orElseThrow(
    	        () -> new ResultsNotFoundException("Ship Code could be found for the given Ship Codes ! " + String.join(",", shippingCodes)));
      
      exportContainers.forEach(exportContainer -> {
    	  shipCodelist.forEach(shipCode -> {
    		  if(StringUtils.equals(shipCode.getShippingCode(), exportContainer.getShipCode())){
    			  exportContainer.setStoragePeriod(shipCode.getStoragePeriod()); 
    		  }
    	  });
      });
    }
    
  }

  
  
  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public void setSCN(List<ExportContainer> exportContainers) {
	  
	  String exportContainer01 = null;
	  String scn01 = null;
	  String exportContainer02 = null;
	  String scn02 = null;
	  
	  if (!(exportContainers == null || exportContainers.isEmpty())) {
		  
		 for(ExportContainer container : exportContainers){
			 if(StringUtils.isBlank(exportContainer01)){
				  exportContainer01 = container.getContainer().getContainerNumber();
				  scn01 = container.getScn().getScnNo();
			  }else{
				  exportContainer02 = container.getContainer().getContainerNumber();
				  scn02 = container.getScn().getScnNo();
			  }
		 }
		
		  Optional<List<ShipSCN>> optionalshipSCN = shipSCNRepository.fetchContainerSCN(scn01, exportContainer01, scn02, exportContainer02);
		  
		  if(optionalshipSCN.isPresent()){
			  List<ShipSCN> shipSCNlist  =  optionalshipSCN.get();
			  exportContainers.forEach(exportContainer -> {
				  shipSCNlist.forEach(shipSCN -> {
		    		  if(StringUtils.equals(exportContainer.getContainer().getContainerNumber(), shipSCN.getContainerNo())){
		    			  ShipSCNDTO shipSCNDTO = new ShipSCNDTO();
		    			  modelMapper.map(shipSCN, shipSCNDTO);
		    			  exportContainer.setScn(shipSCNDTO);
		    		      exportContainer.setBypassEEntry(shipSCN.getScnByPass());
		    		      exportContainer.setRegisteredInEarlyEntry(true);
		    		  }
		    	  });
		      });
		  }else{
			  throw new ResultsNotFoundException("Ship SCN could be found for the given "
  	        		+ "	SCN / ContainerNo Info! " + scn01 +" / "+exportContainer01 +" OR "+scn02 +" / "+exportContainer02);
		  }
		  
	  }  
	
  }

  public List<ExportContainer> saveGateInInfo(GateInWriteRequest gateInWriteRequest) {
    // construct a new export entity for each exportcontainer and save
    if (!(gateInWriteRequest.getExportContainers() == null || gateInWriteRequest.getExportContainers().isEmpty())) {
      gateInWriteRequest.getExportContainers().forEach(exportContainer -> {
        Exports exports = new Exports();
        System.out.println("exportContainer " + exportContainer);
        modelMapper.map(exportContainer, exports);
        System.out.println("exports " + exports);
        exports = exportsRepository.save(exports);
        ExportsQ exportsQ = new ExportsQ();
        modelMapper.map(exports, exportsQ);
        System.out.println("exportsQ " + exportsQ);
        exportsQRepository.save(exportsQ);
      });
      return gateInWriteRequest.getExportContainers();
    }

    return null;
  }



}
