/**
 * 
 */
package com.privasia.scss.common.dto;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Janaka
 *
 */
public class CommonSealDTO implements Serializable {

  /**
  	 * 
  	 */
  private static final long serialVersionUID = 1L;

  private String seal01Origin;

  private String seal01Type;

  private String seal01Number;

  private String seal02Origin;

  private String seal02Type;

  private String seal02Number;

  public String getSeal01Origin() {
    return seal01Origin;
  }

  public void setSeal01Origin(String seal01Origin) {
    this.seal01Origin = seal01Origin;
  }

  public String getSeal01Type() {
    return seal01Type;
  }

  public void setSeal01Type(String seal01Type) {
    this.seal01Type = seal01Type;
  }

  public String getSeal01Number() {
    return seal01Number;
  }

  public void setSeal01Number(String seal01Number) {
    this.seal01Number = seal01Number;
  }

  public String getSeal02Origin() {
    return seal02Origin;
  }

  public void setSeal02Origin(String seal02Origin) {
    this.seal02Origin = seal02Origin;
  }

  public String getSeal02Type() {
    return seal02Type;
  }

  public void setSeal02Type(String seal02Type) {
    this.seal02Type = seal02Type;
  }

  public String getSeal02Number() {
    return seal02Number;
  }

  public void setSeal02Number(String seal02Number) {
    this.seal02Number = seal02Number;
  }

  public CommonSealDTO initializeWithDefaultValues() {
    this.seal01Origin = "L";

    this.seal01Type = "SL";

    this.seal01Number = "B109612";

    this.seal02Origin = "S";

    this.seal02Type = "SL";

    this.seal02Number = "MY0338571";
    return this;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

}
