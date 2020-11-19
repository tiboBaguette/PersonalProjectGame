package com.realdolmen.map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Coordinates {
    private float x;
    private float y;

    public Coordinates(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public int getXTileCoords() {
        return (int) Math.floor(x / 16);
    }

    public int getYTileCoords() {
        return (int) Math.floor(y / 16);
    }
}
