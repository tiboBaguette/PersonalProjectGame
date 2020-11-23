package com.realdolmen.map.mapGenerator;

import com.realdolmen.entities.Slime;
import com.realdolmen.map.*;
import com.realdolmen.textures.MapTiles;
import com.realdolmen.world.World;

import java.util.ArrayList;
import java.util.List;

public class MapGenerator {
    // map settings
    private final int maxRoomWidth, maxRoomHeight, minRoomWidth, minRoomHeight;
    private final int mapSize, bossRoomSize;

    // rooms
    private final List<Room> usedRooms;
    private Room startingRoom;
    private Room bossRoom;

    // map / world
    private Map map;
    private final MapTiles mapTileset;
    private final World world;


    public MapGenerator(World world) {
        this.world = world;
        this.usedRooms = new ArrayList<>();

        // settings
        this.maxRoomWidth = world.getSettings().getMaxRoomWidth();
        this.maxRoomHeight = world.getSettings().getMaxRoomHeight();
        this.minRoomWidth = world.getSettings().getMinRoomWidth();
        this.minRoomHeight = world.getSettings().getMinRoomHeight();
        this.mapSize = world.getSettings().getMapSize();
        this.bossRoomSize = world.getSettings().getBossRoomSize();

        // create the tile textures
        mapTileset = new MapTiles();
        mapTileset.createTextures();
    }

    public Map generateNewMap() {
        // get a new map
        map = new Map();
        // generate starting room at the center of the map
        generateStartingRoom();
        // generate the rest of the rooms
        generateRooms();
        // change the room with the highest coordinates into a boss room
        generateBossRoom();
        // create the map
        createMap();
        // add enemies to the map
        addEnemies();

        return map;
    }

    private void generateStartingRoom() {
        // generate starting room at the center of the map
        int roomWidth = (int)Math.floor(Math.random() * (maxRoomWidth - minRoomWidth) + minRoomWidth);
        int roomHeight = (int)Math.floor(Math.random() * (maxRoomHeight - minRoomHeight) + minRoomWidth);
        startingRoom = new Room(-roomWidth / 2 * 16, -roomHeight / 2 * 16, roomWidth, roomHeight);
        startingRoom.setRoomType(RoomType.STARTING);
        map.getRooms().add(startingRoom);
    }

    private void generateRooms() {
        // generate the rest of the rooms
        int roomChance;
        for (int i = 0; i < mapSize; i++) {
            if (i == 0) {
                // generate 4 random rooms to the starting room
                roomChance = 100; // each side has 100% chance to spawn a new room
                generateCorridorsWithRooms(startingRoom, roomChance);
                usedRooms.add(startingRoom);
            } else {
                // get all the new rooms and generate extra rooms
                roomChance = 50; // each side has 50% chance to spawn a new room
                List<Room> roomsToGenerateOf = new ArrayList<>();
                for (Room room1 : map.getRooms()) {
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
    }

    private void generateBossRoom() {
        // change the room with the highest coordinates into a boss room
        Room bossRoom = startingRoom;
        for (Room room : map.getRooms()) {
            int distance1 = (Math.abs(room.getX()) + Math.abs(room.getY()));
            int distance2 = (Math.abs(bossRoom.getX()) + Math.abs(bossRoom.getY()));

            if (distance1 > distance2) {
                bossRoom = room;
            }

        }

        // set the roomType
        bossRoom.setRoomType(RoomType.BOSS);

        // move the room to fit new size
        if (bossRoom.getX() < 0) {
            bossRoom.setX(bossRoom.getX() -  (bossRoomSize - bossRoom.getWidth()));
        }
        if (bossRoom.getY() < 0) {
            bossRoom.setY(bossRoom.getY() - (bossRoomSize - bossRoom.getHeight()));
        }

        // set new size
        bossRoom.setWidth(bossRoomSize);
        bossRoom.setHeight(bossRoomSize);
        this.bossRoom = bossRoom;
    }

    private void addEnemies() {
        for (Room room : map.getRooms()) {
            if (room.equals(bossRoom)) { // spawn boss
                float x = room.getX() + room.getWidth() / 2f;
                float y = room.getY() + room.getHeight() / 2f;
                Slime slime = new Slime(x * 16, y * 16, 16, 16, 5);

                // prevent slimes from spawning inside eachother
                slime.checkSpawnLocation();

                world.addSlime(slime);
            } else if (!room.equals(startingRoom)) { // spawn slimes
                int roomSize = room.getWidth() * room.getHeight();
                int amountOfEnemies = (int) Math.floor(Math.random() * roomSize / 50f) + 5; // 5 = min enemies

                for (int i = 0; i < amountOfEnemies; i++) {
                    int x = (int) Math.floor((Math.random() * (room.getWidth() - 2)) + room.getX() + 2); // 2 is wall distance
                    int y = (int) Math.floor((Math.random() * (room.getHeight() - 2)) + room.getY() + 2);
                    Slime slime = new Slime(x * 16, y * 16, 16, 16, 1);

                    // prevent slimes from spawning inside eachother
                    slime.checkSpawnLocation();

                    world.addSlime(slime);
                }
            }
        }
    }

    private void createMap() {
        // get all the door coordinates
        List<Coordinates> doorCoordinates = new ArrayList<>();
        for (Doorway doorway : map.getDoors()) {
            doorCoordinates.addAll(doorway.getCoordinates());
        }

        // generate all the rooms
        for (Corridor corridor : map.getCorridors()) {
            corridor.generate(mapTileset, doorCoordinates);
        }

        for (Room room : map.getRooms()) {
            room.generate(mapTileset, doorCoordinates);
        }
    }

    private void generateCorridorsWithRooms(Room room, float roomChance) {
        // corridor lenght
        int lenght1 = 15; // 15 & 10 = between 10 and 25   10 = lenght2, 25 = sum of both lenghts
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
                    map.getRooms().add(newRoom);
                    map.getCorridors().add(new Corridor(corridorX * 16, corridorY * 16, corridorWidth, corridorHeight));

                    // add doors
                    Doorway doorway = new Doorway(corridorX, corridorY, corridorWidth, corridorHeight, "UP");
                    map.getDoors().add(doorway);
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
                    map.getRooms().add(newRoom);
                    map.getCorridors().add(new Corridor(corridorX * 16, corridorY * 16, corridorWidth, corridorHeight));

                    // add doors
                    Doorway doorway = new Doorway(corridorX, corridorY, corridorWidth, corridorHeight, "DOWN");
                    map.getDoors().add(doorway);
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
                    map.getRooms().add(newRoom);
                    map.getCorridors().add(new Corridor(corridorX * 16, corridorY * 16, corridorWidth, corridorHeight));

                    // add doors
                    Doorway doorway = new Doorway(corridorX, corridorY, corridorWidth, corridorHeight, "LEFT");
                    map.getDoors().add(doorway);
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
                    map.getRooms().add(newRoom);
                    map.getCorridors().add(new Corridor(corridorX * 16, corridorY * 16, corridorWidth, corridorHeight));

                    // add doors
                    Doorway doorway = new Doorway(corridorX, corridorY, corridorWidth, corridorHeight, "RIGHT");
                    map.getDoors().add(doorway);
                }
            }
        }
    }

    private boolean collision(int roomX, int roomY, int roomWidth, int roomHeight) {
        boolean collision = false;
        for (Room room : map.getRooms()) {
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
