package com.facetuis.server.admin.web.premission;

import com.facetuis.server.app.web.basic.BaseResponse;
import com.facetuis.server.app.web.basic.FacetuisController;
import com.facetuis.server.model.admin.RolePermission;
import com.facetuis.server.service.admin.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/admin/rolePremission")
@RestController
public class RolePremissionController extends FacetuisController {
    @Autowired
    private RolePermissionService rolePermissionService;

    //
    @RequestMapping(value = "/save",method = RequestMethod.GET)
    public BaseResponse save(@Valid RolePermission rolePermission, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return erroorResult(bindingResult);
        }else{
            rolePermissionService.save(rolePermission);
            return successResult("保存成功！");
        }
    }
    //pageNumber 第几页  0开始  pageSize 每页数量
    //按条件查询 http://localhost/facetuis/admin/findBy?userName=1&pageNumber=2&pageSize=1
    @RequestMapping(value = "/findBy",method = RequestMethod.GET)
    public BaseResponse findBy(@Valid RolePermission rolePermission,int pageNumber,int pageSize, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return erroorResult(bindingResult);
        }else{
            return successResult(rolePermissionService.findBy(rolePermission,pageNumber,pageSize));
        }
    }
    @RequestMapping(value = "/findById",method = RequestMethod.GET)
    public BaseResponse findById(@Valid RolePermission rolePermission, BindingResult bindingResult){
        if(rolePermission.getUuid() ==null || "".equals(rolePermission.getUuid())){
            return erroorResult(bindingResult);
        }
        return successResult(rolePermissionService.findById(rolePermission.getUuid()));
    }
}
