/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;

import com.privasia.scss.core.util.constant.CompanyAccountType;
import com.privasia.scss.core.util.constant.CompanyStatus;
import com.privasia.scss.core.util.constant.CompanyType;

/**
 * @author Janaka
 *
 */
@Entity
@Table(name="SCSS_COMPANY")

/*@Table(name="SCSS_COMPANY", uniqueConstraints={
		@UniqueConstraint(columnNames={"COM_REGNO"}),
		@UniqueConstraint(columnNames={"COM_CPNEWNRICNO"}),
		@UniqueConstraint(columnNames={"COM_CPOLDNRICNO"})
})*/

@AttributeOverrides({@AttributeOverride(name="addBy",column=@Column(name="COM_CREATEDBY")),
    @AttributeOverride(name="updateBy", column=@Column(name="COM_UPDATEDBY")),
    @AttributeOverride(name="dateTimeAdd",column=@Column(name="COM_DATECREATE")),
    @AttributeOverride(name="dateTimeUpdate",column=@Column(name="COM_DATEUPDATE"))
})
public class Company extends AuditEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCSS_COMPANY")
    @SequenceGenerator(name = "SEQ_SCSS_COMPANY", sequenceName = "COM_ID_SEQ")
	@Column(name = "COM_ID_SEQ")
	private Long companyID;
	
	@Column(name = "COM_NAME")
	private String companyName;
	
	@Column(name = "COM_ACCTNO")
	private String companyAccountNo;
	
	@Column(name = "COM_ACCTTYPE")
	@Type(type="com.privasia.scss.core.util.enumusertype.CompanyAccountTypeEnumUserType")
	private CompanyAccountType 	companyAccountType;
	
	@Column(name = "COM_CODE")
	private String companyCode;
	
	@Column(name = "COM_TYPE")
	@Type(type="com.privasia.scss.core.util.enumusertype.CompanyTypeEnumUserType") 
	private CompanyType companyType;
	
	@Column(name = "COM_REGNO")
	private String companyRegistrationNumber;
	
	@Column(name = "COM_STATUS")
	@Type(type="com.privasia.scss.core.util.enumusertype.CompanyStatusEnumUserType") 
	private CompanyStatus companyStatus;
	
	@Column(name = "COM_NAMEONCARD")
	private String nameOnCard;
	
	@Transient
	@Embedded
	@AttributeOverrides({
		        @AttributeOverride(
		            name = "phoneOffice",
		            column = @Column( name = "COM_PHONEOFFICE")
		        ),
		        @AttributeOverride(
		            name = "personName",
		            column = @Column( name = "COM_CPNAME")
		        ),
		        @AttributeOverride(
		            name = "emailAddress",
		            column = @Column( name = "COM_CPEMAILADDR")
		        ),
		        @AttributeOverride(
			            name = "phoneMobile",
			            column = @Column( name = "COM_CPHONEMOBILE")
			    ),
		        @AttributeOverride(
			            name = "newNRICNO",
			            column = @Column( name = "COM_CPNEWNRICNO")
			    ),
		        @AttributeOverride(
			            name = "oldNRICNO",
			            column = @Column( name = "COM_CPOLDNRICNO")
			    ),
		        @AttributeOverride(
			            name = "designation",
			            column = @Column( name = "COM_CPDESIGNATION")
			    ),
		        @AttributeOverride(
			            name = "postalCode",
			            column = @Column( name = "COM_ADDRPOSTCODE")
		        ),
		        @AttributeOverride(
			            name = "blockNo",
			            column = @Column( name = "COM_ADDRBLOCKNO")
			    ),
		        @AttributeOverride(
			            name = "buildingName",
			            column = @Column( name = "COM_ADDRBUILDNAME")
			    ),
		        @AttributeOverride(
			            name = "buildingNo",
			            column = @Column( name = "COM_ADDRBUILDNO")
			    ),
		        @AttributeOverride(
			            name = "streetName01",
			            column = @Column( name = "COM_ADDRSTNAME1")
			    ),
		        @AttributeOverride(
			            name = "streetName02",
			            column = @Column( name = "COM_ADDRSTNAME2")
			    ),
		        @AttributeOverride(
			            name = "streetName03",
			            column = @Column( name = "COM_ADDRSTNAME3")
			    ),
		        @AttributeOverride(
			            name = "city",
			            column = @Column( name = "COM_ADDRTOWNCITY")
			    ),
		        @AttributeOverride(
			            name = "state",
			            column = @Column( name = "COM_ADDRSTATE")
			    ),
		        @AttributeOverride(
			            name = "country",
			            column = @Column( name = "COM_ADDRCOUNTRY")
			    )
	})
	private CommonContactAttribute commonContactAttribute;
	
	
	@Column(name = "COM_FAXOFFICE")
	private String faxOffice;

	public Long getCompanyID() {
		return companyID;
	}

	public void setCompanyID(Long companyID) {
		this.companyID = companyID;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAccountNo() {
		return companyAccountNo;
	}

	public void setCompanyAccountNo(String companyAccountNo) {
		this.companyAccountNo = companyAccountNo;
	}

	public CompanyAccountType getCompanyAccountType() {
		return companyAccountType;
	}

	public void setCompanyAccountType(CompanyAccountType companyAccountType) {
		this.companyAccountType = companyAccountType;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public CompanyType getCompanyType() {
		return companyType;
	}

	public void setCompanyType(CompanyType companyType) {
		this.companyType = companyType;
	}

	public String getCompanyRegistrationNumber() {
		return companyRegistrationNumber;
	}

	public void setCompanyRegistrationNumber(String companyRegistrationNumber) {
		this.companyRegistrationNumber = companyRegistrationNumber;
	}

	public CompanyStatus getCompanyStatus() {
		return companyStatus;
	}

	public void setCompanyStatus(CompanyStatus companyStatus) {
		this.companyStatus = companyStatus;
	}

	public String getNameOnCard() {
		return nameOnCard;
	}

	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}
	
	public String getFaxOffice() {
		return faxOffice;
	}

	public void setFaxOffice(String faxOffice) {
		this.faxOffice = faxOffice;
	}

	public CommonContactAttribute getCommonContactAttribute() {
		return commonContactAttribute;
	}

	public void setCommonContactAttribute(CommonContactAttribute commonContactAttribute) {
		this.commonContactAttribute = commonContactAttribute;
	}
	
	

}
