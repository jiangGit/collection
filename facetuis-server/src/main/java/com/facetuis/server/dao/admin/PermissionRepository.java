package com.facetuis.server.dao.admin;

import com.facetuis.server.model.admin.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PermissionRepository extends JpaRepository<Permission,String>,JpaSpecificationExecutor<Permission> {

}
