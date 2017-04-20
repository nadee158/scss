/**
 * 
 */
package com.privasia.scss.gateout.imports.exports.business.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.GateOutWriteRequest;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.enums.ClientType;
import com.privasia.scss.common.enums.ImpExpFlagStatus;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.core.exception.BusinessException;
import com.privasia.scss.core.model.Client;

/**
 * @author Janaka
 *
 */
@Service("importExportCommonGateOutBusinessService")
public class ImportExportCommonGateOutBusinessService {

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public void isValidGateOutLane(Client client, GateOutWriteRequest gateOutWriteRequest) {

		ImpExpFlagStatus impExpFlag = ImpExpFlagStatus.fromValue(gateOutWriteRequest.getImpExpFlag());
		TransactionStatus impEirStatus = TransactionStatus.REJECT;
		TransactionStatus expEirStatus = TransactionStatus.REJECT;

		ImportContainer importContainer = null;
		ExportContainer exportContainer = null;

		switch (impExpFlag) {
		case IMPORT:
			importContainer = gateOutWriteRequest.getImportContainers().stream().findFirst().get();
			impEirStatus = TransactionStatus.fromCode(importContainer.getBaseCommonGateInOutAttribute().getEirStatus());
			break;
		case EXPORT:
			exportContainer = gateOutWriteRequest.getExportContainers().stream().findFirst().get();
			expEirStatus = TransactionStatus.fromCode(exportContainer.getBaseCommonGateInOutAttribute().getEirStatus());
			break;
		case IMPORT_EXPORT:
			importContainer = gateOutWriteRequest.getImportContainers().stream().findFirst().get();
			impEirStatus = TransactionStatus.fromCode(importContainer.getBaseCommonGateInOutAttribute().getEirStatus());
			exportContainer = gateOutWriteRequest.getExportContainers().stream().findFirst().get();
			expEirStatus = TransactionStatus.fromCode(exportContainer.getBaseCommonGateInOutAttribute().getEirStatus());
			break;
		default:
			break;
		}

		if (StringUtils.equalsIgnoreCase(impEirStatus.getValue(), TransactionStatus.APPROVED.getValue())
				|| StringUtils.equalsIgnoreCase(expEirStatus.getValue(), TransactionStatus.APPROVED.getValue())) {
			if (client.getType() != null) {
				if (StringUtils.isNotBlank(client.getType().getValue())) {
					if (ClientType.GATE_IN.getValue().equalsIgnoreCase(StringUtils.trim(client.getType().getValue()))) {
						throw new BusinessException("Gate out transaction not allowed for gate in lane "
								+ client.getDescription() + ". Please rescan the card at gate out lane and try again.");
					}
				}
			}

		}

	}

}
