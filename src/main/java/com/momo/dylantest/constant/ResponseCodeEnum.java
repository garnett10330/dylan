package com.momo.dylantest.constant;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ResponseCodeEnum {
    /** 操作成功 */
    SUCCESS(1),
    /** 系统错误 */
    FAIL(-1);


    private int value;

    public int getValue() {
        return value;
    }

    public String getValueStr() {
        return String.valueOf(value);
    }
}
