package com.realdolmen.map;

import com.badlogic.gdx.graphics.g2d.Batch;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Map {
    private final List<Room> rooms;
    private final List<Corridor> corridors;
    private final List<Doorway> doors;
    private Room startingRoom;
    private Room bossRoom;

    public Map() {
        this.rooms = new ArrayList<>();
        this.corridors = new ArrayList<>();
        this.doors = new ArrayList<>();
    }

    public void draw(Batch batch) {
        for (Corridor corridor : corridors) {
            corridor.draw(batch);
        }

        for (Room room : rooms) {
            room.draw(batch);
        }
    }
}