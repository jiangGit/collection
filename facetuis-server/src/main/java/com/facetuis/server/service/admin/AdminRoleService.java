package com.facetuis.server.service.admin;

import com.facetuis.server.dao.admin.AdminRoleRepository;
import com.facetuis.server.model.admin.AdminRole;
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
public class AdminRoleService extends BasicService {
    @Autowired
    private AdminRoleRepository adminRoleRepository;

    //保存角色
    public BaseResult<AdminRole> save(AdminRole adminRole) {
        if(adminRole.getUuid() !=null && !"".equals(adminRole.getUuid())){
            //删除标记不为空则设置删除时间
            if(adminRole.getIsDelete()!=null && adminRole.getIsDelete()==1){
                adminRole.setDeleteTime(new Date());
            }
        }else{
            adminRole.setUuid(UUID.randomUUID().toString());
        }
        adminRoleRepository.save(adminRole);
        return  new BaseResult();
    }
    //按条件分页查询
    public Page<AdminRole> findBy(AdminRole adminRole, int pageNumber, int pageSize) {
        Pageable pageable=new PageRequest(pageNumber, pageSize);  //分页信息
        Specification<AdminRole> spec = new Specification<AdminRole>() {        //查询条件构造
            @Override
            public Predicate toPredicate(Root<AdminRole> root, CriteriaQuery<?> query,CriteriaBuilder cb) {
                Path<String> admin_id = root.get("admin_id");
                Predicate p1 = cb.like(admin_id, adminRole.getAdmin_id());
                Predicate p = cb.and(p1);
                return p;
            }
        };
        return adminRoleRepository.findAll(spec, pageable);
    }
    //获取一个
    public Optional<AdminRole> findById(String uuid) {
       return adminRoleRepository.findById(uuid);
    }
}
