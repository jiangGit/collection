package com.facetuis.server.service.pinduoduo.response;

public class GoodsDetails {

    private String goods_eval_count;
    private String goods_eval_score;
    private String category_name;
    private int coupon_min_order_amount;
    private int coupon_discount;
    private int coupon_total_quantity;
    private String coupon_start_time;
    private String coupon_end_time;
    private int promotion_rate;
    private String mall_name;
    private int category_id;
    private String goods_id;
    private String goods_name;
    private String goods_desc;
    private String goods_image_url;
    private String goods_gallery_urls;
    private int sold_quantity;
    private int min_group_price;
    private int min_normal_price;
    private int coupon_remain_quantity;
    private String goods_thumbnail_url;

    public String getGoods_thumbnail_url() {
        return goods_thumbnail_url;
    }

    public void setGoods_thumbnail_url(String goods_thumbnail_url) {
        this.goods_thumbnail_url = goods_thumbnail_url;
    }

    public int getCoupon_remain_quantity() {
        return coupon_remain_quantity;
    }

    public void setCoupon_remain_quantity(int coupon_remain_quantity) {
        this.coupon_remain_quantity = coupon_remain_quantity;
    }

    private long aboutEarn;// 预计赚

    public long getAboutEarn() {
        return aboutEarn;
    }

    public void setAboutEarn(long aboutEarn) {
        this.aboutEarn = aboutEarn;
    }

    public String getGoods_eval_count() {
        return goods_eval_count;
    }

    public void setGoods_eval_count(String goods_eval_count) {
        this.goods_eval_count = goods_eval_count;
    }

    public String getGoods_eval_score() {
        return goods_eval_score;
    }

    public void setGoods_eval_score(String goods_eval_score) {
        this.goods_eval_score = goods_eval_score;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public int getCoupon_min_order_amount() {
        return coupon_min_order_amount;
    }

    public void setCoupon_min_order_amount(int coupon_min_order_amount) {
        this.coupon_min_order_amount = coupon_min_order_amount;
    }

    public int getCoupon_discount() {
        return coupon_discount;
    }

    public void setCoupon_discount(int coupon_discount) {
        this.coupon_discount = coupon_discount;
    }

    public int getCoupon_total_quantity() {
        return coupon_total_quantity;
    }

    public void setCoupon_total_quantity(int coupon_total_quantity) {
        this.coupon_total_quantity = coupon_total_quantity;
    }

    public String getCoupon_start_time() {
        return coupon_start_time;
    }

    public void setCoupon_start_time(String coupon_start_time) {
        this.coupon_start_time = coupon_start_time;
    }

    public String getCoupon_end_time() {
        return coupon_end_time;
    }

    public void setCoupon_end_time(String coupon_end_time) {
        this.coupon_end_time = coupon_end_time;
    }

    public int getPromotion_rate() {
        return promotion_rate;
    }

    public void setPromotion_rate(int promotion_rate) {
        this.promotion_rate = promotion_rate;
    }

    public String getMall_name() {
        return mall_name;
    }

    public void setMall_name(String mall_name) {
        this.mall_name = mall_name;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_desc() {
        return goods_desc;
    }

    public void setGoods_desc(String goods_desc) {
        this.goods_desc = goods_desc;
    }

    public String getGoods_image_url() {
        return goods_image_url;
    }

    public void setGoods_image_url(String goods_image_url) {
        this.goods_image_url = goods_image_url;
    }

    public String getGoods_gallery_urls() {
        return goods_gallery_urls;
    }

    public void setGoods_gallery_urls(String goods_gallery_urls) {
        this.goods_gallery_urls = goods_gallery_urls;
    }

    public int getSold_quantity() {
        return sold_quantity;
    }

    public void setSold_quantity(int sold_quantity) {
        this.sold_quantity = sold_quantity;
    }

    public int getMin_group_price() {
        return min_group_price;
    }

    public void setMin_group_price(int min_group_price) {
        this.min_group_price = min_group_price;
    }

    public int getMin_normal_price() {
        return min_normal_price;
    }

    public void setMin_normal_price(int min_normal_price) {
        this.min_normal_price = min_normal_price;
    }
}
