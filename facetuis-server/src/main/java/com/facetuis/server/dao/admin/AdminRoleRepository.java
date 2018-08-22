package com.facetuis.server.dao.admin;

import com.facetuis.server.model.admin.AdminRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AdminRoleRepository extends JpaRepository<AdminRole,String>,JpaSpecificationExecutor<AdminRole> {

}
