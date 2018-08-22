package com.facetuis.server.app.web;

import com.facetuis.server.app.web.basic.BaseResponse;
import com.facetuis.server.app.web.basic.FacetuisController;
import com.facetuis.server.app.web.response.PromotionUrl;
import com.facetuis.server.model.user.User;
import com.facetuis.server.service.pinduoduo.GoodsService;
import com.facetuis.server.service.pinduoduo.response.GoodsDetails;
import com.facetuis.server.utils.NeedLogin;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/1.0/goods")
public class GoodsController extends FacetuisController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 获取商品详情
     * @param goodsId
     * @return
     */
    @RequestMapping("/{goodsId}")
    public BaseResponse get(@PathVariable String goodsId){
        int level = getLevel();
        GoodsDetails details = goodsService.getGoodsById(goodsId,level);
        return  successResult(details);
    }

    /**
     * 根据关键字查询商品列表
     * @param keywords
     * @param sortType
     * @param page
     * @return
     */
    @RequestMapping(value = "/keywords/{keywords}/{sortType}")
    public BaseResponse findByWords(@PathVariable String keywords,@PathVariable String sortType,Integer page,String rangeList){
        if(page == null){
            page = 1;
        }
        int level = getLevel();
        Page<GoodsDetails> byWrods = goodsService.findByWrods(keywords, null, sortType,page, level,rangeList);
        return successResult(byWrods);
    }


    /**
     *
     * 根据分类查询商品列表
     * @param category
     * @param sortType
     * @param page
     * @return
     */
    @RequestMapping(value = "/category/{category}/{sortType}")
    public BaseResponse findByCategory(@PathVariable String category,@PathVariable String sortType,Integer page,String rangeList){
        if(page == null){
            page = 1;
        }
        int level = getLevel();
        Page<GoodsDetails> byWrods = goodsService.findByWrods("", category, sortType,page, level,rangeList);
        return successResult(byWrods);
    }

    /**
     * 根据商品ID获取推广链接，可掉起拼多多APP
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/promotion/{goodsId}")
    @NeedLogin(needLogin = true)
    public BaseResponse findByPromotion(@PathVariable String goodsId){
        User user = getUser();
        String pid = user.getPid();
        PromotionUrl promontion = goodsService.promontion(pid, goodsId, true);
        return successResult(promontion);
    }

    /**
     * 生成产品详情图片
     * @param goodsId
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/image/{goodsId}",method = RequestMethod.GET)
    @NeedLogin(needLogin = true)
    public void getGoodsImage(@PathVariable String goodsId,Integer imageIndex, HttpServletResponse response) throws IOException {
        User user = getUser();
        Boolean settingRQInGoodsImage = user.getSettingRQInGoodsImage();
        String pid = user.getPid();
        PromotionUrl promontion = goodsService.promontion(pid, goodsId, true);
        byte[] image = goodsService.createImage(goodsId,promontion.getMobile_short_url(),imageIndex,settingRQInGoodsImage);
        response.setContentType("image/png");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(image);
        outputStream.flush();
        outputStream.close();
    }


    @RequestMapping(value = "/share/{goodsId}",method = RequestMethod.GET)
    public BaseResponse getShare(@PathVariable String goodsId){
        Long share = goodsService.getShare(goodsId);
        return successResult(share + "");
    }

    @RequestMapping(value = "/share/{goodsId}",method = RequestMethod.POST)
    public BaseResponse addShare(@PathVariable String goodsId){
        goodsService.addShare(goodsId);
        return successResult();
    }


    private int getLevel(){
        User user = getUser();
        int level = 0;
        if(user != null){
            level = user.getLevel().getLevel();
        }
        return level;
    }

}
