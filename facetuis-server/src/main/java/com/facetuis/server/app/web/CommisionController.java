package com.facetuis.server.app.web;

import com.facetuis.server.app.web.basic.BaseResponse;
import com.facetuis.server.app.web.basic.FacetuisController;
import com.facetuis.server.model.commision.CommisionSettings;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/1.0/commision")
@RestController
public class CommisionController extends FacetuisController {


    /**
     * TODO 价格说明
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public BaseResponse index(){
        return successResult(new CommisionSettings());
    }

}
