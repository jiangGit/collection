package com.facetuis.server.admin.web.premission;

import com.facetuis.server.app.web.basic.BaseResponse;
import com.facetuis.server.app.web.basic.FacetuisController;
import com.facetuis.server.model.admin.AdminRole;
import com.facetuis.server.service.admin.AdminRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/admin/adminRole")
@RestController
public class AdminRoleController extends FacetuisController {
    @Autowired
    private AdminRoleService adminRoleService;

    //保存 http://localhost/facetuis/admin/role/save?role_name=1
    @RequestMapping(value = "/save",method = RequestMethod.GET)
    public BaseResponse save(@Valid AdminRole adminRole, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return erroorResult(bindingResult);
        }else{
            adminRoleService.save(adminRole);
            return successResult("保存权限成功！");
        }
    }
    //pageNumber 第几页  0开始  pageSize 每页数量
    //按条件查询 http://localhost/facetuis/admin/role/findBy?role_name=1&pageNumber=0&pageSize=1
    @RequestMapping(value = "/findBy",method = RequestMethod.GET)
    public BaseResponse findBy(@Valid AdminRole adminRole,int pageNumber,int pageSize, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return erroorResult(bindingResult);
        }else{
            return successResult(adminRoleService.findBy(adminRole,pageNumber,pageSize));
        }
    }
    //获取一个 http://localhost/facetuis/admin/role/findById?uuid=
    //1aca67a2-fc4b-4081-9b23-745b20073b6b
    @RequestMapping(value = "/findById",method = RequestMethod.GET)
    public BaseResponse findById(@Valid AdminRole adminRole,BindingResult bindingResult){
        if(adminRole.getUuid() ==null || "".equals(adminRole.getUuid())){
            return erroorResult(bindingResult);
        }
        return successResult(adminRoleService.findById(adminRole.getUuid()));
    }
}
