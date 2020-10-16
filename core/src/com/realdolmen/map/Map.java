package com.realdolmen.map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.realdolmen.textures.MapTiles;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private List<Room> rooms = new ArrayList<>();
    private List<Corridor> corridors = new ArrayList<>();
    private int x,y;
    private int maxRoomWidth, maxRoomHeight, minRoomWidth, minRoomHeight;
    private int maxRooms, minRooms;
    private List<Doorway> doors = new ArrayList<>();

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
        // generate starting room at the center of the map
        int roomWidth = (int)Math.floor(Math.random() * (maxRoomWidth - minRoomWidth) + minRoomWidth);
        int roomHeight = (int)Math.floor(Math.random() * (maxRoomHeight - minRoomHeight) + minRoomWidth);
        Room startingRoom = new Room(-roomWidth / 2 * 16, -roomHeight / 2 * 16, roomWidth, roomHeight);
        rooms.add(startingRoom);

        // generate 4 random rooms to the starting room
        float roomChance = 100;
        generateCorridorsWithRooms(startingRoom, roomChance);

        List<Coordinates> doorCoordinatesnew = new ArrayList<>();
//        for (Doorway doorway, doors) {
//            doorCoordinatesnew.add(doorway.getDoorCoordinates());
//        }

        for (Corridor corridor : corridors) {
            corridor.generate(batch, mapTiles, doorCoordinatesnew);
        }

        for (Room room : rooms) {

            room.generate(batch, mapTiles, doorCoordinatesnew);
        }
    }

    private void generateCorridorsWithRooms(Room room, float roomChance) {
        // corridor lenght
        int lenght1 = 15; // 15 & 10 = between 10 and 25   25 = sum of both lenghts
        int length2 = 10;

        if (!room.isRoomUp()) {
            if (Math.random() * 100 < roomChance) {
                // generate corridor with random length at random place
                int corridorWidth = 4; // width is 1 bigger than actual value
                int corridorHeight = (int) Math.floor(Math.random() * lenght1 + length2);
                int corridorX = room.getX() + ((int) Math.floor(Math.random() * (room.getWidth() - corridorWidth + 1)));
                int corridorY = room.getY() + room.getHeight();

                // generate room at the end of the corridor
                int roomWidth = (int)Math.floor(Math.random() * (maxRoomWidth - minRoomWidth) + minRoomWidth);
                int roomHeight = (int)Math.floor(Math.random() * (maxRoomHeight - minRoomHeight) + minRoomWidth);
                int roomX = (int) Math.floor(Math.random() * (corridorWidth - roomWidth) + corridorX);
                int roomY = corridorY + corridorHeight;

                // add room/corridor if there are no collisions
                if (!collision(roomX, roomY, roomWidth, roomHeight)) {
                    Room newRoom = new Room(roomX * 16, roomY * 16 , roomWidth, roomHeight);
                    newRoom.setRoomDown(true);
                    rooms.add(newRoom);
                    corridors.add(new Corridor(corridorX * 16, corridorY * 16, corridorWidth, corridorHeight));
                }

                // add doors
                Doorway doorway = new Doorway(corridorX, corridorY, corridorWidth, "UP");
                doors.add(doorway);

                int doorX1 = corridorX + 1;
                int doorX2 = corridorX + 2;
                int doorX3 = corridorX + 3;

                int doorY1 = corridorY;
                int doorY2 = corridorY + corridorHeight;


            }
        }
        if (!room.isRoomDown()) {
            if (Math.random() * 100 < roomChance) {
                // generate corridor with random length at random place
                int corridorWidth = 4; // width is 1 bigger than actual value
                int corridorHeight = (int) Math.floor(Math.random() * lenght1 + length2);
                int corridorX = room.getX() + ((int) Math.floor(Math.random() * (room.getWidth() - corridorWidth + 1)));
                int corridorY = room.getY() - corridorHeight;

                // generate room at the end of the corridor
                int roomWidth = (int)Math.floor(Math.random() * (maxRoomWidth - minRoomWidth) + minRoomWidth);
                int roomHeight = (int)Math.floor(Math.random() * (maxRoomHeight - minRoomHeight) + minRoomWidth);
                int roomX = (int) Math.floor(Math.random() * (corridorWidth - roomWidth) + corridorX);
                int roomY = corridorY - roomHeight;

                // add room/corridor if there are no collisions
                if (!collision(roomX, roomY, roomWidth, roomHeight)) {
                    Room newRoom = new Room(roomX * 16, roomY * 16 , roomWidth, roomHeight);
                    newRoom.setRoomUp(true);
                    rooms.add(newRoom);
                    corridors.add(new Corridor(corridorX * 16, corridorY * 16, corridorWidth, corridorHeight));
                }

                // add doors
                Doorway doorway = new Doorway(corridorX, corridorY, corridorWidth, "DOWN");
                doors.add(doorway);

                int doorX1 = corridorX + 1;
                int doorX2 = corridorX + 2;
                int doorX3 = corridorX + 3;

                int doorY1 = corridorY;
                int doorY2 = corridorY - corridorHeight;
            }
        }
        if (!room.isRoomLeft()) {
            if (Math.random() * 100 < roomChance) {
                // generate corridor with random length at random place
                int corridorWidth = (int) Math.floor(Math.random() * lenght1 + length2);
                int corridorHeight = 4; // width is 1 bigger than actual value
                int corridorX = room.getX() - corridorWidth;
                int corridorY = room.getY() + ((int) Math.floor(Math.random() * (room.getHeight() - corridorHeight + 1)));

                // generate room at the end of the corridor
                int roomWidth = (int)Math.floor(Math.random() * (maxRoomWidth - minRoomWidth) + minRoomWidth);
                int roomHeight = (int)Math.floor(Math.random() * (maxRoomHeight - minRoomHeight) + minRoomWidth);
                int roomX = corridorX - roomWidth;
                int roomY = (int) Math.floor(Math.random() * (corridorHeight - roomHeight) + corridorY);

                // add room/corridor if there are no collisions
                if (!collision(roomX, roomY, roomWidth, roomHeight)) {
                    Room newRoom = new Room(roomX * 16, roomY * 16 , roomWidth, roomHeight);
                    newRoom.setRoomRight(true);
                    rooms.add(newRoom);
                    corridors.add(new Corridor(corridorX * 16, corridorY * 16, corridorWidth, corridorHeight));
                }

                // add doors
                Doorway doorway = new Doorway(corridorX, corridorY, corridorWidth, "LEFT");
                doors.add(doorway);

                int doorY1 = corridorY + 1;
                int doorY2 = corridorY + 2;
                int doorY3 = corridorY + 3;

                int doorX1 = corridorX;
                int doorX2 = corridorX + corridorWidth;
            }
        }
        if (!room.isRoomRight()) {
            if (Math.random() * 100 < roomChance) {
                // generate corridor with random length at random place
                int corridorWidth = (int) Math.floor(Math.random() * lenght1 + length2);
                int corridorHeight = 4; // width is 1 bigger than actual value
                int corridorX = room.getX() + room.getWidth();
                int corridorY = room.getY() + ((int) Math.floor(Math.random() * (room.getHeight() - corridorHeight + 1)));

                // generate room at the end of the corridor
                int roomWidth = (int)Math.floor(Math.random() * (maxRoomWidth - minRoomWidth) + minRoomWidth);
                int roomHeight = (int)Math.floor(Math.random() * (maxRoomHeight - minRoomHeight) + minRoomWidth);
                int roomX = corridorX + corridorWidth;
                int roomY = (int) Math.floor(Math.random() * (corridorHeight - roomHeight) + corridorY);

                // add room/corridor if there are no collisions
                if (!collision(roomX, roomY, roomWidth, roomHeight)) {
                    Room newRoom = new Room(roomX * 16, roomY * 16 , roomWidth, roomHeight);
                    newRoom.setRoomLeft(true);
                    rooms.add(newRoom);
                    corridors.add(new Corridor(corridorX * 16, corridorY * 16, corridorWidth, corridorHeight));
                }

                // add doors
                Doorway doorway = new Doorway(corridorX, corridorY, corridorWidth, "RIGHT");
                doors.add(doorway);

                int doorY1 = corridorY + 1;
                int doorY2 = corridorY + 2;
                int doorY3 = corridorY + 3;

                int doorX1 = corridorX;
                int doorX2 = corridorX + corridorWidth;
            }
        }
    }

    public void draw(Batch batch) {
        for (Corridor corridor : corridors) {
            corridor.draw(batch);
        }

        for (Room room : rooms) {
            room.draw(batch);
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