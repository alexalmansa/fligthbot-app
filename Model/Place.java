package Model;

import com.google.gson.annotations.SerializedName;

public class Place
{
    @SerializedName("PlaceId")
    private String placeId;
    @SerializedName("PlaceName")
    private String placeName;
    @SerializedName("CountryId")
    private String countryId;
    @SerializedName("RegionId")
    private String regionId;
    @SerializedName("CityId")
    private String cityId;
    @SerializedName("CountryName")
    private String countryName;

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}