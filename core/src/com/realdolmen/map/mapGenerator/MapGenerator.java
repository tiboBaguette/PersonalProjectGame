package com.realdolmen.map.mapGenerator;

import com.realdolmen.entities.Slime;
import com.realdolmen.map.*;
import com.realdolmen.textures.MapTiles;
import com.realdolmen.world.Settings;
import com.realdolmen.world.World;

import java.util.ArrayList;
import java.util.List;

public class MapGenerator {
    // settings
    private final Settings settings;

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
        settings = new Settings();

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
        int roomWidth = (int)Math.floor(Math.random() * (settings.getMaxRoomWidth() - settings.getMinRoomWidth()) + settings.getMinRoomWidth());
        int roomHeight = (int)Math.floor(Math.random() * (settings.getMaxRoomHeight() - settings.getMinRoomHeight()) + settings.getMinRoomWidth());
        startingRoom = new Room(-roomWidth / 2 * settings.getTileSize(), -roomHeight / 2 * settings.getTileSize(), roomWidth, roomHeight);
        startingRoom.setRoomType(RoomType.STARTING);
        map.getRooms().add(startingRoom);
    }

    private void generateRooms() {
        // generate the rest of the rooms
        int roomChance;
        for (int i = 0; i < settings.getMapSize(); i++) {
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
            bossRoom.setX(bossRoom.getX() -  (settings.getBossRoomSize() - bossRoom.getWidth()));
        }
        if (bossRoom.getY() < 0) {
            bossRoom.setY(bossRoom.getY() - (settings.getBossRoomSize() - bossRoom.getHeight()));
        }

        // set new size
        bossRoom.setWidth(settings.getBossRoomSize());
        bossRoom.setHeight(settings.getBossRoomSize());
        this.bossRoom = bossRoom;
    }

    private void addEnemies() {
        for (Room room : map.getRooms()) {
            if (room.equals(bossRoom)) { // spawn boss
                float x = room.getX() + room.getWidth() / 2f;
                float y = room.getY() + room.getHeight() / 2f;
                Slime slime = new Slime(x * settings.getTileSize(), y * settings.getTileSize(), settings.getSlimeSize(), settings.getSlimeSize(), 5);

                // prevent slimes from spawning inside eachother
                slime.checkSpawnLocation();

                world.addSlime(slime);
            } else if (!room.equals(startingRoom)) { // spawn slimes
                int roomSize = room.getWidth() * room.getHeight();
                int amountOfEnemies = (int) Math.floor(Math.random() * roomSize / 50f) + 5; // 5 = min enemies

                for (int i = 0; i < amountOfEnemies; i++) {
                    int x = (int) Math.floor((Math.random() * (room.getWidth() - 2)) + room.getX() + 2); // 2 is wall distance
                    int y = (int) Math.floor((Math.random() * (room.getHeight() - 2)) + room.getY() + 2);

                    // 20% chance to spawn stage 2
                    int stage = (int) Math.floor(Math.random() * 5) + 1;
                    if (stage > 1) {
                        stage = 1;
                    } else {
                        stage = 2;
                    }

                    // create new slime
                    // todo slimeSize in slime class
                    Slime slime = new Slime(x * settings.getTileSize(), y * settings.getTileSize(), settings.getSlimeSize(), settings.getSlimeSize(), stage);

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
        // room variables
        String orientation;
        int roomWidth = (int)Math.floor(Math.random() * (settings.getMaxRoomWidth() - settings.getMinRoomWidth()) + settings.getMinRoomWidth());
        int roomHeight = (int)Math.floor(Math.random() * (settings.getMaxRoomHeight() - settings.getMinRoomHeight()) + settings.getMinRoomHeight());
        int roomX;
        int roomY;
        // corridor variables
        int corridorWidth = 4; // width is 1 bigger than actual value
        int corridorHeight = (int) Math.floor(Math.random() * settings.getMaxExtraCorridorLenght() + settings.getMinCorridorLenght());
        int corridorX;
        int corridorY;

        if (!room.isRoomUp()) {
            if (Math.random() * 100 < roomChance) {
                orientation = "UP";
                // generate corridor with random length at random place
                corridorX = room.getX() + ((int) Math.floor(Math.random() * (room.getWidth() - corridorWidth + 1)));
                corridorY = room.getY() + room.getHeight();

                // generate room at the end of the corridor
                roomX = (int) Math.floor(Math.random() * (corridorWidth - roomWidth) + corridorX);
                roomY = corridorY + corridorHeight;

                // make the new room
                makeNewRoom(roomX, roomY, roomWidth, roomHeight, corridorX, corridorY, corridorWidth, corridorHeight, orientation);
            }
        }

        if (!room.isRoomDown()) {
            if (Math.random() * 100 < roomChance) {
                orientation = "DOWN";
                // generate corridor with random length at random place
                corridorX = room.getX() + ((int) Math.floor(Math.random() * (room.getWidth() - corridorWidth + 1)));
                corridorY = room.getY() - corridorHeight;

                // generate room at the end of the corridor
                roomX = (int) Math.floor(Math.random() * (corridorWidth - roomWidth) + corridorX);
                roomY = corridorY - roomHeight;

                // make the new room
                makeNewRoom(roomX, roomY, roomWidth, roomHeight, corridorX, corridorY, corridorWidth, corridorHeight, orientation);
            }
        }

        if (!room.isRoomLeft()) {
            if (Math.random() * 100 < roomChance) {
                orientation = "LEFT";
                // generate corridor with random length at random place
                corridorX = room.getX() - corridorHeight;
                corridorY = room.getY() + ((int) Math.floor(Math.random() * (room.getHeight() - corridorWidth + 1)));

                // generate room at the end of the corridor
                roomX = corridorX - roomWidth;
                roomY = (int) Math.floor(Math.random() * (corridorWidth - roomHeight) + corridorY);

                // make the new room
                makeNewRoom(roomX, roomY, roomWidth, roomHeight, corridorX, corridorY, corridorHeight, corridorWidth, orientation);
            }
        }

        if (!room.isRoomRight()) {
            if (Math.random() * 100 < roomChance) {
                orientation = "RIGHT";
                // generate corridor with random length at random place
                corridorX = room.getX() + room.getWidth();
                corridorY = room.getY() + ((int) Math.floor(Math.random() * (room.getHeight() - corridorWidth + 1)));

                // generate room at the end of the corridor
                roomX = corridorX + corridorHeight;
                roomY = (int) Math.floor(Math.random() * (corridorWidth - roomHeight) + corridorY);

                // make the new room
                makeNewRoom(roomX, roomY, roomWidth, roomHeight, corridorX, corridorY, corridorHeight, corridorWidth, orientation);
            }
        }
    }

    private void makeNewRoom(int roomX, int roomY, int roomWidth, int roomHeight, int corridorX, int corridorY, int corridorWidth, int corridorHeight, String orientation) {
        // add room & corridor if there are no collisions with other rooms
        if (!collision(roomX, roomY, roomWidth, roomHeight)) {
            // create the new room
            Room newRoom = new Room(roomX * settings.getTileSize(), roomY * settings.getTileSize() , roomWidth, roomHeight);

            // set the used side of the room
            switch (orientation) {
                case "UP":
                    newRoom.setRoomDown(true);
                    break;
                case "DOWN":
                    newRoom.setRoomUp(true);
                    break;
                case "LEFT":
                    newRoom.setRoomRight(true);
                    break;
                case "RIGHT":
                    newRoom.setRoomLeft(true);
                    break;
            }

            // add new room
            map.getRooms().add(newRoom);

            // add corridors
            map.getCorridors().add(new Corridor(corridorX * settings.getTileSize(), corridorY * settings.getTileSize(), corridorWidth, corridorHeight));

            // add doors
            map.getDoors().add(new Doorway(corridorX, corridorY, corridorWidth, corridorHeight, orientation));
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
