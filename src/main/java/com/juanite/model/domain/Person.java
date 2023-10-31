package com.juanite.model.domain;

import java.util.Objects;

public class Person {
    private int id_Person;
    private String name;
    private String gmail;
    private String password;

    public Person(int id_Person, String name, String gmail, String password) {
        this.id_Person = id_Person;
        this.name = name;
        this.gmail = gmail;
        this.password = password;
    }


    public int getId_Person() {
        return id_Person;
    }

    public void setId_Person(int id_Person) {
        this.id_Person = id_Person;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "Person{" +
                "id_Person=" + id_Person +
                ", name='" + name + '\'' +
                ", gmail='" + gmail + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id_Person == person.id_Person && Objects.equals(name, person.name) && Objects.equals(gmail, person.gmail) && Objects.equals(password, person.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_Person, name, gmail, password);
    }
}
