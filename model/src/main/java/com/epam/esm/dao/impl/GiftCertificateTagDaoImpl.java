package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateTagDao;
import com.epam.esm.entity.GiftCertificateTag;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.esm.dao.ColumnName.GIFT_CERTIFICATE_ID;
import static com.epam.esm.dao.ColumnName.TAG_ID;

/**
 * The class {@code GiftCertificateTagDaoImpl} is implementation of interface {@link GiftCertificateTagDao}
 * and intended to work with {@link com.epam.esm.entity.GiftCertificateTag} table.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@Repository
public class GiftCertificateTagDaoImpl extends AbstractDao<GiftCertificateTag, Long> implements GiftCertificateTagDao {

    /**
     * The constructor creates an GiftCertificateTagDaoImpl object
     *
     * @param jdbcTemplate JdbcTemplate
     */
    public GiftCertificateTagDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public GiftCertificateTag buildEntity(ResultSet rs) throws SQLException {
        return new GiftCertificateTag(rs.getLong(GIFT_CERTIFICATE_ID), rs.getLong(TAG_ID));
    }
}
