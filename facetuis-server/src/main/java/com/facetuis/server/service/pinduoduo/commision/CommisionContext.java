package com.facetuis.server.service.pinduoduo.commision;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommisionContext {

    @Autowired
    private CommisionCreateStrategy commisionCreateStrategy;

    @Autowired
    private CommisionSubStrategy commisionSubStrategy;

    public CommisionStrategy getStrategy(int status){
        switch (status){
            case 0:
                return commisionCreateStrategy;
            case 1:
                return commisionCreateStrategy;
            case 2:
                return commisionCreateStrategy;
            case 3:
                return commisionCreateStrategy;
            case 4:
                return commisionSubStrategy;
            case 5:
                return commisionCreateStrategy;
            default:
                return null;
        }
    }
}
