package com.privasia.scss.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.privasia.scss.common.enums.TransactionType;

//@Entity
//@Table(name = "SCSS_CUSTOMS")
@AttributeOverrides({@AttributeOverride(name = "addBy", column = @Column(name = "CREATED_BY")),
    @AttributeOverride(name = "updateBy", column = @Column(name = "UPDATED_BY")),
    @AttributeOverride(name = "dateTimeAdd", column = @Column(name = "DATE_TIME_CREATED")),
    @AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "DATE_TIME_UPDATE"))})
public class Customs  extends AuditEntity implements Serializable {

	  /**
	   * 
	   */
	  private static final long serialVersionUID = 1L;

	  
	  @Column(name = "CSM_FLAG", nullable = false)
	  @Type(type = "com.privasia.scss.common.enumusertype.TransactionTypeEnumUserType")
	  private TransactionType csmFlag;
	  
	  @Column(name = "CSM_CLIENTID_GATEOUT", nullable = false)
	  private String csmClientIdGateOut;
	   
	  @Column(name = "CSM_TIMEGATEOUT", nullable = false)
	  private LocalDateTime csmTimeGateOut;
	  
	  @Column(name = "CSM_LANE_STATUS", nullable = false)
	  private String laneStatus;
	  
	  @Column(name = "CSM_TIMEOPENED")
	  private LocalDateTime csmTimeOpend;
	  
	  @ManyToOne(fetch = FetchType.LAZY)
	  @JoinColumn(name = "CSO_USERID_SEQ", nullable = true, referencedColumnName = "SYS_USERID_SEQ")
	  private SystemUser csoUserIdSeq;
	  
	  @ManyToOne(fetch = FetchType.LAZY)
	  @JoinColumn(name = "ODD_ID_SEQ", nullable = true, referencedColumnName = "ODD_ID_SEQ")
	  @Column(name = "ODD_ID_SEQ")
	  private WHODD oddIdSeq;
	  
	  /*@ManyToOne(fetch = FetchType.LAZY)
	  @JoinColumn(name = "CSM_SEQ_ID", nullable = true, referencedColumnName = "CSM_SEQ_ID")
	  private CustomsReport csmSeqId;*/
	  
	  @Column(name = "IS_CHECK")
	  @Type(type = "yes_no")
	  private boolean isCheck;
	  
	  @Column(name = "LANE_OPENED_DATE")
	  private LocalDateTime laneOpenedDate;
	  
	  @Column(name = "LANE_OPENED_BY")
	  private int laneOpenedBy;
	  
	  @Column(name = "WPT_LANE_STATUS")
	  private Long wptLaneStatus;
	  
	 /* @Embedded
	  @AttributeOverrides({@AttributeOverride(name = "containerNo", column = @Column(name = "CSM_CONTAINERNO1")),
	      @AttributeOverride(name = "fullEmpatyFlag", column = @Column(name = "CSM_FULLEMPTY_FLAGC1")),
	      @AttributeOverride(name = "gcsDeclareNo", column = @Column(name = "GCS_DELCARENOC1")),
	      @AttributeOverride(name = "oddLocation", column = @Column(name = "ODD_LOCATION")),
	      @AttributeOverride(name = "rejectionReason", column = @Column(name = "CSM_REJECTREASON")),
	      @AttributeOverride(name = "eirstatus", column = @Column(name = "CSM_EIRSTATUS")),
	      @AttributeOverride(name = "gatePassNo", column = @Column(name = "GATE_PASS_NO1")),
	      @AttributeOverride(name = "gscReleaseDate", column = @Column(name = "GCS_RELEASE_DATEC1")),
	      @AttributeOverride(name = "gatePassDate", column = @Column(name = "GATE_PASS_DATEC1")),
	      @AttributeOverride(name = "portPoliceDate", column = @Column(name = "PORT_POLICE_DATEC1")),
	      @AttributeOverride(name = "exportNoSeq", column = @Column(name = "EXP_EXPORTNO_SEQ1"))})
	  private CustomeContainerDetails container01;
	  
	  @Embedded
	  @AttributeOverrides({@AttributeOverride(name = "containerNo", column = @Column(name = "CSM_CONTAINERNO2")),
	      @AttributeOverride(name = "fullEmpatyFlag", column = @Column(name = "CSM_FULLEMPTY_FLAGC2")),
	      @AttributeOverride(name = "gcsDeclareNo", column = @Column(name = "GCS_DELCARENOC2")),
	      @AttributeOverride(name = "oddLocation", column = @Column(name = "ODD_LOCATION2")),
	      @AttributeOverride(name = "rejectionReason", column = @Column(name = "CSM_REJECTREASON2")),
	      @AttributeOverride(name = "eirstatus", column = @Column(name = "CSM_EIRSTATUS2")),
	      @AttributeOverride(name = "gatePassNo", column = @Column(name = "GATE_PASS_NO2")),
	      @AttributeOverride(name = "gscReleaseDate", column = @Column(name = "GCS_RELEASE_DATEC2")),
	      @AttributeOverride(name = "gatePassDate", column = @Column(name = "GATE_PASS_DATEC2")),
	      @AttributeOverride(name = "portPoliceDate", column = @Column(name = "PORT_POLICE_DATEC2")),
	      @AttributeOverride(name = "exportNoSeq", column = @Column(name = "EXP_EXPORTNO_SEQ2"))})
	  private CustomeContainerDetails container02; */

	
	public String getCsmClientIdGateOut() {
		return csmClientIdGateOut;
	}

	public void setCsmClientIdGateOut(String csmClientIdGateOut) {
		this.csmClientIdGateOut = csmClientIdGateOut;
	}

	public LocalDateTime getCsmTimeGateOut() {
		return csmTimeGateOut;
	}

	public void setCsmTimeGateOut(LocalDateTime csmTimeGateOut) {
		this.csmTimeGateOut = csmTimeGateOut;
	}

	public String getLaneStatus() {
		return laneStatus;
	}

	public void setLaneStatus(String laneStatus) {
		this.laneStatus = laneStatus;
	}

	public LocalDateTime getCsmTimeOpend() {
		return csmTimeOpend;
	}

	public void setCsmTimeOpend(LocalDateTime csmTimeOpend) {
		this.csmTimeOpend = csmTimeOpend;
	}

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

	public LocalDateTime getLaneOpenedDate() {
		return laneOpenedDate;
	}

	public void setLaneOpenedDate(LocalDateTime laneOpenedDate) {
		this.laneOpenedDate = laneOpenedDate;
	}

	public int getLaneOpenedBy() {
		return laneOpenedBy;
	}

	public void setLaneOpenedBy(int laneOpenedBy) {
		this.laneOpenedBy = laneOpenedBy;
	}

	public Long getWptLaneStatus() {
		return wptLaneStatus;
	}

	public void setWptLaneStatus(Long wptLaneStatus) {
		this.wptLaneStatus = wptLaneStatus;
	}

	
	  
	  
}
