/**
 * 
 */
package com.privasia.scss.core.modelmap.config;

import org.modelmapper.PropertyMap;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.dto.KioskBoothRightsDTO;
import com.privasia.scss.common.dto.LaneOpenDTO;
import com.privasia.scss.common.dto.ReferRejectDTO;
import com.privasia.scss.common.dto.ReferRejectDetailDTO;
import com.privasia.scss.common.dto.ReferRejectReasonDTO;
import com.privasia.scss.common.dto.WHoddDTO;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.core.model.Customs;
import com.privasia.scss.core.model.CustomsReport;
import com.privasia.scss.core.model.Exports;
import com.privasia.scss.core.model.ExportsQ;
import com.privasia.scss.core.model.GatePass;
import com.privasia.scss.core.model.KioskBoothRights;
import com.privasia.scss.core.model.LaneOpen;
import com.privasia.scss.core.model.ReferReject;
import com.privasia.scss.core.model.ReferRejectDetail;
import com.privasia.scss.core.model.ReferRejectReason;
import com.privasia.scss.core.model.WHODD;

/**
 * @author Janaka
 *
 */

public final class ModelMapPropertyMap {

	public static PropertyMap<KioskBoothRights, KioskBoothRightsDTO> kioskBoothRightsDomainToDto() {

		return new PropertyMap<KioskBoothRights, KioskBoothRightsDTO>() {
			protected void configure() {
				map().setBoothClientID(source.getKioskBoothRightsID().getBooth().getClientID());
				map().setKioskClientID(source.getKioskBoothRightsID().getKiosk().getClientID());
			}
		};

	}

	public static PropertyMap<KioskBoothRightsDTO, KioskBoothRights> kioskBoothRightsDtoToDomain() {

		return new PropertyMap<KioskBoothRightsDTO, KioskBoothRights>() {
			protected void configure() {
				map().getKioskBoothRightsID().getKiosk().setClientID(source.getKioskClientID());
				map().getKioskBoothRightsID().getBooth().setClientID(source.getBoothClientID());

			}
		};

	}

	public static PropertyMap<GatePass, GatePass> gatePassUpdate() {

		return new PropertyMap<GatePass, GatePass>() {
			protected void configure() {
				skip().setGatePassID(null);
				skip().setGateOrderNo(null);
				skip().setGatePassNo(null);
				skip().getContainer().setContainerNumber(null);
				skip().setCompany(null);
				skip().getCommonGateInOut().setEirNumber(null);
				skip().getBaseCommonGateInOutAttribute().setHpabBooking(null);
				skip().setGatePassStatus(null);
				skip().setGateInOut(null);

				// properties to skip
				// gatePassID;
				// gateOrderNo;
				// gatePassNo;
				// containerNumber
				// company;
				// eirNumber
				// hpatBooking
				// gatePassStatus
				// gateInOut;

			}
		};

	}

	public static PropertyMap<GatePass, ImportContainer> gatePassToImportContainer() {

		return new PropertyMap<GatePass, ImportContainer>() {
			protected void configure() {
				map().setCompany(source.getCompany().getCompanyID());
				// map().getBaseCommonGateInOutAttribute().getCard()
				// .setCardID(source.getBaseCommonGateInOutAttribute().getCard().getCardID());
				skip().setContainerLength(0);

			}
		};
	}

	public static PropertyMap<ImportContainer, GatePass> importContainerToGatePass() {

		return new PropertyMap<ImportContainer, GatePass>() {
			protected void configure() {
				skip().getBaseCommonGateInOutAttribute().setHpabBooking(null);
				skip().getBaseCommonGateInOutAttribute().setCard(null);
				skip().getBaseCommonGateInOutAttribute().setGateInClerk(null);
				skip().getBaseCommonGateInOutAttribute().setGateInClient(null);
				skip().getBaseCommonGateInOutAttribute().setGateOutBoothClerk(null);
				skip().getBaseCommonGateInOutAttribute().setGateOutClerk(null);
				skip().getBaseCommonGateInOutAttribute().setGateOutClient(null);
				//skip().setPrintEir(null);
			}
		};
	}

