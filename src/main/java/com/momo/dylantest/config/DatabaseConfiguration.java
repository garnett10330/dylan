package com.momo.dylantest.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {
    public static final String MYSQL_DATASOURCE = "MysqlDS";
    public static final String POSTGRES_DATASOURCE = "PostgresDS";


    @Bean(name = MYSQL_DATASOURCE,destroyMethod = "")
    @ConfigurationProperties(prefix = "spring.datasource.mysql")
    @Primary
    public DataSource dataSourceMysql(){
        return new HikariDataSource();
    }

    @Bean(name = POSTGRES_DATASOURCE,destroyMethod = "")
    @ConfigurationProperties(prefix = "spring.datasource.postgres")
    public DataSource dataSourcePostgres(){
        return new HikariDataSource();
    }

}
