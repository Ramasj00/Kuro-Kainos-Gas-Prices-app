package com.example.kurokainos.adapters;

public class DegalinesLocation {
    String adresas;
    double latitude;
    double longtitude;


    public DegalinesLocation(String adresas, double latitude, double longtitude) {
        this.adresas = adresas;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }


    public String getAdresas() {
        return adresas;
    }

    public void setAdresas(String adresas) {
        this.adresas = adresas;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }


}
