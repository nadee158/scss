package com.privasia.scss.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.privasia.scss.common.enums.KioskLockStatus;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.KioskBoothRights;
import com.privasia.scss.core.model.KioskBoothRightsPK;


public interface KioskBoothRightsRepository
    extends BaseRepository<KioskBoothRights, KioskBoothRightsPK>, QueryDslPredicateExecutor<KioskBoothRights> {

  public List<KioskBoothRights> findByKioskBoothRightsID_Booth(Client boothID);

//@formatter:off
  // String lockSql = " SELECT COUNT(1) FROM SCSS_KIOSK_BOOTH_RIGHTS" +
  // " WHERE KIOSK_ID = ? AND KIOSK_LOCK_STATUS = ? AND BOOTH_ID <> ?";
  @Query("SELECT k FROM KioskBoothRights k where"
      + " k.kioskBoothRightsID.kiosk.clientID = :kioskID"
      + " AND k.kioskLockStatus = :kioskLockStatus" 
      + " AND k.kioskBoothRightsID.booth.clientID <> :boothID")
  List<KioskBoothRights> findByKioskIDAndKioskLockStatusAndBoothID(@Param("kioskID") Long kioskID,
      @Param("kioskLockStatus") KioskLockStatus kioskLockStatus, @Param("boothID") Long boothID);
//@formatter:on

  public List<KioskBoothRights> findByKioskBoothRightsID_Kiosk(Client kioskID);

  public Optional<KioskBoothRights> findByKioskBoothRightsID_KioskAndKioskBoothRightsID_Booth(Client kioskID,
      Client boothID);

  public Optional<KioskBoothRights> findByKioskBoothRightsID_Kiosk_ClientIDAndKioskBoothRightsID_Booth_ClientID(
      long kioskID, long boothID);


  public List<KioskBoothRights> findByKioskBoothRightsID_KioskAndKioskBoothRightsID_BoothAndCardNumber(
      Client kioskID, Client boothID, int cardNumber);

  public List<KioskBoothRights> findByKioskBoothRightsID_KioskAndCardNumber(Client kioskID, int cardNumber);

//@formatter:off
  @Query("SELECT k FROM KioskBoothRights k where"
      + " k.kioskBoothRightsID.kiosk.clientID = :kioskID"
      + " AND k.kioskBoothRightsID.booth.clientID <> :boothID")
  public List<KioskBoothRights> findByKioskIDAndNotBoothID(long kioskID, long boothID);
//@formatter:on


  public Optional<List<KioskBoothRights>> findByKioskBoothRightsID_Booth_ClientIDOrderByKioskBoothRightsID_Kiosk_LaneNoAsc(
      long parseLong);


}
