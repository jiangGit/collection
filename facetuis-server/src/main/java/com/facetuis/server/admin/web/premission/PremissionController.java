package com.facetuis.server.admin.web.premission;

import com.facetuis.server.app.web.basic.BaseResponse;
import com.facetuis.server.app.web.basic.FacetuisController;
import com.facetuis.server.model.admin.Permission;
import com.facetuis.server.service.admin.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/admin/premission")
@RestController
public class PremissionController extends FacetuisController {
    @Autowired
    private PermissionService permissionService;

    //
    @RequestMapping(value = "/save",method = RequestMethod.GET)
    public BaseResponse save(@Valid Permission permission, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return erroorResult(bindingResult);
        }else{
            permissionService.save(permission);
            return successResult("保存成功！");
        }
    }
    //pageNumber 第几页  0开始  pageSize 每页数量
    //按条件查询 http://localhost/facetuis/admin/findBy?userName=1&pageNumber=2&pageSize=1
    @RequestMapping(value = "/findBy",method = RequestMethod.GET)
    public BaseResponse findBy(@Valid Permission permission,int pageNumber,int pageSize, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return erroorResult(bindingResult);
        }else{
            return successResult(permissionService.findBy(permission,pageNumber,pageSize));
        }
    }
    @RequestMapping(value = "/findById",method = RequestMethod.GET)
    public BaseResponse findById(@Valid Permission permission, BindingResult bindingResult){
        if(permission.getUuid() ==null || "".equals(permission.getUuid())){
            return erroorResult(bindingResult);
        }
        return successResult(permissionService.findById(permission.getUuid()));
    }
}
