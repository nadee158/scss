package com.privasia.scss.common.exception;

import javax.persistence.NoResultException;

public class ResultsNotFoundException extends NoResultException {

  private static final long serialVersionUID = 1L;

  public ResultsNotFoundException(String message) {
    super(message);
  }



}
