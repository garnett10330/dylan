package com.momo.dylantest.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "spring.jpa")
@Setter
@Getter
public class BaseDataSourceConfig {
    private final Map<String, String> properties = new HashMap<>();

//    @Bean
//    public JpaVendorAdapter jpaVendorAdapter() {
//        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
//        adapter.setShowSql(Boolean.parseBoolean(properties.get("show-sql")));
//        adapter.setGenerateDdl("update".equalsIgnoreCase(properties.get("hibernate.ddl-auto")));
//        return adapter;
//    }

    protected Map<String, Object> jpaProperties(Database databaseType) {
        Map<String, Object> jpaProps = new HashMap<>(properties);
        jpaProps.put("hibernate.dialect", getDialectByDatabaseType(databaseType));
        return jpaProps;
    }

    private String getDialectByDatabaseType(Database databaseType) {
        return switch (databaseType) {
            case ORACLE -> "org.hibernate.dialect.OracleDialect";
            case MYSQL -> "org.hibernate.dialect.MySQLDialect";
            case POSTGRESQL -> "org.hibernate.dialect.PostgreSQLDialect";
            default -> throw new IllegalArgumentException("Unsupported database type: " + databaseType);
        };
    }

}
