package com.privasia.scss.gatein.service;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.GateInForm;
import com.privasia.scss.common.util.DateUtil;

/**
 * @author nadee158
 *
 */
@Service("containerService")
public class ContainerService {

  public ExportContainer getContainerByContainerNo(String containerNo) throws Exception {
    // must be in this order
    this.checkContainerInAS400();
    this.checkContainer();
    this.checkSCN();
    this.checkDg();

    return null;
  }



  private void checkContainerInAS400Old() throws Exception {
    // AS400Database db = null;
    // Connection conn = null;
    // Statement stmt = null;
    // ResultSet rs = null;
    // String sql = "";
    //
    // try {
    // db = AS400Database.getInstance();
    //
    // sql = " SELECT PCTCSE.CTLTHD2.CNID10 " + ", PCTCSE.CTORDRU.ORRF05 " + ",
    // PCTCSE.CTORDRU.LYND05 " // Line
    // // Code
    // + ", PCTCSE.CTORDRU.ORGV05 " // AGENT
    // + ", PCTCSE.CTVSIT4.VMID01 " // VESSEL CODE
    // + ", PCTCSE.CTMDVLI.MVVA47 " // VESSEL NAME
    // + ", PCTCSE.CTVSIT4.ETAD01 " + ", PCTCSE.CTVSIT4.ETAT01 " + ", PCTCSE.CTVSIT4.RSIN01 " //
    // VOYAGE
    // // IN
    // + ", PCTCSE.CTVSIT4.RSUT01 " // VOYAGE OUT
    // + ", PCTCSE.CTVSIT4.ATAD01 " + ", PCTCSE.CTVSIT4.ATAT01 " + ", PCTCSE.CTVSIT4.REGN01 "
    // + ", PCTCSE.CTVSIT4.BZID01 " + ", PCTCSE.CTVSIT4.BZFS01 " + ", PCTCSE.CTLTHD2.HDTP10 "
    // + ", PCTCSE.CTHNDL9.CNBT03 " + ", PCTCSE.CTLTHD2.HDID10 " + ", PCTCSE.CTGODSI.GDOM12 "
    // + ", PCTCSE.CTVSIT4.VMYK01 " + ", CONCAT(PCTCSE.CTLOTTU.VUSL02, PCTCSE.CTLOTTU.VUSP02) AS
    // spod "
    // + ", PCTCSE.CTHNDL9.CNIS03 " + ", PCTCSE.CTHNDL9.CNOR03 " + ", PCTCSE.CTHNDL9.ORRT03 "
    // + ", PCTCSE.CTHNDL9.ORTE03 " + ", PCTCSE.CTHNDL9.CNIM03 " + ", PCTCSE.CTHNDL9.CNUN03 "
    // + ", PCTCSE.CTHNDL9.OT0103 " + ", PCTCSE.CTHNDL9.OW0103 " + ", PCTCSE.CTHNDL9.HDEO03 "
    // + ", PCTCSE.CTHNDL9.HDED03 " + ", PCTCSE.CTHNDL9.DM0103 " + ", PCTCSE.CTHNDL9.DM0203 "
    // + ", PCTCSE.CTHNDL9.DM0303 " + ", PCTCSE.CTHNDL9.DM0403 " + ", PCTCSE.CTHNDL9.DM0503 "
    // + ", PCTCSE.CTHNDL9.DM0603 " + ", PCTCSE.CTHNDL9.DM0703 " + ", PCTCSE.CTHNDL9.DM0803 "
    // + " FROM {oj PCTCSE.CTHNDL9 LEFT OUTER JOIN PCTCSE.CTGODSI ON PCTCSE.CTHNDL9.HDID03 =
    // PCTCSE.CTGODSI.HDID12} "
    // + ", PCTCSE.CTLTHD2 " + ", PCTCSE.CTORDRU " + ", PCTCSE.CTLOTTU " + ", PCTCSE.CTVSIT4 " + ",
    // PCTCSE.CTMDVLI "
    // + " WHERE ( PCTCSE.CTHNDL9.CNID03 = " + SQL.format(containerNo) + ") AND "
    // + " ( PCTCSE.CTHNDL9.HDDT03 >= 0 ) AND " + " ( PCTCSE.CTHNDL9.HDTD03 >= 0 ) AND "
    // + " ( PCTCSE.CTHNDL9.HDTP03 = 'IN' ) AND " + " ( PCTCSE.CTHNDL9.HDFS03 = 'RGS' ) AND "
    // + " ( PCTCSE.CTLTHD2.LTTP10 = 'ORD' ) AND " + " ( PCTCSE.CTLTHD2.HDID10 =
    // PCTCSE.CTHNDL9.HDID03 ) AND "
    // + " ( PCTCSE.CTLTHD2.LHFS10 = 'RGS' ) AND " + " ( PCTCSE.CTORDRU.ORID05 =
    // PCTCSE.CTLTHD2.ORID10 ) AND "
    // + " ( PCTCSE.CTLOTTU.LTID02 = PCTCSE.CTLTHD2.LTID10 ) AND " + " ( PCTCSE.CTVSIT4.TRMK01 =
    // 'WPT1' ) AND "
    // + " ( PCTCSE.CTVSIT4.RSUT01 = PCTCSE.CTLOTTU.VURS02 ) AND "
    // + " ( PCTCSE.CTVSIT4.VMSR01 = PCTCSE.CTLOTTU.VUVS02 ) AND "
    // + " ( PCTCSE.CTVSIT4.VMID01 = PCTCSE.CTLOTTU.VUVI02 ) AND "
    // + " ( PCTCSE.CTVSIT4.BZFS01 NOT IN ('CAN','EXE' )) AND " + " ( PCTCSE.CTVSIT4.BZFS01 <> 'CAN'
    // ) AND "
    // + " ( PCTCSE.CTMDVLI.MVID47 = 'VMDIDF' ) AND " + " ( PCTCSE.CTMDVLI.TRMK47 = '' ) AND "
    // + " ( PCTCSE.CTMDVLI.TALK47 = 'E' ) AND " + " ( PCTCSE.CTMDVLI.MVV147 = PCTCSE.CTVSIT4.VMID01
    // ) ";
    //
    // conn = db.getConnection();
    // stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    //
    // /**
    // * Change COSMOS Schema
    // */
    // sql = db.changeCosmosSchema(sql);
    //
    // rs = stmt.executeQuery(sql);
    // /**
    // * Validate only 1 booking
    // */
    // int rowcount = 0;
    // if (rs.last()) {
    // rowcount = rs.getRow();
    // totalBooking = rowcount;
    // rs.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the
    // // first element
    // }
    //
    // if (rs.next()) {
    // bookingNoExist = true;
    // bookingNo = rs.getString("orrf05");
    // // shipCode = rs.getString("vmid01");
    // shipCode = rs.getString("LYND05");
    // // etad = new SimpleDateFormat("ddMMyyyy").parse(rs.getString("etad01"));
    // etad = new SimpleDateFormat("yyyyMMdd").parse(rs.getString("etad01"));
    // scn = StringUtils.trim(rs.getString("regn01"));
    // fOrE = rs.getString("cnbt03");
    // agentCode = rs.getString("orgv05");
    // vesselCode = rs.getString("vmid01");
    // vesselVoyage = rs.getString("rsin01");
    // vesselScn = StringUtils.trim(rs.getString("regn01"));
    // vesselName = StringUtils.trim(rs.getString("MVVA47"));
    // vesselVisitId = rs.getString("bzid01");
    // vesselStatus = rs.getString("bzfs01");
    // lineCode = rs.getString("lynd05");
    // vesselVoyageOut = rs.getString("rsut01");
    // vesselDateEta =
    // parseAS400Date(rs.getString("etad01") + TextString.padding(rs.getString("etat01"), 6, '0',
    // true));
    // // vesselDateAta = parseAS400Date(rs.getString("atad01") +
    // // TextString.padding(rs.getString("atat01"), 6, '0', true));
    //
    // if (fOrE.equals("F")) {
    // fullOrEmpty = true;
    // }
    // }
    //
    // this.checkInternal(conn);
    // this.checkOGA(conn);
    // } catch (Exception e) {
    // throw e;
    // } finally {
    // if (rs != null) {
    // rs.close();
    // }
    // if (stmt != null) {
    // stmt.close();
    // }
    // if (conn != null) {
    // conn.close();
    // }
    // }
  }

