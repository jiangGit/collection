package com.facetuis.server.service.admin;

import com.facetuis.server.dao.admin.AdminUserRepository;
import com.facetuis.server.model.admin.AdminUsers;
import com.facetuis.server.service.admin.vo.AminUsersVO;
import com.facetuis.server.service.basic.BaseResult;
import com.facetuis.server.service.basic.BasicService;
import com.facetuis.server.utils.MD5Utils;
import com.facetuis.server.utils.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class AdminUsersService extends BasicService {
    @Autowired
    private AdminUserRepository adminUserRepository;
    @Value("${sys.admin.salt}")
    private String salt;

    //根据userName 查用户信息并返回用户信息
    public BaseResult<AdminUsers> login(String user, String pass)
    {
        if(StringUtils.isEmpty(user) || StringUtils.isEmpty(pass) )
        {
            return null;
        }
        AdminUsers   adminUsers= adminUserRepository.findByUserName(user);

        if(adminUsers == null){
            return new BaseResult<>(500,"用户名不存在！");
        }

        String p1= MD5Utils.MD5(pass);
        String p2=MD5Utils.MD5(p1);
        String p3=MD5Utils.MD5(p2);
        String p4= MD5Utils.MD5( p3+salt);
        //String p3=MD
        if(!adminUsers.getPassword().equals(p4))
        {
            return new BaseResult<>(600,"用户名或密码错误！");

        }
        //生成Auto_token
        String token=RandomUtils.random(64);
        adminUsers.setAccessToken(token);
        //token保存在表中
        adminUserRepository.save(adminUsers);
        //登陆成功给返回角色和权限 和token
        AminUsersVO adminUsersVO =new AminUsersVO();
        adminUsersVO.setAccessToken(token);
        //获取UUid
        String uuid=adminUserRepository.findUUIDByAccessToken(token);
        Set<String> roleId=adminUserRepository.findRoleIdByAdminUUID(uuid);
        Set<String> permissionId=adminUserRepository.findPermissionIdByRoleId(roleId);
        //设置角色
        adminUsersVO.setRole(adminUserRepository.findRoleByID(roleId));
        //设置权限
        adminUsersVO.setPermission(adminUserRepository.findPermissionByID(permissionId));
        return  new BaseResult(adminUsersVO);
    }


    public AdminUsers findByAccessToken(String token){
        return adminUserRepository.findByAccessToken(token);
    }

    //保存管理员
    public BaseResult<AdminUsers> save(AdminUsers adminUsers) {
        if(adminUsers.getUuid() !=null && !"".equals(adminUsers.getUuid())){
            //删除标记不为空则设置删除时间
            if(adminUsers.getIsDelete()!=null && adminUsers.getIsDelete()==1){
                adminUsers.setDeleteTime(new Date());
            }
        }else{
            adminUsers.setUuid(UUID.randomUUID().toString());
        }
        //设置密码
        if(adminUsers.getPassword()!=null && !"".equals(adminUsers.getPassword())){
            String p1= MD5Utils.MD5(adminUsers.getPassword());
            String p2=MD5Utils.MD5(p1);
            String p3=MD5Utils.MD5(p2);
            String p4= MD5Utils.MD5( p3+salt);
            adminUsers.setPassword(p4);
        }
        adminUserRepository.save(adminUsers);
        return  new BaseResult();
    }
    //按条件分页查询
    public Page<AdminUsers> findBy(AdminUsers adminUsers, int pageNumber, int pageSize) {
        Pageable pageable=new PageRequest(pageNumber, pageSize);  //分页信息
        Specification<AdminUsers> spec = new Specification<AdminUsers>() {        //查询条件构造
            @Override
            public Predicate toPredicate(Root<AdminUsers> root, CriteriaQuery<?> query,CriteriaBuilder cb) {
                Path<String> user_name = root.get("userName");
                Predicate p1 = cb.like(user_name, "%"+adminUsers.getUserName()+"%");
                Predicate p = cb.and(p1);
                return p;
            }
        };
        return adminUserRepository.findAll(spec, pageable);
    }
    //获取一个
    public Optional<AdminUsers> findById(String uuid) {
        return adminUserRepository.findById(uuid);
    }
}
