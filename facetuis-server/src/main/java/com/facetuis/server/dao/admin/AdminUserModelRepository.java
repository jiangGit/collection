package com.facetuis.server.dao.admin;

import com.facetuis.server.model.admin.AdminUserModel;
import com.facetuis.server.model.admin.AdminUsers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUserModelRepository extends JpaRepository<AdminUserModel,String> {
}
