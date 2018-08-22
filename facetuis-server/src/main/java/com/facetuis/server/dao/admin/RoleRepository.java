package com.facetuis.server.dao.admin;

import com.facetuis.server.model.admin.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoleRepository extends JpaRepository<Role,String>,JpaSpecificationExecutor<Role> {

}
