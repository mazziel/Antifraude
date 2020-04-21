package com.pe.af.android.data.exception;

import android.text.TextUtils;

public class BaseException extends Exception {

    public final static String ERROR_GENERAL = "99999";
    public final static String ERROR_DATA_RESPONSE = "99998";
    public final static String ERROR_FAILURE = "99997";
    public final static String ERROR_CONNECTION = "99996";
    public final static String ERROR_SESSION_USER = "99995";
    public final static String ERROR_SESSION_SERVICE = "60000";
    public final static String ERROR_TOKEN_NOT_EXIST_SERVICE = "60001";

    private String gsCodeError;
    private String gsMessage;
    private String gsMessageError;
    private TAG goTag;

    public enum TAG {
        BUSINESS, EXCEPTION
    }

    public BaseException(String psCodeError, String psMessage, String psMessageError, Throwable poCause) {
        super(poCause);
        this.gsCodeError = psCodeError;
        this.gsMessage = psMessage;
        this.gsMessageError = psMessageError;
    }

    public BaseException(String psCodeError, String psMessage, String psMessageError) {
        super();
        this.gsCodeError = psCodeError;
        this.gsMessage = psMessage;
        this.gsMessageError = psMessageError;
    }

    public BaseException(String psCodeError, String psMessage, TAG poTag) {
        super();
        this.gsCodeError = psCodeError;
        this.gsMessage = psMessage;
        this.goTag = poTag;
    }

    public void setTag(TAG poTag) {
        this.goTag = poTag;
    }

    public TAG getTag() {
        return goTag == null ? TAG.EXCEPTION : goTag;
    }

    @Override
    public String getMessage() {
        return !TextUtils.isEmpty(gsMessage) ? gsMessage : "";
        /// /return !TextUtils.isEmpty(gsMessage) ? gsMessage : gsMessageError;
    }

    public String getMessageError() {
        return gsMessageError;
    }

    public String getCodeError() {
        return gsCodeError;
    }
}