  /**
   * WPTSCSSSUP-187: Change cosmos query
   * 
   * 
   * SELECT "PCTCSE"."CTLTHD2"."CNID10", "PCTCSE"."CTORDRU"."ORRF05", "PCTCSE"."CTORDRU"."LYND05",
   * "PCTCSE"."CTORDRU"."ORGV05", "PCTCSE"."CTVSIT4"."VMID01", "PCTCSE"."CTMDVLI"."MVVA47",
   * "PCTCSE"."CTVSIT4"."ETAD01", "PCTCSE"."CTVSIT4"."ETAT01", "PCTCSE"."CTVSIT4"."RSIN01",
   * "PCTCSE"."CTVSIT4"."RSUT01", "PCTCSE"."CTVSIT4"."ATAD01", "PCTCSE"."CTVSIT4"."ATAT01",
   * "PCTCSE"."CTVSIT4"."REGN01", "PCTCSE"."CTVSIT4"."BZID01", "PCTCSE"."CTVSIT4"."BZFS01",
   * "PCTCSE"."CTLTHD2"."HDTP10", "PCTCSE"."CTHNDL9"."CNBT03", "PCTCSE"."CTLTHD2"."HDID10",
   * "PCTCSE"."CTVSIT4"."VMYK01", "PCTCSE"."CTLOTTU"."VUSL02", "PCTCSE"."CTLOTTU"."VUSP02",
   * "PCTCSE"."CTHNDL9"."CNIS03", "PCTCSE"."CTHNDL9"."CNOR03", "PCTCSE"."CTHNDL9"."ORRT03",
   * "PCTCSE"."CTHNDL9"."ORTE03", "PCTCSE"."CTHNDL9"."CNIM03", "PCTCSE"."CTHNDL9"."CNUN03",
   * "PCTCSE"."CTHNDL9"."OT0103", "PCTCSE"."CTHNDL9"."OW0103", "PCTCSE"."CTHNDL9"."HDEO03",
   * "PCTCSE"."CTHNDL9"."HDED03", "PCTCSE"."CTHNDL9"."DM0103", "PCTCSE"."CTHNDL9"."DM0203",
   * "PCTCSE"."CTHNDL9"."DM0303", "PCTCSE"."CTHNDL9"."DM0403", "PCTCSE"."CTHNDL9"."DM0503",
   * "PCTCSE"."CTHNDL9"."DM0603", "PCTCSE"."CTHNDL9"."DM0703", "PCTCSE"."CTHNDL9"."DM0803",
   * "PCTCSE"."CTORDRU"."ORTP05" FROM "PCTCSE"."CTHNDL9", "PCTCSE"."CTLTHD2", "PCTCSE"."CTORDRU",
   * "PCTCSE"."CTLOTTU", "PCTCSE"."CTVSIT4", "PCTCSE"."CTMDVLI" WHERE ( "PCTCSE"."CTHNDL9"."CNID03"
   * = ? ) AND ( "PCTCSE"."CTHNDL9"."HDDT03" >= 0 ) AND ( "PCTCSE"."CTHNDL9"."HDTD03" >= 0 ) AND (
   * "PCTCSE"."CTHNDL9"."HDTP03" = 'IN' ) AND ( "PCTCSE"."CTHNDL9"."HDFS03" = 'RGS' ) AND (
   * "PCTCSE"."CTLTHD2"."LTTP10" in ('INF','ORD') ) AND ( "PCTCSE"."CTLTHD2"."HDID10" =
   * "PCTCSE"."CTHNDL9"."HDID03" ) AND ( "PCTCSE"."CTLTHD2"."LHFS10" = 'RGS' ) AND (
   * "PCTCSE"."CTORDRU"."ORID05" = "PCTCSE"."CTLTHD2"."ORID10" ) AND ( "PCTCSE"."CTORDRU"."ORTP05"
   * in ('MTI','BKG','CNA') ) AND ( "PCTCSE"."CTLOTTU"."LTID02" = "PCTCSE"."CTLTHD2"."LTID10" ) AND
   * ( "PCTCSE"."CTVSIT4"."TRMK01" = 'WPT1' ) AND ( "PCTCSE"."CTVSIT4"."RSUT01" =
   * "PCTCSE"."CTLOTTU"."VURS02" ) AND ( "PCTCSE"."CTVSIT4"."VMSR01" = "PCTCSE"."CTLOTTU"."VUVS02" )
   * AND ( "PCTCSE"."CTVSIT4"."VMID01" = "PCTCSE"."CTLOTTU"."VUVI02" ) AND (
   * "PCTCSE"."CTVSIT4"."BZFS01" <> 'CAN' ) AND ( "PCTCSE"."CTMDVLI"."MVID47" = 'VMDIDF' ) AND (
   * "PCTCSE"."CTMDVLI"."TRMK47" = '' ) AND ( "PCTCSE"."CTMDVLI"."TALK47" = 'E' ) AND (
   * "PCTCSE"."CTMDVLI"."MVV147" = "PCTCSE"."CTVSIT4"."VMID01" )
   * 
   * @throws Exception
   */
  private void checkContainerInAS400() throws Exception {
    // log.error("-------------START checkContainerInAS400 -----------" + containerNo);
    // AS400Database db = null;
    // Connection conn = null;
    // Statement stmt = null;
    // ResultSet rs = null;
    // String sql = "";
    //
    // try {
    // db = AS400Database.getInstance();
    // sql = " SELECT " + "PCTCSE.CTLTHD2.CNID10," + "PCTCSE.CTORDRU.ORRF05," +
    // "PCTCSE.CTORDRU.LYND05,"
    // + "PCTCSE.CTORDRU.ORGV05," + "PCTCSE.CTVSIT4.VMID01," + "PCTCSE.CTMDVLI.MVVA47," +
    // "PCTCSE.CTVSIT4.ETAD01,"
    // + "PCTCSE.CTVSIT4.ETAT01," + "PCTCSE.CTVSIT4.RSIN01," + "PCTCSE.CTVSIT4.RSUT01," +
    // "PCTCSE.CTVSIT4.ATAD01,"
    // + "PCTCSE.CTVSIT4.ATAT01," + "PCTCSE.CTVSIT4.REGN01," + "PCTCSE.CTVSIT4.BZID01," +
    // "PCTCSE.CTVSIT4.BZFS01,"
    // + "PCTCSE.CTLTHD2.HDTP10," + "PCTCSE.CTHNDL9.CNBT03," + "PCTCSE.CTLTHD2.HDID10," +
    // "PCTCSE.CTVSIT4.VMYK01,"
    // + "PCTCSE.CTLOTTU.VUSL02," + "PCTCSE.CTLOTTU.VUSP02," + "PCTCSE.CTHNDL9.CNIS03," +
    // "PCTCSE.CTHNDL9.CNOR03,"
    // + "PCTCSE.CTHNDL9.ORRT03," + "PCTCSE.CTHNDL9.ORTE03," + "PCTCSE.CTHNDL9.CNIM03," +
    // "PCTCSE.CTHNDL9.CNUN03,"
    // + "PCTCSE.CTHNDL9.OT0103," + "PCTCSE.CTHNDL9.OW0103," + "PCTCSE.CTHNDL9.HDEO03," +
    // "PCTCSE.CTHNDL9.HDED03,"
    // + "PCTCSE.CTHNDL9.DM0103," + "PCTCSE.CTHNDL9.DM0203," + "PCTCSE.CTHNDL9.DM0303," +
    // "PCTCSE.CTHNDL9.DM0403,"
    // + "PCTCSE.CTHNDL9.DM0503," + "PCTCSE.CTHNDL9.DM0603," + "PCTCSE.CTHNDL9.DM0703," +
    // "PCTCSE.CTHNDL9.DM0803,"
    // + "PCTCSE.CTORDRU.ORTP05 " + "FROM " + "PCTCSE.CTHNDL9," + "PCTCSE.CTLTHD2," +
    // "PCTCSE.CTORDRU,"
    // + "PCTCSE.CTLOTTU," + "PCTCSE.CTVSIT4," + "PCTCSE.CTMDVLI " + "WHERE ( PCTCSE.CTHNDL9.CNID03
    // = "
    // + SQL.format(containerNo) + " ) " + "AND( PCTCSE.CTHNDL9.HDDT03 >= 0 ) "
    // + "AND( PCTCSE.CTHNDL9.HDTD03 >= 0 ) " + "AND( PCTCSE.CTHNDL9.HDTP03 = 'IN' ) "
    // + "AND( PCTCSE.CTHNDL9.HDFS03 = 'RGS' ) " + "AND( PCTCSE.CTLTHD2.LTTP10 in ('INF','ORD') ) "
    // + "AND( PCTCSE.CTLTHD2.HDID10 = PCTCSE.CTHNDL9.HDID03 ) " + "AND( PCTCSE.CTLTHD2.LHFS10 =
    // 'RGS' ) "
    // + "AND( PCTCSE.CTORDRU.ORID05 = PCTCSE.CTLTHD2.ORID10 ) "
    // + "AND( PCTCSE.CTORDRU.ORTP05 in ('MTI','BKG','CNA') ) "
    // + "AND( PCTCSE.CTLOTTU.LTID02 = PCTCSE.CTLTHD2.LTID10 ) " + "AND( PCTCSE.CTVSIT4.TRMK01 =
    // 'WPT1' ) "
    // + "AND( PCTCSE.CTVSIT4.RSUT01 = PCTCSE.CTLOTTU.VURS02 ) "
    // + "AND( PCTCSE.CTVSIT4.VMSR01 = PCTCSE.CTLOTTU.VUVS02 ) "
    // + "AND( PCTCSE.CTVSIT4.VMID01 = PCTCSE.CTLOTTU.VUVI02 ) " + "AND( PCTCSE.CTVSIT4.BZFS01 <>
    // 'CAN' ) "
    // + "AND( PCTCSE.CTMDVLI.MVID47 = 'VMDIDF' ) " + "AND( PCTCSE.CTMDVLI.TRMK47 = '' ) "
    // + "AND( PCTCSE.CTMDVLI.TALK47 = 'E' ) " + "AND( PCTCSE.CTMDVLI.MVV147 = PCTCSE.CTVSIT4.VMID01
    // )";
    // conn = db.getConnection();
    // stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    //
    // /**
    // * Change COSMOS Schema
    // */
    // sql = db.changeCosmosSchema(sql);
    // rs = stmt.executeQuery(sql);
    // /**
    // * Validate only 1 booking
    // */
    // int rowcount = 0;
    // if (rs.last()) {
    // rowcount = rs.getRow();
    // totalBooking = rowcount;
    // rs.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the
    // // first element
    // }
    //
    // if (rs.next()) {
    // bookingNoExist = true;
    // bookingNo = rs.getString("orrf05");
    // // shipCode = rs.getString("vmid01");
    // shipCode = rs.getString("LYND05");
    // // etad = new SimpleDateFormat("ddMMyyyy").parse(rs.getString("etad01"));
    // etad = new SimpleDateFormat("yyyyMMdd").parse(rs.getString("etad01"));
    // scn = StringUtils.trim(rs.getString("regn01"));
    // fOrE = rs.getString("cnbt03");
    // agentCode = rs.getString("orgv05");
    // vesselCode = rs.getString("vmid01");
    // vesselVoyage = rs.getString("rsin01");
    // vesselScn = StringUtils.trim(rs.getString("regn01"));
    // vesselName = StringUtils.trim(rs.getString("MVVA47"));
    // vesselVisitId = rs.getString("bzid01");
    // vesselStatus = rs.getString("bzfs01");
    // lineCode = rs.getString("lynd05");
    // vesselVoyageOut = rs.getString("rsut01");
    // vesselDateEta =
    // parseAS400Date(rs.getString("etad01") + TextString.padding(rs.getString("etat01"), 6, '0',
    // true));
    // // vesselDateAta = parseAS400Date(rs.getString("atad01") +
    // // TextString.padding(rs.getString("atat01"), 6, '0', true));
    //
    // if (fOrE.equals("F")) {
    // fullOrEmpty = true;
    // }
    // }
    //
    // this.checkInternal(conn);
    // this.checkOGA(conn);
    // } catch (Exception e) {
    // throw e;
    // } finally {
    // if (rs != null) {
    // rs.close();
    // }
    // if (stmt != null) {
    // stmt.close();
    // }
    // if (conn != null) {
    // conn.close();
    // }
    // }
    // log.error("-------------END checkContainerInAS400 -----------" + containerNo);
  }


