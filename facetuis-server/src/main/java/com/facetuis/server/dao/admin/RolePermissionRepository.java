package com.facetuis.server.dao.admin;

import com.facetuis.server.model.admin.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RolePermissionRepository extends JpaRepository<RolePermission,String>,JpaSpecificationExecutor<RolePermission> {

}
