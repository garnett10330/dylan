package com.momo.dylantest.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
@Data
@Schema(description = "分頁結果泛型類，包含分頁相關信息和數據內容")
public class PageResult<T> {
    @Schema(description = "分頁數據內容")
    private List<T> content;
    @Schema(description = "總記錄數", example = "50")
    private long totalElements;
    @Schema(description = "總頁數", example = "5")
    private int totalPages;
    @Schema(description = "每頁大小", example = "10")
    private int pageSize;
    @Schema(description = "當前頁數", example = "1")
    private int pageNumber;

}
