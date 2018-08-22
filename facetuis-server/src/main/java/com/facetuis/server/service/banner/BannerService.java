package com.facetuis.server.service.banner;

import com.facetuis.server.model.banner.Banner;
import com.facetuis.server.service.basic.BasicService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BannerService extends BasicService {

    @Value("${sys.server.ip}")
    private String serverIp;

    public Page<Banner> getBanner(){
        Banner b1 = new Banner();
        b1.setUuid("1");
        b1.setImgUrl("http://" + serverIp + "/banner/1.jpg");
        b1.setDescription("ddd");
        b1.setName("111");

        Banner b2 = new Banner();
        b2.setUuid("3");
        b2.setImgUrl("http://" + serverIp + "/banner/2.jpg");
        b2.setDescription("ddd");
        b2.setName("111");

        Banner b3 = new Banner();
        b3.setUuid("3");
        b3.setImgUrl("http://"+serverIp+"/banner/3.jpg");
        b3.setDescription("ddd");
        b3.setName("111");

        Banner b4 = new Banner();
        b4.setUuid("4");
        b4.setImgUrl("http://"+serverIp+"/banner/4.jpg");
        b4.setDescription("ddd");
        b4.setName("44444444");

        Banner b5 = new Banner();
        b5.setUuid("5");
        b5.setImgUrl("http://"+serverIp+"/banner/5.jpg");
        b5.setDescription("ddd");
        b5.setName("5555");
        List<Banner> list = new ArrayList<>();
        list.add(b1);
        list.add(b2);
        list.add(b3);
        list.add(b4);
        //list.add(b5);
        return new PageImpl<Banner>(list);
    }


    public Page<Banner> getBannerBig(){
        Banner b1 = new Banner();
        b1.setUuid("1");
        b1.setImgUrl("http://" + serverIp + "/banner/big.jpg");
        b1.setDescription("ddd");
        b1.setName("111");

        List<Banner> list = new ArrayList<>();
        list.add(b1);

        return new PageImpl<Banner>(list);
    }

}
