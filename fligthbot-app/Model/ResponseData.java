package Model;

public class ResponseData {
    private String response;
    private String city;
    private String country;


    public ResponseData() {
    }

    public ResponseData(String response, String city, String country) {
        this.response = response;
        this.city = city;
        this.country = country;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
