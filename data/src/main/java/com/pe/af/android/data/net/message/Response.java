package com.pe.af.android.data.net.message;

import com.google.gson.annotations.SerializedName;

public class Response<T>
{
    public static int CODE_OK = 200;
    public static int CODE_ERROR = 0;

    public Response(){
        setMessage("");
        setCode(CODE_OK);
    }
    @SerializedName("result")
    private T payLoad;
    @SerializedName("msg")
    private String message;
    @SerializedName("codResult")
    private int code;


    public T getPayLoad() {
        return payLoad;
    }

    public void setPayLoad(T payLoad) {
        this.payLoad = payLoad;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}