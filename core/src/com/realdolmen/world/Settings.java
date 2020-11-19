package com.realdolmen.world;

import lombok.Getter;

@Getter
public class Settings {
    // map settings
    private final int mapSize;
    private final int maxRoomWidth;
    private final int maxRoomHeight;
    private final int minRoomWidth;
    private final int minRoomHeight;

    public Settings() {
        // map settings
        this.mapSize = 5;
        this.maxRoomWidth = 41;
        this.maxRoomHeight = 41;
        this.minRoomWidth = 21;
        this.minRoomHeight = 21;
    }
}
