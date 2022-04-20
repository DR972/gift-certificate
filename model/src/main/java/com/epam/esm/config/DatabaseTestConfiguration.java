package com.epam.esm.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

/**
 * Class {@code DatabaseTestConfiguration} contains the spring database configuration for the tests.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@Configuration
@ComponentScan("com.epam.esm")
@PropertySource("classpath:database/database.properties")
@Profile("test")
public class DatabaseTestConfiguration {

    /**
     * This method creates a component that will be used as a data source.
     *
     * @return the ComboPooledDataSource
     */
    @SneakyThrows
    @Bean
    public DataSource dataSource(@Value("${db.driver}") String driver,
                                 @Value("${db.url}") String url,
                                 @Value("${db.user}") String user,
                                 @Value("${db.password}") String password,
                                 @Value("${db.poolSize}") int poolSize) {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();

        dataSource.setDriverClass(driver);
        dataSource.setJdbcUrl(url);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setMinPoolSize(poolSize);
        return dataSource;
    }
}
