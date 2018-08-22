package com.facetuis.server.app.web;

import com.facetuis.server.app.web.basic.BaseResponse;
import com.facetuis.server.app.web.basic.FacetuisController;
import com.facetuis.server.model.inviting.InvitingImage;
import com.facetuis.server.model.user.User;
import com.facetuis.server.service.inviting.InvitingService;
import com.facetuis.server.utils.NeedLogin;
import javafx.scene.chart.ValueAxis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/1.0/inviting")
public class InvitingController extends FacetuisController{


    @Autowired
    private InvitingService invitingService;

    @RequestMapping(value = "/images",method = RequestMethod.GET)
    @NeedLogin(needLogin = true)
    public BaseResponse imgList(){
        User user = getUser();
        Page<InvitingImage> page = invitingService.imageList(user.getDisplayRecommend());
        return successResult(page);
    }

}
