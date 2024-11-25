package com.momo.dylantest.util;

import com.momo.dylantest.model.PageResult;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;

public class PageConverter {

    public static <T> PageResult<T> convertToPageResult(@NotNull Page<T> page) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setContent(page.getContent());
        pageResult.setTotalElements(page.getTotalElements());
        pageResult.setTotalPages(page.getTotalPages());
        pageResult.setPageNumber(page.getNumber());
        pageResult.setPageSize(page.getSize());
        return pageResult;
    }
}
