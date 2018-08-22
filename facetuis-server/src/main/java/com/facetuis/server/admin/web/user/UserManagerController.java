package com.facetuis.server.admin.web.user;

import com.facetuis.server.app.web.basic.BaseResponse;
import com.facetuis.server.app.web.basic.FacetuisController;
import com.facetuis.server.model.user.User;
import com.facetuis.server.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/user")
@RestController
public class UserManagerController extends FacetuisController {
    @Autowired
    private UserService userService;

    //pageNumber 第几页  0开始  pageSize 每页数量
    //按条件查询 http://localhost/facetuis/user/findBy?page=1&limit=10&userName=
    @RequestMapping(value = "/findBy",method = RequestMethod.GET)
    public BaseResponse findBy(@Valid User user, Integer page, Integer limit, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return erroorResult(bindingResult);
        }else{
            return successResult(userService.findBy(user,page-1,limit));
        }
    }
    @RequestMapping(value = "/findById",method = RequestMethod.POST)
    public BaseResponse findById(@Valid User user, BindingResult bindingResult){
        if(user.getUuid() ==null || "".equals(user.getUuid())){
            return erroorResult(bindingResult);
        }
        return successResult(userService.findById(user.getUuid()));
    }
}
