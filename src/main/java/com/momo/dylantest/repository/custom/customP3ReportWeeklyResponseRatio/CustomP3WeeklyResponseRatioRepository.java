package com.momo.dylantest.repository.custom.customP3ReportWeeklyResponseRatio;

import com.momo.dylantest.model.P3ReportWeeklyResponseRatio;

import java.util.List;

public interface CustomP3WeeklyResponseRatioRepository {
    List<P3ReportWeeklyResponseRatio> findByConditions(String entpCode, String goodsCode, Boolean eUnread);
}
