/**
 * 
 */
package com.privasia.scss.core.modelmap.config;

import org.modelmapper.PropertyMap;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.dto.KioskBoothRightsDTO;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.core.model.Exports;
import com.privasia.scss.core.model.ExportsQ;
import com.privasia.scss.core.model.GatePass;
import com.privasia.scss.core.model.KioskBoothRights;

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
				skip().getBaseCommonGateInOutAttribute().setHpatBooking(null);
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
				map().getBaseCommonGateInOutAttribute()
						.setCard(source.getBaseCommonGateInOutAttribute().getCard().getCardID());
			}
		};
	}

	public static PropertyMap<ImportContainer, GatePass> importContainerToGatePass() {

		return new PropertyMap<ImportContainer, GatePass>() {
			protected void configure() {
				skip().getBaseCommonGateInOutAttribute().setHpatBooking(null);
				skip().getBaseCommonGateInOutAttribute().setCard(null);
				skip().getBaseCommonGateInOutAttribute().setGateInClerk(null);
				skip().getBaseCommonGateInOutAttribute().setGateInClient(null);
				skip().getBaseCommonGateInOutAttribute().setGateOutBoothClerk(null);
				skip().getBaseCommonGateInOutAttribute().setGateOutClerk(null);
				skip().getBaseCommonGateInOutAttribute().setGateOutClient(null);
				skip().setCardUsage(null);
				skip().setPrintEir(null);
			}
		};
	}

	public static PropertyMap<ExportContainer, Exports> exportContainerToExports() {
		return new PropertyMap<ExportContainer, Exports>() {
			protected void configure() {
				skip().getCardUsage().setCard(null);
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
				map().getBaseCommonGateInOutAttribute().setEirStatus(TransactionStatus.valueOf(source.getBaseCommonGateInOutAttribute().getEirStatus()));
			}
		};
	}

}
