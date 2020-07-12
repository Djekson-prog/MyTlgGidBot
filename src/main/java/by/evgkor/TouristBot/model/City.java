package by.evgkor.TouristBot.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Data
@Entity
@Table(name="cities")
public class City {
    @Id
    @GeneratedValue
    private Long id;

   @Column(name="cityName")
    private String name;
   @Column(name = "descriptionOfTheAttraction")
    private String info;

    public City() {
    }


    public City(String name, String info) {
        this.name=name;
        this.info=info;
    }
}
