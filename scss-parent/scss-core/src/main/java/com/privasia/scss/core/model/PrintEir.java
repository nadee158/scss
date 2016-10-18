/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.privasia.scss.core.util.constant.ClientType;
import com.privasia.scss.core.util.constant.RecordStatus;

/**
 * @author Janaka
 *
 */
@Entity
@Table(name="SCSS_PRINT_EIR")
public class PrintEir  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCSS_PRINT_EIR")
    @SequenceGenerator(name = "SEQ_SCSS_PRINT_EIR", sequenceName = "SCSS_PRINT_EIR_SEQ")
	@Column(name = "PRINT_NO")
	private Long printEIRID;
	
	@Column(name = "CLI_WEB_IPADDR", nullable = false)
	private String webIPAddress;
	
	@Column(name = "CLI_SCR_IPADDR",  nullable = false)
	private String scrIPAddress;
	
	@Column(name = "CLI_READERID", nullable = false)
	private int readerID;
	
	@Column(name = "CLI_LPS_IPADDR")
	private String lpsIPAddress;
	
	@Column(name = "CLI_CSM_IPADDR")
	private String csmIPAddress;
	
	@Column(name = "CLI_DESCRIPTION", nullable = false)
	private String description;
	
	@Column(name = "CLI_STATUS", nullable = false)
	@Enumerated(EnumType.STRING)
	private RecordStatus status;
	
	@Column(name = "CLI_TYPE", nullable = false)
	@Enumerated(EnumType.STRING)
	private ClientType type;
	
	@Column(name = "CLI_UNITNO", nullable = false)
	private String unitNo;
	
	@Column(name = "CLI_CSM_CTRL")
	@Type(type="YES_NO")
	private String csmControl;
	
	@Column(name = "COSMOS_PORT_NO")
	private int cosmosPortNo;
	
	@Column(name = "SORT_SEQ")
	private String sortSEQ;
	
	@Column(name = "CAMERA_SERVER_IPADDR")
	private String cameraServerIPAddress;
	
	@Column(name = "CAMERA_SERVER_PORT")
	private int cameraServerPortNo;
	
	@Column(name = "LANE_NO")
	private String laneNo;
	
	@Column(name = "FTP_PORT")
	private String ftpIPAddress;
	
	@Column(name = "FTP_PORT")
	private String ftpPort;
	
	@Column(name = "FTP_PROTOCAL")
	private String ftpProtocol;
	
	@Column(name = "FTP_USERNAME")
	private String ftpUsername;
	
	@Column(name = "FTP_PASSWORD")
	private String ftpPassword;
	
	@Column(name = "WITH_CMA_IMAGE")
	@Type(type="YES_NO")
	private boolean withCameraImage;
	
	@Column(name = "FTP_DIRECTORY")
	private String ftpDirectory;
	
	@Embedded
	@AttributeOverrides({
		        @AttributeOverride(
		            name = "addBy",
		            column = @Column( name = "CLI_CREATEDBY")
		        ),
		        @AttributeOverride(
		            name = "updateBy",
		            column = @Column( name = "CLI_UPDATEDBY")
		        ),
		        @AttributeOverride(
		            name = "dateTimeAdd",
		            column = @Column( name = "CLI_DATECREATE")
		        ),
		        @AttributeOverride(
		            name = "dateTimeUpdate",
		            column = @Column( name = "CLI_DATEUPDATE")
		        )
	})
	private AuditEntity auditEntity;

	

}
