package com.mall.ware.controller;

import com.mall.ware.entity.WareOrderTaskDetailEntity;
import com.mall.ware.service.WareOrderTaskDetailService;
import com.mall.common.utils.PageUtils;
import com.mall.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 库存工作单
 *
 */
@RestController
@RequestMapping("ware/wareordertaskdetail")
public class WareOrderTaskDetailController {
    @Autowired
    private WareOrderTaskDetailService wareOrderTaskDetailService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wareOrderTaskDetailService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		WareOrderTaskDetailEntity wareOrderTaskDetail = wareOrderTaskDetailService.getById(id);

        return R.ok().put("wareOrderTaskDetail", wareOrderTaskDetail);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody WareOrderTaskDetailEntity wareOrderTaskDetail){
		wareOrderTaskDetailService.save(wareOrderTaskDetail);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody WareOrderTaskDetailEntity wareOrderTaskDetail){
		wareOrderTaskDetailService.updateById(wareOrderTaskDetail);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		wareOrderTaskDetailService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
