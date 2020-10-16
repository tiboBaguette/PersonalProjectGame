package com.realdolmen.map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.realdolmen.textures.MapTiles;

import java.util.List;

public class Corridor {
    Room room;

    public Corridor(int x, int y, int width, int height) {
        room = new Room(x, y, width, height);
    }

    public void generate(Batch batch, MapTiles mapTileset, List<Coordinates> coordinates) {
        room.generate(batch, mapTileset, coordinates);
    }

    public void draw(Batch batch) {
        room.draw(batch);
    }
}