	public static PropertyMap<ExportContainer, Exports> exportContainerToExports() {
		return new PropertyMap<ExportContainer, Exports>() {
			protected void configure() {

				skip().setDamageCode_01(null);
				skip().setDamageCode_02(null);
				skip().setDamageCode_03(null);
				skip().setDamageCode_04(null);
				skip().setDamageCode_05(null);
				skip().setDamageCode_06(null);
				skip().setDamageCode_07(null);
				skip().setDamageCode_08(null);
				skip().setDamageCode_09(null);
				skip().getBaseCommonGateInOutAttribute().setHpabBooking(null);
				skip().getBaseCommonGateInOutAttribute().setCard(null);
				skip().getBaseCommonGateInOutAttribute().setGateInClerk(null);
				skip().getBaseCommonGateInOutAttribute().setGateInClient(null);
				skip().getBaseCommonGateInOutAttribute().setGateOutBoothClerk(null);
				skip().getBaseCommonGateInOutAttribute().setGateOutClerk(null);
				skip().getBaseCommonGateInOutAttribute().setGateOutClient(null);
				skip().setPrintEir(null);
				skip().setManualPlanIndicator(null);
				map().setDirectEntry(source.getSolas().isDirectEntry());
				
			}
		};
	}

	public static PropertyMap<Exports, ExportContainer> exportsToExportContainer() {
		return new PropertyMap<Exports, ExportContainer>() {
			protected void configure() {
				skip().setManualPlanIndicator(null);
			}
		};
	}

	public static PropertyMap<Exports, ExportsQ> exportsToExportsQ() {
		return new PropertyMap<Exports, ExportsQ>() {
			protected void configure() {
				map().setEirNumber(source.getCommonGateInOut().getEirNumber());
				map().setImpExpFlag(source.getCommonGateInOut().getImpExpFlag());
				map().setRejectReason(source.getCommonGateInOut().getRejectReason());
				map().setGateInStatus(source.getCommonGateInOut().getGateInStatus());
				map().setZipFileNo(source.getCommonGateInOut().getZipFileNo());
				map().setTrxSlipNo(source.getCommonGateInOut().getTrxSlipNo());

				map().setPmHeadNo(source.getBaseCommonGateInOutAttribute().getPmHeadNo());
				map().setPmPlateNo(source.getBaseCommonGateInOutAttribute().getPmPlateNo());
				map().setEirStatus(source.getBaseCommonGateInOutAttribute().getEirStatus());
				map().setTimeGateIn(source.getBaseCommonGateInOutAttribute().getTimeGateIn());
				map().setTimeGateInOk(source.getBaseCommonGateInOutAttribute().getTimeGateInOk());
				map().setTimeGateOut(source.getBaseCommonGateInOutAttribute().getTimeGateOut());
				map().setTimeGateOutOk(source.getBaseCommonGateInOutAttribute().getTimeGateOutOk());
				map().setCard(source.getBaseCommonGateInOutAttribute().getCard());
				map().setGateInClerk(source.getBaseCommonGateInOutAttribute().getGateInClerk());
				map().setGateOutClerk(source.getBaseCommonGateInOutAttribute().getGateOutClerk());
				map().setGateInClient(source.getBaseCommonGateInOutAttribute().getGateInClient());
				map().setGateOutClient(source.getBaseCommonGateInOutAttribute().getGateOutClient());
			}
		};
	}

	public static PropertyMap<ImportContainer, GatePass> importContainerToGatePassInGateOut() {

		return new PropertyMap<ImportContainer, GatePass>() {
			protected void configure() {
				map().getBaseCommonGateInOutAttribute().setEirStatus(
						TransactionStatus.valueOf(source.getBaseCommonGateInOutAttribute().getEirStatus()));
			}
		};
	}

