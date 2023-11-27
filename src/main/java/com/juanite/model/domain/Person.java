package com.juanite.model.domain;

import com.juanite.util.AppData;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "PERSON")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    protected int id;

    @Column(name = "NAME")
    protected String name;

    @Column(name = "EMAIL")
    protected String email;

    @Column(name = "PASSWORD")
    protected String password;

    public Person() {
        this.id = -1;
        this.name = "";
        this.email = "";
        this.password = "";
    }

    public Person(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && Objects.equals(name, person.name) && Objects.equals(email, person.email) && Objects.equals(password, person.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password);
    }

    public static List<Person> selectAllPersons() {
        EntityManager em = AppData.getManager();
        Query query = em.createQuery("FROM Person", Person.class);
        List<Person> persons = query.getResultList();
        return persons;
    }

    public static Person selectPersonById(int id) {
        EntityManager em = AppData.getManager();
        Person person = em.find(Person.class, id);
        return person;
    }

    public static void savePerson(Person person) {
        EntityManager em = AppData.getManager();
        em.getTransaction().begin();
        em.persist(person);
        em.getTransaction().commit();
    }

    public static void updatePerson(Person person) {
        EntityManager em = AppData.getManager();
        em.getTransaction().begin();
        em.merge(person);
        em.getTransaction().commit();
    }

    public static void deletePerson(Person person) {
        EntityManager em = AppData.getManager();
        em.getTransaction().begin();
        person = em.merge(person);
        em.remove(person);
        em.getTransaction().commit();
    }
}
