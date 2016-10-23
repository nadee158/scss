package com.privasia.scss.kioskbooth.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import com.privasia.scss.core.dto.ClientInfo;
import com.privasia.scss.core.dto.KioskBoothRightInfo;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.KioskBoothRights;
import com.privasia.scss.core.model.KioskBoothRightsPK;
import com.privasia.scss.core.repository.KioskBoothRightsRepository;
import com.privasia.scss.core.util.constant.DBTransactionStatus;
import com.privasia.scss.core.util.constant.TransactionStatus;

@Service("kioskBoothService")
public class KioskBoothService {
  
  @Autowired
  private KioskBoothRightsRepository kioskBoothRightsRepository;
  
  public List<ClientInfo> getKioskInfoByBooth(String boothID){
    List<ClientInfo> clientInfoList = new ArrayList<ClientInfo>();
    Client clientBoothID =new Client();
    clientBoothID.setClientID(Long.parseLong(boothID));
    List<KioskBoothRights> KioskBoothRightList=kioskBoothRightsRepository.findByKioskBoothRightsIDBoothID(clientBoothID);
    if(!(KioskBoothRightList==null || KioskBoothRightList.isEmpty())){
      for (KioskBoothRights kioskBoothRights : KioskBoothRightList) {
        
        KioskBoothRightsPK kioskBoothRightsID = kioskBoothRights.getKioskBoothRightsID();
        Client clBoothID = kioskBoothRightsID.getBoothID();
        
        Client kioskID = kioskBoothRightsID.getKioskID();
        
        ClientInfo clientInfo = new ClientInfo();
        
//        clientInfo.setClientIdSeq(rs.getLong("cli_clientid_seq"));
//        clientInfo.setWebIPAddress(rs.getString("cli_web_ipaddr"));
//        clientInfo.setClientDescription(rs.getString("cli_description"));
//        clientInfo.setClientStaus(rs.getString("cli_status"));
//        clientInfo.setClientType(rs.getString("cli_type"));
//        clientInfo.setUnitNo(rs.getString("cli_unitno"));
//        clientInfo.setCsmControl(rs.getString("cli_csm_ctrl"));
//        clientInfo.setCosmosPortNumber(rs.getString("cosmos_port_no"));
//        clientInfo.setSortSeq(rs.getString("sort_seq"));
//        clientInfo.setCameraServerIPAddress(rs.getString("camera_server_ipaddr"));
//        clientInfo.setCameraServerPort(rs.getString("camera_server_port"));
//        clientInfo.setDisplayScreenId(rs.getString("display_screen_id"));
//        clientInfo.setKioskLockStatus(rs.getString("kiosk_lock_status"));
//        clientInfo.setLaneNO(rs.getString("lane_no"));
//        clientInfo.setFtpIP(rs.getString("ftp_ip"));
//        clientInfo.setFtpPort(rs.getString("ftp_port"));
//        clientInfo.setFtpProtocal(rs.getString("ftp_protocal"));
//        clientInfo.setFtpUserName(rs.getString("ftp_username"));
//        clientInfo.setFtpPassword(rs.getString("ftp_password"));
//        clientInfo.setFtpDirectory(rs.getString("ftp_directory"));
//        clientInfo.setWithCameraImage(rs.getString("with_cma_image"));
        
        clientInfoList.add(clientInfo);
        
        
      }
    }
    return clientInfoList;
  }
  
