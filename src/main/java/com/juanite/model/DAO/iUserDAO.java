package com.juanite.model.DAO;

import com.juanite.model.domain.User;

import java.util.Set;

public interface iUserDAO extends AutoCloseable {

    Set<User> getAll();
    boolean getById(int id);
    boolean getByName(String name);
}
