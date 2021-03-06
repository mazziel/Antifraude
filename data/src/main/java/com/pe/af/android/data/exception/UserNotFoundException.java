/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 */
package com.pe.af.android.data.exception;

/**
 * Exception throw by the application when a User search can't return a valid result.
 */
public class UserNotFoundException extends Exception {

  public UserNotFoundException() {
    super();
  }

  public UserNotFoundException(final String message) {
    super(message);
  }

  public UserNotFoundException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public UserNotFoundException(final Throwable cause) {
    super(cause);
  }
}
