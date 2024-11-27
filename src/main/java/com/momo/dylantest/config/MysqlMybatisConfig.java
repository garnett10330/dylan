package com.momo.dylantest.config;

import com.momo.dylantest.mapper.mysql.MysqlDBMapper;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.momo.dylantest.mapper.mysql", sqlSessionFactoryRef = MysqlMybatisConfig.MYSQL_SESSION_FACTORY)
public class MysqlMybatisConfig {
    public static final String MYSQL_SESSION_FACTORY = "mysqlSessionFactory";
    public static final String MYSQL_TRANSACTION_MANAGER = "mysqlTransactionManager";

    @Bean(name = MYSQL_SESSION_FACTORY,destroyMethod = "")
    @Primary
    public SqlSessionFactoryBean mysqlSqlSessionFactory(@Qualifier(DatabaseConfig.MYSQL_DATASOURCE) final DataSource mysqlDataSource)throws Exception{
        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        String password = AESUtil.druidDecrypt(publicKey, ((HikariDataSource) mysqlDataSource).getPassword());
//        ((HikariDataSource) mysqlDataSource).setPassword(password);
        sqlSessionFactoryBean.setDataSource(mysqlDataSource);
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session
                .Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.addMapper(MysqlDBMapper.class);
        sqlSessionFactoryBean.setConfiguration(configuration);
        sqlSessionFactoryBean.getObject();
        return sqlSessionFactoryBean;
    }

    @Bean
    public MapperFactoryBean<MysqlDBMapper> mysqlMapper(@Qualifier (MYSQL_SESSION_FACTORY) final SqlSessionFactoryBean sqlSessionFactoryBean) throws Exception{
        MapperFactoryBean<MysqlDBMapper> factoryBean = new MapperFactoryBean<>(MysqlDBMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactoryBean.getObject());
        return factoryBean;
    }

    @Bean(name = MYSQL_TRANSACTION_MANAGER)
    public DataSourceTransactionManager mysqlTransactionManager(@Qualifier(DatabaseConfig.MYSQL_DATASOURCE) DataSource mysqlDataSource) {
        return new DataSourceTransactionManager(mysqlDataSource);
    }
}
