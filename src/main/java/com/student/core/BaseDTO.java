/**   
 * Copyright © 2019 国网信通亿力科技有限责任公司. All rights reserved.
 * 
 * @Title: BaseDTO.java 
 * @Prject: com.ylkj.cloud.example
 * @Package: com.ylkj.cloud.core.model 
 * @author: Jianbin   
 * @date: 2019年7月26日 下午7:28:59 
 * @version: V1.0   
 */
package com.student.core;

import java.io.Serializable;

import org.hibernate.validator.constraints.Range;


import lombok.Data;

/**
 * @ClassName: BaseDTO
 * @Description: 基础DTO
 * @author: Jianbin
 * @date: 2019年7月26日 下午7:28:59
 */
@Data
public class BaseDTO implements Serializable {
	
	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 8993571607402946091L;
	/**
	 * 页数
	 */
	@Range(min = 1)
	private long pageNo = 1L;
	/**
	 * 行数
	 */
	@Range(min = 0, max = 1000)
	private long pageSize = 10L;

	
}
