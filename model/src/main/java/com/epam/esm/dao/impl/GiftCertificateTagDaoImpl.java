package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateTagDao;
import com.epam.esm.entity.GiftCertificateTag;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;

@Repository
public class GiftCertificateTagDaoImpl extends AbstractDao<GiftCertificateTag, Long> implements GiftCertificateTagDao {

    public GiftCertificateTagDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public GiftCertificateTag buildEntity(ResultSet rs) {
        return null;
    }
}
