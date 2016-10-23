/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
import javax.persistence.UniqueConstraint;

import com.privasia.scss.common.security.Authority;
import com.privasia.scss.common.security.User;
import com.privasia.scss.core.util.constant.Gender;
import com.privasia.scss.core.util.constant.Nationality;
import com.privasia.scss.core.util.constant.UserStatus;
import com.privasia.scss.core.util.constant.UserType;



/**
 * @author Janaka
 *
 */
@Entity
@Table(name="SCSS_SYSUSER", uniqueConstraints={
		@UniqueConstraint(columnNames={"SYS_PASSPORTNO"}),
		@UniqueConstraint(columnNames={"SYS_EMAILADDR"}),
		@UniqueConstraint(columnNames={"SYS_NEWNRICNO"}),
		@UniqueConstraint(columnNames={"SYS_OLDNRICNO"})
})
public class SystemUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCSS_SYSUSER")
    @SequenceGenerator(name = "SEQ_SCSS_SYSUSER", sequenceName = "SYS_USERID_SEQ")
	@Column(name = "SYS_USERID_SEQ")
	private Long systemUserID;
	
	@Column(name = "SYS_STAFFNO")
	private String staffNumber;
	
	@Column(name = "SYS_NAME")
	private String name;
	
	@Column(name = "SYS_GENDER")
	@Enumerated(EnumType.STRING) 
	private Gender gender;
	
	@Column(name = "SYS_DOB")
	private LocalDateTime dateOfBirth;
	
	@Column(name = "SYS_PASSPORTNO")
	private String passportNo;
	
	@Column(name = "SYS_PASSPORTEXPDATE")
	private LocalDateTime passportExpireDate;
	
	@Column(name = "SYS_NATIONALITY")
	@Enumerated(EnumType.STRING) 
	private Nationality nationality;
	
	@Column(name = "SYS_DEPTNAME")
	private String department;
	
	@Embedded
	@AttributeOverrides({
		        @AttributeOverride(
		            name = "addBy",
		            column = @Column( name = "SYS_CREATEDBY")
		        ),
		        @AttributeOverride(
		            name = "updateBy",
		            column = @Column( name = "SYS_UPDATEDBY")
		        ),
		        @AttributeOverride(
		            name = "dateTimeAdd",
		            column = @Column( name = "SYS_DATECREATE")
		        ),
		        @AttributeOverride(
		            name = "dateTimeUpdate",
		            column = @Column( name = "SYS_DATEUPDATE")
		        )
	})
	private AuditEntity auditEntity;
	
	@Embedded
	@AttributeOverrides({
		        @AttributeOverride(
		            name = "phoneOffice",
		            column = @Column( name = "SYS_PHONEWORK")
		        ),
		        @AttributeOverride(
		            name = "faxOffice",
		            column = @Column( name = "NULL")
		        ),
		        @AttributeOverride(
		            name = "personName",
		            column = @Column( name = "NULL")
		        ),
		        @AttributeOverride(
		            name = "emailAddress",
		            column = @Column( name = "SYS_EMAILADDR")
		        ),
		        @AttributeOverride(
			            name = "phoneMobile",
			            column = @Column( name = "SYS_PHONEMOBILE")
			    ),
		        @AttributeOverride(
			            name = "newNRICNO",
			            column = @Column( name = "SYS_NEWNRICNO")
			    ),
		        @AttributeOverride(
			            name = "oldNRICNO",
			            column = @Column( name = "SYS_OLDNRICNO")
			    ),
		        @AttributeOverride(
			            name = "designation",
			            column = @Column( name = "SYS_DESIGNATION")
			    ),
		        @AttributeOverride(
			            name = "postalCode",
			            column = @Column( name = "SYS_ADDRPOSTCODE")
		        ),
		        @AttributeOverride(
			            name = "blockNo",
			            column = @Column( name = "SYS_ADDRBLOCKNO")
			    ),
		        @AttributeOverride(
			            name = "buildingName",
			            column = @Column( name = "SYS_ADDRBUILDNAME")
			    ),
		        @AttributeOverride(
			            name = "buildingNo",
			            column = @Column( name = "SYS_ADDRBUILDNO")
			    ),
		        @AttributeOverride(
			            name = "streetName01",
			            column = @Column( name = "SYS_ADDRSTNAME1")
			    ),
		        @AttributeOverride(
			            name = "streetName02",
			            column = @Column( name = "SYS_ADDRSTNAME2")
			    ),
		        @AttributeOverride(
			            name = "streetName03",
			            column = @Column( name = "SYS_ADDRSTNAME3")
			    ),
		        @AttributeOverride(
			            name = "city",
			            column = @Column( name = "SYS_ADDRTOWNCITY")
			    ),
		        @AttributeOverride(
			            name = "state",
			            column = @Column( name = "SYS_ADDRSTATE")
			    ),
		        @AttributeOverride(
			            name = "country",
			            column = @Column( name = "SYS_ADDRCOUNTRY")
			    )
	})
	private CommonContactAttribute commonContactAttribute;
	
	@Column( name = "SYS_PHONEHOME")
	private String homePhone;
	
	@Column( name = "SYS_USERSTATUS")
	@Enumerated(EnumType.STRING) 
	private UserStatus userStatus;
	
	@Column( name = "SYS_USRTYP_FLAG")
	@Enumerated(EnumType.STRING) 
	private UserType userType;

	public Long getSystemUserID() {
		return systemUserID;
	}

	public void setSystemUserID(Long systemUserID) {
		this.systemUserID = systemUserID;
	}

	public String getStaffNumber() {
		return staffNumber;
	}

	public void setStaffNumber(String staffNumber) {
		this.staffNumber = staffNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public LocalDateTime getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDateTime dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPassportNo() {
		return passportNo;
	}

	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	public LocalDateTime getPassportExpireDate() {
		return passportExpireDate;
	}

	public void setPassportExpireDate(LocalDateTime passportExpireDate) {
		this.passportExpireDate = passportExpireDate;
	}

	public Nationality getNationality() {
		return nationality;
	}

	public void setNationality(Nationality nationality) {
		this.nationality = nationality;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public AuditEntity getCommonAttribute() {
		return auditEntity;
	}

	public void setCommonAttribute(AuditEntity auditEntity) {
		this.auditEntity = auditEntity;
	}

	public CommonContactAttribute getCommonContactAttribute() {
		return commonContactAttribute;
	}

	public void setCommonContactAttribute(CommonContactAttribute commonContactAttribute) {
		this.commonContactAttribute = commonContactAttribute;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

  
	
	
	

}
