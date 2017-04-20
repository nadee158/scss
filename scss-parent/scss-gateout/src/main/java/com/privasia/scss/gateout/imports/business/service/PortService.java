/**
 * 
 */
package com.privasia.scss.gateout.imports.business.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.enums.ClientGateType;
import com.privasia.scss.core.exception.BusinessException;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.repository.ClientGateTypeRepository;

/**
 * @author Janaka
 *
 */
@Service("portService")
public class PortService {

	private ClientGateTypeRepository clientGateTypeRepository;

	@Autowired
	public void setClientGateTypeRepository(ClientGateTypeRepository clientGateTypeRepository) {
		this.clientGateTypeRepository = clientGateTypeRepository;
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public void checkContainerTobeReleasedByPort(Client client, List<ImportContainer> importContainers) {

		Optional<List<String>> optGateTypes = clientGateTypeRepository.findAllGateTypeByClient(client);

		List<String> gateTypes = optGateTypes.orElse(null);

		if (!(gateTypes == null || gateTypes.isEmpty())) {
			importContainers.forEach(impContainer -> {

				Optional<String> moveType = Optional.of(impContainer.getMoveType());

				if (moveType.isPresent()) {
					Optional<String> type = gateTypes.stream().findAny()
							.filter(gate -> StringUtils.equalsIgnoreCase(gate, moveType.get()));
					if (!type.isPresent()) {
						ClientGateType clientGateType = ClientGateType.fromValue(moveType.get());
						throw new BusinessException("Containers released by " + clientGateType.name()
								+ " are only allowed to exit the port through "
								+ clientGateType.getClientGateDescription() + ". Gate Pass No "
								+ impContainer.getGatePassNo() + ".");
					}
				}

				if (StringUtils.isNotBlank(impContainer.getGcsBlock())) {

					if (StringUtils.equalsIgnoreCase("R", impContainer.getGcsBlock())
							|| StringUtils.equalsIgnoreCase("RLS", impContainer.getGcsBlock())) {
						Optional<String> type = gateTypes.stream().findAny()
								.filter(gate -> StringUtils.equalsIgnoreCase(gate, ClientGateType.GCS.getValue()));
						if (!type.isPresent())
							throw new BusinessException(
									"Containers released by GCS are only allowed to exit the port through Main gate. Gate Pass No "
											+ impContainer.getGatePassNo() + ".");
					}
				}

				if (StringUtils.isNotBlank(impContainer.getPkfzBlock())) {

					if (StringUtils.equalsIgnoreCase("R", impContainer.getPkfzBlock())
							|| StringUtils.equalsIgnoreCase("RLS", impContainer.getPkfzBlock())) {
						Optional<String> type = gateTypes.stream().findAny()
								.filter(gate -> StringUtils.equalsIgnoreCase(gate, ClientGateType.PKFZ.getValue()));
						if (!type.isPresent())
							throw new BusinessException(
									"Containers released by PKFZ are only allowed to exit the port through Pkfz gate. Gate Pass No "
											+ impContainer.getGatePassNo() + ".");
					}
				}

				if (StringUtils.isNotBlank(impContainer.getLpkBlock())) {

					if (StringUtils.equalsIgnoreCase("R", impContainer.getLpkBlock())
							|| StringUtils.equalsIgnoreCase("RLS", impContainer.getLpkBlock())) {
						Optional<String> type = gateTypes.stream().findAny()
								.filter(gate -> StringUtils.equalsIgnoreCase(gate, ClientGateType.LPK.getValue()));
						if (!type.isPresent())
							throw new BusinessException(
									"Containers released by LPK are only allowed to exit the port through mini booth. Gate Pass No "
											+ impContainer.getGatePassNo() + ".");
					}
				}
			});
		}

	}

}
