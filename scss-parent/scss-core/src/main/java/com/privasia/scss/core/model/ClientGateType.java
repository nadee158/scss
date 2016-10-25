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
@Table(name="SCSS_CLIENT_GATE_TYPE")
@AttributeOverrides({
    @AttributeOverride(name="addBy",
                       column=@Column(name="GTYPE_CREATEDBY")),
    @AttributeOverride(name="updateBy",
                       column=@Column(name="GTYPE_UPDATEDBY")),
    @AttributeOverride(name="dateTimeAdd",
    				   column=@Column(name="GTYPE_DATECREATE")),
    @AttributeOverride(name="dateTimeUpdate",
                       column=@Column(name="GTYPE_DATEUPDATE"))
})
public class ClientGateType extends AuditEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId 
	private ClientGateTypePK clientGateTypeID;
	
	public ClientGateTypePK getClientGateTypeID() {
		return clientGateTypeID;
	}

	public void setClientGateTypeID(ClientGateTypePK clientGateTypeID) {
		this.clientGateTypeID = clientGateTypeID;
	}

}
