package com.juanite.model.DAO;

import com.juanite.model.domain.Person;
import com.juanite.util.AppData;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class PersonDAO extends Person implements AutoCloseable{
    /**
     * Retrieves all persons from the database.
     *
     * @return A List of Person objects representing all persons in the database.
     */
    public List<Person> getAll() {
        EntityManager em = AppData.getManager();
        Query query = em.createQuery("FROM Person", Person.class);
        List<Person> persons = query.getResultList();
        return persons;
    }

    /**
     * Retrieves a person from the database based on their ID.
     *
     * @param id The ID of the person to retrieve.
     * @return The Person object with the specified ID, or null if not found.
     */
    public Person getById(int id) {
        EntityManager em = AppData.getManager();
        Person person = em.find(Person.class, id);
        return person;
    }

    /**
     * Saves a new person to the database.
     *
     * @param person The Person object to be saved.
     */
    public void save(Person person) {
        EntityManager em = AppData.getManager();
        em.getTransaction().begin();
        em.persist(person);
        em.getTransaction().commit();
    }

    /**
     * Updates an existing person in the database.
     *
     * @param person The Person object to be updated.
     */
    public void update(Person person) {
        EntityManager em = AppData.getManager();
        em.getTransaction().begin();
        em.merge(person);
        em.getTransaction().commit();
    }

    /**
     * Deletes a person from the database.
     *
     * @param person The Person object to be deleted.
     */
    public void remove(Person person) {
        EntityManager em = AppData.getManager();
        em.getTransaction().begin();
        person = em.merge(person);
        em.remove(person);
        em.getTransaction().commit();
    }

    @Override
    public void close() throws Exception {

    }
}
