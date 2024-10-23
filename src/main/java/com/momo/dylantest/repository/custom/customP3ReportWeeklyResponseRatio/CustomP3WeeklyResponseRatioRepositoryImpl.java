package com.momo.dylantest.repository.custom.customP3ReportWeeklyResponseRatio;

import com.momo.dylantest.model.P3ReportWeeklyResponseRatio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;
public class CustomP3WeeklyResponseRatioRepositoryImpl implements CustomP3WeeklyResponseRatioRepository {
    private final EntityManager entityManager;

    public CustomP3WeeklyResponseRatioRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public List<P3ReportWeeklyResponseRatio> findByConditions(String entpCode, String goodsCode, Boolean eUnread) {
        StringBuilder sql = new StringBuilder("SELECT * FROM `3Preport_weekly_response_ratio` WHERE 1=1");

        if (entpCode != null && !entpCode.isEmpty()) {
            sql.append(" AND entpcode = :entpCode");
        }

        if (goodsCode != null && !goodsCode.isEmpty()) {
            sql.append(" AND goodscode = :goodsCode");
        }

        if (eUnread != null) {
            sql.append(" AND e_unread = :eUnread");
        }

        Query query = entityManager.createNativeQuery(sql.toString(), P3ReportWeeklyResponseRatio.class);

        if (entpCode != null && !entpCode.isEmpty()) {
            query.setParameter("entpCode", entpCode);
        }

        if (goodsCode != null && !goodsCode.isEmpty()) {
            query.setParameter("goodsCode", goodsCode);
        }

        if (eUnread != null) {
            query.setParameter("eUnread", eUnread);
        }

        return query.getResultList();
    }

}
