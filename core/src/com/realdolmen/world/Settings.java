package com.realdolmen.world;

import lombok.Getter;

@Getter
public class Settings {
    // general
    @Getter private static final int tileSize = 16;
    @Getter private static final int chunkSize = 8;
    @Getter private static final float animationLength = 0.125f;
    @Getter private static final int arrowSpeed = 10;

    // player
    @Getter private static final int playerSize = 48;
    @Getter private static final int playerMaxHealth = 100;
    @Getter private static final int playerMoveSpeed = 3;
    @Getter private static final int playerAttackSpeed = 1; // not yet implemented
    @Getter private static final int playerAttackDamage = 100;

    // slime
    @Getter private static final int slimeSize = 16;
    @Getter private static final int slimeAttackDistance = 16;
    @Getter private static final int slimeMaxHealth = 100;
    @Getter private static final int slimeMoveSpeed = 2;
    @Getter private static final int slimeAttackSpeed = 1; // not yet implemented
    @Getter private static final int slimeAttackDamage = 5;

    // map
    @Getter private static final int mapSize = 6;
    @Getter private static final int bossRoomSize = 60;
    @Getter private static final int maxRoomWidth = 50;
    @Getter private static final int maxRoomHeight = 50;
    @Getter private static final int minRoomWidth = 30;
    @Getter private static final int minRoomHeight = 30;
    @Getter private static final int minDistanceBetweenRooms = 5;
    @Getter private static final int corridorWidth = 5;
    @Getter private static final int minCorridorLength = 15;
    @Getter private static final int maxExtraCorridorLength = 15; // max corridor length = minCorridorLength + maxExtraCorridorLength
    @Getter private static final int startingRoomChance = 100; // the chance to spawn extra rooms to the starting rooms
    @Getter private static final int roomChance = 60; // the chance to spawn extra rooms

    // enemies
    @Getter private static final int minEnemiesPerRoom = 10;
    @Getter private static final int bossStage = 6;
}
