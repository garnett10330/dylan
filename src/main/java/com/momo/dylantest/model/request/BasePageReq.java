package com.momo.dylantest.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasePageReq {
    private Integer pageNum = 1; // 默認值為 1
    private Integer pageSize = 10; // 默認值為 10

    public int getOffset(){
        return (pageNum - 1) * pageSize;
    }
}
