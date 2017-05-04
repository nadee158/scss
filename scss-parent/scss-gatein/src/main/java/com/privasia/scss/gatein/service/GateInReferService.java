package com.privasia.scss.gatein.service;

import java.util.ArrayList;
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
import com.privasia.scss.common.dto.GateInReponse;
import com.privasia.scss.core.model.ReferReject;
import com.privasia.scss.core.repository.ReferRejectRepository;

/**
 * @author nadee158 - This class need to be moved to scss-refer module on a
 *         microservice
 */
@Service("gateInReferService")
@Transactional
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
	public GateInReponse fetchReferDataForExport(Long referID) {
		Optional<ReferReject> optReferReject = referRejectRepository.findOne(referID);
		if (optReferReject.isPresent()) {
			ReferReject referReject = optReferReject.get();
			if (!(referReject.getReferRejectDetails() == null || referReject.getReferRejectDetails().isEmpty())) {
				GateInReponse gateInReponse = new GateInReponse();
				gateInReponse.setExpWeightBridge(referReject.getExpWeightBridge());
				gateInReponse.setGateINDateTime(referReject.getReferDateTime());
				gateInReponse.setPmVerified(referReject.getPmVerified());
				gateInReponse.setAxleVerified(referReject.getAxleVerified());
				if (referReject.getBaseCommonGateInOut() != null) {
					
					if(referReject.getBaseCommonGateInOut().getHpabBooking()!=null){
						gateInReponse
						.setHpabBookingId(referReject.getBaseCommonGateInOut().getHpabBooking().getBookingID());
					}
					
					gateInReponse.setTruckHeadNo(referReject.getBaseCommonGateInOut().getPmHeadNo());
					gateInReponse.setTruckPlateNo(referReject.getBaseCommonGateInOut().getPmPlateNo());
				}
				gateInReponse.setTrailerPlate(referReject.getTrailerPlateNo());
				gateInReponse.setTrailerWeight(referReject.getTrailerWeight());
				gateInReponse.setTruckWeight(referReject.getPmWeight());
				
				List<ExportContainer> exportContainers = new ArrayList<ExportContainer>();
				referReject.getReferRejectDetails().forEach(referRejectDetail -> {
					ExportContainer exportContainer = new ExportContainer();

					if (!(referRejectDetail.getSolas() == null)) {
						if (exportContainer.getSolas() == null) {
							exportContainer.setSolas(new CommonSolasDTO());
						}
						modelMapper.map(referRejectDetail.getSolas(), exportContainer.getSolas());
					}

					if (!(referRejectDetail.getSeal() == null)) {
						if (exportContainer.getSealAttribute() == null) {
							exportContainer.setSealAttribute(new CommonSealDTO());
						}
						modelMapper.map(referRejectDetail.getSeal(), exportContainer.getSealAttribute());
					}

					if (!(referReject.getBaseCommonGateInOut() == null)) {
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
					exportContainers.add(exportContainer);

				});
				
				

				gateInReponse.setExportContainers(exportContainers);
				return gateInReponse;
			}
		}
		return null;
	}

	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%% CANT FIND FIELDS IN ExportContainer to
	// set following fields
	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	// f.setCardIdSeq(rejectFrom.getCrdCardidSeq());
	// f.setExpWeightBridge(rejectFrom.getExpWeightBridge());
	// f.setCugIdSeq(rejectFrom.getCugIdSeq());
	// f.setTimeIn(rejectFrom.getReferDateTime());
	// f.setHpatSeqId(rejectFrom.getHpatSeqId());
	// f.setPmVerified(StringUtils.isBlank(rejectFrom.getPmVerified()) ? "F"
	// :
	// rejectFrom.getPmVerified().toUpperCase());
	// f.setTrailerVerified(StringUtils.isBlank(rejectFrom.getTrailerVerified())
	// ? "F" :
	// rejectFrom.getTrailerVerified().toUpperCase());

}
