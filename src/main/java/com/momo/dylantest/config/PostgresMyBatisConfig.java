package com.momo.dylantest.config;

import com.momo.dylantest.mapper.postgres.PostgresDBMapper;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author wei
 */
@Configuration
@MapperScan(basePackages = "com.momo.dylantest.mapper.postgres", sqlSessionFactoryRef = PostgresMyBatisConfig.POSTGRES_SESSION_FACTORY)
public class PostgresMyBatisConfig {
    public static final String POSTGRES_SESSION_FACTORY = "postgresSessionFactory";
    public static final String POSTGRES_TRANSACTION_MANAGER = "postgresTransactionManager";

    @Bean(name = POSTGRES_SESSION_FACTORY,destroyMethod = "")
    public SqlSessionFactoryBean postgresSqlSessionFactory(@Qualifier (DatabaseConfig.POSTGRES_DATASOURCE) final DataSource postgresDataSource)throws Exception{
        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(postgresDataSource);
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session
                .Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.addMapper(PostgresDBMapper.class);
        sqlSessionFactoryBean.setConfiguration(configuration);
        sqlSessionFactoryBean.getObject();
        return sqlSessionFactoryBean;
    }

    @Bean
    public MapperFactoryBean<PostgresDBMapper> postgresMapper(@Qualifier(POSTGRES_SESSION_FACTORY) final SqlSessionFactoryBean sqlSessionFactoryBean) throws Exception{
        MapperFactoryBean<PostgresDBMapper> factoryBean = new MapperFactoryBean<>(PostgresDBMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactoryBean.getObject());
        return factoryBean;
    }
    @Bean(name = POSTGRES_TRANSACTION_MANAGER)
    public DataSourceTransactionManager postgresTransactionManager(@Qualifier(DatabaseConfig.POSTGRES_DATASOURCE) DataSource postgresDataSource) {
        return new DataSourceTransactionManager(postgresDataSource);
    }
}
