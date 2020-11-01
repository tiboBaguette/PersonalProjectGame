package com.realdolmen.world;

public class Settings {
    // map settings
    private int mapSize;
    private int maxRoomWidth;
    private int maxRoomHeight;
    private int minRoomWidth;
    private int minRoomHeight;

    public Settings() {
        // map settings
        this.mapSize = 5;
        this.maxRoomWidth = 41;
        this.maxRoomHeight = 41;
        this.minRoomWidth = 21;
        this.minRoomHeight = 21;
    }

    public int getMapSize() {
        return mapSize;
    }

    public int getMaxRoomWidth() {
        return maxRoomWidth;
    }

    public int getMaxRoomHeight() {
        return maxRoomHeight;
    }

    public int getMinRoomWidth() {
        return minRoomWidth;
    }

    public int getMinRoomHeight() {
        return minRoomHeight;
    }
}
