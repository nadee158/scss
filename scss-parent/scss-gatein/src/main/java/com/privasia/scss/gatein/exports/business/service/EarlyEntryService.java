/**
 * 
 */
package com.privasia.scss.gatein.exports.business.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.ExportContainer;

/**
 * @author Janaka
 *
 */
@Service("earlyEntryService")
public class EarlyEntryService {
	
	public boolean isAllowedForEarlyEntry(ExportContainer container) throws Exception {
		
		final LocalDateTime now = LocalDateTime.now();
	    boolean ret = false;

	    // if no record found in the shp_ship_code table
	    if (container.getStoragePeriod() == -1) {
	    	return true;
	    }
	    
	    long earlyEnrtyDate = 0;
	    LocalDateTime vesselETADate = container.getVesselETADate();
	    vesselETADate.minusDays(1);
	    earlyEnrtyDate = vesselETADate.until( now, ChronoUnit.DAYS);
	   
	    // Before Eta Date
	    if (earlyEnrtyDate > 1){
	    	if (earlyEnrtyDate <= container.getStoragePeriod()){
	    		return true;
	    	} else {
	    		/**
	    		 * Check if it is allowed for early entry
	    		 */
	    		if (container.getShipSCNID().isPresent()) {
	    			/**
	    			 * check if container coming during early entry window.
	    			 */
	    			/*if(inEarlyEntryWindow()){
	    				earlyEntry = true;
	    				return true;
	    			}else {
	    				earlyEntry = true;
	    				if(container.isBypassEEntry()){
	    					return true;
	    	    		}else{
	    	    			return false;
	    	    		}
	    			}*/
	    		}
	    	}
	    } else{
	    	// After ETA
	    	return true;
	    }
	    
	  
	    return false;
	  }
	
	
	/*public boolean inEarlyEntryWindow() throws Exception {
		  boolean isInWindow = false;
		  Connection conn = null;
		  Statement stmt = null;
		  ResultSet rs = null;
		  String sql = "SELECT GLOBAL_STRING, PARAM_VALUE1 FROM WDC_GLOBAL_SETTINGS"
		                 + " WHERE GLOBAL_CODE in ('EE_TIME')";
		  try {
			  conn = SCSSDatabase.getInstance().getConnection();
			  stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			  rs = stmt.executeQuery(sql);
			  while(rs.next()){
				  startEarlyEntry = rs.getString("GLOBAL_STRING");
				  endEarlyEntry = rs.getString("PARAM_VALUE1");
				  break;
			  }
		  } catch (Exception e) {
			  throw e;
		  } finally {
			  if (rs != null) {
				  rs.close();
			  }
			  if (stmt != null) {
				  stmt.close();
			  }
			  if (conn != null) {
				  conn.close();
			  }
	    }
		final Date now = new Date();
		final SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
		final SimpleDateFormat dateTime = new SimpleDateFormat("dd/MM/yyyy h:mm a");
		String dateNow = date.format(now);
		
		String startFullDate = dateNow + " " + startEarlyEntry;
		String endFullDate = dateNow + " " + endEarlyEntry;
		Date strtFullDate = dateTime.parse(startFullDate);
		Date edFullDate = dateTime.parse(endFullDate);
		
		
		if(strtFullDate.after(edFullDate)){
			if(StringUtils.contains(dateTime.format(now),"AM")){
				strtFullDate = DateUtil.addDate(strtFullDate, -1);
			}else{
				edFullDate = DateUtil.addDate(edFullDate, 1);
			}
		}
		
		if(now.after(strtFullDate) && now.before(edFullDate)){
			isInWindow = true;
		}
		return isInWindow;
	}*/

}
