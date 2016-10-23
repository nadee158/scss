package com.privasia.scss.common.constant;

import java.nio.charset.Charset;

public interface ApplicationConstants {


  public static final String SUCCESS = "SUCCESS";
  public static final String ERROR = "ERROR";
  public static final String UNSUCCESS = "UNSUCCESS";

  public static final String YEAR = "YEAR";
  public static final String MONTH = "MONTH";
  public static final String DATE = "DATE";

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

  public static final String FILE_TYPE_PDF = "PDF";
  public static final String FILE_TYPE_WORD = "WORD";
  public static final String FILE_TYPE_IMAGE = "IMAGE";
  public static final String FILE_TYPE_EXCEL = "EXCEL";
  public static final String FILE_TYPE_WORD_TO_PDF = "WORD_TO_PDF";
  public static final String FILE_TYPE_ODT = "ODT";

  public static int STATUS_CODE_OK = 200; // - Ok The operation was successful and a response has
  // been returned.

  public static int STATUS_CODE_CREATED = 201; // - The operation was successful and the entity has
  // been created and is returned in the response-body
  // (POST request).
  public static int STATUS_CODE_NO_CONTENT = 204; // - The operation was successful and entity has
  // been deleted and therefore there is no
  // response-body returned (DELETE request).
  public static int STATUS_CODE_UNAUTHORIZED = 401; // - The operation failed. The operation
  // requires an Authentication header to be set.
  // If this was present in the request, the
  // supplied credentials are not valid or the
  // user is not authorized to perform this
  // operation.
  public static int STATUS_CODE_FORBIDDEN = 403; // - The operation is forbidden and should not be
  // re-attempted. This does not imply an issue with
  // authentication not authorization, it’s an
  // operation that is not allowed. Example: deleting
  // a task that is part of a running process is not
  // allowed and will never be allowed, regardless of
  // the user or process/task state.
  public static int STATUS_CODE_NOT_FOUND = 404; // - The operation failed.The requested resource
  // was not found.
  public static int STATUS_CODE_METHOD_NOT_ALLOWED = 405; // - The operation failed. The used method
  // is not allowed for this resource. E.g.
  // trying to update (PUT) a
  // deployment-resource will result in a
  // 405 status.
  public static int STATUS_CODE_CONFLICT = 409; // - The operation causes an update of a resource
  // that has been updated by another operation, which
  // makes the update no longer valid. Can also
  // indicate a resource that is being created in a
  // collection where a resource with that identifier
  // already exists.
  public static int STATUS_CODE_UNSUPPORTED_MEDIA_TYPE = 415; // - The operation failed. The request
  // body contains an unsupported media
  // type. Also occurs when the
  // request-body JSON contains an
  // unknown attribute or value that
  // doesn’t have the right format/type
  // to be accepted.
  public static int STATUS_CODE_INTERNAL_SERVER_ERROR = 500; // - Internal server error


  public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
  public static final Charset UTF_8 = Charset.forName("UTF-8");

  public static final String EXTENSION_TYPE_JRXML = ".jrxml";

  public static final String CHARACTER_ENCODING_UTF8 = "utf-8";
  public static final String REPORT_NOT_FOUND_IMAGE = null;



}
