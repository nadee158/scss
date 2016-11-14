package com.privasia.scss.scancard.service;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.GateInfo;
import com.privasia.scss.common.dto.ScssClientDto;
import com.privasia.scss.common.enums.CardStatus;
import com.privasia.scss.common.enums.CardUsageStatus;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.CardUsage;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.repository.CardUsageRepository;

/**
 * @author nadee158
 *
 */
@Service("cardUsageService")
@Transactional()
public class CardUsageService {

  private static final String STATUS_LOCK = "L";
  private static final String STATUS_COMPLETE = "C";
  private static final String STATUS_RESET = "R";

  private static Logger logger = Logger.getLogger(CardUsage.class.getName());

  @Autowired
  private CardUsageRepository cardUsageRepository;

  @Autowired
  private CardRepository cardRepository;


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

  /**
   * Method's return type was modified to GateInfo dto object - older version had a string array
   * Returned information is the same
   * 
   * @author nadee158
   * @param webIP
   * @return
   */
  public GateInfo lookupGateInfo(String webIP) {
    // ret = new String[] {cardId, clientId, timeInOut, mcFlag, weightBridge, cugIdSeq};

    String clientId = selectClientId(webIP);

    CardUsage cardUsage = cardUsageRepository.findByClient_ClientIDAndUsageStatusAndDateTimeUpdateIsNull(clientId,
        CardUsageStatus.STATUS_LOCK);

    if (!(cardUsage == null)) {
      return cardUsage.constructGateInfo();
    }

    return null;
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

  /**
   * Method modified
   * 
   * @author nadee158
   * @param cardIdSeq
   * @param date
   * @return
   */
  public boolean doCheckCardStatus(String cardIdSeq, LocalDateTime date) {
    boolean isActive = false;
    date = date.minusDays(1);

    // String sql = "SELECT CRD_DATETHRU FROM SCSS_CARD " + " WHERE CRD_CARDID_SEQ = " +
    // SQL.format(cardIdSeq)
    // + " AND CRD_CARDSTATUS = " + SQL.format(Card.ACTIVE);
    Card card = cardRepository.findByCardIDAndCardStatus(cardIdSeq, CardStatus.ACTIVE);
    if (!(card == null)) {
      if (card.getDateThrough().isAfter(date)) {
        isActive = true;
      } else {
        /** update card status to expired **/
        isActive = false;
        card.setCardStatus(CardStatus.EXPIRED);
        // String sqlUpdate = "UPDATE SCSS_CARD SET CRD_CARDSTATUS = " + SQL.format(Card.EXPIRED)
        // + " WHERE CRD_CARDID_SEQ =" + SQL.format(cardIdSeq);
        cardRepository.save(card);
      }
    }

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
