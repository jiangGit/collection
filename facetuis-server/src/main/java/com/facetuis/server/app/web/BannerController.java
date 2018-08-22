package com.facetuis.server.app.web;

import com.facetuis.server.app.web.basic.BaseResponse;
import com.facetuis.server.app.web.basic.FacetuisController;
import com.facetuis.server.model.banner.Banner;
import com.facetuis.server.service.banner.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/1.0/banner")
public class BannerController extends FacetuisController {

    @Autowired
    private BannerService bannerService;

    @RequestMapping(method = RequestMethod.GET)
    public BaseResponse list(){
        Page<Banner> banner = bannerService.getBanner();
        return  successResult(banner);
    }

    @RequestMapping(value = "/big",method = RequestMethod.GET)
    public BaseResponse list_body(){
        Page<Banner> banner = bannerService.getBannerBig();
        return  successResult(banner);
    }

}
