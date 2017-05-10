package com.privasia.scss.cosmos.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.CommonContainerDTO;
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

	@Autowired
	public void setJdbcTemplate(@Qualifier("as400JdbcTemplate") JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Transactional(value = "as400TransactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public ExportContainer isOGABlock(ExportContainer exportContainer, String containerNo) {

		return jdbcTemplate.queryForObject(queryOGABlock, new Object[] { containerNo },
				(rs, i) -> extractOGABlock(exportContainer, rs));

	}

	private ExportContainer extractOGABlock(ExportContainer exportContainer, ResultSet rs) throws SQLException {

		if (rs.next()) {
			exportContainer.setOgaBlock(true);
		}
		return exportContainer;
	}

	@Transactional(value = "as400TransactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public ExportContainer isInternalBlock(ExportContainer exportContainer, String containerNo) {

		return jdbcTemplate.queryForObject(queryInternalBlock, new Object[] { containerNo },
				(rs, i) -> extractInternalBlock(exportContainer, rs, i));

	}

	private ExportContainer extractInternalBlock(ExportContainer exportContainer, ResultSet rs, int rowNum)
			throws SQLException {

		if (rs.next()) {
			exportContainer.setInternalBlock(true);
			exportContainer.setInternalBlockDesc(rs.getString("INOP30"));
			throw new BusinessException("Container " + exportContainer.getContainer().getContainerNumber()
					+ " : Internal Block! " + exportContainer.getInternalBlockDesc());
		}

		return exportContainer;

	}

	@Transactional(value = "as400TransactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public String checkContainerStatus(String containerNo, String timeGateIn) {

		containerNo = StringUtils.upperCase(containerNo);
		return jdbcTemplate.queryForObject(queryContainerStatus, new Object[] { containerNo, timeGateIn },
				(rs, i) -> extractContainerStatus(rs));

	}

	private String extractContainerStatus(ResultSet rs) throws SQLException {

		if (rs != null && rs.next()) {
			return rs.getString("HDFS03");
		} else {
			return ContainerStatus.NON_EXECUTE.getValue();
		}

	}

	@Transactional(value = "as400TransactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public ExportContainer fetchContainerInfo(String exportContainerNo) {

		exportContainerNo = StringUtils.upperCase(exportContainerNo);
		Optional<ExportContainer> optExportContainer = jdbcTemplate.queryForObject(queryContainerPrimaryInfo,
				new Object[] { exportContainerNo }, (rs, i) -> extractPrimaryContainerInfo(rs, i));

		if (!optExportContainer.isPresent())
			optExportContainer = Optional.of(new ExportContainer());

		final ExportContainer exportContainer = optExportContainer.get();
		optExportContainer.get().getContainer().setContainerNumber(exportContainerNo);
		isInternalBlock(exportContainer, exportContainerNo);
		isOGABlock(exportContainer, exportContainerNo);
		
		jdbcTemplate.queryForObject(queryContainerSecondaryInfo,
				new Object[] { exportContainerNo }, (rs, i) -> extractSecondaryContainerInfo(exportContainer, rs, i));

		return exportContainer;

	}

	private Optional<ExportContainer> extractPrimaryContainerInfo(ResultSet rs, int rowNum) throws SQLException {

		ExportContainer exportContainer = null;
		int rowcount = 0;
		if (rs.last()) {
			rowcount = rs.getRow();
			rs.beforeFirst();
		}

		if (rs.next()) {

			exportContainer = new ExportContainer();
			CommonContainerDTO commonContainerDTO = new CommonContainerDTO();
			exportContainer.setContainer(commonContainerDTO);
			exportContainer.setTotalBooking(rowcount);

			exportContainer.setBookingNoExist(true);
			exportContainer.setBookingNo(rs.getString("ORRF05"));
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

			exportContainer.setVesselETADate(DateUtil.getLocalDategFromString(
					rs.getString("ETAD01") + TextString.padding(rs.getString("ETAT01"), 6, '0', true)));

		}

		if (rowcount > 1)
			throw new BusinessException(
					"Multiple booking found for container " + exportContainer.getContainer().getContainerNumber());

		if (!exportContainer.isBookingNoExist())
			throw new BusinessException(
					"Invalid Container No(s) " + exportContainer.getContainer().getContainerNumber());

		return Optional.ofNullable(exportContainer);
	}
	
	private ExportContainer extractSecondaryContainerInfo(ExportContainer exportContainer, 
			ResultSet rs, int rowNum) throws SQLException {
		
		if (rs.next()) {
		
		    /*exportContainer.setExportOrderStatus(gIReadResponseExporterContainer.getExportOrderStatus());
		    exportContainer.setExportOrderType(gIReadResponseExporterContainer.getExportOrderType());
		    exportContainer.setExpCarrierType(gIReadResponseExporterContainer.getExpCarrierType());
		    exportContainer.setYardOpeningDateTime(
			        DateUtil.getLocalDategFromString(gIReadResponseExporterContainer.getYardOpeningDateTime()));
		    exportContainer.getContainer().setContainerHeight(
			        ExportUtilService.getDoubleValueFromString(gIReadResponseExporterContainer.getContainerHeight()));
			    exportContainer.setContainerType(gIReadResponseExporterContainer.getContainerType());
			    exportContainer.getContainer().setContainerSize(gIReadResponseExporterContainer.getContainerSize());
			 // private String containerSeal1_SL;// null,
			    exportContainer.getSealAttribute().setSeal01Origin(gIReadResponseExporterContainer.getContainerSeal1_SL());
			    // private String containerSeal1_NO;// SEAL001,
			    exportContainer.getSealAttribute().setSeal01Number(gIReadResponseExporterContainer.getContainerSeal1_NO());
			    // private String containerSeal2_SL;// null,
			    exportContainer.getSealAttribute().setSeal02Origin(gIReadResponseExporterContainer.getContainerSeal2_SL());
			    // private String containerSeal2_NO;// null,
			    exportContainer.getSealAttribute().setSeal02Origin(gIReadResponseExporterContainer.getContainerSeal2_NO());

			    // private String containerReeferIndicator;// N,
			    exportContainer.setReferFlag(
			        ExportUtilService.getBooleanFromString(gIReadResponseExporterContainer.getContainerReeferIndicator()));
			    // private String containerReeferTempSign;// null,
			    exportContainer.setReferTempType(gIReadResponseExporterContainer.getContainerReeferTempSign());
			    // private String containerReeferTempValue;// null,
			    if (StringUtils.isNotEmpty(gIReadResponseExporterContainer.getContainerReeferTempValue())) {
			      exportContainer.setReferTemp(
			          ExportUtilService.getDoubleValueFromString(gIReadResponseExporterContainer.getContainerReeferTempValue()));
			    }
			    // private String containerReeferTempUnit;// null,
			    exportContainer.setReeferTempUnit(gIReadResponseExporterContainer.getContainerReeferTempUnit());
			    
			 // private double containerOOG_OH;// 5.0,
			    exportContainer
			        .setOogOH(ExportUtilService.getDoubleValueFromString(gIReadResponseExporterContainer.getContainerOOG_OH()).intValue());
			    // private double containerOOG_OL;// 3.0,
			    exportContainer
			        .setOogOL(ExportUtilService.getDoubleValueFromString(gIReadResponseExporterContainer.getContainerOOG_OL()).intValue());
			    // private double containerOOG_OF;// 1.0,
			    exportContainer
			        .setOogOF(ExportUtilService.getDoubleValueFromString(gIReadResponseExporterContainer.getContainerOOG_OF()).intValue());
			    // private double containerOOG_OA;// 2.0,
			    exportContainer
			        .setOogOA(ExportUtilService.getDoubleValueFromString(gIReadResponseExporterContainer.getContainerOOG_OA()).intValue());
			    // private double containerOOG_OR;// 4.0
			    exportContainer
			        .setOogOR(ExportUtilService.getDoubleValueFromString(gIReadResponseExporterContainer.getContainerOOG_OR()).intValue());
			    exportContainer.setImdgLabelID(gIReadResponseExporterContainer.getContainerDGImdgLabel());
			    // private String yardDGOpeningDateTime;// 20160904080000,
			    exportContainer.setYardDGOpeningDateTime(
			        DateUtil.getLocalDategFromString(gIReadResponseExporterContainer.getYardDGOpeningDateTime()));*/
		    // private String containerInOrOut;// IN,
//		    exportContainer.setGateInOut(TextString.format(rs.getString("HDTP10")));
		   
		    // private String containerFullOrEmpty;// F,
		    exportContainer.getContainer().setContainerFullOrEmpty(TextString.format(rs.getString("CNBT03")));
		    
		    exportContainer.setSubHandlingType(TextString.format(rs.getString("HDST03")));
		    
		   
		    // private String containerSpod;// USSVN,
		    exportContainer.setExpSpod(TextString.format(rs.getString("SPOD")));
		    // private String containerIso;// 22G0,
		    exportContainer.setCosmosISOCode(TextString.format(rs.getString("CNIS03")));
		    exportContainer.getContainer().setContainerISOCode(TextString.format(rs.getString("CNIS03")));
		    
		    exportContainer.setGrossWeight(
		        ExportUtilService.getIntValueFromString(rs.getString("CNBG03")));
		    exportContainer.setCosmosNetWeight(
		        ExportUtilService.getIntValueFromString(rs.getString("CNNG03")));
		    exportContainer.setTareWeight(
		        ExportUtilService.getIntValueFromString(rs.getString("CNTG03")));

		    if (exportContainer.getSealAttribute() == null) {
		      exportContainer.setSealAttribute(new CommonSealDTO());
		    }
		    

		    // private String containerDGImdg;// null,
		    exportContainer.setImdg(TextString.format(rs.getString("CNIM03")));
		    // private String containerDGUNCode;// null,
		    exportContainer.setDgUNCode(TextString.format(rs.getString("CNUN03")));
		    // private String containerDGImdgLabel;// null,
		   

		    
		    // private String containerDamageCode1;//
		    exportContainer.setDamageCode_01(UtilService.constructDamageCode(TextString.format(rs.getString("dm0103"))));
		    // private String containerDamageCode2;//
		    exportContainer.setDamageCode_02(UtilService.constructDamageCode(TextString.format(rs.getString("dm0203"))));
		    // private String containerDamageCode3;//
		    exportContainer.setDamageCode_03(UtilService.constructDamageCode(TextString.format(rs.getString("dm0303"))));
		    // private String containerDamageCode4;//
		    exportContainer.setDamageCode_04(UtilService.constructDamageCode(TextString.format(rs.getString("dm0403"))));
		    // private String containerDamageCode5;//
		    exportContainer.setDamageCode_05(UtilService.constructDamageCode(TextString.format(rs.getString("dm0503"))));
		    

		}


		return exportContainer;
	}

}
