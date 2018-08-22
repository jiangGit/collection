package com.facetuis.server.utils;

import com.alipay.api.domain.GoodsDetail;
import com.facetuis.server.service.pinduoduo.response.GoodsDetails;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class GoodsImageUtils {

    public static byte[] createImage(String backgroundImgPath, GoodsDetails goodsDetail,String link,Integer imageIndex,Boolean settingRQInGoodsImage){
        String goodsImageUrl = goodsDetail.getGoods_image_url();
        // 获取产品主图
        BufferedImage goodsImage = null;
        if(imageIndex == null || imageIndex == 0 ) {
            try {
                goodsImage = ImageIO.read(new URL(goodsImageUrl));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            String goods_gallery_urls = goodsDetail.getGoods_gallery_urls();
            goods_gallery_urls = goods_gallery_urls.replace("[","");
            goods_gallery_urls = goods_gallery_urls.replace("]","");
            goods_gallery_urls = goods_gallery_urls.replaceAll("\"","");
            String[] split = goods_gallery_urls.split(",");
            if(imageIndex < split.length) {
                goodsImageUrl = split[imageIndex - 1];
                if(!StringUtils.isEmpty(goodsImageUrl)) {
                    try {
                        goodsImage = ImageIO.read(new URL(goodsImageUrl));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    return null;
                }
            }else{
                return null;
            }
        }
        BufferedImage rqImageBuffered = null;
        if(settingRQInGoodsImage == null || settingRQInGoodsImage == true) {
            // 根据推广链接生成二维码图片
            rqImageBuffered = RQCodeUtils.encode(link);
        }

        BufferedImage resultImage = null;
        try {
            resultImage = ImageIO.read(new File(backgroundImgPath));
            Graphics2D  graphics = resultImage.createGraphics();
            // 商品图片
            graphics.drawImage(goodsImage,0,0,750,760,null);
            // 卷后价格
            graphics.setFont( new Font("微软雅黑", Font.BOLD, 40));
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            graphics.setColor(new Color(217, 47, 156));
            graphics.drawString("￥" + CommisionUtils.divide ((goodsDetail.getMin_group_price() - goodsDetail.getCoupon_discount())/1.0,100.00,2) ,10,827);
            // 优惠券价格
            graphics.setFont( new Font("微软雅黑", Font.BOLD, 25));
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            graphics.setColor(new Color(255, 255, 255));
            graphics.drawString((CommisionUtils.divide ((goodsDetail.getCoupon_discount())/1.0,100.00,0) + "").replace(".0","") ,590,819);
            // 原价
            graphics.setFont( new Font("微软雅黑", Font.BOLD, 22));
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            graphics.setColor(new Color(136, 136, 136));
            graphics.drawString("原价 ￥" + CommisionUtils.divide ((goodsDetail.getMin_group_price())/1.0,100.00,2) ,10,870);

            // 标题
            graphics.setFont( new Font("微软雅黑", Font.BOLD, 28));
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            graphics.setColor(new Color(0, 0, 0));
            String goods_name = goodsDetail.getGoods_name();
            String name1 = "";
            String name2 = "";
            if(goods_name.length() > 20){
                name1 = goods_name.substring(0,19);
                name2 = goods_name.substring(20,goods_name.length() - 1);
            }else{
                name1 = goods_name;
            }
            graphics.drawString(name1 ,135,923);
            if(!StringUtils.isEmpty(name2)){
                graphics.drawString(name2 ,10,980);
            }

            //二维码
            if(rqImageBuffered != null) {
                graphics.drawImage(rqImageBuffered, 60 * 2, 538 * 2, 180, 180, null);
            }
            graphics.dispose();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            boolean flag = ImageIO.write(resultImage, "png", out);
            if(flag){
                return out.toByteArray();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
