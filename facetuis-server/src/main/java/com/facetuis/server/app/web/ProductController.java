package com.facetuis.server.app.web;

import com.facetuis.server.app.web.basic.BaseResponse;
import com.facetuis.server.app.web.basic.FacetuisController;
import com.facetuis.server.model.product.Product;
import com.facetuis.server.utils.ProductUtils;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/1.0/products")
public class ProductController extends FacetuisController {


    @RequestMapping(method = RequestMethod.GET)
    public BaseResponse list(){
        List<Product> list = ProductUtils.getList();
        BaseResponse response = new BaseResponse();
        response.setResult(new PageImpl<Product>(list));
        return response;
    }
}
