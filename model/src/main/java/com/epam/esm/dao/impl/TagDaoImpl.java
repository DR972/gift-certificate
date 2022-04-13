package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.esm.dao.ColumnName.ID;
import static com.epam.esm.dao.ColumnName.NAME;

@Repository
public class TagDaoImpl extends AbstractDao<Tag, Long> implements TagDao {

    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Tag buildEntity(ResultSet rs) throws SQLException {
        return new Tag(rs.getLong(ID),
                rs.getString(NAME));
    }
}
