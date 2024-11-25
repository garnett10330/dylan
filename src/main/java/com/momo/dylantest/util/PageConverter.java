package com.momo.dylantest.util;

import com.momo.dylantest.model.PageResult;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class PageConverter {

    public static <T> PageResult<T> convertToPageResult(@NotNull List<T> data, long totalElement, int totalPages, int pageNumber, int pageSize) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setContent(data);
        pageResult.setTotalElements(totalElement);
        pageResult.setTotalPages(totalPages);
        pageResult.setPageNumber(pageNumber);
        pageResult.setPageSize(pageSize);
        return pageResult;
    }
}