  private void checkContainer() throws Exception {
    // log.error("-------------START checkContainer -----------" + shipCode);
    // Connection conn = null;
    // Statement stmt = null;
    // ResultSet rs = null;
    // String sql = "SELECT shp_storageperiod FROM scss_ship_code" + " WHERE shp_status='A'" + " AND
    // shp_shippingcode="
    // + SQL.format(shipCode);
    // try {
    // conn = SCSSDatabase.getInstance().getConnection();
    // stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    // rs = stmt.executeQuery(sql);
    // if (rs.next()) {
    // storagePeriod = rs.getInt("shp_storageperiod");
    // }
    // } catch (Exception e) {
    // throw e;
    // } finally {
    // if (rs != null) {
    // rs.close();
    // }
    // if (stmt != null) {
    // stmt.close();
    // }
    // if (conn != null) {
    // conn.close();
    // }
    // }
    // log.error("-------------END checkContainer -----------" + shipCode);
  }


  private java.util.Date parseAS400Date(String yyyyMMddHHmmss) throws Exception {
    SimpleDateFormat sdf_yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
    if (yyyyMMddHHmmss == null || "".equals(yyyyMMddHHmmss) || "0".equals(yyyyMMddHHmmss)) {
      return null;
    }
    return sdf_yyyyMMddHHmmss.parse(yyyyMMddHHmmss);
  }


