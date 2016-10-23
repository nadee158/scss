/**
 * 
 */
package com.privasia.scss.core.util.constant;

/**
 * @author Janaka
 *
 */
public enum KioskLockStatus {
	
	ACTIVE("A"), LOCK("L"), COMPLETE("C");
	
	private final String kioskLockStatus;

    private KioskLockStatus(String kioskLockStatus) {
        this.kioskLockStatus = kioskLockStatus;
    }

	/**
	 * @return the kioskLockStatus
	 */
	public String getKioskLockStatus() {
		return kioskLockStatus;
	}

	
}
