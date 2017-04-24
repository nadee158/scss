/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;

import com.privasia.scss.common.enums.Gender;
import com.privasia.scss.common.enums.Nationality;

/**
 * @author Janaka
 *
 */
@Entity
@Table(name = "SCSS_SCUSER", uniqueConstraints = {@UniqueConstraint(columnNames = {"SCU_PASSPORTNO"}),
    @UniqueConstraint(columnNames = {"SCU_NEWNRICNO"}), @UniqueConstraint(columnNames = {"SCU_OLDNRICNO"})})

@AttributeOverrides({@AttributeOverride(name = "addBy", column = @Column(name = "SCU_CREATEDBY") ),
    @AttributeOverride(name = "updateBy", column = @Column(name = "SCU_UPDATEDBY") ),
    @AttributeOverride(name = "dateTimeAdd", column = @Column(name = "SCU_DATECREATE") ),
    @AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "SCU_DATEUPDATE") )})
public class SmartCardUser extends AuditEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCU_USERID")
  @SequenceGenerator(name = "SEQ_SCU_USERID", sequenceName = "SCU_USERID_SEQ")
  @Column(name = "SCU_USERID_SEQ")
  private Long smartCardUserID;

  @Column(name = "SCU_GENDER")
  @Type(type = "com.privasia.scss.common.enumusertype.GenderEnumUserType")
  private Gender gender;

  @Column(name = "SCU_DOB")
  private LocalDateTime dateOfBirth;

  @Column(name = "SCU_NATIONALITY")
  @Type(type = "com.privasia.scss.common.enumusertype.NationalityEnumUserType")
  private Nationality nationality;

  @Column(name = "SCU_PASSPORTNO")
  private String passportNo;

  @Column(name = "SCU_PASSPORTEXPDATE")
  private LocalDateTime passportExpireDate;

  @Column(name = "SCU_NAMEONCARD")
  private String nameOnCard;

  @Basic(fetch = FetchType.LAZY, optional = true)
  @Lob
  @Column(name = "SCU_PHOTO")
  private byte[] photo;

  @Column(name = "SCU_NAME")
  private String personName;

  @Column(name = "SCU_NEWNRICNO")
  private String newNRICNO;

  @Column(name = "SCU_OLDNRICNO")
  private String oldNRICNO;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "cardID")
  private Set<Card> card;

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

  public String getPersonName() {
    return personName;
  }

  public void setPersonName(String personName) {
    this.personName = personName;
  }

  public String getNewNRICNO() {
    return newNRICNO;
  }

  public void setNewNRICNO(String newNRICNO) {
    this.newNRICNO = newNRICNO;
  }

  public String getOldNRICNO() {
    return oldNRICNO;
  }

  public void setOldNRICNO(String oldNRICNO) {
    this.oldNRICNO = oldNRICNO;
  }

  public Long getSmartCardUserID() {
    return smartCardUserID;
  }

  public void setSmartCardUserID(Long smartCardUserID) {
    this.smartCardUserID = smartCardUserID;
  }

  public String getNameOnCard() {
    return nameOnCard;
  }

  public void setNameOnCard(String nameOnCard) {
    this.nameOnCard = nameOnCard;
  }

  public byte[] getPhoto() {
    return photo;
  }

  public void setPhoto(byte[] photo) {
    this.photo = photo;
  }

  public Set<Card> getCard() {
    return card;
  }

  public void setCard(Set<Card> card) {
    this.card = card;
  }

}
