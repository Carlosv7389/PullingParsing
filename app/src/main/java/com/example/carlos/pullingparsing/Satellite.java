package com.example.carlos.pullingparsing;

public class Satellite {
    private static int visableSats;
    private int ID;
    private String name;
    private int Latitude;
    private int Longitude;
    private int Altitude;
    Satellite(int a, String b, int c, int d, int e) {
        ID = a;
        name = b;
        Latitude = c;
        Longitude = d;
        Altitude = e;
    }
    public void setVisableSats(int totalSatellites) {
        visableSats = totalSatellites;
    }
}
