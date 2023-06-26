package com.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.ware.entity.UndoLogEntity;
import com.mall.common.utils.PageUtils;

import java.util.Map;

/**
 * 
 *
 * @author mallyanfeng
 * @email 2011912434@qq.com
 * @date 2021-12-27 14:06:22
 */
public interface UndoLogService extends IService<UndoLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

