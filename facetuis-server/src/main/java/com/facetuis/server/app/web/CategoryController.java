package com.facetuis.server.app.web;

import com.facetuis.server.app.web.basic.BaseResponse;
import com.facetuis.server.app.web.basic.FacetuisController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/1.0/category")
public class CategoryController extends FacetuisController {


    public class Category{
        private String name;
        private String id;
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }


    @RequestMapping(method = RequestMethod.GET)
    public BaseResponse list(){
        Category category = new Category();
        category.setId("1");
        category.setName("美食");
        Category category1 = new Category();
        category1.setId("4");
        category1.setName("母婴");
        Category category2 = new Category();
        category2.setId("13");
        category2.setName("水果");
        Category category3 = new Category();
        category3.setId("14");
        category3.setName("服饰");
        Category category4 = new Category();
        category4.setId("15");
        category4.setName("百货");
        Category category5 = new Category();
        category5.setId("16");
        category5.setName("美妆");
        Category category6 = new Category();
        category6.setId("18");
        category6.setName("电器");
        Category category7 = new Category();
        category7.setId("743");
        category7.setName("男装");
        Category category8 = new Category();
        category8.setId("818");
        category8.setName("家纺");
        Category category9 = new Category();
        category9.setId("1281");
        category9.setName("鞋包");
        Category category10 = new Category();
        category10.setId("1451");
        category10.setName("运动");
        Category category11 = new Category();
        category11.setId("1543");
        category11.setName("手机");
        List<Category> list = new ArrayList<>();
        list.add(category);
        list.add(category1);
        list.add(category2);
        list.add(category3);
        list.add(category4);
        list.add(category5);
        list.add(category6);
        list.add(category7);
        list.add(category8);
        list.add(category9);
        list.add(category10);
        list.add(category11);
        Page<Category> page = new PageImpl<>(list);
        return successResult(page);
    }
}
