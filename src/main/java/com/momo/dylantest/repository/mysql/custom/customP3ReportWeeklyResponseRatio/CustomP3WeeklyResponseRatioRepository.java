package com.momo.dylantest.repository.mysql.custom.customP3ReportWeeklyResponseRatio;

import com.momo.dylantest.model.mysql.P3ReportWeeklyResponseRatioPo;

import java.util.List;

public interface CustomP3WeeklyResponseRatioRepository {
    List<P3ReportWeeklyResponseRatioPo> findByConditions(String entpCode, String goodsCode, Boolean eUnread);
}
