package com.pe.af.android.domain.exception;

public class ErrorBundle implements IErrorBundle {

    private Exception error;

    public ErrorBundle(Exception e) {
        this.error = e;
    }

    @Override
    public Exception getException() {
        return error;
    }

    @Override
    public String getErrorMessage() {
        if (error != null)
            return error.getMessage();
        else
            return "No definido";
    }
}
