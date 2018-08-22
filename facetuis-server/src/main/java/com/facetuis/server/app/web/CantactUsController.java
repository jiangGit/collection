package com.facetuis.server.app.web;

import com.facetuis.server.app.web.basic.BaseResponse;
import com.facetuis.server.app.web.basic.FacetuisController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

//联系我们CantactUs__xiute
@RestController
@RequestMapping("/1.0/cantactus")
public class CantactUsController extends FacetuisController {


    @Value("${sys.server.ip}")
    private String serverIp;
    public class CantactUs{
        private String name;
        private String pic;
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public BaseResponse get()
    {
        CantactUs cm=new CantactUs();
        cm.setName("脸推");
        cm.setPic("http://" + serverIp  + "/rq.jpg");
        return successResult(cm);
    }


}
