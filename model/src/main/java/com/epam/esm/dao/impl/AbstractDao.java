package com.epam.esm.dao.impl;

import com.epam.esm.dao.ColumnName;
import com.epam.esm.dao.Dao;
import com.epam.esm.entity.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * The class {@code AbstractDao} is designed for basic work with database tables.
 *
 * @param <T> indicates that for this instantiation of the DAO, will be used this type of Entity implementation.
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@Repository
public abstract class AbstractDao<T extends Entity<ID>, ID> implements Dao<T, ID> {
    /**
     * JdbcTemplate jdbcTemplate.
     */
    private final JdbcTemplate jdbcTemplate;

    /**
     * The constructor creates an AbstractDAO object
     *
     * @param jdbcTemplate JdbcTemplate
     */
    @Autowired
    public AbstractDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<T> findEntity(String query, Object... params) {
        return jdbcTemplate.query(query, (rs, rowNum) -> buildEntity(rs), params).stream().findAny();
    }

    @Override
    public List<T> findListEntities(String query, Object... params) {
        return jdbcTemplate.query(query, (rs, rowNum) -> buildEntity(rs), params);
    }

    @Override
    public void updateEntity(String query, Object... params) {
        jdbcTemplate.update(query, params);
    }

    @Override
    public long createEntity(String query, Object... params) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(query, new String[]{ColumnName.ID});
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            return preparedStatement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    /**
     * The method builds T entity.
     *
     * @param rs ResultSet
     * @return T object
     * @throws SQLException if there was an error accessing the database
     */
    abstract T buildEntity(ResultSet rs) throws SQLException;
}
