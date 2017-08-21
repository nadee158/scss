/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.privasia.scss.common.enums.LaneOpenFlag;

/**
 * @author Janaka
 *
 */
@Entity
@Table(name="SCSS_LANE_OPENED")
@AttributeOverrides({@AttributeOverride(name="addBy",column=@Column(name="LO_ADD_BY")),
    @AttributeOverride(name="updateBy", column=@Column(name="LO_UPDATE_BY")),
    @AttributeOverride(name="dateTimeAdd",column=@Column(name="LO_TIMENOTIFY_TO_OPEN")),
    @AttributeOverride(name="dateTimeUpdate",column=@Column(name="LO_TIMEOPENED"))
})
public class LaneOpen extends AuditEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCSS_LANE_OPENED")
    @SequenceGenerator(name = "SEQ_SCSS_LANE_OPENED", sequenceName = "LO_SEQ", allocationSize = 1)
	@Column(name = "LO_SEQ")
	private Long laneOpenID;
	
	@Column(name = "LO_CONTAINERNO1")
	private String container01;
	
	@Column(name = "LO_CONTAINERNO2")
	private String container02;
	
	@Column(name = "LO_FLAG")
	@Type(type="com.privasia.scss.common.enumusertype.LaneOpenEnumUserType")
	private LaneOpenFlag laneOpenFlag;
	
	@Column(name = "LO_REMARKS")
	private String remarks;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LO_USERID_SEQ", nullable = true, referencedColumnName = "SYS_USERID_SEQ")
	private SystemUser laneOpenBy;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LO_CLIENTID_SEQ", nullable = false, referencedColumnName = "CLI_CLIENTID_SEQ")
	private Client laneID;

	public Long getLaneOpenID() {
		return laneOpenID;
	}

	public void setLaneOpenID(Long laneOpenID) {
		this.laneOpenID = laneOpenID;
	}

	public String getContainer01() {
		return container01;
	}

	public void setContainer01(String container01) {
		this.container01 = container01;
	}

	public String getContainer02() {
		return container02;
	}

	public void setContainer02(String container02) {
		this.container02 = container02;
	}

	public LaneOpenFlag getLaneOpenFlag() {
		return laneOpenFlag;
	}

	public void setLaneOpenFlag(LaneOpenFlag laneOpenFlag) {
		this.laneOpenFlag = laneOpenFlag;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public SystemUser getLaneOpenBy() {
		return laneOpenBy;
	}

	public void setLaneOpenBy(SystemUser laneOpenBy) {
		this.laneOpenBy = laneOpenBy;
	}

	public Client getLaneID() {
		return laneID;
	}

	public void setLaneID(Client laneID) {
		this.laneID = laneID;
	}

}
