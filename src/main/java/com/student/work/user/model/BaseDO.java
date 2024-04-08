package com.student.work.user.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @author wenhui
 */
@Data
public class BaseDO {
    private static final long serialVersionUID = 8451480989718151296L;


    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;


    /**
     * 主键id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;



}
