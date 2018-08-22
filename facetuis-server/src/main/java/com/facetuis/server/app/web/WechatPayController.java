package com.facetuis.server.app.web;

import com.facetuis.server.app.web.basic.BaseResponse;
import com.facetuis.server.app.web.basic.FacetuisController;
import com.facetuis.server.model.pay.Payment;
import com.facetuis.server.model.product.Product;
import com.facetuis.server.model.user.User;
import com.facetuis.server.service.basic.BaseResult;
import com.facetuis.server.service.wechat.WechatPayService;
import com.facetuis.server.utils.IpUtils;
import com.facetuis.server.utils.NeedLogin;
import com.facetuis.server.utils.ProductUtils;
import javafx.scene.chart.ValueAxis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

@RestController
@RequestMapping("/1.0/wechat/pay")
public class WechatPayController extends FacetuisController {

    private static Logger logger = Logger.getLogger(WechatPayController.class.getName());

    @Autowired
    private WechatPayService wechatPayService;


    /**
     * 微信统一下单
     * @param productid
     * @param request
     * @return
     */
    @RequestMapping(value = "/payment/{productid}",method = RequestMethod.GET)
    @NeedLogin(needLogin = true)
    public BaseResponse payment(@PathVariable String productid, HttpServletRequest request){
        Product product = ProductUtils.getProduct(productid);
        if(product == null){
            return setErrorResult(600,"没有找到需要支付的产品");
        }
        User user = getUser();
        String userId = null;
        if(user != null){
            userId = user.getUuid();
        }
        BaseResult baseResult = wechatPayService.unifiedorder(product.getTitle(), product.getTitle(), product.getAmount(), IpUtils.getIpAddr(request),userId,productid);
        return onResult(baseResult);
    }

    /**
     * 微信异步通知
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/notify",method = RequestMethod.POST)
    public String weixinNotify(HttpServletRequest request, HttpServletResponse response){
        String notityXml = "";
        String inputLine;
        //微信给返回的东西
        try {
            while ((inputLine = request.getReader().readLine()) != null) {
                notityXml += inputLine;
            }
            request.getReader().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        BaseResult<String> baseResult = wechatPayService.checkNotify(notityXml);
        if(baseResult.hasError()){
            return "ERROR";
        }
        return baseResult.getResult();
    }

    /**
     * 查询支付状态
     * @param tradNo
     * @return
     */
    @RequestMapping(value = "/result/{tradNo}",method = RequestMethod.GET)
    public BaseResponse payResult(@PathVariable String tradNo){
        Payment byOutTradeNo = wechatPayService.findByOutTradeNo(tradNo);
        if(byOutTradeNo != null){
            if(byOutTradeNo.getTradeNo() != null) {
                return successResult();
            }
        }
        return setErrorResult(600,"支付失败!");
    }




}