  private void checkDg() throws Exception {
    // log.error("-------------START checkDg -----------" + containerNo);
    // Connection conn = null;
    // Statement stmt = null;
    // ResultSet rs = null;
    // String sql = "SELECT count(container_no) FROM SCSS_DG_DETAIL" + " WHERE SCN=" +
    // SQL.format(scn)
    // + " AND CONTAINER_NO=" + SQL.format(containerNo);
    // try {
    // conn = SCSSDatabase.getInstance().getConnection();
    // stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    // rs = stmt.executeQuery(sql);
    // if (rs.next() && rs.getInt(1) > 0) {
    // bypassDg = true;
    // }
    // } catch (Exception e) {
    // throw e;
    // } finally {
    // if (rs != null) {
    // rs.close();
    // }
    // if (stmt != null) {
    // stmt.close();
    // }
    // if (conn != null) {
    // conn.close();
    // }
    // }
    // log.error("-------------END checkDg -----------" + containerNo);
  }

  private void checkSCN() throws Exception {
    // log.error("-------------START checkSCN -----------" + containerNo);
    // Connection conn = null;
    // Statement stmt = null;
    // ResultSet rs = null;
    // String bypass = "";
    // String sql = "SELECT scn_seq, scn_bypass_eentry FROM scss_ship_scn" + " WHERE scn_scnno=" +
    // SQL.format(scn)
    // + " AND scn_containerno=" + SQL.format(containerNo);
    // try {
    // conn = SCSSDatabase.getInstance().getConnection();
    // stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    // rs = stmt.executeQuery(sql);
    // if (rs.next()) {
    // registeredInEarlyEntry = true;
    // seq = rs.getString("scn_seq");
    // bypass = rs.getString("scn_bypass_eentry");
    // }
    // if (StringUtils.isNotBlank(bypass) && StringUtils.equals(bypass, "Y")) {
    // this.bypassEEntry = true;
    // }
    // } catch (Exception e) {
    // throw e;
    // } finally {
    // if (rs != null) {
    // rs.close();
    // }
    // if (stmt != null) {
    // stmt.close();
    // }
    // if (conn != null) {
    // conn.close();
    // }
    // }
    // log.error("-------------END checkSCN -----------" + containerNo);
  }