  @Transactional(value=TxType.REQUIRED)
  public int updateKioskBoothInfo(KioskBoothRightInfo kioskBoothRightInfo){
    
//    try {
//           
//      if(StringUtils.equals(kioskBoothRightInfo.getKioskLockStatus(), DBTransactionStatus.LOCK.getDBTransactionStatus())){
//             
//             String lockSql = " SELECT COUNT(1) FROM SCSS_KIOSK_BOOTH_RIGHTS" +
//                        " WHERE KIOSK_ID = ? AND KIOSK_LOCK_STATUS = ? AND BOOTH_ID <> ?";
//             stmt = conn.prepareStatement(lockSql);
//             stmt.setLong(1, Long.parseLong(kioskBoothRightInfo.getKioskID()));
//             stmt.setString(2, TransactionStatus.LOCK);
//             stmt.setLong(3, Long.parseLong(kioskBoothRightInfo.getBoothID()));
//             ResultSet results = stmt.executeQuery();
//             if (results != null && results.next()) {
//                long count = results.getLong(1);
//                if (count > 0) {
//                    islock = true;
//                }
//             }
//             gateLogger.info("islock : "+islock);
//             conn.commit();
//       }
//      
//      
//      if(!islock){
//          
//         StringBuilder updateBuilder = new StringBuilder();
//        
//         updateBuilder.append("UPDATE SCSS_KIOSK_BOOTH_RIGHTS "+ 
//                                "SET CARDNUMBER = ?, CT1CANPICKUP = ?, CT1 = ?, CT1L = ?, CT1S = ?, CT1ISO = ?,"
//                                + " CT1LOC = ?, CT1STATUS = ?, CT1FE = ?, CT1_OTHERS = ?, CT1_REJECT_REMARK = ?, CT1_CUSTOM_CHECK = ?, "
//                                + " CT2CANPICKUP = ?, CT2 = ?, CT2L = ?, CT2S = ?, CT2ISO = ?, CT2LOC = ? , CT2STATUS = ?, CT2FE = ?,"
//                                + " CT2_OTHERS = ?, CT2_REJECT_REMARK = ?, CT2_CUSTOM_CHECK = ?,"
//                                + " CT3CANPICKUP = ?, CT3 = ?, CT3L = ?, CT3S = ?, CT3ISO = ?, CT3LOC = ?, CT3STATUS = ?, CT3FE = ?,"
//                                + " CT3_OTHERS = ?, CT3_REJECT_REMARK = ?, CT3_CUSTOM_CHECK = ?,"
//                                + " CT4CANPICKUP = ?, CT4 = ?, CT4L = ?, CT4S = ?, CT4ISO = ?, CT4LOC = ?, CT4STATUS = ?, "
//                                + " CT4FE = ?, CT4_OTHERS = ?, CT4_REJECT_REMARK = ?, CT4_CUSTOM_CHECK = ?, "
//                                + " CARD_SCAN_TIME = ?, KIOSK_SELECT_TIME = ?, "
//                                + " DISPLAY_SCREEN_ID = ?, KIOSK_LOCK_STATUS = ?, TRANSID = ?, DRIVERNAME = ?, PMHEAD = ?, TRUCKCO = ?, DRIVER_IC = ?, "
//                                + " PLATE_NO = ?, TRANSACTION_TYPE = ?, REVISE_HEAD_NO = ?, REVISE_HEAD_NO_REMARK = ?,  "
//                                + " RE_TAKE_PHOTO = ?, TRX_COMPLETE_TIME = ?, LOCK_USER_ID = ?, LOCK_USER_NAME = ?,  REFER_REASON_01 = ?, REFER_REASON_02 = ?"
//                                + " WHERE KIOSK_ID = ?") ;
//        
//         if(StringUtils.isNotEmpty(kioskBoothRightInfo.getBoothID())){
//             updateBuilder.append(" AND BOOTH_ID = ? ");
//         }
//        
//         if(StringUtils.isNotEmpty(kioskBoothRightInfo.getCardNumber())){
//             updateBuilder.append(" AND CARDNUMBER = ? ");
//         }
//
//          
//          DateFormat format = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss", Locale.ENGLISH);
//          stmt = conn.prepareStatement(updateBuilder.toString());
//          
//          if(StringUtils.isEmpty(kioskBoothRightInfo.getNewCardNumber())){
//              stmt.setString(1, kioskBoothRightInfo.getNewCardNumber());
//          }else{
//              stmt.setLong(1, Long.parseLong(kioskBoothRightInfo.getNewCardNumber()));
//          }
//          
//          stmt.setString(2, kioskBoothRightInfo.getCt1CanPickup());
//          stmt.setString(3, kioskBoothRightInfo.getCt1());
//          stmt.setString(4, kioskBoothRightInfo.getCt1L());
//          stmt.setString(5, kioskBoothRightInfo.getCt1s());
//          stmt.setString(6, kioskBoothRightInfo.getCt1ISO());
//          stmt.setString(7, kioskBoothRightInfo.getCt1Loc());
//          stmt.setString(8, kioskBoothRightInfo.getCt1Status());
//          stmt.setString(9, kioskBoothRightInfo.getCt1fe());
//          stmt.setString(10, kioskBoothRightInfo.getCt1Others());
//          stmt.setString(11, kioskBoothRightInfo.getCt1RejectRemarks());
//          stmt.setString(12, kioskBoothRightInfo.getCt1CustomCheck());
//          stmt.setString(13, kioskBoothRightInfo.getCt2CanPickup());
//          stmt.setString(14, kioskBoothRightInfo.getCt2());
//          stmt.setString(15, kioskBoothRightInfo.getCt2L());
//          stmt.setString(16, kioskBoothRightInfo.getCt2s());
//          stmt.setString(17, kioskBoothRightInfo.getCt2ISO());
//          stmt.setString(18, kioskBoothRightInfo.getCt2Loc());
//          stmt.setString(19, kioskBoothRightInfo.getCt2Status());
//          stmt.setString(20, kioskBoothRightInfo.getCt2fe());
//          stmt.setString(21, kioskBoothRightInfo.getCt2Others());
//          stmt.setString(22, kioskBoothRightInfo.getCt2RejectRemarks());
//          stmt.setString(23, kioskBoothRightInfo.getCt2CustomCheck());
//          stmt.setString(24, kioskBoothRightInfo.getCt3CanPickup());
//          stmt.setString(25, kioskBoothRightInfo.getCt3());
//          stmt.setString(26, kioskBoothRightInfo.getCt3L());
//          stmt.setString(27, kioskBoothRightInfo.getCt3s());
//          stmt.setString(28, kioskBoothRightInfo.getCt3ISO());
//          stmt.setString(29, kioskBoothRightInfo.getCt3Loc());
//          stmt.setString(30, kioskBoothRightInfo.getCt3Status());
//          stmt.setString(31, kioskBoothRightInfo.getCt3fe());
//          stmt.setString(32, kioskBoothRightInfo.getCt3Others());
//          stmt.setString(33, kioskBoothRightInfo.getCt3RejectRemarks());
//          stmt.setString(34, kioskBoothRightInfo.getCt3CustomCheck());
//          stmt.setString(35, kioskBoothRightInfo.getCt4CanPickup());
//          stmt.setString(36, kioskBoothRightInfo.getCt4());
//          stmt.setString(37, kioskBoothRightInfo.getCt4L());
//          stmt.setString(38, kioskBoothRightInfo.getCt4s());
//          stmt.setString(39, kioskBoothRightInfo.getCt4ISO());
//          stmt.setString(40, kioskBoothRightInfo.getCt4Loc());
//          stmt.setString(41, kioskBoothRightInfo.getCt4Status());
//          stmt.setString(42, kioskBoothRightInfo.getCt4fe());
//          stmt.setString(43, kioskBoothRightInfo.getCt4Others());
//          stmt.setString(44, kioskBoothRightInfo.getCt4RejectRemarks());
//          stmt.setString(45, kioskBoothRightInfo.getCt4CustomCheck());
//          
//          if(StringUtils.isEmpty(kioskBoothRightInfo.getCardScanTime())){
//              stmt.setString(46, kioskBoothRightInfo.getCardScanTime());
//          }else{
//              stmt.setTimestamp(46, new java.sql.Timestamp(format.parse(kioskBoothRightInfo.getCardScanTime()).getTime()));
//          }
//          
//          if(StringUtils.isEmpty(kioskBoothRightInfo.getKioskSelectTime())){
//              stmt.setString(47, kioskBoothRightInfo.getKioskSelectTime());
//          }else{
//              stmt.setTimestamp(47, new java.sql.Timestamp(format.parse(kioskBoothRightInfo.getKioskSelectTime()).getTime()));
//          }
//          
//          if(StringUtils.isNotEmpty(kioskBoothRightInfo.getDisplayScreenID())){
//              stmt.setInt(48, Integer.parseInt(kioskBoothRightInfo.getDisplayScreenID()));
//          }else{
//              stmt.setString(48, kioskBoothRightInfo.getDisplayScreenID());
//          }
//          
//          stmt.setString(49, kioskBoothRightInfo.getKioskLockStatus());
//          
//          if(StringUtils.isEmpty(kioskBoothRightInfo.getTransID())){
//              stmt.setString(50, kioskBoothRightInfo.getTransID());
//          }else{
//              stmt.setString(50, kioskBoothRightInfo.getTransID());
//          }
//          
//          stmt.setString(51, kioskBoothRightInfo.getDriverName());
//          stmt.setString(52, kioskBoothRightInfo.getPmHead());
//          stmt.setString(53, kioskBoothRightInfo.getTruckCO());
//          stmt.setString(54, kioskBoothRightInfo.getDriverIC());
//          stmt.setString(55, kioskBoothRightInfo.getPlateNo());
//          stmt.setString(56, kioskBoothRightInfo.getTransactionType());
//          
//          stmt.setString(57, kioskBoothRightInfo.getReviseHeadNo());
//          
//          stmt.setString(58, kioskBoothRightInfo.getReviseHeadNoRemark());
//          
//          stmt.setString(59, kioskBoothRightInfo.getReTakePhoto());
//          
//          if(StringUtils.isEmpty(kioskBoothRightInfo.getTrxCompleateTime())){
//              stmt.setString(60, kioskBoothRightInfo.getTrxCompleateTime());
//          }else{
//              stmt.setTimestamp(60, new java.sql.Timestamp(format.parse(kioskBoothRightInfo.getTrxCompleateTime()).getTime()));
//          }
//          
//          stmt.setString(61, kioskBoothRightInfo.getLockUserID());
//          
//          stmt.setString(62, kioskBoothRightInfo.getLockUserName());
//          
//          stmt.setString(63, kioskBoothRightInfo.getReferReasonList01());
//          
//          stmt.setString(64, kioskBoothRightInfo.getReferReasonList02());
//          
//          stmt.setLong(65, Long.parseLong(kioskBoothRightInfo.getKioskID()));
//          
//          if(StringUtils.isNotEmpty(kioskBoothRightInfo.getBoothID())){
//              stmt.setLong(66, Long.parseLong(kioskBoothRightInfo.getBoothID()));
//              if(StringUtils.isNotEmpty(kioskBoothRightInfo.getCardNumber())){
//                  stmt.setLong(67, Long.parseLong(kioskBoothRightInfo.getCardNumber()));
//                }
//            }else{
//                if(StringUtils.isNotEmpty(kioskBoothRightInfo.getCardNumber())){
//                      stmt.setLong(66, Long.parseLong(kioskBoothRightInfo.getCardNumber()));
//                }
//            }
//          
//          rs = stmt.executeUpdate();
//          gateLogger.info("execute results : "+rs);
//          
//          if(kioskBoothRightInfo.getKioskLockStatus().equals(TransactionStatus.LOCK) && StringUtils.isNotEmpty(kioskBoothRightInfo.getBoothID())){
//              
//              String updateQuery = "UPDATE SCSS_KIOSK_BOOTH_RIGHTS "+ 
//                        "SET CARDNUMBER = ?, CT1CANPICKUP = ?, CT1 = ?, CT1L = ?, CT1S = ?, CT1ISO = ?,"
//                        + " CT1LOC = ?, CT1STATUS = ?, CT1FE = ?, CT1_OTHERS = ?, CT1_REJECT_REMARK = ?, CT1_CUSTOM_CHECK = ?,"
//                        + " CT2CANPICKUP = ?, CT2 = ?, CT2L = ?, CT2S = ?, CT2ISO = ?, CT2LOC = ? , CT2STATUS = ?, CT2FE = ?,"
//                        + " CT2_OTHERS = ?, CT2_REJECT_REMARK = ?, CT2_CUSTOM_CHECK = ?,"
//                        + " CT3CANPICKUP = ?, CT3 = ?, CT3L = ?, CT3S = ?, CT3ISO = ?, CT3LOC = ?, CT3STATUS = ?, CT3FE = ?, "
//                        + " CT3_OTHERS = ?, CT3_REJECT_REMARK = ?, CT3_CUSTOM_CHECK = ?,"
//                        + " CT4CANPICKUP = ?, CT4 = ?, CT4L = ?, CT4S = ?, CT4ISO = ?, CT4LOC = ?, CT4STATUS = ?, CT4FE = ?, "
//                        + " CT4_OTHERS = ?, CT4_REJECT_REMARK = ?, CT4_CUSTOM_CHECK = ?, "
//                        + " CARD_SCAN_TIME = ?, KIOSK_SELECT_TIME = ?, "
//                        + " DISPLAY_SCREEN_ID = ?, KIOSK_LOCK_STATUS = ?, TRANSID = ?, DRIVERNAME = ?, PMHEAD = ?, TRUCKCO = ?, DRIVER_IC = ?, "
//                        + " PLATE_NO = ?, TRANSACTION_TYPE = ?, REVISE_HEAD_NO = ?, REVISE_HEAD_NO_REMARK = ?, RE_TAKE_PHOTO = ?, TRX_COMPLETE_TIME = ?, "
//                        + " LOCK_USER_ID = ?, LOCK_USER_NAME = ?,  REFER_REASON_01 = ?, REFER_REASON_02 = ? WHERE KIOSK_ID = ?  AND BOOTH_ID != ? " ;
//              
//              stmt = conn.prepareStatement(updateQuery);
//              
//              stmt.setString(1, null);
//              stmt.setString(2, null);
//              stmt.setString(3, null);
//              stmt.setString(4, null);
//              stmt.setString(5, null);
//              stmt.setString(6, null);
//              stmt.setString(7, null);
//              stmt.setString(8, null);
//              stmt.setString(9, null);
//              stmt.setString(10, null);
//              stmt.setString(11, null);
//              stmt.setString(12, null);
//              stmt.setString(13, null);
//              stmt.setString(14, null);
//              stmt.setString(15, null);
//              stmt.setString(16, null);
//              stmt.setString(17, null);
//              stmt.setString(18, null);
//              stmt.setString(19, null);
//              stmt.setString(20, null);
//              stmt.setString(21, null);
//              stmt.setString(22, null);
//              stmt.setString(23, null);
//              stmt.setString(24, null);
//              stmt.setString(25, null);
//              stmt.setString(26, null);
//              stmt.setString(27, null);
//              stmt.setString(28, null);
//              stmt.setString(29, null);
//              stmt.setString(30, null);
//              stmt.setString(31, null);
//              stmt.setString(32, null);
//              stmt.setString(33, null);
//              stmt.setString(34, null);
//              stmt.setString(35, null);
//              stmt.setString(36, null);
//              stmt.setString(37, null);
//              stmt.setString(38, null);
//              stmt.setString(39, null);
//              stmt.setString(40, null);
//              stmt.setString(41, null);
//              stmt.setString(42, null);
//              stmt.setString(43, null);
//              stmt.setString(44, null);
//              stmt.setString(45, null);
//              stmt.setString(46, null);
//              stmt.setString(47, null);
//              stmt.setString(48, null);
//              stmt.setString(49, null);
//              stmt.setString(50, null);
//              stmt.setString(51, null);
//              stmt.setString(52, null);
//              stmt.setString(53, null);
//              stmt.setString(54, null);
//              stmt.setString(55, null);
//              stmt.setString(56, null);
//              stmt.setString(57, null);
//              stmt.setString(58, null);
//              stmt.setString(59, null);
//              stmt.setString(60, null);
//              stmt.setString(61, null);
//              stmt.setString(62, null);
//              stmt.setString(63, null);
//              stmt.setString(64, null);
//              stmt.setLong(65, Long.parseLong(kioskBoothRightInfo.getKioskID()));
//              stmt.setLong(66, Long.parseLong(kioskBoothRightInfo.getBoothID()));
//                 
//              stmt.executeUpdate();
//              
//          }
//          
//          conn.commit();
//      }else{
//          rs = -1;
//      }
//    } catch (Exception e) {
//        gateLogger.error("Exceptions happen!", e);
//    } finally {
//      try {
//        if (stmt != null) {
//          stmt.close();
//        }
//        if (conn != null) {
//          conn.close();
//        }
//
//      } catch (Exception e) {
//          gateLogger.error("Exceptions happen!", e);
//      }
//    }
//    return rs;
    
    return 0;
    
}

}
