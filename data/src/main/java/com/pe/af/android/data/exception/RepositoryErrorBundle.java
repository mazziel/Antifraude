package com.pe.af.android.data.exception;


import com.pe.af.android.domain.exception.IErrorBundle;

public class RepositoryErrorBundle implements IErrorBundle {

    private final Exception exception;

    public RepositoryErrorBundle(Exception exception) {
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }

    public String getErrorMessage() {
        String message = "";
        if (this.exception != null) {
            message = this.exception.getMessage();
        }
        return message;
    }
}
