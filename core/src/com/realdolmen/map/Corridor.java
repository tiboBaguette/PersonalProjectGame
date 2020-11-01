package com.realdolmen.map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.realdolmen.textures.MapTiles;

import java.util.List;

public class Corridor {
    Room room;

    public Corridor(int x, int y, int width, int height) {
        room = new Room(x, y, width, height);
        room.setRoomType(RoomType.CORRIDOR);
    }

    public void generate(MapTiles mapTileset, List<Coordinates> coordinates) {
        room.generate(mapTileset, coordinates);
    }

    public void draw(Batch batch) {
        room.draw(batch);
    }
}
