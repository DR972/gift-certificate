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

/**
 * Class {@code DataBaseConfig} contains the spring database configuration for the model subproject.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@Configuration
@PropertySource("classpath:database/database.properties")
public class DataBaseConfig {
    @Value("classpath:database/schema.sql")
    Resource dbSchema;
    @Value("classpath:database/create.sql")
    Resource dbCreate;

    /**
     * Create bean {@link DataSource} which will be used as data source.
     *
     * @return the ComboPooledDataSource
     */
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

    /**
     * Create bean {@link DataSourceInitializer} which will be used to initialize the database.
     *
     * @param dataSource the data source
     * @return DataSourceInitializer
     */
    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {

        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScript(dbSchema);
        databasePopulator.setIgnoreFailedDrops(false);

        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator);

        return initializer;
    }

    /**
     * Create bean {@link JdbcTemplate} which will be used for queries to database.
     *
     * @param dataSource the data source
     * @return the jdbc template
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    /**
     * Create bean {@link PlatformTransactionManager} which will be used to perform transactions while the database is running.
     *
     * @param dataSource the data source
     * @return PlatformTransactionManager
     */
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
