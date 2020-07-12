package by.evgkor.TouristBot.controller;

import by.evgkor.TouristBot.model.City;
import by.evgkor.TouristBot.repository.CityRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.Map;

@Controller
@Transactional
public class CityController {

    private final CityRepo cityRepository;

    private static final Logger LOGGER = LogManager.getLogger();

    public CityController(CityRepo cityRepository) {
        this.cityRepository = cityRepository;
    }

    @GetMapping("main")
    public String mainPage(Map<String, Object> model) {
        Iterable<City> messages = cityRepository.findAll();
        model.put("cities", messages);
        return "main";
    }

    @PostMapping("addCity")
    public String addCity(@RequestParam String name, @RequestParam String info, Map<String, Object> model) {
        City city = new City(name,info);
        cityRepository.save(city);
        LOGGER.info(city + "was added.");
        Iterable<City> messages = cityRepository.findAll();
        model.put("cities", messages);
        return "main";
    }

    @PostMapping("editCity")
    public String editCity(@RequestParam String name, @RequestParam String info, Map<String, Object> model) {
        City city = cityRepository.findByName(name);
        if (city != null) {
            city.setName(name);
            city.setInfo(info);
            cityRepository.save(city);
            LOGGER.info(city + "was edited.");
        }
        LOGGER.error("EDITING: There is no city named "+ name);
        Iterable<City> messages = cityRepository.findAll();
        model.put("cities", messages);
        return "main";
    }

    @PostMapping("deleteCity")
    public String deleteCity(@RequestParam String name, Map<String, Object> model) {
        cityRepository.deleteByName(name);
        LOGGER.error("DELETING: City named "+ name + " was deleted.");
        Iterable<City> messages = cityRepository.findAll();
        model.put("cities", messages);
        return "main";
    }
}
