package com.privasia.scss.gatein.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.BaseCommonGateInOutDTO;
import com.privasia.scss.common.dto.CommonContainerDTO;
import com.privasia.scss.common.dto.CommonSealDTO;
import com.privasia.scss.common.dto.CommonSolasDTO;
import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.GateInResponse;
import com.privasia.scss.core.model.ReferReject;
import com.privasia.scss.core.repository.ReferRejectRepository;

/**
 * @author nadee158 - This class need to be moved to scss-refer module on a
 *         microservice
 */
@Service("gateInReferService")
public class GateInReferService {

	private ReferRejectRepository referRejectRepository;

	private ModelMapper modelMapper;

	@Autowired
	public void setReferRejectRepository(ReferRejectRepository referRejectRepository) {
		this.referRejectRepository = referRejectRepository;
	}

	@Autowired
	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public GateInResponse fetchReferDataForExport(Long referID, GateInResponse gateInResponse ) {
		Optional<ReferReject> optReferReject = referRejectRepository.findOne(referID);
		if (optReferReject.isPresent()) {
			ReferReject referReject = optReferReject.get();
			if (!(referReject.getReferRejectDetails() == null || referReject.getReferRejectDetails().isEmpty())) {
//				GateInResponse gateInResponse = new GateInResponse();
				gateInResponse.setExpWeightBridge(referReject.getExpWeightBridge());
				gateInResponse.setGateINDateTime(referReject.getReferDateTime());
				gateInResponse.setPmVerified(referReject.getPmVerified());
				gateInResponse.setAxleVerified(referReject.getAxleVerified());
				if (referReject.getBaseCommonGateInOut() != null) {
					
					if(referReject.getBaseCommonGateInOut().getHpabBooking()!=null){
						gateInResponse
						.setHpabBookingId(referReject.getBaseCommonGateInOut().getHpabBooking().getBookingID());
					}
					
					gateInResponse.setTruckHeadNo(referReject.getBaseCommonGateInOut().getPmHeadNo());
					gateInResponse.setTruckPlateNo(referReject.getBaseCommonGateInOut().getPmPlateNo());
				}
				gateInResponse.setTrailerPlate(referReject.getTrailerPlateNo());
				gateInResponse.setTrailerWeight(referReject.getTrailerWeight());
				gateInResponse.setTruckWeight(referReject.getPmWeight());
				
				List<ExportContainer> exportContainers =  gateInResponse.getExportContainers(); 
				referReject.getReferRejectDetails().forEach(referRejectDetail -> {
					Optional<ExportContainer> exportContainerOpt = exportContainers.stream().filter(
							p -> p.getContainer().getContainerNumber().equals(referRejectDetail.getContainerNo()))
							.findFirst();
					ExportContainer exportContainer = exportContainerOpt.get();
					
					if (referRejectDetail.getSolas() != null) {
						if (exportContainer.getSolas() == null) {
							exportContainer.setSolas(new CommonSolasDTO());
						}
						modelMapper.map(referRejectDetail.getSolas(), exportContainer.getSolas());
						
					}

					if (referRejectDetail.getSeal() != null) {
						if (exportContainer.getSealAttribute() == null) {
							exportContainer.setSealAttribute(new CommonSealDTO());
						}
						modelMapper.map(referRejectDetail.getSeal(), exportContainer.getSealAttribute());
					}

					if (referReject.getBaseCommonGateInOut() != null) {
						if (exportContainer.getBaseCommonGateInOutAttribute() == null) {
							exportContainer.setBaseCommonGateInOutAttribute(new BaseCommonGateInOutDTO());
						}
						modelMapper.map(referReject.getBaseCommonGateInOut(),
								exportContainer.getBaseCommonGateInOutAttribute());

					}
					
					if(exportContainer.getContainer()==null)
						exportContainer.setContainer(new CommonContainerDTO());
					exportContainer.getContainer().setContainerNumber(referRejectDetail.getContainerNo());
					exportContainer.getContainer().setContainerISOCode(referRejectDetail.getContainerIsoCode());
					exportContainer.setShippingLine(referRejectDetail.getLineCode());
					exportContainer.setContainerPosition(referRejectDetail.getPosition().getValue());
					if(!exportContainers.contains(exportContainer))
					exportContainers.add(exportContainer);

				});
				
				

				gateInResponse.setExportContainers(exportContainers);
				return gateInResponse;
			}
		}
		return null;
	}

}
