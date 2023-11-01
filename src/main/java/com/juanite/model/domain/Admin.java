package com.juanite.model.domain;

import java.util.Objects;

public class Admin extends Person {

    public Admin() {
        super();
    }
    public Admin(int id, String name, String gmail, String password) {
        super(id,name,gmail,password);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return this.id == admin.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
