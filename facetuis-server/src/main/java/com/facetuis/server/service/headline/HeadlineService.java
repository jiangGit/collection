package com.facetuis.server.service.headline;

import com.facetuis.server.model.headline.Headline;
import com.facetuis.server.service.basic.BasicService;
import com.facetuis.server.service.headline.vo.HeadlineVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HeadlineService extends BasicService {


    @Value("${sys.server.ip}")
    private String serverIp;


    public PageImpl<HeadlineVO> list(){
        HeadlineVO<String> headline = new HeadlineVO();
        headline.setContentText("2018年5月7日脸推App正式上线，前期致力于拼多多导购，" +
                "让更多的人加入到脸推的大家庭享受到推广的力量带来的乐趣及回报！" +
                "脸推，一个专心做产品的App，希望在接下来的日子里能够与脸推的家人一起共同成长和进步，即将对接更多的平台以追求我们不息的脚步！" +
                "我们的口号：奋斗吧青春，既省钱又赚钱，我们一起努力着" +
                "---一个有温度的App<脸推>");
        headline.setId("1");
        headline.setTitle("官方公告");
        headline.setType("TXT");
        headline.setSubTitle("2018的第一个重大消息：脸推App上线了！");
        headline.setCreateTime(new Date());

        HeadlineVO headline1 = new HeadlineVO();
        HeadlineVO.HeadlineContent hc1 = headline1.new HeadlineContent();
        hc1.setImg("http://" + serverIp + "/head/head_big.png");
        hc1.setTxt("描述");
        hc1.setProductId("584952311");
        List< HeadlineVO.HeadlineContent> hcs1 = new ArrayList<>();
        hcs1.add(hc1);
        headline1.setContent(hcs1 );
        headline1.setId("1");
        headline1.setTitle("今日爆款");
        headline1.setType("IMG");
        headline1.setSubTitle("【多款可选】3/5对粘贴式马桶垫家用保暖防水马桶坐垫加厚坐便垫");
        headline1.setCreateTime(new Date());

        List<HeadlineVO> list = new ArrayList<>();
        list.add(headline);
        list.add(headline1);
        return new PageImpl<HeadlineVO>(list);
    }

    public PageImpl<HeadlineVO> recommandList(){
        HeadlineVO headline = new HeadlineVO();
        headline.setContentText("2018年5月7日脸推App正式上线，前期致力于拼多多导购，" +
                "让更多的人加入到脸推的大家庭享受到推广的力量带来的乐趣及回报！" +
                "脸推，一个专心做产品的App，希望在接下来的日子里能够与脸推的家人一起共同成长和进步，即将对接更多的平台以追求我们不息的脚步！" +
                "我们的口号：奋斗吧青春，既省钱又赚钱，我们一起努力着" +
                "---一个有温度的App<脸推>");
        headline.setId("1");
        headline.setTitle("官方公告");
        headline.setType("TXT");
        headline.setSubTitle("SubTitle");
        headline.setCreateTime(new Date());

        HeadlineVO headline1 = new HeadlineVO();
        HeadlineVO.HeadlineContent hc1 = headline1.new HeadlineContent();
        hc1.setImg("http://" + serverIp + "/head/head_big.png");
        hc1.setTxt("描述");
        hc1.setProductId("584952311");
        List< HeadlineVO.HeadlineContent> hcs1 = new ArrayList<>();
        hcs1.add(hc1);
        headline1.setContent(hcs1 );
        headline1.setId("1");
        headline1.setTitle("今日爆款");
        headline1.setType("IMG");
        headline1.setSubTitle("【多款可选】3/5对粘贴式马桶垫家用保暖防水马桶坐垫加厚坐便垫");
        headline1.setCreateTime(new Date());

        List<HeadlineVO> list = new ArrayList<>();
        list.add(headline);
        list.add(headline1);
        return new PageImpl<HeadlineVO>(list);
    }

}
