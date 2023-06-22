package com.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mall.product.entity.CategoryBrandRelationEntity;
import com.mall.product.service.CategoryBrandRelationService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.utils.PageUtils;
import com.mall.common.utils.Query;

import com.mall.product.dao.BrandDao;
import com.mall.product.entity.BrandEntity;
import com.mall.product.service.BrandService;
import org.springframework.transaction.annotation.Transactional;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {


    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;


    /*
        品牌的检索查询
    */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        QueryWrapper<BrandEntity> wrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if(StringUtils.isNotEmpty(key)){
            wrapper.eq("brand_id",key).or().like("name",key);
        }


        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params), wrapper
        );

        return new PageUtils(page);
    }


    /*
    * 级联更新冗余字段 name
    * */
    @Override
    @Transactional
    public void updatePro(BrandEntity brand) {

        this.updateById(brand);


//        CategoryBrandRelationEntity categoryBrandRelationEntity = categoryBrandRelationDao.selectById(brand.getBrandId());
//        categoryBrandRelationEntity.setBrandName(brand.getName());
//
//
//        categoryBrandRelationService.save(categoryBrandRelationEntity);

        if(StringUtils.isNotEmpty(brand.getName())){
            //同步更新其他关联表中的数据
            CategoryBrandRelationEntity categoryBrandRelationEntity = new CategoryBrandRelationEntity();
            categoryBrandRelationEntity.setBrandName(brand.getName());
            categoryBrandRelationEntity.setBrandId(brand.getBrandId());
            categoryBrandRelationService.update(categoryBrandRelationEntity,new UpdateWrapper<CategoryBrandRelationEntity>()
                    .eq("brand_id",brand.getBrandId()));
        }

    }

}