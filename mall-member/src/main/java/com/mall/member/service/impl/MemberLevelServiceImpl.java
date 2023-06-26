package com.mall.member.service.impl;

import com.mall.member.dao.MemberDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.utils.PageUtils;
import com.mall.common.utils.Query;

import com.mall.member.dao.MemberLevelDao;
import com.mall.member.entity.MemberLevelEntity;
import com.mall.member.service.MemberLevelService;


@Service("memberLevelService")
public class MemberLevelServiceImpl extends ServiceImpl<MemberLevelDao, MemberLevelEntity> implements MemberLevelService {


    @Autowired
    MemberLevelDao memberLevelDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        String key = (String) params.get("key");

        QueryWrapper<MemberLevelEntity> wrapper = new QueryWrapper<>();

        if (StringUtils.isNotEmpty(key)){
            wrapper.and((item)->{
                item.eq("id",key).or().like("name",key);
            });
        }

        IPage<MemberLevelEntity> page = this.page(
                new Query<MemberLevelEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }


}