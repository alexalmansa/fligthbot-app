package Model;

import java.time.ZonedDateTime;
import java.util.ArrayList;

public class Suggestions {

    private String name;
    private String continent;
    private ArrayList<String> countries;
    private ArrayList<String> cities;
    private String maxPrice;
    private String month;

    public Suggestions() {
        countries = new ArrayList<String>();
        cities = new ArrayList<String>();
    }

    public Suggestions(String name, String continent, ArrayList<String> countries, ArrayList<String> cities, String maxPrice, String month) {
        this.name = name;
        this.continent = continent;
        this.countries = countries;
        this.cities = cities;
        this.maxPrice = maxPrice;
        this.month = month;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public ArrayList<String> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<String> countries) {
        this.countries = countries;
    }

    public ArrayList<String> getCities() {
        return cities;
    }

    public void setCities(ArrayList<String> cities) {
        this.cities = cities;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }


}
