package com.epam.esm.dao;

import com.epam.esm.entity.Entity;
import java.util.List;

public interface Dao<T extends Entity<ID>, ID> {

    T findEntity(String query, Object... params);

    List<T> findListEntities(String query, Object... params);

    void updateEntity(String query, Object... params);

    long createEntity(String query, Object... params);
}
