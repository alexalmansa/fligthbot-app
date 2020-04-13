package Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Places {
    @SerializedName("Places")
    private ArrayList<Place> places;

    public ArrayList<Place> getPlaces() {
        return places;
    }

    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }
}
