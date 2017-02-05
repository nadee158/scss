package com.privasia.scss.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.privasia.scss.common.enums.CustomLaneStatus;
import com.privasia.scss.common.enums.TransactionType;

@Entity
@Table(name = "SCSS_CUSTOMS")
@AttributeOverrides({@AttributeOverride(name = "addBy", column = @Column(name = "CREATED_BY")),
    @AttributeOverride(name = "updateBy", column = @Column(name = "UPDATED_BY")),
    @AttributeOverride(name = "dateTimeAdd", column = @Column(name = "DATE_TIME_CREATED")),
    @AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "DATE_TIME_UPDATE"))})
public class Customs  extends AuditEntity implements Serializable {

	  /**
	   * 
	   */
	  private static final long serialVersionUID = 1L;
	  
	  @Id
	  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCSS_CUSTOMS")
	  @SequenceGenerator(name = "SEQ_SCSS_CUSTOMS", sequenceName = "SEQ_CSM_PKEY")
	  @Column(name = "CSM_PKEY_SEQ")
      private Long customsID;
	  
	  @Column(name = "CSM_FLAG", nullable = false)
	  @Type(type = "com.privasia.scss.common.enumusertype.TransactionTypeEnumUserType")
	  private TransactionType csmFlag;
	  
	  @ManyToOne(fetch = FetchType.LAZY)
	  @JoinColumn(name = "CSM_CLIENTID_GATEOUT", nullable = true, referencedColumnName = "CLI_CLIENTID_SEQ")
	  private Client csmGateOutClient;
	   
	  @Column(name = "CSM_TIMEGATEOUT", nullable = true)
	  private LocalDateTime timeGateOut;
	  
	  @Column(name = "CSM_LANE_STATUS", nullable = true)
	  @Type(type = "com.privasia.scss.common.enumusertype.TransactionStatusEnumUserType")
	  private CustomLaneStatus laneStatus;
	  
	  @Column(name = "CSM_TIMEOPENED")
	  private LocalDateTime timeOpend;
	  
	  @ManyToOne(fetch = FetchType.LAZY)
	  @JoinColumn(name = "CSO_USERID_SEQ", nullable = true, referencedColumnName = "SYS_USERID_SEQ")
	  private SystemUser csoUserIdSeq;
	  
	  @ManyToOne(fetch = FetchType.LAZY)
	  @JoinColumn(name = "ODD_ID_SEQ", nullable = true, referencedColumnName = "ODD_ID_SEQ")
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
	  
	 @Embedded
	  @AttributeOverrides({@AttributeOverride(name = "containerNumber", column = @Column(name = "CSM_CONTAINERNO1")),
	      @AttributeOverride(name = "containerFullOrEmpty", column = @Column(name = "CSM_FULLEMPTY_FLAGC1")),
	      /*@AttributeOverride(name = "gcsDeclareNo", column = @Column(name = "GCS_DELCARENOC1")),
	      @AttributeOverride(name = "oddLocation", column = @Column(name = "ODD_LOCATION")),
	      @AttributeOverride(name = "rejectionReason", column = @Column(name = "CSM_REJECTREASON")),*/
	      @AttributeOverride(name = "eirStatus", column = @Column(name = "CSM_EIRSTATUS"))
	     /* @AttributeOverride(name = "gatePassNo", column = @Column(name = "GATE_PASS_NO1")),
	      @AttributeOverride(name = "gscReleaseDate", column = @Column(name = "GCS_RELEASE_DATEC1")),
	      @AttributeOverride(name = "gatePassDate", column = @Column(name = "GATE_PASS_DATEC1")),
	      @AttributeOverride(name = "portPoliceDate", column = @Column(name = "PORT_POLICE_DATEC1")),
	      @AttributeOverride(name = "exportNoSeq", column = @Column(name = "EXP_EXPORTNO_SEQ1"))*/})
	  private CustomeContainerDetail container01;
	  
	  @Embedded
	  @AttributeOverrides({@AttributeOverride(name = "containerNumber", column = @Column(name = "CSM_CONTAINERNO2")),
	      @AttributeOverride(name = "containerFullOrEmpty", column = @Column(name = "CSM_FULLEMPTY_FLAGC2")),
	      /*@AttributeOverride(name = "gcsDeclareNo", column = @Column(name = "GCS_DELCARENOC1")),
	      @AttributeOverride(name = "oddLocation", column = @Column(name = "ODD_LOCATION")),
	      @AttributeOverride(name = "rejectionReason", column = @Column(name = "CSM_REJECTREASON")),*/
	      @AttributeOverride(name = "eirStatus", column = @Column(name = "CSM_EIRSTATUS2")),
	      /* @AttributeOverride(name = "gatePassNo", column = @Column(name = "GATE_PASS_NO1")),
	      @AttributeOverride(name = "gscReleaseDate", column = @Column(name = "GCS_RELEASE_DATEC1")),
	      @AttributeOverride(name = "gatePassDate", column = @Column(name = "GATE_PASS_DATEC1")),
	      @AttributeOverride(name = "portPoliceDate", column = @Column(name = "PORT_POLICE_DATEC1")),
	      @AttributeOverride(name = "exportNoSeq", column = @Column(name = "EXP_EXPORTNO_SEQ1"))*/})
	  private CustomeContainerDetail container02; 


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
