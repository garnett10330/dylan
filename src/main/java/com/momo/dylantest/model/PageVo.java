package com.momo.dylantest.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
/**
 * @author dylan
 * @date 2024-11-18
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "分頁結果泛型類，包含分頁相關信息和數據內容")
public class PageVo<T> {
    @Schema(description = "分頁數據內容")
    private List<T> result;
    @Schema(description = "總記錄數", example = "50")
    private Long total;

}
