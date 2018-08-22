package com.facetuis.server.service.pinduoduo.response;

import java.io.Serializable;
import java.util.List;

public class GoodsSearchResponse implements Serializable {

    public class GoodsDetailsList{
        private List<GoodsDetails>  goods_list;
        private int total_count;

        public int getTotal_count() {
            return total_count;
        }
        public void setTotal_count(int total_count) {
            this.total_count = total_count;
        }
        public List<GoodsDetails> getGoods_list() {
            return goods_list;
        }
        public void setGoods_list(List<GoodsDetails> goods_list) {
            this.goods_list = goods_list;
        }
    }

    private GoodsDetailsList goods_search_response;




    public GoodsDetailsList getGoods_search_response() {
        return goods_search_response;
    }

    public void setGoods_search_response(GoodsDetailsList goods_search_response) {
        this.goods_search_response = goods_search_response;
    }
}