	/*
	 * public static PropertyMap<ReferRejectDTO, ReferReject>
	 * referRejectDTOToReferReject() { return new PropertyMap<ReferRejectDTO,
	 * ReferReject>() { protected void configure() {
	 * skip().getBaseCommonGateInOut().setHpatBooking(null);
	 * skip().getBaseCommonGateInOut().setCard(null);
	 * skip().getBaseCommonGateInOut().setGateInClerk(null);
	 * skip().getBaseCommonGateInOut().setGateInClient(null);
	 * skip().getBaseCommonGateInOut().setGateOutBoothClerk(null);
	 * skip().getBaseCommonGateInOut().setGateOutClerk(null);
	 * skip().getBaseCommonGateInOut().setGateOutClient(null);
	 * skip().setCompany(null); } }; }
	 */

	public static PropertyMap<ReferRejectDetailDTO, ReferRejectDetail> referRejectDetailDTOToReferRejectDetail() {
		return new PropertyMap<ReferRejectDetailDTO, ReferRejectDetail>() {
			protected void configure() {
				skip().setReferReject(null);
			}
		};
	}

	public static PropertyMap<ReferRejectReasonDTO, ReferRejectReason> referRejectReasonDTOToReferRejectReason() {
		return new PropertyMap<ReferRejectReasonDTO, ReferRejectReason>() {
			protected void configure() {
				// skip().setReferReason(null);
				skip().setReferRejectDetail(null);
			}
		};
	}

	public static PropertyMap<ReferRejectReason, ReferRejectReasonDTO> referRejectReasonToReferRejectReasonDTO() {
		return new PropertyMap<ReferRejectReason, ReferRejectReasonDTO>() {
			protected void configure() {
				map().getReferReason().setReferReasonID(source.getReferReason().getReferReasonID());
				map().getReferReason().setReasonDescription(source.getReferReason().getReasonDescription());
				skip().getReferReason().setChildList(null);
				skip().getReferReason().setReferStatus(null);
			}
		};
	}

	public static PropertyMap<ReferReject, ReferRejectDTO> referRejectToReferRejectDTO() {
		return new PropertyMap<ReferReject, ReferRejectDTO>() {
			protected void configure() {
				skip().setReferRejectDetails(null);
				skip().getBaseCommonGateInOut().setHpabBooking(null);
			}
		};
	}

	public static PropertyMap<ReferRejectDetail, ReferRejectDetailDTO> referRejectDetailToReferRejectDetailDTO() {

		return new PropertyMap<ReferRejectDetail, ReferRejectDetailDTO>() {
			protected void configure() {
				map().setReferBy(source.getReferBy().getSystemUserID());
				map().setRejectBy(source.getRejectBy().getSystemUserID());
				skip().setReferReject(null);
				skip().setReferRejectReasons(null);
			}
		};

	}

	public static PropertyMap<WHODD, WHoddDTO> WHODDToWHoddDTO() {
		return new PropertyMap<WHODD, WHoddDTO>() {
			protected void configure() {
				skip().setCardId(null);
				skip().setGateOutBoothClerkId(null);
				skip().setGateOutClerkId(null);
				skip().setGateInClerkId(null);
				skip().setGateInClientId(null);
				skip().setGateOutClientId(null);
			}
		};
	}

	public static PropertyMap<LaneOpen, LaneOpenDTO> laneOpenToReferLaneOpenDTO() {
		return new PropertyMap<LaneOpen, LaneOpenDTO>() {
			protected void configure() {
				map().setLaneID(source.getLaneID().getClientID());
				map().setLaneOpenBy(source.getLaneOpenBy().getSystemUserID());
			}
		};
	}

	public static PropertyMap<Customs, CustomsReport> CustomsToCustomsReport() {
		return new PropertyMap<Customs, CustomsReport>() {
			protected void configure() {
				skip().setVersion(null);
			}
		};
	}

}
