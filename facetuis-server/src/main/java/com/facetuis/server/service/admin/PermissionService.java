package com.facetuis.server.service.admin;

import com.facetuis.server.dao.admin.PermissionRepository;
import com.facetuis.server.model.admin.Permission;
import com.facetuis.server.service.basic.BaseResult;
import com.facetuis.server.service.basic.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class PermissionService extends BasicService {
    @Autowired
    private PermissionRepository permissionRepository;
    //保存权限
    public BaseResult<Permission> save(Permission permission) {
        if(permission.getUuid() !=null && !"".equals(permission.getUuid())){
            //删除标记不为空则设置删除时间
            if(permission.getIsDelete()!=null && permission.getIsDelete()==1){
                permission.setDeleteTime(new Date());
            }
        }else{
            permission.setUuid(UUID.randomUUID().toString());
        }
        permissionRepository.save(permission);
        return  new BaseResult();
    }
    //按条件分页查询
    public Page<Permission> findBy(Permission permission, int pageNumber, int pageSize) {
        Pageable pageable=new PageRequest(pageNumber, pageSize);  //分页信息
        Specification<Permission> spec = new Specification<Permission>() {        //查询条件构造
            @Override
            public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> query,CriteriaBuilder cb) {
                Path<String> permission_name = root.get("permission_name");
                Predicate p1 = cb.like(permission_name, "%"+permission.getPermission_name()+"%");
                Predicate p = cb.and(p1);
                return p;
            }
        };
        return permissionRepository.findAll(spec, pageable);
    }
    //获取一个
    public Optional<Permission> findById(String uuid) {
        return permissionRepository.findById(uuid);
    }
}
