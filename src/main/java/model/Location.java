package model;

import java.io.Serializable;

public class Location implements Serializable {
    private double lat;
    private double lng;
    public double getLatitude() {return lat;}
    public double getLongitude() {return lng;}
    public Location(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "(" + lat + ", " + lng + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Location)) {
            return false;
        }
        Location that = (Location) o;
        return this.lat == that.lat && this.lng == that.lng;
    }
}