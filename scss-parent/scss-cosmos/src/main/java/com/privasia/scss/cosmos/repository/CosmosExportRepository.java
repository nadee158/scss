package com.privasia.scss.cosmos.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.CommonSealDTO;
import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.enums.ContainerStatus;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.common.service.export.ExportUtilService;
import com.privasia.scss.common.util.DateUtil;
import com.privasia.scss.cosmos.util.TextString;
import com.privasia.scss.cosmos.util.UtilService;

@Repository
public class CosmosExportRepository {

	private static final Log log = LogFactory.getLog(CosmosExportRepository.class);

	private JdbcTemplate jdbcTemplate;

	@Value("${export.ogaBlock}")
	private String queryOGABlock;

	@Value("${export.internalBlock}")
	private String queryInternalBlock;

	@Value("${export.containerStatus}")
	private String queryContainerStatus;

	@Value("${export.containerPrimaryInfo}")
	private String queryContainerPrimaryInfo;

	@Value("${export.containerSecondaryInfo}")
	private String queryContainerSecondaryInfo;

	@Value("${export.containerOOGInfo}")
	private String queryContainerOOGInfo;

	@Value("${export.containerSealInfo}")
	private String queryContainerSealInfo;

	@Autowired
	public void setJdbcTemplate(@Qualifier("as400JdbcTemplate") JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Transactional(value = "as400TransactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public ExportContainer isOGABlock(ExportContainer exportContainer) {

		String containerNo = StringUtils.upperCase(exportContainer.getContainer().getContainerNumber());
		try {
			return jdbcTemplate.queryForObject(queryOGABlock, new Object[] { containerNo },
					(rs, i) -> extractOGABlock(exportContainer, rs));
		} catch (EmptyResultDataAccessException e) {
			return exportContainer;
		}

	}

	private ExportContainer extractOGABlock(ExportContainer exportContainer, ResultSet rs) {
		exportContainer.setOgaBlock(true);
		return exportContainer;
	}

	@Transactional(value = "as400TransactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public ExportContainer isInternalBlock(ExportContainer exportContainer) {

		String containerNo = StringUtils.upperCase(exportContainer.getContainer().getContainerNumber());

		try {
			return jdbcTemplate.queryForObject(queryInternalBlock, new Object[] { containerNo },
					(rs, i) -> extractInternalBlock(exportContainer, rs, i));
		} catch (EmptyResultDataAccessException e) {
			exportContainer.setInternalBlock(false);
			exportContainer.setInternalBlockDesc(null);
			return exportContainer;
		}

	}

	private ExportContainer extractInternalBlock(ExportContainer exportContainer, ResultSet rs, int rowNum) {

		try {
			exportContainer.setInternalBlock(true);
			exportContainer.setInternalBlockDesc(rs.getString("INOP30"));
			throw new BusinessException("Container " + exportContainer.getContainer().getContainerNumber()
					+ " : Internal Block! " + exportContainer.getInternalBlockDesc());
		} catch (SQLException e) {
			log.error("ExportContainer extractInternalBlock " + e.getMessage());
			e.printStackTrace();
			throw new BusinessException("Error cound while fetching container in cosmos "
					+ exportContainer.getContainer().getContainerNumber());
		}

	}

	@Transactional(value = "as400TransactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public String checkContainerStatus(String containerNo, String timeGateIn) {
		try {
			containerNo = StringUtils.upperCase(containerNo);
			return jdbcTemplate.queryForObject(queryContainerStatus, new Object[] { containerNo, timeGateIn },
					(rs, i) -> extractContainerStatus(rs));
		} catch (EmptyResultDataAccessException e) {
			log.error("Container checkContainerStatus " + e.getMessage());
			return extractContainerStatus(null);
			//e.printStackTrace();
			//throw new BusinessException("Container status cound not found in cosmos " + containerNo);
		}
	}

	private String extractContainerStatus(ResultSet rs)  {
		try {
			if (rs != null) {
				return rs.getString("HDFS03");
			} else {
				return ContainerStatus.NON_EXECUTE.getValue();
			}
		} catch (SQLException e) {
			log.error("checkContainerStatus extractContainerStatus " + e.getMessage());
			e.printStackTrace();
			return ContainerStatus.NON_EXECUTE.getValue();
		}
	}

	@Transactional(value = "as400TransactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public ExportContainer fetchContainerPrimanyInfo(ExportContainer exportContainer) {
		System.out.println("fetchContainerPrimanyInfo repo");
		String exportContainerNo = StringUtils.upperCase(exportContainer.getContainer().getContainerNumber());
		System.out.println("exportContainerNo " + exportContainerNo);
		try {
			return jdbcTemplate.queryForObject(queryContainerPrimaryInfo, new Object[] { exportContainerNo },
					(rs, i) -> extractPrimaryContainerInfo(exportContainer, rs, i));
		} catch (EmptyResultDataAccessException e) {
			log.error("ExportContainer fetchContainerPrimanyInfo " + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(
					"Container cound not found in cosmos " + exportContainer.getContainer().getContainerNumber());
		}
	}

	private ExportContainer extractPrimaryContainerInfo(ExportContainer exportContainer, ResultSet rs, int row) {

		int rowcount = 0;

		try {
			exportContainer.setBookingNo(rs.getString("ORRF05"));
			if (StringUtils.isNotEmpty(exportContainer.getBookingNo())) {
				exportContainer.setBookingNoExist(true);
			} else {
				throw new BusinessException("Invalid Container. Booking Number Not Found in Cosmos "
						+ exportContainer.getContainer().getContainerNumber());
			}

			exportContainer.getContainer().setContainerFullOrEmpty(rs.getString("CNBT03"));
			exportContainer.setShippingLine(rs.getString("LYND05"));
			exportContainer.setShippingAgent(rs.getString("ORGV05"));

			exportContainer.setVesselCode(rs.getString("VMID01"));
			exportContainer.setVesselVoyageIN(rs.getString("RSIN01"));
			exportContainer.setVesselVoyageOUT(rs.getString("RSUT01"));
			exportContainer.setVesselVisitID(rs.getString("BZID01"));
			exportContainer.setVesselSCN(StringUtils.trim(rs.getString("REGN01")));
			exportContainer.setVesselName(StringUtils.trim(rs.getString("MVVA47")));
			exportContainer.setVesselStatus(rs.getString("BZFS01"));

			System.out.println("ETA DATA : " + rs.getString("ETAD01"));

			exportContainer.setVesselETADate(DateUtil.getLocalDategFromString(
					rs.getString("ETAD01") + TextString.padding(rs.getString("ETAT01"), 6, '0', true)));
			System.out.println("rowcount bef " + rowcount);
			while (rs.next()) {
				rowcount += rs.getRow();
			}
			System.out.println("rowcount aft " + rowcount);
			exportContainer.setTotalBooking(rowcount);
			if (rowcount > 1)
				throw new BusinessException("Multiple Bookings found for container in COSMOS "
						+ exportContainer.getContainer().getContainerNumber());

			return exportContainer;
		} catch (SQLException e) {
			log.error("ExportContainer fetchContainerPrimanyInfo " + e.getMessage());
			e.printStackTrace();
			throw new BusinessException("Error cound while fetching container in cosmos "
					+ exportContainer.getContainer().getContainerNumber());
		}

	}

	@Transactional(value = "as400TransactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public ExportContainer fetchContainerSecondaryInfo(ExportContainer exportContainer) {

		String exportContainerNo = StringUtils.upperCase(exportContainer.getContainer().getContainerNumber());

		try {
			return jdbcTemplate.queryForObject(queryContainerSecondaryInfo, new Object[] { exportContainerNo },
					(rs, i) -> extractSecondaryContainerInfo(exportContainer, rs));
		} catch (EmptyResultDataAccessException e) {
			log.error("ExportContainer fetchContainerSecondaryInfo " + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(
					"Container cound not found in cosmos " + exportContainer.getContainer().getContainerNumber());
		}
	}

	private ExportContainer extractSecondaryContainerInfo(ExportContainer exportContainer, ResultSet rs) {
		System.out.println("exportContainer " + exportContainer.getContainer().getContainerNumber());
		System.out.println("rs " + rs);
		try {

			exportContainer.setGateInOut(TextString.format(rs.getString("HDTP10")));
			// private String containerFullOrEmpty;// F,
			exportContainer.getContainer().setContainerFullOrEmpty(TextString.format(rs.getString("CNBT03")));

			exportContainer.setExpOut(TextString.format(rs.getString("VMSR01")));

			exportContainer.setExpCar(TextString.format(rs.getString("VMID01")));

			exportContainer.setSubHandlingType(TextString.format(rs.getString("HDST03")));

			// private String containerSpod;// USSVN,
			exportContainer.setExpSpod(TextString.format(rs.getString("SPOD")));
			// private String containerIso;// 22G0,
			exportContainer.setCosmosISOCode(TextString.format(rs.getString("CNIS03")));
			exportContainer.getContainer().setContainerISOCode(TextString.format(rs.getString("CNIS03")));

			// private String containerReeferIndicator;// N,
			exportContainer
					.setReferFlag(ExportUtilService.getBooleanFromString(TextString.format(rs.getString("CNOR03"))));
			// private String containerReeferTempValue;// null,
			if (StringUtils.isNotEmpty(TextString.format(rs.getString("ORRT03")))) {
				// private String containerReeferTempSign;// null,
				exportContainer.setReferTempType(
						ExportUtilService.getCosmosReferTempSign(TextString.format(rs.getString("ORRT03"))));
				exportContainer.setReferTemp(
						ExportUtilService.getDoubleValueFromString(TextString.format(rs.getString("ORRT03"))));
			}
			// private String containerReeferTempUnit;// null,
			exportContainer.setReeferTempUnit(TextString.format(rs.getString("ORTE03")));

			exportContainer.setGrossWeight(ExportUtilService.getIntValueFromString(rs.getString("CNBG03")));
			exportContainer.setCosmosNetWeight(ExportUtilService.getIntValueFromString(rs.getString("CNNG03")));
			exportContainer.setTareWeight(ExportUtilService.getIntValueFromString(rs.getString("CNTG03")));

			// private String containerDGImdg;// null,
			exportContainer.setImdg(TextString.format(rs.getString("CNIM03")));
			// private String containerDGUNCode;// null,
			exportContainer.setDgUNCode(TextString.format(rs.getString("CNUN03")));

			// private String containerDamageCode1;//
			exportContainer
					.setDamageCode_01(UtilService.constructDamageCode(TextString.format(rs.getString("DM0103"))));
			// private String containerDamageCode2;//
			exportContainer
					.setDamageCode_02(UtilService.constructDamageCode(TextString.format(rs.getString("DM0203"))));
			// private String containerDamageCode3;//
			exportContainer
					.setDamageCode_03(UtilService.constructDamageCode(TextString.format(rs.getString("DM0303"))));
			// private String containerDamageCode4;//
			exportContainer
					.setDamageCode_04(UtilService.constructDamageCode(TextString.format(rs.getString("DM0403"))));
			// private String containerDamageCode5;//
			exportContainer
					.setDamageCode_05(UtilService.constructDamageCode(TextString.format(rs.getString("DM0503"))));

			String type = TextString.format(rs.getString("OT0103"));
			String val = TextString.format(rs.getString("OW0103"));
			setOGG(exportContainer, type, val);
			fetchOOGInfo(exportContainer, TextString.format(rs.getString("HDID10")));
			fetchSealInfo(exportContainer, TextString.format(rs.getString("HDID10")));

			return exportContainer;

		} catch (SQLException e) {
			log.error("ExportContainer extractSecondaryContainerInfo " + e.getMessage());
			e.printStackTrace();
			throw new BusinessException("Error cound while fetching container in cosmos "
					+ exportContainer.getContainer().getContainerNumber());
		}

	}

	@Transactional(value = "as400TransactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public ExportContainer fetchSealInfo(ExportContainer exportContainer, String handlingId) {

		try {
			return jdbcTemplate.queryForObject(queryContainerSealInfo, new Object[] { handlingId },
					(rs, i) -> extractSealInfo(exportContainer, rs, i));
		} catch (EmptyResultDataAccessException e) {
			return exportContainer;
		}

	}

	private ExportContainer extractSealInfo(ExportContainer exportContainer, ResultSet rs, int rowNum) {

		if (exportContainer.getSealAttribute() == null) {
			exportContainer.setSealAttribute(new CommonSealDTO());
		}
		try {

			exportContainer.getSealAttribute().setSeal01Number(rs.getString("SEAL2K"));
			exportContainer.getSealAttribute().setSeal01Origin(TextString.format(rs.getString("SLOR2K")));
			exportContainer.getSealAttribute().setSeal01Type(TextString.format(rs.getString("SLTP2K")));

			while (rs.next()) {
				exportContainer.getSealAttribute().setSeal02Number(rs.getString("SEAL2K"));
				exportContainer.getSealAttribute().setSeal02Origin(TextString.format(rs.getString("SLOR2K")));
				exportContainer.getSealAttribute().setSeal02Type(TextString.format(rs.getString("SLTP2K")));
			}

			return exportContainer;
		} catch (SQLException e) {
			log.error("ExportContainer extractSealInfo " + e.getMessage());
			e.printStackTrace();
			throw new BusinessException("Error cound while fetching container seal in cosmos "
					+ exportContainer.getContainer().getContainerNumber());
		}

	}

	@Transactional(value = "as400TransactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public ExportContainer fetchOOGInfo(ExportContainer exportContainer, String handlingId) {

		try {
			return jdbcTemplate.queryForObject(queryContainerOOGInfo, new Object[] { handlingId },
					(rs, i) -> extractContainerOOGInfo(exportContainer, rs, i));
		} catch (EmptyResultDataAccessException e) {
			return exportContainer;
		}

	}

	private ExportContainer extractContainerOOGInfo(ExportContainer exportContainer, ResultSet rs, int rowNum) {

		try {

			String type207 = TextString.format(rs.getString("OT0207"));
			String val207 = TextString.format(rs.getString("OW0207"));

			String type307 = TextString.format(rs.getString("OT0307"));
			String val307 = TextString.format(rs.getString("OW0307"));

			while (rs.next()) {
				type207 = TextString.format(rs.getString("OT0207"));
				val207 = TextString.format(rs.getString("OW0207"));
				type307 = TextString.format(rs.getString("OT0307"));
				val307 = TextString.format(rs.getString("OW0307"));
				break;
			}

			setOGG(exportContainer, type207, val207);
			setOGG(exportContainer, type307, val307);

		} catch (SQLException e) {
			log.error("ExportContainer extractContainerOOGInfo " + e.getMessage());
			e.printStackTrace();
			throw new BusinessException("Error cound while fetching container OOG in cosmos "
					+ exportContainer.getContainer().getContainerNumber());
		}
		return exportContainer;
	}

	private ExportContainer setOGG(ExportContainer exportContainer, String type, String value) {

		if (StringUtils.isNotEmpty(type)) {
			type = StringUtils.trim(type).toString();
		}

		switch (type) {
		case "OH":
			exportContainer.setOogOH(ExportUtilService.getDoubleValueFromString(value).intValue());
			break;

		case "OL":
			exportContainer.setOogOL(ExportUtilService.getDoubleValueFromString(value).intValue());
			break;

		case "OF":
			exportContainer.setOogOF(ExportUtilService.getDoubleValueFromString(value).intValue());
			break;

		case "OA":
			exportContainer.setOogOA(ExportUtilService.getDoubleValueFromString(value).intValue());
			break;

		case "OR":
			exportContainer.setOogOR(ExportUtilService.getDoubleValueFromString(value).intValue());
			break;

		default:
			break;
		}

		return exportContainer;

	}

	public static void main(String args[]) {

		System.out.println(DateUtil.getLocalDategFromString("20170612" + TextString.padding("20170612", 6, '0', true)));
	}

}
