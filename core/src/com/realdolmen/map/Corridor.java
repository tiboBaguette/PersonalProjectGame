package com.realdolmen.map;

public class Corridor {
    private int x;
    private int y;
    private int width;
    private int height;

    public Corridor(Room room1, Room room2) {
        // create vertical corridor
        x = room1.getX() + room1.getWidth() / 2;
        if (room1.getY() > room2.getY()) {
            y = room1.getY();
        } else {
            y = room1.getY() + room1.getHeight();
        }
        width = 5;
        height = Math.abs(room1.getY() - room2.getY());
        Room verticalCorridor = new Room(x, y, width, height);

        // create horizontal corridor
        if (room1.getX() > room2.getX()) {
            y = room1.getX();
        } else {
            y = room1.getX() + room1.getWidth();
        }
        y = room1.getY() + room1.getHeight() / 2;
        width = 5;
        height = Math.abs(room1.getX() - room2.getX());
        Room horizontalCorridor = new Room(x, y, width, height);

        // create corner
    }
}