  private boolean registeredInEarlyEntry = false;

  public boolean isRegisteredInEarlyEntry() {
    return registeredInEarlyEntry;
  }

  private void checkInternal(Connection conn) throws Exception {
    // AS400Database db = null;
    // Statement stmt = null;
    // ResultSet rs = null;
    //
    // String sql = "";
    //
    // try {
    // db = AS400Database.getInstance();
    //
    // sql = "SELECT inop30" + " FROM PCTCSE.ctinst7" + " WHERE cnid30=" + SQL.format(containerNo) +
    // " AND (infs30='RGS'"
    // + " OR infs30='ACT')" + " AND (intp30='DNA' or intp30='BLK')" + " ORDER BY orid30 DESC";
    //
    // stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    //
    // /**
    // * Change COSMOS Schema
    // */
    // sql = db.changeCosmosSchema(sql);
    //
    // rs = stmt.executeQuery(sql);
    //
    // if (rs.next()) {
    // internalBlock = true;
    // internalBlockDesc = rs.getString("inop30");
    // }
    // } catch (Exception e) {
    // throw e;
    // } finally {
    // if (rs != null) {
    // rs.close();
    // }
    // if (stmt != null) {
    // stmt.close();
    // }
    // }
  }

  private void checkOGA(Connection conn) throws Exception {
    // AS400Database db = null;
    // Statement stmt = null;
    // ResultSet rs = null;
    // String sql = "";
    //
    // try {
    // db = AS400Database.getInstance();
    //
    // sql = "SELECT cnid80" + ", rlgb80" + ", bldt80" + ", bltd80" + ", dgfs80" + " FROM
    // PCTCSE.ctcubl"
    // + " WHERE cnid80=" + SQL.format(containerNo) + " AND cufs80='RGS'" + " AND rldt80=0" + " AND
    // rltd80=0"
    // + " AND trmk80='WPT1'";
    //
    // stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    //
    // /**
    // * Change COSMOS Schema
    // */
    // sql = db.changeCosmosSchema(sql);
    // rs = stmt.executeQuery(sql);
    //
    // if (rs.next()) {
    // OGABlock = true;
    // }
    // } catch (Exception e) {
    // throw e;
    // } finally {
    // if (rs != null) {
    // rs.close();
    // }
    // if (stmt != null) {
    // stmt.close();
    // }
    // }
  }


