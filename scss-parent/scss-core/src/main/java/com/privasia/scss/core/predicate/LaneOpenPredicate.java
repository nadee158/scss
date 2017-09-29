package com.privasia.scss.core.predicate;

import java.time.LocalDateTime;

import com.privasia.scss.common.enums.LaneOpenFlag;
import com.privasia.scss.core.model.QLaneOpen;
import com.querydsl.core.types.Predicate;

public final class LaneOpenPredicate {
	
	private LaneOpenPredicate(){}

	public static Predicate bylaneOpenID (Long laneOpenID){
		if (laneOpenID == null) {
			return QLaneOpen.laneOpen.laneID.clientID.isNull();
		} else {
			return QLaneOpen.laneOpen.laneID.clientID.eq(laneOpenID);
		}
		
	}
	
	public static Predicate byLaneOpenFlag(LaneOpenFlag laneOpenFlag) {
		if (laneOpenFlag == null) {
			return QLaneOpen.laneOpen.laneOpenFlag.isNull();
		} else {
			return QLaneOpen.laneOpen.laneOpenFlag.eq(laneOpenFlag);
		}
	}
	
	public static Predicate byDateTimeUpdate (LocalDateTime dateTimeUpdate){
		if (dateTimeUpdate == null) {
			return QLaneOpen.laneOpen.dateTimeUpdate.isNull();
		} else {
			return QLaneOpen.laneOpen.dateTimeUpdate.eq(dateTimeUpdate);
		}
		
	}
}
