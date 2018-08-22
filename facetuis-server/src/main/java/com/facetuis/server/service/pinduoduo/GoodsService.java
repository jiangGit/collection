package com.facetuis.server.service.pinduoduo;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.domain.GoodsDetail;
import com.facetuis.server.app.web.response.PromontionResponse;
import com.facetuis.server.app.web.response.PromotionUrl;
import com.facetuis.server.dao.goods.GoodsRepository;
import com.facetuis.server.model.goods.Goods;
import com.facetuis.server.model.user.UserLevel;
import com.facetuis.server.service.basic.BaseResult;
import com.facetuis.server.service.basic.BasicService;
import com.facetuis.server.service.pinduoduo.response.GoodsDetailResponse;
import com.facetuis.server.service.pinduoduo.response.GoodsDetails;
import com.facetuis.server.service.pinduoduo.response.GoodsSearchResponse;
import com.facetuis.server.service.pinduoduo.utils.PRequestUtils;
import com.facetuis.server.utils.Base64Util;
import com.facetuis.server.utils.CommisionUtils;
import com.facetuis.server.utils.GoodsImageUtils;
import com.facetuis.server.utils.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.logging.Logger;

@Service
public class GoodsService extends BasicService {


    private Logger logger = Logger.getLogger(GoodsService.class.getName());

    @Autowired
    private PRequestUtils pRequestUtils;

    private String API_GOODS_DETAIL = "pdd.ddk.goods.detail";
    private String API_GOODS_LIST_KWYWORDS = "pdd.ddk.goods.search";

    private String API_GOODS_PROMOTION_URL = "pdd.ddk.goods.promotion.url.generate";

    @Value("${sys.goods.backgroud.image}")
    private String goodsBackgroundImage;



    @Autowired
    private GoodsRepository goodsRepository;


    public void addShare(String goodsId){
        Goods goods = goodsRepository.findByGoodsId(goodsId);
        if(goods != null) {
            goods.setShareNum(goods.getShareNum() + 15);
        }else{
            goods = new Goods();
            goods.setUuid(UUID.randomUUID().toString());
            goods.setGoodsId(goodsId);
            goods.setShareNum(Long.parseLong(RandomUtils.rate(3000,8000)));
        }
        goodsRepository.save(goods);
    }

    public Long getShare(String goodsId){
        Goods goods = goodsRepository.findByGoodsId(goodsId);
        if(goods != null){
            return goods.getShareNum();
        }
        goods = new Goods();
        goods.setShareNum(Long.parseLong(RandomUtils.rate(3000,8000)));
        goods.setGoodsId(goodsId);
        goods.setUuid(UUID.randomUUID().toString());
        goodsRepository.save(goods);
        return goods.getShareNum();
    }


    public GoodsDetails getGoodsById(String id,int level){
        SortedMap<String,String> map = new TreeMap<>();
        map.put("goods_id_list","[" + id + "]");
        BaseResult<String> send = pRequestUtils.send(API_GOODS_DETAIL, map);
        GoodsDetailResponse details = JSONObject.parseObject(send.getResult(),GoodsDetailResponse.class);
        if(details !=null && details.getGoods_detail_response() != null && details.getGoods_detail_response().getGoods_details().size() > 0){
            GoodsDetails goodsDetails = details.getGoods_detail_response().getGoods_details().get(0);
            if(goodsDetails == null){
                return goodsDetails;
            }
            goodsDetails.setAboutEarn(CommisionUtils.getEarn(level,(goodsDetails.getMin_group_price() -  goodsDetails.getCoupon_discount())  * goodsDetails.getPromotion_rate() ));
            return goodsDetails;
        }
        return null;
    }

    public Page<GoodsDetails> findByWrods(String words,String categoryId,String softType,int page,int level,String rangeList){

        SortedMap<String,String> map = new TreeMap<>();
        if(words != null) {
            map.put("keyword", words.equals("0") ? "" : words);
        }
        if(categoryId != null) {
            map.put("category_id", categoryId.equals("0") ? "" : categoryId);
        }
        map.put("sort_type",softType);
        map.put("page",page + "");
        map.put("page_size","50");
        map.put("with_coupon","true");
        if(!StringUtils.isEmpty(rangeList) ) {
            try {
                rangeList = URLEncoder.encode(rangeList, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            map.put("range_list", rangeList );
        }
        BaseResult<String> send = pRequestUtils.send(API_GOODS_LIST_KWYWORDS, map);
        GoodsSearchResponse details = JSONObject.parseObject(send.getResult(),GoodsSearchResponse.class);
        if(details != null) {
            if (details.getGoods_search_response() != null) {
                List<GoodsDetails> goods_list = details.getGoods_search_response().getGoods_list();
                for (GoodsDetails goodsDetail : goods_list) {
                    goodsDetail.setAboutEarn(CommisionUtils.getEarn(level, (goodsDetail.getMin_group_price() -  goodsDetail.getCoupon_discount()) * goodsDetail.getPromotion_rate() ));
                }
                return new PageImpl<>(details.getGoods_search_response().getGoods_list());
            }
        }
        return new PageImpl<>(Collections.EMPTY_LIST);
    }

    /**
     * 商品推广链接
     * @param pid
     * @param goodsId
     * @param isShortUrl
     * @return
     */
    public PromotionUrl promontion(String pid, String goodsId, boolean isShortUrl){
        SortedMap<String,String> map = new TreeMap<>();
        map.put("goods_id_list","[" + goodsId + "]");
        map.put("p_id",pid);
        map.put("generate_short_url",isShortUrl + "");
        BaseResult<String> send = pRequestUtils.send(API_GOODS_PROMOTION_URL, map);
        PromontionResponse details = JSONObject.parseObject(send.getResult(),PromontionResponse.class);
        if(details.getGoods_promotion_url_generate_response() != null){
            return details.getGoods_promotion_url_generate_response().getGoods_promotion_url_list().get(0);
        }
        return null;
    }

    public byte[] createImage(String goodsId,String link ,Integer imageIndex,Boolean settingRQInGoodsImage) {
        GoodsDetails goods = getGoodsById(goodsId, UserLevel.LEVEL1.getLevel());
        if(goods != null){
            byte[] image = GoodsImageUtils.createImage(goodsBackgroundImage, goods,link,imageIndex,settingRQInGoodsImage);
            return image;
        }
        System.out.println("...........................................");
        return null;
    }
}
