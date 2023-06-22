package com.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mall.product.entity.CategoryBrandRelationEntity;
import com.mall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.utils.PageUtils;
import com.mall.common.utils.Query;

import com.mall.product.dao.CategoryDao;
import com.mall.product.entity.CategoryEntity;
import com.mall.product.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {


    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listwithtree() {

        //查询所有cat_level为1的数据嵌套的查询出为2为3的数据
        List<CategoryEntity> entities = baseMapper.selectList(null);

        List<CategoryEntity> collect = entities.stream().filter(categoryEntity -> {
            return categoryEntity.getCatLevel() == 1;
        }).map((menu)->{
            menu.setChildren(getChildren(menu,entities));
            return menu;
        }).sorted((menu1,menu2)->{
            return (menu1.getSort()==null?0:menu1.getSort()) - (menu2.getSort()==null?0:menu2.getSort());
        }).collect(Collectors.toList());


        return collect;
    }



    public List<CategoryEntity> getChildren(CategoryEntity root, List<CategoryEntity> all){

        List<CategoryEntity> children = all.stream().filter((categoryEntity) -> {
            return categoryEntity.getParentCid() == root.getCatId();
        }).map((categoryEntity)->{
            categoryEntity.setChildren(getChildren(categoryEntity,all));
            return categoryEntity;
        }).sorted((menu1,menu2)->{
            return (menu1.getSort()==null?0:menu1.getSort()) - (menu2.getSort()==null?0:menu2.getSort());
        }).collect(Collectors.toList());

        return children;

    }

/*
    属性分组的目录回显，新定义了一个catalogPath用来收集目录的三级分类id
*/
    @Override
    public Long[] getParentPath(Long catalogId) {

        List<Long> path = new ArrayList<>();
        //递归的查询
        List<Long> parentPath = findPath(catalogId, path);
        Collections.reverse(parentPath);


        return parentPath.toArray(new Long[parentPath.size()]);
    }



    public List<Long> findPath(Long catalogId, List<Long> path){
        path.add(catalogId);

        CategoryEntity categoryEntity = this.getById(catalogId);
        if(categoryEntity.getParentCid() != 0){
            findPath(categoryEntity.getParentCid(), path);
        }

        return path;
    }


    @Override
    public void updatePro(CategoryEntity category) {
        this.updateById(category);

        if(StringUtils.isNotEmpty(category.getName())){
            CategoryBrandRelationEntity categoryBrandRelationEntity = new CategoryBrandRelationEntity();
            categoryBrandRelationEntity.setCatelogName(category.getName());
            categoryBrandRelationEntity.setCatelogId(category.getCatId());
            categoryBrandRelationService.update(categoryBrandRelationEntity,
                    new UpdateWrapper<CategoryBrandRelationEntity>().eq("catelog_id",category.getCatId()));
        }
    }
}