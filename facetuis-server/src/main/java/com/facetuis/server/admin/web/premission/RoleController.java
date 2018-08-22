package com.facetuis.server.admin.web.premission;

import com.facetuis.server.app.web.basic.BaseResponse;
import com.facetuis.server.app.web.basic.FacetuisController;
import com.facetuis.server.model.admin.Role;
import com.facetuis.server.service.admin.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/admin/role")
@RestController
public class RoleController extends FacetuisController {
    @Autowired
    private RoleService roleService;

    //保存 http://localhost/facetuis/admin/role/save?role_name=1
    @RequestMapping(value = "/save",method = RequestMethod.GET)
    public BaseResponse save(@Valid Role role, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return erroorResult(bindingResult);
        }else{
            roleService.save(role);
            return successResult("保存权限成功！");
        }
    }
    //pageNumber 第几页  0开始  pageSize 每页数量
    //按条件查询 http://localhost/facetuis/admin/role/findBy?role_name=&pageNumber=0&pageSize=3
    @RequestMapping(value = "/findBy",method = RequestMethod.GET)
    public BaseResponse findBy(@Valid Role role,int pageNumber,int pageSize, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return erroorResult(bindingResult);
        }else{
            return successResult(roleService.findBy(role,pageNumber,pageSize));
        }
    }
    //获取一个 http://localhost/facetuis/admin/role/findById?uuid=
    //1aca67a2-fc4b-4081-9b23-745b20073b6b
    @RequestMapping(value = "/findById",method = RequestMethod.GET)
    public BaseResponse findById(@Valid Role role,BindingResult bindingResult){
        if(role.getUuid() ==null || "".equals(role.getUuid())){
            return erroorResult(bindingResult);
        }
        return successResult(roleService.findById(role.getUuid()));
    }
}
