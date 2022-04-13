package com.epam.esm.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:database/database.properties")
public class DataBaseConfig {
    @Value("classpath:database/schema.sql")
    Resource dbSchema;
    @Value("classpath:database/create.sql")
    Resource dbCreate;

    @SneakyThrows
    @Bean
    public DataSource dataSource(@Value("${db.driver}") String driver,
                                 @Value("${db.url}") String url,
                                 @Value("${db.username}") String user,
                                 @Value("${db.password}") String password,
                                 @Value("${db.minPoolSize}") int minPoolSize,
                                 @Value("${db.maxPoolSize}") int maxPoolSize,
                                 @Value("${db.maxIdleTime}") int maxIdleTime,
                                 @Value("${db.maxStatements}") int maxStatements,
                                 @Value("${db.maxStatementsPerConnection}") int maxStatementsPerConnection,
                                 @Value("${db.MaxIdleTimeExcessConnections}") int MaxIdleTimeExcessConnections) {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();

        dataSource.setDriverClass(driver);
        dataSource.setJdbcUrl(url);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setMinPoolSize(minPoolSize);
        dataSource.setMaxPoolSize(maxPoolSize);
        dataSource.setMaxIdleTime(maxIdleTime);
        dataSource.setMaxStatements(maxStatements);
        dataSource.setMaxStatementsPerConnection(maxStatementsPerConnection);
        dataSource.setMaxIdleTimeExcessConnections(MaxIdleTimeExcessConnections);
        return dataSource;
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {

        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScript(dbSchema);
//        databasePopulator.addScript(dbCreate);
        databasePopulator.setIgnoreFailedDrops(false);

        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator);

        return initializer;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
