package com.privasia.scss.kioskbooth.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.core.dto.ClientInfo;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.KioskBoothRights;
import com.privasia.scss.core.model.KioskBoothRightsPK;
import com.privasia.scss.core.repository.KioskBoothRightsRepository;

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

}
