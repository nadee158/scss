package com.privasia.scss.core.util.constant;

public enum LpkClassType implements Enumable {

	  LPK_CLASS1("1"), LPK_CLASS2("2"), LPK_CLASS3("3"), LPK_CLASS4("4");

	  private final String lpkClass;

	  private LpkClassType(String lpkClass) {
	    this.lpkClass = lpkClass;
	  }

	  /**
	   * @return the lpkClass
	   */
	  public String getValue() {
	    return lpkClass;
	  }

	  public Enum<?> getEnumFromValue(String value) {
	    return EnumableHelper.getEnumFromValue(this, value, null);
	  }
}
