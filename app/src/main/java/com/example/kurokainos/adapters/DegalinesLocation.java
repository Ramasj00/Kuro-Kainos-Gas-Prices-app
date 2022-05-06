package com.example.kurokainos.adapters;

public class DegalinesLocation {
    String pavadinimas;
    String adresas;
    double latitude;
    double longtitude;



    public DegalinesLocation( String pavadinimas,String adresas, double latitude, double longtitude) {
        this.pavadinimas = pavadinimas;
        this.adresas = adresas;
        this.latitude = latitude;
        this.longtitude = longtitude;

    }
    public String getPavadinimas() {
        return pavadinimas;
    }

    public void setPavadinimas(String pavadinimas) {
        this.pavadinimas = pavadinimas;
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
