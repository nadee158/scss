package com.privasia.scss.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.privasia.scss.common.enums.KioskLockStatus;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.KioskBoothRights;
import com.privasia.scss.core.model.KioskBoothRightsPK;


public interface KioskBoothRightsRepository
    extends BaseRepository<KioskBoothRights, KioskBoothRightsPK>, QueryDslPredicateExecutor<KioskBoothRights> {

  public List<KioskBoothRights> findByKioskBoothRightsID_BoothID(Client boothID);

//@formatter:off
  // String lockSql = " SELECT COUNT(1) FROM SCSS_KIOSK_BOOTH_RIGHTS" +
  // " WHERE KIOSK_ID = ? AND KIOSK_LOCK_STATUS = ? AND BOOTH_ID <> ?";
  @Query("SELECT k FROM KioskBoothRights k where"
      + " k.kioskBoothRightsID.kioskID.clientID = :kioskID"
      + " AND k.kioskLockStatus = :kioskLockStatus" 
      + " AND k.kioskBoothRightsID.boothID.clientID <> :boothID")
  List<KioskBoothRights> findByKioskIDAndKioskLockStatusAndBoothID(@Param("kioskID") Long kioskID,
      @Param("kioskLockStatus") KioskLockStatus kioskLockStatus, @Param("boothID") Long boothID);
//@formatter:on

  public List<KioskBoothRights> findByKioskBoothRightsID_KioskID(Client kioskID);

  public List<KioskBoothRights> findByKioskBoothRightsID_KioskIDAndKioskBoothRightsID_BoothID(Client kioskID,
      Client boothID);

  public List<KioskBoothRights> findByKioskBoothRightsID_KioskIDAndKioskBoothRightsID_BoothIDAndCardNumber(
      Client kioskID, Client boothID, int cardNumber);

  public List<KioskBoothRights> findByKioskBoothRightsID_KioskIDAndCardNumber(Client kioskID, int cardNumber);

//@formatter:off
  @Query("SELECT k FROM KioskBoothRights k where"
      + " k.kioskBoothRightsID.kioskID.clientID = :kioskID"
      + " AND k.kioskBoothRightsID.boothID.clientID <> :boothID")
  public List<KioskBoothRights> findByKioskIDAndNotBoothID(long kioskID, long boothID);
//@formatter:on


}
