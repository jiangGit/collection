package com.facetuis.server.app.web;

import com.facetuis.server.app.web.basic.BaseResponse;
import com.facetuis.server.app.web.basic.FacetuisController;
import com.facetuis.server.service.headline.HeadlineService;
import com.facetuis.server.service.headline.vo.HeadlineVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/1.0/headline")
public class HeaderController extends FacetuisController{

    @Autowired
    private HeadlineService headlineService;

    @RequestMapping(method = RequestMethod.GET)
    public BaseResponse list(){
        PageImpl<HeadlineVO> list = headlineService.list();
        return successResult(list);
    }
    @RequestMapping(method = RequestMethod.GET,value = "/recommand")
    public BaseResponse recommandList(){
        PageImpl<HeadlineVO> list = headlineService.recommandList();
        return successResult(list);
    }
}
