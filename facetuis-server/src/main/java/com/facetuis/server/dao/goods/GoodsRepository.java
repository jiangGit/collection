package com.facetuis.server.dao.goods;

import com.facetuis.server.model.banner.Banner;
import com.facetuis.server.model.goods.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<Goods,String> {


    Goods findByGoodsId(String goodsId);
}
