package com.realdolmen.map;

import java.util.ArrayList;
import java.util.List;

public class Doorway {
    private List<Coordinates> coordinates = new ArrayList<>();
    private String orientation;

    public Doorway(List<Coordinates> doorCoordinates, String orientation) {
        this.coordinates = doorCoordinates;
        this.orientation = orientation;
    }

    public Doorway(int corridorX, int corridorY, int corridorHeight, String orientation) {
        int doorX1 = corridorX + 1;
        int doorX2 = corridorX + 2;
        int doorX3 = corridorX + 3;

        int doorY1 = corridorY;
        int doorY2 = corridorY + corridorHeight;
    }

    public List<Coordinates> getDoorCoordinates() {
        return coordinates;
    }

    public void setDoorCoordinates(List<Coordinates> doorCoordinates) {
        this.coordinates = doorCoordinates;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }
}
