package by.evgkor.TouristBot.validator;

public class CityValidation {
    private static final String CITY_REGEX_PATTERN = "([а-яА-Я]+|[а-яА-Я]+\\s[а-яА-Я]+)";

    public static boolean validate(String city) {
        return city.matches(CITY_REGEX_PATTERN);
    }
}