  public boolean isAllowIn(ExportContainer exportContainer) throws Exception {
    final Date now = new Date();
    boolean ret = false;

    // if no record found in the shp_ship_code table
    if (exportContainer.getStoragePeriod() == -1) {
      return true;
    }

    long earlyEnrtyDate = 0;
    exportContainer.setEtad(DateUtil.addDate(exportContainer.getEtad(), -1));
    earlyEnrtyDate = DateUtil.getDaysBetween2Dates(now, exportContainer.getEtad());

    // Before Eta Date
    if (earlyEnrtyDate > 1) {
      if (earlyEnrtyDate <= exportContainer.getStoragePeriod()) {
        ret = true;
      } else {
        /**
         * Check if it is allowed for early entry
         */
        if (!exportContainer.getSeq().equals("")) {
          /**
           * check if container coming during early entry window.
           */
          if (inEarlyEntryWindow()) {
            exportContainer.setEarlyEntry(true);
            ret = true;
          } else {
            exportContainer.setEarlyEntry(true);
            if (exportContainer.isBypassEEntry()) {
              ret = true;
            } else {
              ret = false;
            }
          }
        }
      }
    } else {
      // After ETA
      ret = true;
    }

    return ret;
  }

  public boolean inEarlyEntryWindow() throws Exception {
    // boolean isInWindow = false;
    // Connection conn = null;
    // Statement stmt = null;
    // ResultSet rs = null;
    // String sql = "SELECT GLOBAL_STRING, PARAM_VALUE1 FROM WDC_GLOBAL_SETTINGS" + " WHERE
    // GLOBAL_CODE in ('EE_TIME')";
    // try {
    // conn = SCSSDatabase.getInstance().getConnection();
    // stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    // rs = stmt.executeQuery(sql);
    // while (rs.next()) {
    // startEarlyEntry = rs.getString("GLOBAL_STRING");
    // endEarlyEntry = rs.getString("PARAM_VALUE1");
    // break;
    // }
    // } catch (Exception e) {
    // throw e;
    // } finally {
    // if (rs != null) {
    // rs.close();
    // }
    // if (stmt != null) {
    // stmt.close();
    // }
    // if (conn != null) {
    // conn.close();
    // }
    // }
    // final Date now = new Date();
    // final SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
    // final SimpleDateFormat dateTime = new SimpleDateFormat("dd/MM/yyyy h:mm a");
    // String dateNow = date.format(now);
    //
    // String startFullDate = dateNow + " " + startEarlyEntry;
    // String endFullDate = dateNow + " " + endEarlyEntry;
    // Date strtFullDate = dateTime.parse(startFullDate);
    // Date edFullDate = dateTime.parse(endFullDate);
    //
    //
    // if (strtFullDate.after(edFullDate)) {
    // if (StringUtils.contains(dateTime.format(now), "AM")) {
    // strtFullDate = DateUtil.addDate(strtFullDate, -1);
    // } else {
    // edFullDate = DateUtil.addDate(edFullDate, 1);
    // }
    // }
    //
    // if (now.after(strtFullDate) && now.before(edFullDate)) {
    // isInWindow = true;
    // }
    // return isInWindow;
    return false;
  }


