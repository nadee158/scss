package com.privasia.scss.core.repository;

import java.util.List;

import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.KioskBoothRights;
import com.privasia.scss.core.model.KioskBoothRightsPK;

public interface KioskBoothRightsRepository extends BaseRepository<KioskBoothRights, KioskBoothRightsPK> {

  public List<KioskBoothRights> findByKioskBoothRightsIDBoothID(Client boothID);
}
