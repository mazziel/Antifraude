package com.pe.af.android.domain.exception;

public class NotExistIErrorBundle implements IErrorBundle {

    private String nombre;

    public NotExistIErrorBundle(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public Exception getException() {
        return new Exception("Not Exist " + nombre);
    }

    @Override
    public String getErrorMessage() {
        return "Not exist " + nombre;
    }
}
