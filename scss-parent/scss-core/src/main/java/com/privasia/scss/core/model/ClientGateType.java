/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * @author Janaka
 *
 */
@Entity
@Table(name="SCSS_CLIENT_GATE_TYPE")
public class ClientGateType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId 
	private ClientGateTypePK clientGateTypeID;
	
	@Embedded
	@AttributeOverrides({
		        @AttributeOverride(
		            name = "addBy",
		            column = @Column( name = "GTYPE_CREATEDBY")
		        ),
		        @AttributeOverride(
		            name = "updateBy",
		            column = @Column( name = "GTYPE_UPDATEDBY")
		        ),
		        @AttributeOverride(
		            name = "dateTimeAdd",
		            column = @Column( name = "GTYPE_DATECREATE")
		        ),
		        @AttributeOverride(
		            name = "dateTimeUpdate",
		            column = @Column( name = "GTYPE_DATEUPDATE")
		        )
	})
	private AuditEntity auditEntity;

	public ClientGateTypePK getClientGateTypeID() {
		return clientGateTypeID;
	}

	public void setClientGateTypeID(ClientGateTypePK clientGateTypeID) {
		this.clientGateTypeID = clientGateTypeID;
	}

	public AuditEntity getCommonAttribute() {
		return auditEntity;
	}

	public void setCommonAttribute(AuditEntity auditEntity) {
		this.auditEntity = auditEntity;
	}

	

}