  public static void main__bak(String[] args) {
    int replan = -4;
    int overClosing = -10;
    Calendar today = Calendar.getInstance();
    today.set(2008, 4, 13, 21, 58, 0);
    // System.out.println("today : " + today.getTime());
    Calendar etad1 = Calendar.getInstance();
    etad1.set(2008, 4, 13, 23, 30, 0);
    // System.out.println("etad1 : " + etad1.getTime());
    Calendar etad2 = Calendar.getInstance();
    etad2.set(2008, 4, 13, 23, 30, 0);
    // System.out.println("etad2 : " + etad2.getTime());

    etad1.add(Calendar.HOUR, replan);
    // System.out.println("replan : " + etad1.getTime());
    etad2.add(Calendar.HOUR, overClosing);
    // System.out.println("overClosing : " + etad2.getTime());

    if ((today.after(etad1))) {
      // System.out.println("REPLAN SSR");
    } else if ((today.after(etad2))) {
      // System.out.println("OVERCLOSING SSR");
    } else {
      // System.out.println("OK");
    }
    Calendar today3 = Calendar.getInstance();
    // System.out.println(today3.get(Calendar.WEEK_OF_YEAR));
    today3.set(Calendar.WEEK_OF_YEAR, 34);
    // System.out.println(today3.getTime());
  }



  public GateInForm selectContainerNoInfo(GateInForm f) {
    // TODO Auto-generated method stub
    return f;
  }


}
