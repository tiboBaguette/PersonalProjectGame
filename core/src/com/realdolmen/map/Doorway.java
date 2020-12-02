package com.realdolmen.map;

import com.realdolmen.world.Settings;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Doorway {
    private List<Coordinates> coordinates = new ArrayList<>();
    private String orientation;

    public Doorway(int corridorX, int corridorY, int corridorWidth, int corridorHeight, String orientation) {
        int doorX1, doorX2, doorX3;
        int doorY1, doorY2, doorY3;

        switch (orientation) {
            case "UP":
            case "DOWN":
                doorX1 = corridorX + 1;
                doorX2 = corridorX + 2;
                doorX3 = corridorX + 3;

                doorY1 = corridorY;
                doorY2 = corridorY + corridorHeight;

                this.coordinates.add(new Coordinates(doorX1 * Settings.getTileSize(), doorY1 * Settings.getTileSize()));
                this.coordinates.add(new Coordinates(doorX2 * Settings.getTileSize(), doorY1 * Settings.getTileSize()));
                this.coordinates.add(new Coordinates(doorX3 * Settings.getTileSize(), doorY1 * Settings.getTileSize()));
                this.coordinates.add(new Coordinates(doorX1 * Settings.getTileSize(), doorY2 * Settings.getTileSize()));
                this.coordinates.add(new Coordinates(doorX2 * Settings.getTileSize(), doorY2 * Settings.getTileSize()));
                this.coordinates.add(new Coordinates(doorX3 * Settings.getTileSize(), doorY2 * Settings.getTileSize()));
                this.orientation = "HORIZONTAL";

                break;
            case "LEFT":
            case "RIGHT":
                doorY1 = corridorY + 1;
                doorY2 = corridorY + 2;
                doorY3 = corridorY + 3;

                doorX1 = corridorX;
                doorX2 = corridorX + corridorWidth;

                this.coordinates.add(new Coordinates(doorX1 * Settings.getTileSize(), doorY1 * Settings.getTileSize()));
                this.coordinates.add(new Coordinates(doorX1 * Settings.getTileSize(), doorY2 * Settings.getTileSize()));
                this.coordinates.add(new Coordinates(doorX1 * Settings.getTileSize(), doorY3 * Settings.getTileSize()));
                this.coordinates.add(new Coordinates(doorX2 * Settings.getTileSize(), doorY1 * Settings.getTileSize()));
                this.coordinates.add(new Coordinates(doorX2 * Settings.getTileSize(), doorY2 * Settings.getTileSize()));
                this.coordinates.add(new Coordinates(doorX2 * Settings.getTileSize(), doorY3 * Settings.getTileSize()));
                this.orientation = "VERTICAL";

                break;
        }
    }
}
