/**
 * 
 */
package com.privasia.scss.core.predicate;


import java.util.List;

import org.springframework.stereotype.Component;

import com.privasia.scss.common.enums.BookingType;
import com.privasia.scss.core.model.QHPATBookingDetail;
import com.querydsl.core.types.Predicate;



/**
 * @author Janaka
 *
 */
@Component
public final class HPATBookingDetailPredicates {
	
	
	private HPATBookingDetailPredicates(){}
	
	
	public static Predicate byBookingTypes(List<BookingType> bookingTypes) {
        return  QHPATBookingDetail.hPATBookingDetail.bookingType.in(bookingTypes);
        
	}
	
	
}
