package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateTagDao;
import com.epam.esm.entity.GiftCertificateTag;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.esm.dao.ColumnName.GIFT_CERTIFICATE_ID;
import static com.epam.esm.dao.ColumnName.TAG_ID;

@Repository
public class GiftCertificateTagDaoImpl extends AbstractDao<GiftCertificateTag, Long> implements GiftCertificateTagDao {

    public GiftCertificateTagDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public GiftCertificateTag buildEntity(ResultSet rs) throws SQLException {
        return new GiftCertificateTag(rs.getLong(GIFT_CERTIFICATE_ID), rs.getLong(TAG_ID));
    }
}
