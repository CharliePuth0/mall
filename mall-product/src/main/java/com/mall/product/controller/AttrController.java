package com.mall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.mall.product.entity.AttrAttrgroupRelationEntity;
import com.mall.product.entity.AttrGroupEntity;
import com.mall.product.entity.ProductAttrValueEntity;
import com.mall.product.service.*;
import com.mall.product.vo.AttrRespVo;
import com.mall.product.vo.AttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.mall.product.entity.AttrEntity;
import com.mall.common.utils.PageUtils;
import com.mall.common.utils.R;



/**
 * 商品属性
 *
 * @author sugerdaddy
 * @email KFCv50@gmail.com
 * @date 2023-06-09 20:01:13
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;


    @Autowired
    private CategoryService categoryService;


    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;

    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private ProductAttrValueService productAttrValueService;


    @GetMapping("/base/listforspu/{spuId}")
    public R baseAttrlistforspu(@PathVariable("spuId") Long spuId){

        List<ProductAttrValueEntity> entities = productAttrValueService.baseAttrlistforspu(spuId);

        return R.ok().put("data",entities);
    }

    @GetMapping("{type}/list/{catId}")
    public R attrList(@RequestParam Map<String, Object> params, @PathVariable("type") String attrType, @PathVariable("catId") Long catelogId){

        PageUtils page = attrService.queryPagePro(params,attrType,catelogId);


        return R.ok().put("page",page);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    public R info(@PathVariable("attrId") Long attrId){
		AttrEntity attr = attrService.getById(attrId);

        Long catalogId = attr.getCatelogId();
        Long[] parentPath = categoryService.getParentPath(catalogId);
        attr.setCatalogPath(parentPath);


        if(  attr.getAttrType() == 1){
            Long attId =  attr.getAttrId();
            String name = attrAttrgroupRelationService.getgroupName(attId);
            attr.setGroupName(name);
        }


        return R.ok().put("attr", attr);
    }

    /**
     * 保存
     */
    @Transactional
    @RequestMapping("/save")
    public R save(@RequestBody AttrRespVo attrRespVo){
		attrService.savePro(attrRespVo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrEntity attr){
		attrService.updateById(attr);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrIds){
		attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
