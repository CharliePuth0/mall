package com.mall.product.vo;


import lombok.Data;

@Data
public class AttrRespVo extends AttrVo{

    private String catalogName;

    private String groupName;

    private Long[] catalogPath;
}
