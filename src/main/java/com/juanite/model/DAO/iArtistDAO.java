package com.juanite.model.DAO;

import com.juanite.model.Countries;
import com.juanite.model.domain.Artist;

import java.util.Set;

public interface iArtistDAO extends AutoCloseable {

    Set<Artist> getAll();
    boolean getById(int id);
    Set<Artist> getByName(String name);
    Set<Artist> getByCountry(Countries country);

}
