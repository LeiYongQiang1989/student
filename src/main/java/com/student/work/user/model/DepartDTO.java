package com.student.work.user.model;///**
// * Copyright © 2019 国网信通亿力科技有限责任公司. All rights reserved.
// *
// * @Title:DepartDTO.java
// * @Prject: com.ylkj.cloud.upms.depart.model
// * @Package: com.ylkj.cloud.upms.depart.model
// * @author: HuangZhenPeng
// * @date: 2019-08-20
// * @version: V1.0
// */
//package com.student.user.model;
//
//import com.ylkj.cloud.core.model.BaseDTO;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//
//import javax.validation.constraints.Pattern;
//import java.util.Date;
//
///**
// * @ClassName: DepartDTO
// * @Description: 组织机构表 传输对象
// * @author: HuangZhenPeng
// * @date: 2019-08-20
// */
//@Data
//@EqualsAndHashCode(callSuper = true)
//@ApiModel(value="DepartDTO对象", description="组织机构表")
//public class DepartDTO extends BaseDTO {
//
//    private static final long serialVersionUID=1L;
//
//    @ApiModelProperty(value = "ID", required = false)
//    private String id;
//
//    @ApiModelProperty(value = "父机构ID", required = false)
//    private String parentId;
//
//    @Pattern(regexp = "^\\s*$|^[\\u4E00-\\u9FA5A-Za-z0-9_]+$", message = "{name.valid.format}")
//    @ApiModelProperty(value = "机构/部门名称", required = false)
//    private String departName;
//
//    @ApiModelProperty(value = "英文名", required = false)
//    private String departNameEn;
//
//    @ApiModelProperty(value = "缩写", required = false)
//    private String departNameAbbr;
//
//    @ApiModelProperty(value = "排序", required = false)
//    private Integer departOrder;
//
//    @ApiModelProperty(value = "描述", required = false)
//    private String description;
//
//    @ApiModelProperty(value = "机构类型 1一级部门 2子部门", required = false)
//    private String orgType;
//
//    @ApiModelProperty(value = "机构编码", required = false)
//    private String orgCode;
//
//    @ApiModelProperty(value = "手机号", required = false)
//    private String mobile;
//
//    @ApiModelProperty(value = "传真", required = false)
//    private String fax;
//
//    @ApiModelProperty(value = "地址", required = false)
//    private String address;
//
//    @ApiModelProperty(value = "备注", required = false)
//    private String memo;
//
//    @ApiModelProperty(value = "状态（1启用，0不启用）", required = false)
//    private String status;
//
//    @ApiModelProperty(value = "删除状态（0，正常，1已删除）", required = false)
//    private String delFlag;
//
//    @ApiModelProperty(value = "创建日期", required = false)
//    private Date createTime;
//
//    @ApiModelProperty(value = "更新日期", required = false)
//    private Date updateTime;
//
//    @ApiModelProperty(value = "更新人",required = false)
//    private String operatorId;
//}
