/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;



/**
 * @author Janaka
 *
 */

@Entity
@Table(name="SCSS_VESSEL_OMIT")
@AttributeOverrides({
    @AttributeOverride(name="addBy",
                       column=@Column(name="ADD_BY")),
    @AttributeOverride(name="updateBy",
                       column=@Column(name="UPDATE_BY")),
    @AttributeOverride(name="dateTimeAdd",
    				   column=@Column(name="DATETIME_ADD")),
    @AttributeOverride(name="dateTimeUpdate",
                       column=@Column(name="DATETIME_UPDATE"))
})
public class VesselOmit extends AuditEntity implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId 
	private VesselOmitPK vesselOmitID;
	
	@Column(name = "VESSEL_VOY_IN")
	private String vesselVoyIN;
	
	@Column(name = "VESSEL_VOY_OUT")
	private String vesselVoyOUT;

	public VesselOmitPK getVesselOmitID() {
		return vesselOmitID;
	}

	public void setVesselOmitID(VesselOmitPK vesselOmitID) {
		this.vesselOmitID = vesselOmitID;
	}

	public String getVesselVoyIN() {
		return vesselVoyIN;
	}

	public void setVesselVoyIN(String vesselVoyIN) {
		this.vesselVoyIN = vesselVoyIN;
	}

	public String getVesselVoyOUT() {
		return vesselVoyOUT;
	}

	public void setVesselVoyOUT(String vesselVoyOUT) {
		this.vesselVoyOUT = vesselVoyOUT;
	}

	
	
	
}
