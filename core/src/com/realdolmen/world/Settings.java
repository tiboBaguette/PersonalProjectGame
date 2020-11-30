package com.realdolmen.world;

import lombok.Getter;

@Getter
public class Settings {
    // general
    private final int tileSize;
    private final int slimeSize;

    // map
    private final int mapSize;
    private final int maxRoomWidth;
    private final int maxRoomHeight;
    private final int minRoomWidth;
    private final int minRoomHeight;
    private final int bossRoomSize;

    public Settings() {
        // general
        this.tileSize = 16;
        this.slimeSize = 16;

        // map
        this.mapSize = 5;
        this.maxRoomWidth = 41;
        this.maxRoomHeight = 41;
        this.minRoomWidth = 21;
        this.minRoomHeight = 21;
        this.bossRoomSize = 50;
    }
}
