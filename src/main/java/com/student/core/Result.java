package com.student.core;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * @author wenhui
 */
public class Result implements Serializable {
    private static final long serialVersionUID = 4981862263867358375L;
    private Integer code;
    private boolean success;
    private String resultCode;
    private String msg;
    private Object data;

    private Result(final Result.Builder builder) {
        this.success = true;
        this.success = builder.success;
        this.code = builder.code;
        this.msg = builder.msg;
        this.data = builder.data;
        this.resultCode = builder.resultCode;
    }

    public Result() {
        this.success = true;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public Integer getCode() {
        return this.code;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public String getResultCode() {
        return this.resultCode;
    }

    public String getMsg() {
        return this.msg;
    }

    public Object getData() {
        return this.data;
    }

    public void setCode(final Integer code) {
        this.code = code;
    }

    public void setSuccess(final boolean success) {
        this.success = success;
    }

    public void setResultCode(final String resultCode) {
        this.resultCode = resultCode;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }

    public void setData(final Object data) {
        this.data = data;
    }

    public static class Builder {
        private Boolean success;
        private final Integer code;
        private String msg;
        private Object data;
        private String resultCode;

        public Builder(final Integer code) {
            this.code = code;
        }

        public Result.Builder success(final boolean success) {
            this.success = success;
            return this;
        }

        public Result.Builder msg(final String msg) {
            this.msg = msg;
            return this;
        }

        public Result.Builder rows(final Object data) {
            this.data = data;
            return this;
        }

        public Result.Builder resultCode(final String resultCode) {
            this.resultCode = resultCode;
            return this;
        }

        public Result build() {
            return new Result(this);
        }
    }
}

