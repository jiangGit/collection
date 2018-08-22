package com.facetuis.server.dao.banner;

import com.facetuis.server.model.admin.AdminModel;
import com.facetuis.server.model.banner.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepository extends JpaRepository<Banner,String> {

}
