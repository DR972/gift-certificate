package com.epam.esm.dao.impl;

import com.epam.esm.dao.ColumnName;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.dao.ColumnName.ID;
import static com.epam.esm.dao.ColumnName.NAME;
import static com.epam.esm.dao.ColumnName.DESCRIPTION;
import static com.epam.esm.dao.ColumnName.PRICE;
import static com.epam.esm.dao.ColumnName.DURATION;
import static com.epam.esm.dao.ColumnName.CREATE_DATE;
import static com.epam.esm.dao.ColumnName.LAST_UPDATE_DATE;

/**
 * The class {@code GiftCertificateDaoImpl} is implementation of interface {@link GiftCertificateDao}
 * and intended to work with {@link com.epam.esm.entity.GiftCertificate} table.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@Repository
public class GiftCertificateDaoImpl extends AbstractDao<GiftCertificate, Long> implements GiftCertificateDao {

    /**
     * The constructor creates an GiftCertificateDaoImpl object
     *
     * @param jdbcTemplate JdbcTemplate
     */
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public GiftCertificate buildEntity(ResultSet rs) throws SQLException {
        List<Tag> tags = Arrays.stream((Object[]) rs.getArray(ColumnName.TAGS).getArray())
                .map(t -> t.toString().replace("(", "").replace(")", "").replaceAll("\"", "").split(","))
                .map(t -> new Tag(Long.parseLong(t[0]), t[1]))
                .collect(Collectors.toList());
        return new GiftCertificate(rs.getLong(ID), rs.getString(NAME), rs.getString(DESCRIPTION), rs.getBigDecimal(PRICE), rs.getInt(DURATION),
                LocalDateTime.parse(rs.getString(CREATE_DATE).replaceAll(" ", "T")),
                LocalDateTime.parse(rs.getString(LAST_UPDATE_DATE).replaceAll(" ", "T"), DateTimeFormatter.ISO_LOCAL_DATE_TIME), tags);
    }
}
