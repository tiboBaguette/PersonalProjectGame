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

    public Doorway(int corridorX, int corridorY, int corridorWidth, int corridorHeight, String orientation) {
        int doorX1, doorX2, doorX3;
        int doorY1, doorY2, doorY3;

        switch (orientation) {
            case "UP":
                doorX1 = corridorX + 1;
                doorX2 = corridorX + 2;
                doorX3 = corridorX + 3;

                doorY1 = corridorY;
                doorY2 = corridorY + corridorHeight;

                this.coordinates.add(new Coordinates(doorX1 * 16, doorY1 * 16));
                this.coordinates.add(new Coordinates(doorX2 * 16, doorY1 * 16));
                this.coordinates.add(new Coordinates(doorX3 * 16, doorY1 * 16));
                this.coordinates.add(new Coordinates(doorX1 * 16, doorY2 * 16));
                this.coordinates.add(new Coordinates(doorX2 * 16, doorY2 * 16));
                this.coordinates.add(new Coordinates(doorX3 * 16, doorY2 * 16));
                this.orientation = "HORIZONTAL";

                break;
            case "DOWN":
//                doorX1 = corridorX + 1;
//                doorX2 = corridorX + 2;
//                doorX3 = corridorX + 3;
//
//                doorY1 = corridorY;
//                doorY2 = corridorY - corridorHeight;
//
//                this.coordinates.add(new Coordinates(doorX1, doorY1));
//                this.coordinates.add(new Coordinates(doorX2, doorY1));
//                this.coordinates.add(new Coordinates(doorX3, doorY1));
//                this. coordinates.add(new Coordinates(doorX1, doorY2));
//                this.coordinates.add(new Coordinates(doorX2, doorY2));
//                this.coordinates.add(new Coordinates(doorX3, doorY2));
//                this.orientation = "HORIZONTAL";
                break;
            case "LEFT":
            case "RIGHT":
//                doorY1 = corridorY + 1;
//                doorY2 = corridorY + 2;
//                doorY3 = corridorY + 3;
//
//                doorX1 = corridorX;
//                doorX2 = corridorX + corridorWidth;
//
//                this.coordinates.add(new Coordinates(doorX1, doorY1));
//                this.coordinates.add(new Coordinates(doorX1, doorY1));
//                this.coordinates.add(new Coordinates(doorX1, doorY2));
//                this.coordinates.add(new Coordinates(doorX2, doorY2));
//                this.coordinates.add(new Coordinates(doorX2, doorY3));
//                this.coordinates.add(new Coordinates(doorX2, doorY3));
//                this.orientation = "VERTICAL";
                break;
        }
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
