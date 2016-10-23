package com.privasia.scss.scancard.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.privasia.scss.core.dto.ScssClientDto;
import com.privasia.scss.core.model.CardUsage;

@Service("cardUsageService")
@Transactional()
public class CardUsageService {

  private static final String STATUS_LOCK = "L";
  private static final String STATUS_COMPLETE = "C";
  private static final String STATUS_RESET = "R";

  private static Logger logger = Logger.getLogger(CardUsage.class.getName());


  public String selectClientId(String webIP) {
    String ret = "";

    // String sql = "SELECT cli_clientid_seq FROM scss_client "
    // + "WHERE cli_web_ipaddr = " + SQL.format(webIP);

    return ret;
  }

  public String getCardID(String clientID) {
    String ret = "";

    // String sql = "SELECT crd_cardid "
    // + "FROM scss_card_usage "
    // + "WHERE cli_clientid = " + SQL.format(clientID)
    // + "AND cug_status = " + SQL.format(STATUS_LOCK)
    // + "AND (cug_time_end IS NULL OR cug_time_end = '')";

    return ret;
  }

  public String[] lookupGateInfo(String webIP) {
    String[] ret = new String[] {};
    String clientId = selectClientId(webIP);

    // String sql = "SELECT cug_id_seq, crd_cardid, TO_CHAR(cug_time_start, 'DD-MM-YYYY HH24:MI:SS')
    // AS cug_time_start, cug_mc_flag, exp_weight_bridge "
    // + "FROM scss_card_usage "
    // + "WHERE cli_clientid = " + SQL.format(clientId)
    // + "AND cug_status = " + SQL.format(STATUS_LOCK)
    // + "AND (cug_time_end IS NULL OR cug_time_end = '')";

    // String cardId = rs.getString("crd_cardid");
    // String timeInOut = rs.getString("cug_time_start");
    // String mcFlag = rs.getString("cug_mc_flag");
    // mcFlag = mcFlag.equals(Card.MASTER_CARD) ? mcFlag = "true" : "false";
    // String weightBridge = rs.getString("exp_weight_bridge");
    // String cugIdSeq = rs.getString("cug_id_seq");
    // ret = new String[] {cardId, clientId, timeInOut, mcFlag, weightBridge, cugIdSeq};

    return ret;
  }

  public boolean saveStartCardUsage(String cardId, String clientId, boolean isMC, String weight) {
    boolean ret = false;

    // String cardType = Card.NORMAL_CARD;
    // if (isMC) {
    // cardType = Card.MASTER_CARD;
    // }

    // //if the current client is in used
    // //DO NOT insert
    // String[] columns = new String[] {"cli_clientid", "cug_status"};
    // String[] data = new String[] {clientId, STATUS_LOCK};
    // boolean isLock = CheckDB.isRecordExist(columns, "scss_card_usage", data);
    // //boolean isLock = false;
    //
    // String sql1 = "INSERT INTO scss_card_usage ("
    // + "cug_id_seq, crd_cardid, cli_clientid, cug_time_start, cug_status, cug_mc_flag,
    // exp_weight_bridge"
    // + ") VALUES ("
    // //+ "cug_id_seq.NEXTVAL"
    // + Integer.parseInt(SQL.NEXT_VALUE("cug_id_seq"))
    // + ", " + SQL.format(cardId)
    // + ", " + SQL.format(clientId)
    // + ", " + SQL.TO_DATETIME()
    // + ", " + SQL.format(STATUS_LOCK)
    // + ", " + SQL.format(cardType)
    // + ", " + SQL.format(weight)
    // + ")";
    //
    // String sql2 = "UPDATE scss_card SET crd_isused = 'Y'"
    // + " WHERE crd_cardid_seq=" + SQL.format(cardId);
    //
    // ret = true;

    return ret;
  }

  public void saveEndCardUsage(String clientId) {
    // String sql = "UPDATE scss_card_usage SET"
    // + " cug_time_end = " + SQL.TO_DATETIME()
    // + ", cug_status = " + SQL.format(STATUS_COMPLETE)
    // + " WHERE (cug_time_end IS NULL OR cug_time_end = '')"
    // + " AND cug_status = " + SQL.format(STATUS_LOCK)
    // + " AND cli_clientid = " + SQL.format(clientId);
  }

  // to reset the status
  public void reset(String clientId) {
    // String sql = "UPDATE scss_card_usage SET"
    // + " cug_status = " + SQL.format(STATUS_RESET)
    // + ", cug_time_end = " + SQL.TO_DATETIME()
    // + " WHERE (cug_time_end IS NULL OR cug_time_end = '')"
    // + " AND cug_status = " + SQL.format(STATUS_LOCK)
    // + " AND cli_clientid = " + SQL.format(clientId);
  }

  // to reset the status
  public int cardResetByKiosk(String kioskID) {
    // String sql = "UPDATE scss_card_usage SET" + " cug_status = " + SQL.format(STATUS_RESET) + ",
    // cug_time_end = "
    // + SQL.TO_DATETIME() + " WHERE (cug_time_end IS NULL OR cug_time_end = '')" + " AND cug_status
    // = "
    // + SQL.format(STATUS_LOCK) + " AND cli_clientid = " + SQL.format(kioskID);
    // int rowcount = db.executeUpdate(sql);
    return 0;
  }

  public boolean doCheckCardStatus(String cardIdSeq, Date date) {
    boolean isActive = false;
    // date = DateUtil.addDate(date, -1);

    // String sql = "SELECT CRD_DATETHRU FROM SCSS_CARD " + " WHERE CRD_CARDID_SEQ = " +
    // SQL.format(cardIdSeq)
    // + " AND CRD_CARDSTATUS = " + SQL.format(Card.ACTIVE);


    // if (rs != null && rs.next()) {
    // if (rs.getDate("CRD_DATETHRU").after(date)) {
    // isActive = true;
    // } else {
    // /*
    // * update card status to expired
    // */
    // isActive = false;
    // String sqlUpdate = "UPDATE SCSS_CARD SET CRD_CARDSTATUS = " + SQL.format(Card.EXPIRED)
    // + " WHERE CRD_CARDID_SEQ =" + SQL.format(cardIdSeq);
    // stmt.executeUpdate(sqlUpdate);
    // conn.commit();
    // }
    // }

    return isActive;
  }

  public ScssClientDto getCliType(String clientId) {
    ScssClientDto scssClientDto = null;
    // String sql =
    // "SELECT CLI_TYPE, CLI_DESCRIPTION FROM scss_client " + "WHERE CLI_CLIENTID_SEQ = " +
    // SQL.format(clientId);
    //
    // String sqlGateType = "SELECT GATE_TYPE FROM SCSS_CLIENT_GATE_TYPE WHERE cli_clientid_seq=" +
    // SQL.format(clientId);


    // conn = SCSSDatabase.getInstance().getConnection();
    // stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    // rs = stmt.executeQuery(sql);
    //
    // if (rs != null && rs.next()) {
    // scssClientDto = new ScssClientDto();
    // scssClientDto.setCliType(rs.getString("CLI_TYPE"));
    // scssClientDto.setCliDescription(rs.getString("CLI_DESCRIPTION"));
    //
    // // Get the Gate Type
    // stmt2 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    // rs2 = stmt.executeQuery(sqlGateType);
    //
    // List<String> gateTypes = new ArrayList<String>();
    // while (rs2.next()) {
    // gateTypes.add(rs2.getString("GATE_TYPE"));
    // }
    //
    // scssClientDto.setGateType(gateTypes);
    // }

    return scssClientDto;
  }



}
