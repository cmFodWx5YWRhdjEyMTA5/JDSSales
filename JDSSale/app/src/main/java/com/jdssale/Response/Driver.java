package com.jdssale.Response;

public class Driver {


    private String driver_id;
    private String latitude;
    private String longitude;

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Driver(){

    }

    public Driver(String driver_id, String latitude, String longitude){
        this.driver_id = driver_id;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
