package com.example.fligthbot.utils;

import com.example.fligthbot.model.Places;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utilities {

    public static List<String> httpRequest(String destination) {
        ArrayList<String> responseData = new ArrayList<>();
        try {
            HttpResponse<String> response = Unirest.get("https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/autosuggest/v1.0/UK/GBP/en-GB/?query=" + destination)
                    .header("x-rapidapi-host", "skyscanner-skyscanner-flight-search-v1.p.rapidapi.com")
                    .header("x-rapidapi-key", "6596d5c78dmsha7a707a09a23029p1a74c8jsndf266d167c11")
                    .asString();
            Gson gson = new Gson();

            Places places = gson.fromJson(response.getBody(), Places.class);
            int random = new Random().nextInt(places.getPlaces().size());
            while (places.getPlaces().get(random).getPlaceName().equalsIgnoreCase(places.getPlaces().get(random).getCountryName())) {
                random = new Random().nextInt(places.getPlaces().size());
                if (places.getPlaces().size() == 1) {
                    break;
                }
            }

            responseData.add(places.getPlaces().get(random).getPlaceName());
            responseData.add(places.getPlaces().get(random).getCountryName());
            return responseData;
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return responseData;
    }
}
