package com.mall.product.service.impl;

import com.mall.product.dao.AttrAttrgroupRelationDao;
import com.mall.product.dao.AttrGroupDao;
import com.mall.product.dao.CategoryDao;
import com.mall.product.entity.AttrAttrgroupRelationEntity;
import com.mall.product.entity.AttrGroupEntity;
import com.mall.product.entity.CategoryEntity;
import com.mall.product.vo.AttrGroupRelationVo;
import com.mall.product.vo.AttrGroupWithAttrsVo;
import com.mall.product.vo.AttrRespVo;
import com.mall.product.vo.AttrVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.utils.PageUtils;
import com.mall.common.utils.Query;

import com.mall.product.dao.AttrDao;
import com.mall.product.entity.AttrEntity;
import com.mall.product.service.AttrService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {


    @Autowired
    AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    @Autowired
    private AttrGroupDao attrGroupDao;

    @Autowired
    private CategoryDao categoryDao;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPagePro(Map<String, Object> params, String attrType, Long catelogId) {

        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>()
                .eq("attr_type","base".equalsIgnoreCase(attrType)? 1:0);

        if(catelogId != 0){
            queryWrapper.eq("catelog_id",catelogId);
        }

        String key = (String) params.get("key");
        if(!StringUtils.isEmpty(key)){
            //attr_id  attr_name
            queryWrapper.and((wrapper)->{
                wrapper.eq("attr_id",key).or().like("attr_name",key);
            });
        }

        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                queryWrapper
        );

        PageUtils pageUtils = new PageUtils(page);
        //获取的数据不够  进行流式编程  增加分类和分组的名字
        List<AttrEntity> records = page.getRecords();
        List<AttrRespVo> respVos = records.stream().map((attrEntity) -> {
            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtils.copyProperties(attrEntity, attrRespVo);

            //1、设置分类和分组的名字
            if("base".equalsIgnoreCase(attrType)){
                AttrAttrgroupRelationEntity attrId = attrAttrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>()
                        .eq("attr_id", attrEntity.getAttrId()));
                if (attrId != null && attrId.getAttrGroupId()!=null) {
                    AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrId.getAttrGroupId());
                    attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }

            }


            CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
            if (categoryEntity != null) {
                attrRespVo.setCatelogName(categoryEntity.getName());
            }
            return attrRespVo;
        }).collect(Collectors.toList());

        pageUtils.setList(respVos);
        return pageUtils;


    }

    @Override
    @Transactional
    public void savePro(AttrRespVo attrRespVo) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attrRespVo, attrEntity);
        //1、保存基本数据
        this.save(attrEntity);
        //2、保存关联关系
        if (attrRespVo.getAttrType() == 1 && attrRespVo.getAttrGroupId() != null) {
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrGroupId(attrRespVo.getAttrGroupId());
            relationEntity.setAttrId(attrEntity.getAttrId());
            attrAttrgroupRelationDao.insert(relationEntity);
            }
        }




        /*
        * todo:暂且搁置，回头填坑。。。
        * */
    @Override
    @Transactional
    public void updatePro(AttrRespVo attrRespVo) {
        AttrEntity attrEntity = new AttrEntity();
        this.save(attrEntity);

        BeanUtils.copyProperties(attrRespVo, attrEntity);

        if(attrRespVo.getAttrType() == 1){

        }



    }

    @Override
    public PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId) {


        //只能关联该分类下没有被其他分组关联到的属性
        AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrgroupId);
        Long catelogId = attrGroupEntity.getCatelogId();


        //根据catid找到该分类下所有的属性分组
        List<AttrGroupEntity> catelog_id = attrGroupDao.selectList(
                new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));
        List<Long> collect = catelog_id.stream().map((id) -> {
            return id.getAttrGroupId();
        }).collect(Collectors.toList());

        //根据所有的属性分组找到所有关联的属性
        List<AttrAttrgroupRelationEntity> groupIds = attrAttrgroupRelationDao.selectList(
                new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id", collect));
        List<Long> attids = groupIds.stream().map((item) -> {
            return item.getAttrId();
        }).collect(Collectors.toList());



        //根据该分类下的所有属性减去所有分组下的属性就是没有被关联的属性
        String key = (String) params.get("key");
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<AttrEntity>()
                .eq("catelog_id", catelogId).eq("attr_type",1);

        if(attids!=null && attids.size()>0){
            wrapper.notIn("attr_id", attids);
        }

        if(!StringUtils.isEmpty(key)){
            wrapper.and((w)->{
                w.eq("attr_id",key).or().like("attr_name",key);
            });
        }
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), wrapper);


        return new PageUtils(page);
    }

    @Override
    public List<AttrEntity> getRelationAttr(Long attrgroupId) {

        List<AttrAttrgroupRelationEntity> relation = attrAttrgroupRelationDao.selectList(
                new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id",attrgroupId));

        List<Long> attIds = relation.stream().map((attr) -> {
            return attr.getAttrId();
        }).collect(Collectors.toList());

        if(attIds == null || attIds.size() == 0){
            return  null;
        }else {
            List<AttrEntity> attrEntities = this.listByIds(attIds);
            return attrEntities;
        }


    }

    @Override
    public void deleteRelation(AttrGroupRelationVo[] vos) {
        List<AttrAttrgroupRelationEntity> entities = Arrays.asList(vos).stream().map((item) -> {
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(item, relationEntity);
            return relationEntity;
        }).collect(Collectors.toList());
        attrAttrgroupRelationDao.deleteBatchRelation(entities);
    }

    @Override
    public List<AttrEntity> getAttrs(Long attrGroupId) {

        List<AttrAttrgroupRelationEntity> groupId = attrAttrgroupRelationDao.selectList(
                new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrGroupId));

        List<Long> collect = groupId.stream().map(item -> {
            return item.getAttrId();
        }).collect(Collectors.toList());


        if(collect == null || collect.size() == 0){
            return  null;
        }
        List<AttrEntity> list = this.listByIds(collect);

        return list;
    }



}