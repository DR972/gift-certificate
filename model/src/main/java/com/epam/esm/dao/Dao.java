package com.epam.esm.dao;

import com.epam.esm.entity.Entity;
import java.util.List;
import java.util.Optional;

public interface Dao<T extends Entity<ID>, ID> {

    Optional<T> findEntity(String query, Object... params);

    List<T> findListEntities(String query, Object... params);

    void updateEntity(String query, Object... params);

    long createEntity(String query, Object... params);
}
