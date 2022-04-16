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

@Repository
public abstract class AbstractDao<T extends Entity<ID>, ID> implements Dao<T, ID> {
    private final JdbcTemplate jdbcTemplate;

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

    abstract T buildEntity(ResultSet rs) throws SQLException;
}
