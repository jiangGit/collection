package com.facetuis.server.service.pinduoduo;

import com.alibaba.fastjson.JSONObject;
import com.facetuis.server.service.basic.BaseResult;
import com.facetuis.server.service.basic.BasicService;
import com.facetuis.server.service.pinduoduo.response.PidCreateResonse;
import com.facetuis.server.service.pinduoduo.utils.PRequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.SortedMap;
import java.util.TreeMap;

@Service
public class PinDuoDuoService extends BasicService {
    @Autowired
    private PRequestUtils pRequestUtils;

    private String API_CREATE_PID = "pdd.ddk.goods.pid.generate";


    /**
     * 创建推广位
     * @return
     */
    public String createPid(){
        SortedMap<String,String> map = new TreeMap<>();
        map.put("number","1");
        BaseResult<String> send = pRequestUtils.send(API_CREATE_PID, map);
        PidCreateResonse resonse = JSONObject.parseObject(send.getResult(), PidCreateResonse.class);
        if(resonse.getP_id_generate_response() != null && resonse.getP_id_generate_response().getP_id_list() != null){
            if(resonse.getP_id_generate_response().getP_id_list().size() == 1){
                return resonse.getP_id_generate_response().getP_id_list().get(0).getP_id();
            }
        }
        return "";
    }





}
