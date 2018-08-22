package com.facetuis.server.service.admin;

import com.facetuis.server.dao.admin.RoleRepository;
import com.facetuis.server.model.admin.Role;
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
public class RoleService extends BasicService {
    @Autowired
    private RoleRepository roleRepository;

    //保存角色
    public BaseResult<Role> save(Role role) {
        if(role.getUuid() !=null && !"".equals(role.getUuid())){
            //删除标记不为空则设置删除时间
            if(role.getIsDelete()!=null && role.getIsDelete()==1){
                role.setDeleteTime(new Date());
            }
        }else{
            role.setUuid(UUID.randomUUID().toString());
        }
        roleRepository.save(role);
        return  new BaseResult();
    }
    //按条件分页查询
    public Page<Role> findBy(Role role, int pageNumber, int pageSize) {
        Pageable pageable=new PageRequest(pageNumber, pageSize);  //分页信息
        Specification<Role> spec = new Specification<Role>() {        //查询条件构造
            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query,CriteriaBuilder cb) {
                Path<String> role_name = root.get("role_name");
                Predicate p1 = cb.like(role_name, "%"+role.getRole_name()+"%");
                Predicate p = cb.and(p1);
                return p;
            }
        };
        return roleRepository.findAll(spec, pageable);
    }
    //获取一个
    public Optional<Role> findById(String uuid) {
       return roleRepository.findById(uuid);
    }
}
