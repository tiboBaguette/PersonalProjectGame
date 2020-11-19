package com.realdolmen.map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.realdolmen.textures.MapTiles;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Room {
    private int x;
    private int y;
    private int width;
    private int height;
    private List<Tile> tileList = new ArrayList<>();
    private RoomType roomType;

    private boolean roomUp = false;
    private boolean roomDown = false;
    private boolean roomLeft = false;
    private boolean roomRight = false;

    public Room(int x, int y, int width, int height) {
        this.x = x / 16;
        this.y = y / 16;
        this.width = width;
        this.height = height;

        this.roomType = RoomType.NORMAL;
    }

    public void generate(MapTiles mapTileset, List<Coordinates> doorCoordinates) {
        for (int i = 0; i <= width; i++) {
            for (int j = 0; j <= height; j++) {
                boolean isDoorLocation = false;
                for (Coordinates coordinates : doorCoordinates) {
                    if (coordinates.getXTileCoords() == i + x && coordinates.getYTileCoords() == j + y) {
                        isDoorLocation = true;
                    }
                }

                // doors
                if (isDoorLocation) {
                    tileList.add(new Tile(i + x, j + y, getRandomTile(mapTileset.getFloor()), false));
                }

                // corners
                else if (i == 0 && j == 0) {
                    tileList.add(new Tile(i + x, j + y, mapTileset.getWallCornerBottomLeft(), true));
                }
                else if (i == 0 && j == height) {
                    tileList.add(new Tile(i + x, j + y, mapTileset.getWallCornerTopLeft(), true));
                }
                else if (i == width && j == 0) {
                    tileList.add(new Tile(i + x, j + y, mapTileset.getWallCornerBottomRight(), true));
                }
                else if (i == width && j == height) {
                    tileList.add(new Tile(i + x , j + y, mapTileset.getWallCornerTopRight(), true));
                }

                // walls
                else if (i == 0) {
                    tileList.add(new Tile(i + x, j + y, getRandomTile(mapTileset.getWallVertical()), true));
                }
                else if (i == width) {
                    tileList.add(new Tile(i + x, j + y, getRandomTile(mapTileset.getWallVertical()), true));
                }
                else if (j == 0) {
                    tileList.add(new Tile(i + x, j + y, getRandomTile(mapTileset.getWallHorizontal()), true));
                }
                else if (j == height) {
                    tileList.add(new Tile(i + x, j + y, getRandomTile(mapTileset.getWallHorizontal()), true));
                }

                // floor
                else {
                    tileList.add(new Tile(i + x, j + y, getRandomTile(mapTileset.getFloor()), false));
                }
            }
        }
    }

    public void draw(Batch batch) {
        for (Tile tile : tileList) {
            tile.draw(batch);
        }
    }

    private TextureRegion getRandomTile(List<TextureRegion> textures) {
        int index = (int)Math.floor(Math.random() * textures.size());
        return textures.get(index);
    }
}