package com.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.ware.entity.WareInfoEntity;
import com.mall.common.utils.PageUtils;

import java.util.Map;

/**
 * 仓库信息
 *
 * @author mallyanfeng
 * @email 2011912434@qq.com
 * @date 2021-12-27 14:06:22
 */
public interface WareInfoService extends IService<WareInfoEntity>  {

    PageUtils queryPage(Map<String, Object> params);

}

