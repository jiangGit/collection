package com.facetuis.server.admin.web;

import com.facetuis.server.admin.web.request.LoginRequest;
import com.facetuis.server.app.web.basic.BaseResponse;
import com.facetuis.server.app.web.basic.FacetuisController;
import com.facetuis.server.model.admin.AdminUsers;
import com.facetuis.server.service.admin.AdminUsersService;
import com.facetuis.server.service.basic.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admin/login")
@RestController
public class AdminLoginController extends FacetuisController {
    @Autowired
    private AdminUsersService adminUsersService;

    //http://localhost/facetuis-server/admin/login
    @RequestMapping(method = RequestMethod.POST)
    public BaseResponse login(@RequestBody LoginRequest loginRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return erroorResult(bindingResult);
        }
       BaseResult<AdminUsers> baseResult= adminUsersService.login(loginRequest.getUserName(),loginRequest.getPassword()) ;//这里调用Service里login
        if(baseResult.hasError()){
            return onResult(baseResult);
        }
        return successResult(baseResult.getResult());
    }
}
