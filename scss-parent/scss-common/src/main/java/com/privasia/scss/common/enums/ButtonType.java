/**
 * 
 */
package com.privasia.scss.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Janaka
 *
 */
public enum ButtonType {

  RESET_BTN_PRESSED(1), SAVE_BTN_PRESSED(2), NO_BTN_PRESSED(3), YES_BTN_PRESSED(4), UPDATE_BTN_PRESSED(
      5), CONTINUE_BTN_PRESSED(6), SEARCH_BTN_PRESSED(7), UPLOAD_BTN_PRESSED(8), ARCHIVE_BTN_PRESSED(9)

  , ADD_AS_BTN_PRESSED(10), EDIT_BTN_PRESSED(11), TERMINATE_BTN_PRESSED(12), SUSPEND_BTN_PRESSED(13), ACTIVE_BTN_PRESSED(14), ISSUE_CRD_BTN_PRESSED(15)

  , BLACKLIST_BTN_PRESSED(16), LOGIN_BTN_PRESSED(17), NEWROLE_BTN_PRESSED(18), PRINT_BTN_PRESSED(19), PURGE_BTN_PRESSED(20), REMOVE_BTN_PRESSED(21), PREVIEW_BTN_PRESSED(22), VIEW_BTN_PRESSED(23), OK_BTN_PRESSED(24), OK_EXP_IMP_BTN_PRESSED(25), CREATE_NEW_ROLE_BTN_PRESSED(26), ADD_BTN_PRESSED(27), LEFT_BTN_PRESSED(28), RIGHT_BTN_PRESSED(29), ADD_ACCT_BTN_PRESSED(30), REJECT_BTN_PRESSED(31), DELETE_BTN_PRESSED(32), PRINT_BAK_BTN_PRESSED(33), REFER_BTN_PRESSED(34), MANUAL_BTN_PRESSED(35), OPEN_BTN_PRESSED(36), OPEN2_BTN_PRESSED(37), PROCEED_BTN_PRESSED(38), DETAIL_BTN_PRESSED(39)

  , YES_ADD_BTN_PRESSED(401), YES_ADD_AS_BTN_PRESSED(402), YES_ISSUE_CRD_BTN_PRESSED(403)

  , PREV_BTN_PRESSED(500), NEXT_BTN_PRESSED(501)

  , EXCEL_BTN_PRESSED(600);

  private final int buttonType;

  private ButtonType(int buttonType) {
    this.buttonType = buttonType;
  }

  public int getButtonType() {
    return buttonType;
  }

  private static final Map<Integer, ButtonType> LOOKUP = new HashMap<Integer, ButtonType>();

  static {
    for (ButtonType buttonType : EnumSet.allOf(ButtonType.class)) {
      LOOKUP.put(buttonType.getButtonType(), buttonType);
    }
  }

  public static ButtonType fromCode(int code) {
    return LOOKUP.get(code);
  }


}
