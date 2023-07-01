package com.mall.product.web;

import com.mall.product.entity.CategoryEntity;
import com.mall.product.service.CategoryService;
import com.mall.product.vo.Catelog2Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;



@Controller
public class IndexController {

    @Autowired
    CategoryService categoryService;



    @GetMapping(value = {"/","index.html"})
    private String indexPage(Model model) {

        //查出所有的一级分类
        List<CategoryEntity> categoryEntities = categoryService.getLevel1Categorys();
        model.addAttribute("categories",categoryEntities);

        return "index";
    }


    @GetMapping(value = "/index/json/catalogJson")
    @ResponseBody
    public Map<String, List<Catelog2Vo>> getCatalogJson() {

        Map<String, List<Catelog2Vo>> catalogJson = categoryService.getCatalogJson();

        return catalogJson;

    }


}
