package com.facetuis.server.service.admin;

import com.facetuis.server.dao.admin.RolePermissionRepository;
import com.facetuis.server.model.admin.RolePermission;
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
public class RolePermissionService extends BasicService {
    @Autowired
    private RolePermissionRepository rolePermissionRepository;
    //保存权限
    public BaseResult<RolePermission> save(RolePermission rolePermission) {
        if(rolePermission.getUuid() !=null && !"".equals(rolePermission.getUuid())){
            //删除标记不为空则设置删除时间
            if(rolePermission.getIsDelete()!=null && rolePermission.getIsDelete()==1){
                rolePermission.setDeleteTime(new Date());
            }
        }else{
            rolePermission.setUuid(UUID.randomUUID().toString());
        }
        rolePermissionRepository.save(rolePermission);
        return  new BaseResult();
    }
    //按条件分页查询
    public Page<RolePermission> findBy(RolePermission rolePermission, int pageNumber, int pageSize) {
        Pageable pageable=new PageRequest(pageNumber, pageSize);  //分页信息
        Specification<RolePermission> spec = new Specification<RolePermission>() {        //查询条件构造
            @Override
            public Predicate toPredicate(Root<RolePermission> root, CriteriaQuery<?> query,CriteriaBuilder cb) {
                Path<String> role_id = root.get("role_id");
                Predicate p1 = cb.like(role_id,rolePermission.getRole_id());
                Predicate p = cb.and(p1);
                return p;
            }
        };
        return rolePermissionRepository.findAll(spec, pageable);
    }
    //获取一个
    public Optional<RolePermission> findById(String uuid) {
        return rolePermissionRepository.findById(uuid);
    }
}
