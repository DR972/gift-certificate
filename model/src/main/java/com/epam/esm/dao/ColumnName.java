package com.epam.esm.dao;

/**
 * The class {@code ColumnName} stores column names.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
public final class ColumnName {
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String PRICE = "price";
    public static final String DURATION = "duration";
    public static final String CREATE_DATE = "create_date";
    public static final String LAST_UPDATE_DATE = "last_update_date";
    public static final String TAGS = "tags";
    public static final String GIFT_CERTIFICATE_ID = "gift_certificate_id";
    public static final String TAG_ID = "tag_id";

    /**
     * Private constructor without parameters.
     * Restrict instantiation of the class.
     */
    private ColumnName() {
    }
}
