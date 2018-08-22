package com.facetuis.server.app.web.basic;

import java.io.Serializable;

public class BaseResponse implements Serializable {

    private int code = 200;
    private String message = "ok";
    private Object result = new String();

    public BaseResponse(){

    }

    public BaseResponse(int code ,String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
