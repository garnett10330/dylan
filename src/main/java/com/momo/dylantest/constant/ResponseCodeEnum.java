package com.momo.dylantest.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通用返回狀態碼
 */
@Getter
@AllArgsConstructor
public enum ResponseCodeEnum {
    /**
     * 操作成功
     */
    SUCCESS(1,"操作成功"),
    /**
     * 系统错误
     */
    FAIL(-1,"操作失敗");


    private int value;

    private String description;

}
