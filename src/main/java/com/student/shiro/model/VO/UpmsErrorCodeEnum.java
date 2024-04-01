package com.student.shiro.model.VO;

/**
 * @author wenhui
 */
public enum UpmsErrorCodeEnum {
    /**
     * upms_015: 密码不能为空！
     */
    OPERATOR_MMPD_EMPTY("upms_014","密码不能为空！"),
    /**
     * upms_016: 用户名与密码不能为空！
     */
    OPERATOR_EMPTY("upms_016","用户名与密码不能为空！"),
    /**
     * upms_013: 用户名不能为空！
     */
    OPERATOR_USERNAME_EMPTY("upms_013","用户名不能为空！");


    /**
     * @fieldName: value
     * @fieldType: String
     * @Description: 错误码
     */
    private final String value;
    /**
     * @fieldName: errorReason
     * @fieldType: String
     * @Description: 错误原因
     */
    private final String errorReason;
    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }
    /**
     * @return the errorReason
     */
    public String getErrorReason() {
        return errorReason;
    }
    /**
     * @param value
     * @param errorReason
     */
    private UpmsErrorCodeEnum(String value, String errorReason) {
        this.value = value;
        this.errorReason = errorReason;
    }
}
