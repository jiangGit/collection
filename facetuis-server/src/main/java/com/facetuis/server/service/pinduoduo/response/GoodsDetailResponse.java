package com.facetuis.server.service.pinduoduo.response;

import java.io.Serializable;
import java.util.List;

public class GoodsDetailResponse implements Serializable {

    public class GoodsDetailsList{
        private List<GoodsDetails>  goods_details;
        public List<GoodsDetails> getGoods_details() {
            return goods_details;
        }
        public void setGoods_details(List<GoodsDetails> goods_details) {
            this.goods_details = goods_details;
        }
    }

    private GoodsDetailsList goods_detail_response;

    public GoodsDetailsList getGoods_detail_response() {
        return goods_detail_response;
    }

    public void setGoods_detail_response(GoodsDetailsList goods_detail_response) {
        this.goods_detail_response = goods_detail_response;
    }
}
