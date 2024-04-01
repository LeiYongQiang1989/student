package com.student.shiro.model.DO;

import lombok.Data;

@Data
public class Secret {
    private static final long serialVersionUID=1L;

    // sm2Key 公钥
    private String pubKey;

    // sm4Key 加密密码所用秘钥
    private String entKey;

}
