package com.realdolmen.map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.realdolmen.textures.MapTiles;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private List<Room> rooms = new ArrayList<>();
    private int x,y;
    private int maxRoomWidth, maxRoomHeight, minRoomWidth, minRoomHeight;
    private int maxRooms, minRooms;

    public Map(int x, int y, int maxRooms, int minRooms, int maxRoomWidth, int maxRoomHeight, int minRoomWidth, int minRoomHeight) {
        this.x = x / 16;
        this.y = y / 16;
        this.maxRoomWidth = maxRoomWidth;
        this.maxRoomHeight = maxRoomHeight;
        this.minRoomWidth = minRoomWidth;
        this.minRoomHeight = minRoomHeight;
        this.maxRooms = maxRooms;
        this.minRooms = minRooms;
    }

    public void generate(Batch batch, MapTiles mapTiles) {
        int amountOfRooms = 0;
        int amountOfAttempts = 0;
        while (minRooms > amountOfRooms || amountOfAttempts < 200) {
            int roomX = (int)Math.floor(Math.random() * x * 2);
            int roomY = (int)Math.floor(Math.random() * y * 2);
            int roomWidth = (int)Math.floor(Math.random() * (maxRoomWidth - minRoomWidth) + minRoomWidth);
            int roomHeight = (int)Math.floor(Math.random() * (maxRoomHeight - minRoomHeight) + minRoomWidth);

            if (!collision(roomX, roomY, roomWidth, roomHeight)) {
                rooms.add(new Room(roomX * 16, roomY * 16, roomWidth, roomHeight));
                amountOfRooms++;
            }
            amountOfAttempts++;
        }
        if (amountOfAttempts > 200) {
            System.out.println("Error during map generation");
        }
        for (Room room : rooms) {
            room.generate(batch, mapTiles);
        }
        for (Room room : rooms) {
            generateCorridors(room);
        }
    }

    public void draw(Batch batch) {
        for (Room room : rooms) {
            room.draw(batch);
        }
    }

    private void generateCorridors(Room room1) {
        double distance1 = 99999;
        for (Room room2 : rooms) {
            if (!room1.equals(room2)) {
                // find the closest room
                double xDiff = Math.abs(room1.getX() - room2.getX());
                double yDiff = Math.abs(room1.getY() - room2.getY());
                double distance2 = Math.sqrt(Math.pow(xDiff, 2) * Math.pow(yDiff, 2));

                System.out.println("1: " + distance1 + "2: " + distance2);

                if (distance1 > distance2) {
                    distance1 = distance2;
                    room1.setClosestRoom(room2);
                }
            }
        }
        for (Room room : rooms) {
            new Corridor(room, room.getClosestRoom());
        }
    }

    private boolean collision(int roomX, int roomY, int roomWidth, int roomHeight) {
        boolean collision = false;
        for (Room room : rooms) {
            // check for collisions
            if (roomX + roomWidth + 2 > room.getX()) {
                if (roomX < room.getX() + room.getWidth() + 2) {
                    if (roomY + roomHeight + 2 > room.getY()) {
                        if (roomY < room.getY() + room.getHeight() + 2) {
                            // collision
                            collision = true;
                        }
                    }
                }
            }
        }
        return collision;
    }
}
