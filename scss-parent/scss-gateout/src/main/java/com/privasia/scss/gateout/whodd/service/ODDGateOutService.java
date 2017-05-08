package com.privasia.scss.gateout.whodd.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.ContainerValidationInfo;
import com.privasia.scss.common.dto.GateOutReponse;
import com.privasia.scss.common.dto.GateOutRequest;
import com.privasia.scss.common.dto.GateOutWriteRequest;
import com.privasia.scss.common.dto.WHoddDTO;
import com.privasia.scss.common.enums.ContainerFullEmptyType;
import com.privasia.scss.common.enums.GateInOutStatus;
import com.privasia.scss.common.enums.ImpExpFlagStatus;
import com.privasia.scss.common.enums.SCSSHDBSStatus;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.common.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.HDBSBkgDetail;
import com.privasia.scss.core.model.ODDContainerDetails;
import com.privasia.scss.core.model.ODDLocation;
import com.privasia.scss.core.model.SystemUser;
import com.privasia.scss.core.model.WHODD;
import com.privasia.scss.core.repository.HDBSBookingDetailRepository;
import com.privasia.scss.core.repository.ODDLocationRepository;
import com.privasia.scss.core.repository.ODDRepository;
import com.privasia.scss.cosmos.repository.CosmosODDRepository;
import com.privasia.scss.gateout.dto.FileDTO;

@Service("oddGateOutService")
public class ODDGateOutService {

	private CosmosODDRepository cosmosODDRepository;

	private ODDRepository oddRepository;

	private ODDLocationRepository oddLocationRepository;

	private ModelMapper modelMapper;

	private HDBSBookingDetailRepository hdbsBookingDetailRepository;

	@Autowired
	public void setCosmosODDRepository(CosmosODDRepository cosmosODDRepository) {
		this.cosmosODDRepository = cosmosODDRepository;
	}

	@Autowired
	public void setOddRepository(ODDRepository oddRepository) {
		this.oddRepository = oddRepository;
	}

	@Autowired
	public void setOddLocationRepository(ODDLocationRepository oddLocationRepository) {
		this.oddLocationRepository = oddLocationRepository;
	}

