package com.facetuis.server.app.web.basic;

import com.facetuis.server.service.basic.BaseResult;
import com.facetuis.server.sys.SysCode;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

public class FacetuisController extends UserController {


    /**
     * 设置参数错误响应信息
     * @param result
     * @param code
     * @param memo
     * @return
     */
    protected BaseResponse setErrorResult(BindingResult result, int code, String memo) {
        BaseResponse response = new BaseResponse();
        response.setCode(code);
        if(result != null){
            response.setMessage(memo + " : " + getErrorMessages(result.getAllErrors()));
        }else{
            response.setMessage(memo);
        }
        return response;
    }

    /**
     * 设置参数错误响应信息
     * @param code
     * @param memo
     * @return
     */
    protected BaseResponse setErrorResult(int code,String memo) {
        BaseResponse response = new BaseResponse();
        response.setCode(code);
        response.setMessage(memo);
        return response;
    }

    /**
     * 设置请求成功信息
     * @param response
     * @return
     */
    protected BaseResponse setSuccestResult(BaseResponse response) {
        response.setCode(SysCode.SUCCESS);
        response.setMessage(SysCode.SUCCESS_MESSAGE);
        return response;
    }

    protected BaseResponse successResult(Object result){
        BaseResponse response = new BaseResponse();
        response.setCode(SysCode.SUCCESS);
        response.setMessage(SysCode.SUCCESS_MESSAGE);
        response.setResult(result);
        return response;
    }

    /**
     * 设置请求成功信息
     * @return
     */
    protected BaseResponse successResult(){
        return new BaseResponse();
    }

    protected BaseResponse erroorResult(BindingResult result) {
        BaseResponse response = new BaseResponse();
        response.setCode(SysCode.REQUESTERROR);
        response.setMessage(SysCode.REQUESTERROR_MESSAGE + " : " + getErrorMessages(result.getAllErrors()));
        return response;
    }

    protected BaseResponse onResult(BaseResult result) {
        BaseResponse response = new BaseResponse();
        response.setCode(result.getCode());
        response.setMessage(result.getMessage());
        response.setResult(result.getResult());
        return response;
    }

    /**
     * 从result中获取错误信息
     * @param errors
     * @return
     */
    private String getErrorMessages(List<ObjectError> errors) {
        StringBuffer buffer = new StringBuffer();
        for(ObjectError error:errors) {
            if(buffer.length() > 0){
                buffer.append(",");
            }
            buffer.append(error.getDefaultMessage());
        }
        return buffer.toString();
    }

}
