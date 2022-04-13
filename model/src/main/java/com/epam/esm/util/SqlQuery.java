package com.epam.esm.util;

public final class SqlQuery {

    private SqlQuery() {
    }

    /* Query to the GiftCertificate table */
    public static final String FIND_CERTIFICATE_BY_ID =
            "SELECT gift_certificate.id, gift_certificate.name, description, price, duration, create_date, last_update_date, " +
                    "array_agg(tag)::tag[] AS tags FROM gift_certificate " +
                    "LEFT JOIN gift_certificate_tag ON gift_certificate_id = gift_certificate.id " +
                    "LEFT JOIN tag ON tag.id = tag_id WHERE gift_certificate.id=? GROUP BY gift_certificate.id";
    public static final String FIND_ALL_CERTIFICATES =
            "SELECT gift_certificate.id, gift_certificate.name, description, price, duration, create_date, last_update_date, " +
                    "array_agg(tag)::tag[] AS tags FROM gift_certificate " +
                    "LEFT JOIN gift_certificate_tag ON gift_certificate_id = gift_certificate.id " +
                    "LEFT JOIN tag ON tag.id = tag_id ";
    public static final String CREATE_CERTIFICATE =
            "INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date) VALUES(?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_CERTIFICATE = "UPDATE gift_certificate SET ";
    public static final String DELETE_CERTIFICATE = "DELETE FROM gift_certificate WHERE id=?";

    /* Query to the Tag table */
    public static final String FIND_ALL_TAGS = "SELECT id, name FROM tag ORDER BY id";
    public static final String FIND_TAG_BY_ID = "SELECT id, name FROM tag WHERE id=?";
    public static final String FIND_TAG_BY_NAME = "SELECT id, name FROM tag WHERE name=?";
    public static final String CREATE_TAG = "INSERT INTO tag (name) VALUES(?)";
    public static final String UPDATE_TAG = "UPDATE tag SET name=? WHERE id=?";
    public static final String DELETE_TAG = "DELETE FROM tag WHERE id=?";

    /* Query to the GiftCertificateTag table */
    public static final String CREATE_CERTIFICATE_TAG_BY_TAG_NAME = "INSERT INTO gift_certificate_tag VALUES(?, (SELECT tag.id FROM tag WHERE name=?))";
    public static final String DELETE_CERTIFICATE_TAG_BY_CERTIFICATE_ID = "DELETE FROM gift_certificate_tag WHERE gift_certificate_id=?";
    public static final String DELETE_CERTIFICATE_TAG_BY_TAG_ID = "DELETE FROM gift_certificate_tag WHERE tag_id=?";

    public static final String WHERE = " WHERE";
    public static final String WHERE_ID = " WHERE id=?";
    public static final String AND = " AND ";
    public static final String SEARCH_BY_TEXT = " (gift_certificate.name ILIKE '%' || ? || '%' or gift_certificate.description ILIKE '%' || ? || '%')";
    public static final String SEARCH_BY_TAG = " (gift_certificate.id IN (SELECT gift_certificate_id FROM gift_certificate_tag WHERE tag_id = (SELECT id FROM tag WHERE name=?)))";
    public static final String GROUP_BY_CERTIFICATE = " GROUP BY gift_certificate.id";
    public static final String ORDER_BY = " ORDER BY ";
    public static final String SUFFIX = "=?,";
    public static final String OFFSET = " offset (? * ?) ROWS fetch next ? ROWS only";

}
