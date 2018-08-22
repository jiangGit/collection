package com.facetuis.server.dao.admin;

import com.facetuis.server.model.admin.AdminUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface AdminUserRepository extends JpaRepository<AdminUsers,String>,JpaSpecificationExecutor<AdminUsers> {
    @Query(value = "select * from t_admin_users where user_name=?1",nativeQuery = true)
    public AdminUsers findByUserName (String userName);
    @Query(value = "select * from t_admin_users where access_token=?1",nativeQuery = true)
    public AdminUsers findByAccessToken(String accessToken);
    // 根据token获取用户uuid
    @Query(value = "select uuid from t_admin_users where access_token=?1",nativeQuery = true)
    public String findUUIDByAccessToken(String accessToken);
    //根据uuid获取角色uuID
    @Query(value = "select uuid from t_admin_role where admin_id=?1",nativeQuery = true)
    public Set<String> findRoleIdByAdminUUID(String uuId);
    //根据角色uuID获取角色名
    @Query(value = "select role_name from t_role where uuid=?1",nativeQuery = true)
    public Set<String> findRoleByID(Set<String> roleId);
    //根据角色ID获取权限Id
    @Query(value = "select permission_id from t_role_permission where role_id=?1",nativeQuery = true)
    public Set<String> findPermissionIdByRoleId(Set<String> roleId);
    //根据权限ID获取权限名
    @Query(value = "select permission_name from t_permission where uuid=?1",nativeQuery = true)
    public Set<String> findPermissionByID(Set<String> permissionId);

}
