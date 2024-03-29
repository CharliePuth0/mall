package com.mall.product.service.impl;

import com.mall.product.dao.AttrGroupDao;
import com.mall.product.entity.AttrGroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.utils.PageUtils;
import com.mall.common.utils.Query;

import com.mall.product.dao.AttrAttrgroupRelationDao;
import com.mall.product.entity.AttrAttrgroupRelationEntity;
import com.mall.product.service.AttrAttrgroupRelationService;


@Service("attrAttrgroupRelationService")
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationDao, AttrAttrgroupRelationEntity> implements AttrAttrgroupRelationService {



    @Autowired
    AttrGroupDao attrGroupDao;




    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrAttrgroupRelationEntity> page = this.page(
                new Query<AttrAttrgroupRelationEntity>().getPage(params),
                new QueryWrapper<AttrAttrgroupRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public String getgroupName(Long attId) {

        AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = this.baseMapper.selectOne(
                new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attId));
        if (attrAttrgroupRelationEntity == null){
            return null;
        }
        Long groupId = attrAttrgroupRelationEntity.getAttrGroupId();
        AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(groupId);

        return attrGroupEntity.getAttrGroupName();


    }


}