package by.evgkor.TouristBot.service;

import by.evgkor.TouristBot.dao.CityDao;
import by.evgkor.TouristBot.exception.NoSuchCityException;
import by.evgkor.TouristBot.model.City;
import by.evgkor.TouristBot.repository.CityRepo;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService  {
    private CityDao cityDao;

    public CityService(){
        cityDao = new CityDao();
    }

    public  City findCityByName(String name) throws NoSuchCityException {
        return cityDao.findByName(name);
    }

    public void addCity(City city){
        cityDao.save(city);
    }

    public void updateCity(City city){
        cityDao.update(city);
    }

    public void deleteCity(City city){
        cityDao.delete(city);
    }

}
