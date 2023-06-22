package com.mall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.mall.common.exception.valid.AddBrand;
import com.mall.common.exception.valid.UpdateBrand;
import lombok.Data;
import org.apache.ibatis.annotations.Update;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;

/**
 * 品牌
 * 
 * @author sugerdaddy
 * @email KFCv50@gmail.com
 * @date 2023-06-09 20:01:13
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 品牌id
	 */
	@TableId
	@Null(message = "新增不用指定id",   groups = {AddBrand.class} )
	@NotNull(message = "修改id不能为空", groups = {UpdateBrand.class})
	private Long brandId;

	/**
	 * 品牌名
	 */
	@NotBlank(message = "品牌名不能为空", groups = {AddBrand.class, UpdateBrand.class})
	private String name;

	/**
	 * 品牌logo地址
	 */
	@NotBlank(message = "品牌logo地址不能为空", groups = {AddBrand.class})
	@URL(message = "url必须是一个合法地址",groups = {AddBrand.class, UpdateBrand.class})
	private String logo;

	/**
	 * 介绍
	 */
	@Size(max = 1000, message = "介绍长度不能超过1000个字符", groups = {AddBrand.class, UpdateBrand.class})
	private String descript;

	/**
	 * 显示状态[0-不显示；1-显示]
	 */
//	@Pattern(regexp = "[01]", message = "显示状态值必须为0或1", groups = {AddBrand.class, UpdateBrand.class})
	private Integer showStatus;

	/**
	 * 检索首字母
	 */
	@Pattern(regexp = "[A-Za-z]", message = "检索首字母必须为字母", groups = {AddBrand.class, UpdateBrand.class})
	@NotEmpty(groups = {AddBrand.class})
	private String firstLetter;

	/**
	 * 排序
	 */
	@NotNull(message = "排序不能为空", groups = {AddBrand.class})
	@Min(value = 0, message = "排序值不能小于0", groups = {AddBrand.class, UpdateBrand.class})
	private Integer sort;

}
