package com.momo.dylantest.util;

import com.momo.dylantest.model.dto.api.CompanyStockApiDto;

public class Test {

    public static void main(String[] args) {
        CompanyStockApiDto dto = new CompanyStockApiDto();
        dto.setSymbol("AAPL"); // 如果 @Data 有效，應該能自動生成 setter 方法
        System.out.println(dto.getSymbol()); // 測試 getter
    }
}
