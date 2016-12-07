package com.privasia.scss.common.util;

public interface ApplicationConstants {

  // IREPORT VARIABLES
  public static final String MEDIA_TYPE_EXCEL = "application/vnd.ms-excel";
  // media setting for a pdf file
  public static final String MEDIA_TYPE_PDF = "application/pdf";
  // media setting for a ms doc file
  public static final String MEDIA_TYPE_WORD =
      "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
  // media setting for a csv file
  public static final String MEDIA_TYPE_CSV = "text/csv";

  public static final String EXTENSION_TYPE_CSV = ".csv";
  public static final String EXTENSION_TYPE_EXCEL = ".xls";
  public static final String EXTENSION_TYPE_PDF = ".pdf";
  public static final String EXTENSION_TYPE_WORD_NEW = ".docx";
  public static final String EXTENSION_TYPE_WORD_OLD = ".doc";
  public static final String EXTENSION_TYPE_PNG = ".png";
  public static final String EXTENSION_TYPE_JPG = ".jpg";
  public static final String EXTENSION_TYPE_JRXML = ".jrxml";

  public static final String FILE_TYPE_PDF = "PDF";
  public static final String FILE_TYPE_WORD = "WORD";
  public static final String FILE_TYPE_IMAGE = "IMAGE";
  public static final String FILE_TYPE_EXCEL = "EXCEL";
  public static final String FILE_TYPE_WORD_TO_PDF = "WORD_TO_PDF";
  public static final String FILE_TYPE_ODT = "ODT";

  public static final String CHARACTER_ENCODING_UTF8 = "utf-8";
  public static final String REPORT_NOT_FOUND_IMAGE = null;

  public static final int OK = 200;// OK
  public static final int UNAUTHORIZED = 401;// Unauthorized
  public static final int FORBIDDEN = 403;// Forbidden
  public static final int INTERNAL_SERVER_ERROR = 500;// Internal Server Error

  public static final String SUCCESS = "SUCCESS";
  public static final String ERROR = "ERROR";

  public static final int EIR_NO_EXIST = 1;
  public static final int COMPANY_NOT_ACTIVE = 2;
  public static final int CARD_NOT_ACTIVE = 3;
  public static final int TRUCK_PLATE_NO_IN_USE = 4;
  public static final int TRUCK_HEAD_NO_IN_USE = 5;
  public static final int ERROR_EXIST = 6;
  public static final int MPL = 7;
  public static final int NO_ERROR = 8;
  public static final int AGS_REPLY_TIME_OUT = 9;
  public static final int MAX_WEIGHTC1 = 10;
  public static final int MAX_WEIGHTC2 = 11;
  public static final int PMCT = 12;
  public static final int NET_WEIGHT = 13;
  public static final int DG_REMARKS = 14;
  public static final int DG_CHECKBOX = 15;
  public static final int DG_CANTBYPASS = 16;

  public static final int GI_OK = 0;

  public static final String GATE_PASS_IS_USED = "ERR_MSG_060";
  public static final String GATE_PASS_IN_PROGRESS = "ERR_MSG_061";
  public static final String GATE_PASS_CANCEL = "ERR_MSG_062";
  public static final String GATE_PASS_INVALID = "ERR_MSG_011";
  public static final String GATE_PASS_COMPANY_NOT_MATCH = "ERR_MSG_012";
  public static final String GATE_PASS_NO_PREARRIVAL = "ERR_MSG_800";
  public static final String GATE_PASS_OGA_BLOCK = "ERR_MSG_073";
  public static final String GATE_PASS_INTERNAL_BLOCK = "ERR_MSG_072";
  public static final String GATE_PASS_OGA_INTERNAL_BLOCK = "ERR_MSG_074";
  public static final String DATE_GATEPASS_EXPIRY = "ERR_MSG_080";
  public static final String DATE_GATEPASS_EDO_EXPIRY = "ERR_MSG_088";
  public static final String EDO_EXPIRY_DATE_NULL = "ERR_MSG_090";

  public static final String HDBS_START_HOUR = "HDBS_START";
  public static final String HDBS_END_HOUR = "HDBS_END";

  public static final String HDBS_ACCEPTED_START = "HDBS_ACC_S";
  public static final String HDBS_ACCEPTED_END = "HDBS_ACC_E";
  public static final String HDBS_MANUAL = "HDBS_MANU";

  public static final int DEFAULT_HDBS_START_HOUR_VALUE = -6;
  public static final int DEFAULT_HDBS_END_HOUR_VALUE = 6;
  public static final int DEFAULT_HDBS_ACCEPTED_START_VALUE = -6;
  public static final int DEFAULT_HDBS_ACCEPTED_END_VALUE = 6;

  public static final String ZIP_FILE_COLLECTION = "zipFile";

  public static final String PDF_FILE_COLLECTION = "pdfFile";

  public static final String SOLAS_CERTIFICATE_COLLECTION = "solasCertificate";

  public static final String ODD_TRANSACTION = "oddWHTransaction";
  public static final String IMP_TRANSACTION = "importTransaction";
  public static final String EXP_TRANSACTION = "exportTransaction";
  public static final String IMP_EXP_TRANSACTION = "importExportTransaction";
  public static final String REFER_TRANSACTION = "referTransaction";
  public static final String NO_TRANSACTION = "noTransaction";

  public static final String ON_TIME = "ON-TIME";
  public static final String ACTIVE = "ACTIVE";
  public static final String LATE = "LATE";
  public static final String COMPLETE = "COMP";
  public static final String EXPIRED = "EXPIRED";
  public static final String EARLY = "EARLY";
  public static final String CANCEL = "CAN";

  public static final String MESSAGE_OK = "OK";
  public static final String MESSAGE_NOK = "NOK";
  public static final String MESSAGE_CODE = "code";
  public static final String MESSAGE_DESCRIPTION = "description";

  public static final String SMTP_SUBJECT = "SMTP_SUBJ";
  public static final String SMTP_HOST = "SMTP_HOST";
  public static final String SMTP_FROM = "SMTP_FROM";
  public static final String SMTP_TO = "SMTP_TO";
  public static final String SMTP_CC = "SMTP_CC";
  public static final String SMTP_BCC = "SMTP_BCC";
}