	@Autowired
	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Autowired
	public void setHdbsBookingDetailRepository(HDBSBookingDetailRepository hdbsBookingDetailRepository) {
		this.hdbsBookingDetailRepository = hdbsBookingDetailRepository;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, value = "as400TransactionManager")
	public ContainerValidationInfo validateODDContainers(ContainerValidationInfo containerValidationInfo) {

		containerValidationInfo.setContainerNo1Status(
				cosmosODDRepository.validateODDContainer(containerValidationInfo.getContainerNo1()));

		if (StringUtils.isNotEmpty(containerValidationInfo.getContainerNo2())) {
			containerValidationInfo.setContainerNo2Status(
					cosmosODDRepository.validateODDContainer(containerValidationInfo.getContainerNo2()));
		}

		return containerValidationInfo;
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public GateOutReponse populateGateOut(GateOutRequest gateOutRequest, GateOutReponse gateOutReponse) {

		Optional<List<WHODD>> optionalODDList = oddRepository.findByCardIDAndEirStatus(gateOutRequest.getCardID(),
				TransactionStatus.INPROGRESS);

		if (optionalODDList.isPresent()) {
			List<WHoddDTO> whODDContainers = new ArrayList<WHoddDTO>();
			List<WHODD> transactionList = optionalODDList.get();
			
			transactionList.forEach(whODD -> {
				WHoddDTO oddDTO = modelMapper.map(whODD, WHoddDTO.class);
				if (oddDTO.getContainer01() != null) {
					Optional<ODDLocation> optionalLocation = oddLocationRepository
							.findOne(oddDTO.getContainer01().getLocation());
					if (optionalLocation.isPresent()) {
						oddDTO.getContainer01().setLocation(optionalLocation.get().getOddDesc());
					}
					
				}

				if (oddDTO.getContainer02() != null) {
					Optional<ODDLocation> optionalLocation = oddLocationRepository
							.findOne(oddDTO.getContainer02().getLocation());
					if (optionalLocation.isPresent()) {
						oddDTO.getContainer02().setLocation(optionalLocation.get().getOddDesc());
					}
				}
				whODDContainers.add(oddDTO);
			});
			
			Optional<WHODD> optElement = optionalODDList.get().stream().findAny();
			gateOutReponse.setWhODDContainers(whODDContainers);
			gateOutReponse.setGateOUTDateTime(gateOutRequest.getGateOUTDateTime());
			if(optElement.isPresent()){
				gateOutReponse.setGateInDateTime(optElement.get().getTimeGateIn());
				gateOutReponse.setTruckPlateNo(optElement.get().getPmPlateNo());
				gateOutReponse.setTruckHeadNo(optElement.get().getPmHeadNo());
			}
			
			boolean imports = optionalODDList.get().stream().filter(whODD -> 
						StringUtils.equalsIgnoreCase(whODD.getImpExpFlag().getValue(), 
								ImpExpFlagStatus.IMPORT.getValue())).findAny().isPresent();
			
			boolean exports = optionalODDList.get().stream().filter(whODD -> 
			StringUtils.equalsIgnoreCase(whODD.getImpExpFlag().getValue(), 
					ImpExpFlagStatus.EXPORT.getValue())).findAny().isPresent();
			
			if(imports && exports){
				gateOutReponse.setTransactionType(ImpExpFlagStatus.IMPORT_EXPORT.getValue());
			}else if(imports){
				gateOutReponse.setTransactionType(ImpExpFlagStatus.IMPORT.getValue());
			}else if(exports){
				gateOutReponse.setTransactionType(ImpExpFlagStatus.EXPORT.getValue());
			}
			
		}
		
		return gateOutReponse;

	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
	public Long saveODDGateOutInFo(GateOutWriteRequest gateOutWriteRequest, Card card, Client gateOutClient,
			SystemUser gateOutClerk, ImpExpFlagStatus impExpFlag, Optional<Client> gateOutBooth,
			Optional<SystemUser> gateOutBoothClerk) {

		if (gateOutWriteRequest.getWhoddContainers() == null || gateOutWriteRequest.getWhoddContainers().isEmpty())
			throw new BusinessException("Invalid GateOutWriteRequest to save ODD ! ");

		gateOutWriteRequest.getWhoddContainers().forEach(whODDdto -> {

			Optional<WHODD> optODD = oddRepository.findOne(whODDdto.getOddIdSeq());

			WHODD whODD = optODD.orElseThrow(
					() -> new ResultsNotFoundException("ODD Transaction Not Found : " + whODDdto.getOddIdSeq()));

			// check for eirStatus
			if (whODD.getContainer01().getOddStatus().getValue() != TransactionStatus.INPROGRESS.getValue())
				throw new BusinessException(
						"Not a valid Gate In ODD Transaction to update : " + whODDdto.getOddIdSeq());

			if (whODD.getContainer02() != null
					&& whODD.getContainer02().getOddStatus().getValue() != TransactionStatus.INPROGRESS.getValue())
				throw new BusinessException(
						"Not a valid Gate In ODD Transaction to update : " + whODDdto.getOddIdSeq());

			whODD.setReviseHeadNo(whODDdto.getReviseHeadNo());
			whODD.setReviseHeadNoRemarks(whODDdto.getReviseHeadNoRemarks());
			if (StringUtils.isNotEmpty(whODDdto.getReviseHeadNo())) {
				whODD.setOldHeadNo(whODD.getPmHeadNo());
				whODD.setPmHeadNo(whODDdto.getReviseHeadNo());
			}

			if (StringUtils.equalsIgnoreCase(ImpExpFlagStatus.IMPORT.getValue(), whODDdto.getImpExpFlag())) {
				whODD.getContainer01().setContainerNo(whODDdto.getContainer01().getContainerNo());
			}

			if (StringUtils.equalsIgnoreCase(TransactionStatus.APPROVED.getValue(),
					whODD.getGateInStatus().getValue())) {

				if (StringUtils.equalsIgnoreCase(whODDdto.getContainer01().getOddStatus(),
						TransactionStatus.REJECT.getValue())) {

					if (StringUtils.isEmpty(whODDdto.getContainer01().getRemarks()))
						throw new BusinessException("Rejection Remarks is need for container  : "
								+ whODDdto.getContainer01().getContainerNo());
					if (StringUtils.isEmpty(whODDdto.getContainer01().getRejectionReason()))
						throw new BusinessException("Rejection Reason is need for container  : "
								+ whODDdto.getContainer01().getContainerNo());

					whODD.getContainer01().setRemarks(whODDdto.getContainer01().getRemarks());
					whODD.getContainer01().setRejectionReason(whODDdto.getContainer01().getRejectionReason());
				}
			}

			whODD.getContainer01()
					.setFullOrEmpty(ContainerFullEmptyType.fromValue(whODDdto.getContainer01().getFullOrEmpty()));
			whODD.getContainer01().setOddStatus(TransactionStatus.fromCode(whODDdto.getContainer01().getOddStatus()));

			if (whODD.getContainer01().getHdbsBkgDetailNo().isPresent()) {

				Optional<HDBSBkgDetail> optHDBSBookingDetail = hdbsBookingDetailRepository
						.findOne(whODD.getContainer01().getHdbsBkgDetailNo().get().getHdbsBKGDetailID());
				HDBSBkgDetail hdbsBookingDetail = optHDBSBookingDetail
						.orElseThrow(() -> new ResultsNotFoundException("Invalid HDBS Booking Detail ID :"
								+ whODD.getContainer01().getHdbsBkgDetailNo().get().getHdbsBKGDetailID()));

				hdbsBookingDetail.setScssStatusCode(SCSSHDBSStatus.fromValue(whODDdto.getContainer02().getOddStatus()));
				hdbsBookingDetail.setOddTimeGateOutOk(whODD.getTimeGateInOk());
				hdbsBookingDetail.setContainerNo(whODD.getContainer01().getContainerNo());
			}

			if (whODDdto.getContainer02() != null) {

				if (StringUtils.equalsIgnoreCase(ImpExpFlagStatus.IMPORT.getValue(), whODDdto.getImpExpFlag())) {
					ODDContainerDetails oddContainerDetails = new ODDContainerDetails();
					oddContainerDetails.setContainerNo(whODDdto.getContainer02().getContainerNo());
					whODD.setContainer02(oddContainerDetails);
				}
				whODD.getContainer02()
						.setFullOrEmpty(ContainerFullEmptyType.fromValue(whODDdto.getContainer02().getFullOrEmpty()));

				if (StringUtils.equalsIgnoreCase(whODDdto.getContainer02().getOddStatus(),
						TransactionStatus.REJECT.getValue())) {

					if (StringUtils.isEmpty(whODDdto.getContainer02().getRemarks()))
						throw new BusinessException("Rejection Remarks is need for container  : "
								+ whODDdto.getContainer02().getContainerNo());
					if (StringUtils.isEmpty(whODDdto.getContainer02().getRejectionReason()))
						throw new BusinessException("Rejection Reason is need for container  : "
								+ whODDdto.getContainer02().getContainerNo());

					whODD.getContainer02().setRemarks(whODDdto.getContainer02().getRemarks());
					whODD.getContainer02().setRejectionReason(whODDdto.getContainer02().getRejectionReason());
				}

				whODD.getContainer02()
						.setOddStatus(TransactionStatus.fromCode(whODDdto.getContainer02().getOddStatus()));

				if (whODD.getContainer02().getHdbsBkgDetailNo().isPresent()) {

					Optional<HDBSBkgDetail> optHDBSBookingDetail = hdbsBookingDetailRepository
							.findOne(whODD.getContainer02().getHdbsBkgDetailNo().get().getHdbsBKGDetailID());
					HDBSBkgDetail hdbsBookingDetail = optHDBSBookingDetail
							.orElseThrow(() -> new ResultsNotFoundException("Invalid HDBS Booking Detail ID :"
									+ whODD.getContainer02().getHdbsBkgDetailNo().get().getHdbsBKGDetailID()));

					hdbsBookingDetail
							.setScssStatusCode(SCSSHDBSStatus.fromValue(whODDdto.getContainer02().getOddStatus()));
					hdbsBookingDetail.setOddTimeGateOutOk(whODD.getTimeGateInOk());
					hdbsBookingDetail.setContainerNo(whODD.getContainer02().getContainerNo());
				}
			}

			whODD.setInOutFlag(GateInOutStatus.OUT);
			whODD.setTimeGateOut(gateOutWriteRequest.getGateOUTDateTime());
			whODD.setTimeGateOutOk(LocalDateTime.now());
			whODD.setGateOutClerk(gateOutClerk);
			whODD.setGateOutClient(gateOutClient);

			if (gateOutBooth.isPresent()) {
				whODD.setGateOutBoothNo(String.valueOf(gateOutBooth.get().getClientID()));
				whODD.setGateOutBoothClerk(gateOutBoothClerk.get());
				whODD.setTimeGateOutBooth(whODD.getTimeGateOut());
			}

			oddRepository.save(whODD);

		});

		return null;
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void updateODDReference(FileDTO fileDTO) {

		Optional<List<WHODD>> oddOptList = oddRepository
				.findByOddIdSeqIn(Arrays.asList(fileDTO.getOddImpSeq1().orElse(0l), fileDTO.getOddImpSeq2().orElse(0l),
						fileDTO.getOddExpSeq1().orElse(null), fileDTO.getOddExpSeq2().orElse(null)));

		if (oddOptList.isPresent() && !oddOptList.get().isEmpty()) {
			oddOptList.get().forEach(whODD -> {
				assignUpdatedValuedWHODDobj(whODD, fileDTO);
				oddRepository.save(whODD);
			});
		} else {
			throw new BusinessException("Invalid WhODD ID to update file reference : "
					+ fileDTO.getOddImpSeq1().orElse(null) + " / " + fileDTO.getOddImpSeq2().orElse(null) + " / "
					+ fileDTO.getOddExpSeq1().orElse(null) + " / " + fileDTO.getOddExpSeq2().orElse(null));
		}
	}

	private WHODD assignUpdatedValuedWHODDobj(WHODD whoDDobj, FileDTO fileDTO) {
		switch (fileDTO.getCollectionType()) {
		case PDF_FILE_COLLECTION:
			whoDDobj.setTrxSlipNo(fileDTO.getFileName().get());
			break;
		case ZIP_FILE_COLLECTION:
			whoDDobj.setZipFileNo(fileDTO.getFileName().get());
			break;
		default:
			break;
		}
		return whoDDobj;
	}

}
