package by.evgkor.TouristBot.repository;

import by.evgkor.TouristBot.model.City;

import org.springframework.data.repository.CrudRepository;


import javax.transaction.Transactional;


public interface CityRepo extends CrudRepository<City, Integer> {

    @Transactional
    City findByName(String name);

    void deleteByName(String name);

}