package com.student.work.user.model;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * token中 头声明对象
 *
 * @author suqizhi
 */
@Data
public class TokenClaim implements Serializable {

    private static final long serialVersionUID = 1L;

    @JSONField(ordinal = 1)
    private String id;

    @JSONField(ordinal = 2)
    private String username;

    public String objToJson() {
        return JSONObject.toJSONString(this);
    }

    public static TokenClaim jsonToObj(String json) {
        return JSONObject.parseObject(json, TokenClaim.class);
    }

    public TokenClaim(String id, String username) {
        this.id = id;
        this.username = username;
    }

}
