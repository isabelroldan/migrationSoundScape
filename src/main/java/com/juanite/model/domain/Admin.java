package com.juanite.model.domain;

import java.util.Objects;

public class Admin {
    private int id_Persona;

    public Admin(int id_Persona) {
        this.id_Persona = id_Persona;
    }

    public int getId_Persona() {
        return id_Persona;
    }

    public void setId_Persona(int id_Persona) {
        this.id_Persona = id_Persona;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id_Persona=" + id_Persona +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return id_Persona == admin.id_Persona;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_Persona);
    }
}
