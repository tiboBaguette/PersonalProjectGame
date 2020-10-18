package com.realdolmen.map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.realdolmen.textures.MapTiles;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private final int maxRoomWidth, maxRoomHeight, minRoomWidth, minRoomHeight;
    private final int mapSize;
    private final List<Room> rooms;
    private final List<Room> usedRooms;
    private final List<Corridor> corridors;
    private final List<Doorway> doors;

    public Map(int mapSize, int maxRoomWidth, int maxRoomHeight, int minRoomWidth, int minRoomHeight) {
        this.maxRoomWidth = maxRoomWidth;
        this.maxRoomHeight = maxRoomHeight;
        this.minRoomWidth = minRoomWidth;
        this.minRoomHeight = minRoomHeight;
        this.mapSize = mapSize;

        rooms = new ArrayList<>();
        usedRooms = new ArrayList<>();
        corridors = new ArrayList<>();
        doors = new ArrayList<>();
    }

    public void generate(MapTiles mapTiles) {
        // generate starting room at the center of the map
        int roomWidth = (int)Math.floor(Math.random() * (maxRoomWidth - minRoomWidth) + minRoomWidth);
        int roomHeight = (int)Math.floor(Math.random() * (maxRoomHeight - minRoomHeight) + minRoomWidth);
        Room startingRoom = new Room(-roomWidth / 2 * 16, -roomHeight / 2 * 16, roomWidth, roomHeight);
        rooms.add(startingRoom);

        // generate the rest of the rooms
        int roomChance;
        for (int i = 0; i < mapSize; i++) {
            if (i == 0) {
                // generate 4 random rooms to the starting room
                roomChance = 100;
                generateCorridorsWithRooms(startingRoom, roomChance);
                usedRooms.add(startingRoom);
            } else {
                // get all the new / outer rooms and generate extra rooms
                roomChance = 50;
                List<Room> roomsToGenerateOf = new ArrayList<>();
                for (Room room1 : rooms) {
                    boolean sameRoom = false;
                    for (Room room2 : usedRooms) {
                        if (room1.equals(room2)) {
                            sameRoom = true;
                            break;
                        }
                    }
                    if (!sameRoom) {
                        roomsToGenerateOf.add(room1);
                    }
                }

                // generate rooms
                for (Room room : roomsToGenerateOf) {
                    generateCorridorsWithRooms(room, roomChance);
                    usedRooms.add(room);
                }
            }
        }


        // get all the door coordinates
        List<Coordinates> doorCoordinates = new ArrayList<>();
        for (Doorway doorway : doors) {
            doorCoordinates.addAll(doorway.getDoorCoordinates());
        }

        // generate everything
        for (Corridor corridor : corridors) {
            corridor.generate(mapTiles, doorCoordinates);
        }

        for (Room room : rooms) {
            room.generate(mapTiles, doorCoordinates);
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

                    // add doors
                    Doorway doorway = new Doorway(corridorX, corridorY, corridorWidth, corridorHeight, "UP");
                    doors.add(doorway);
                }
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

                    // add doors
                    Doorway doorway = new Doorway(corridorX, corridorY, corridorWidth, corridorHeight, "DOWN");
                    doors.add(doorway);
                }
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

                    // add doors
                    Doorway doorway = new Doorway(corridorX, corridorY, corridorWidth, corridorHeight, "LEFT");
                    doors.add(doorway);
                }
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

                    // add doors
                    Doorway doorway = new Doorway(corridorX, corridorY, corridorWidth, corridorHeight, "RIGHT");
                    doors.add(doorway);
                }
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