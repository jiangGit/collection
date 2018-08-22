package com.facetuis.server.service.basic;

import org.springframework.util.StringUtils;

public class BaseResult<T> {
    private int code = 200;
    private String message = "ok";
    private T result;

    public BaseResult(){

    }
    public BaseResult(T result){
        this.result = result;
    }
    public BaseResult(int code,String message){
        this.code = code;
        this.message = message;
    }

    public boolean hasError(){
        if(StringUtils.isEmpty(code)){
            return false;
        }
        if(code == 200 ){
            return false;
        }
        return true;
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

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
