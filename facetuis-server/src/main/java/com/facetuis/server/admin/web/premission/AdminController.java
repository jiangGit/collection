package com.facetuis.server.admin.web.premission;

import com.facetuis.server.app.web.basic.BaseResponse;
import com.facetuis.server.app.web.basic.FacetuisController;
import com.facetuis.server.model.admin.AdminUsers;
import com.facetuis.server.service.admin.AdminUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/admin")
@RestController
public class AdminController extends FacetuisController {
    @Autowired
    private AdminUsersService adminUsersService;

    //注册 http://localhost/facetuis/admin/save?userName=123&password=123
    @RequestMapping(value = "/save",method = RequestMethod.GET)
    public BaseResponse save(@Valid AdminUsers adminUsers, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return erroorResult(bindingResult);
        }else{
            adminUsersService.save(adminUsers);
            return successResult("保存成功！");
        }
    }
    //pageNumber 第几页  0开始  pageSize 每页数量
    //按条件查询 http://localhost/facetuis/admin/findBy?page=1&limit=10&userName=
    @RequestMapping(value = "/findBy",method = RequestMethod.GET)
    public BaseResponse findBy(@Valid AdminUsers adminUsers,Integer page,Integer limit, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return erroorResult(bindingResult);
        }else{
            return successResult(adminUsersService.findBy(adminUsers,page-1,limit));
        }
    }
    @RequestMapping(value = "/findById",method = RequestMethod.POST)
    public BaseResponse findById(@Valid AdminUsers adminUsers, BindingResult bindingResult){
        if(adminUsers.getUuid() ==null || "".equals(adminUsers.getUuid())){
            return erroorResult(bindingResult);
        }
        return successResult(adminUsersService.findById(adminUsers.getUuid()));
    }
}
