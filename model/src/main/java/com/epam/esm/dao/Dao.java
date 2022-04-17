package com.epam.esm.dao;

import com.epam.esm.entity.Entity;

import java.util.List;
import java.util.Optional;

/**
 * The interface {@code Dao} describes abstract behavior for working with
 * {@link com.epam.esm.dao.impl.AbstractDao} in database.
 *
 * @param <T> indicates that for this instantiation of the DAO, will be used this type of Entity implementation.
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
public interface Dao<T extends Entity<ID>, ID> {

    /**
     * Searches for the object T in the table 'T'.
     *
     * @param query  database query
     * @param params array of parameters for the query
     * @return T object
     */
    Optional<T> findEntity(String query, Object... params);

    /**
     * Searches for all T objects in the table `T`.
     *
     * @param query  database query
     * @param params array of parameters for the query
     * @return list of T objects
     */
    List<T> findListEntities(String query, Object... params);

    /**
     * Performs various operations (update and delete) on the object T in the table 'T`.
     *
     * @param query  database query
     * @param params array of parameters for the query
     */
    void updateEntity(String query, Object... params);

    /**
     * Performs the operation of saving the object T in the table 'T'.
     *
     * @param query  database query
     * @param params array of parameters for the query
     * @return the value of the key of the object T in the table 'T'
     */
    long createEntity(String query, Object... params);
}
