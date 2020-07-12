package by.evgkor.TouristBot.dao;

import by.evgkor.TouristBot.exception.NoSuchCityException;
import by.evgkor.TouristBot.model.City;

public interface CrudDao {
    City findByName(String name) throws NoSuchCityException;
    void save(City city);
    void update(City city);
    void delete(City city);
}
