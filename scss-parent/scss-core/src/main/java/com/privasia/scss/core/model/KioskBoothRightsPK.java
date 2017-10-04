/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author Janaka
 *
 */
@Embeddable
public class KioskBoothRightsPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.REFRESH)
	@JoinColumn(name = "BOOTH_ID", nullable = false, referencedColumnName = "CLI_CLIENTID_SEQ", insertable = false, updatable = false)
	private Client booth;

	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.REFRESH)
	@JoinColumn(name = "KIOSK_ID", nullable = false, referencedColumnName = "CLI_CLIENTID_SEQ", insertable = false, updatable = false)
	private Client kiosk;

	public Client getBooth() {
		return booth;
	}

	public void setBooth(Client booth) {
		this.booth = booth;
	}

	public Client getKiosk() {
		return kiosk;
	}

	public void setKiosk(Client kiosk) {
		this.kiosk = kiosk;
	}

}
