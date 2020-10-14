package com.realdolmen.map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.realdolmen.collisions.CollisionEntity;
import com.realdolmen.textures.MapTiles;

public class MapGeneration {
    private int mapWidth;
    private int mapHeight;

    public MapGeneration(int mapWidth, int mapHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }

//    public String generateRandomMapString() {
//        StringBuilder mapString = new StringBuilder();
//        for (int i = 0; i < mapWidth; i++) {
//            for (int j = 0; j < mapHeight; j++) {
//                // get a tile id
//                int randomNumber = (int)Math.round(Math.random() * 256);
//
//                // add tile id to 2d array string
//                mapString.append(randomNumber).append(",");
//            }
//            mapString.append("\n");
//        }
//
//        return mapString.toString();
//    }

    public void generateMap(Batch batch, MapTiles mapTileset) {
        for (int i = 0; i <= mapWidth; i++) {
            for (int j = 0; j <= mapHeight; j++) {
                // draw outer walls (corners, randomized walls, doors)
                if (i == 0 && j == 0) {
                    // bottom left corner
                    batch.draw(mapTileset.getWallCornerBottomLeft(), i * 16, j * 16);
                    CollisionEntity collisionEntity = new CollisionEntity(i * 16, j * 16, 16, 16);
                    collisionEntity.addCollisionEntity(collisionEntity);
                }
                if (i == 0 && j == mapHeight) {
                    // top left corner
                    batch.draw(mapTileset.getWallCornerTopLeft(), i * 16, j * 16);
                    CollisionEntity collisionEntity = new CollisionEntity(i * 16, j * 16, 16, 16);
                    collisionEntity.addCollisionEntity(collisionEntity);
                }
                if (i == mapWidth && j == 0) {
                    // bottom right corner
                    batch.draw(mapTileset.getWallCornerBottomRight(), i * 16, j * 16);
                    CollisionEntity collisionEntity = new CollisionEntity(i * 16, j * 16, 16, 16);
                    collisionEntity.addCollisionEntity(collisionEntity);
                }
                if (i == mapWidth && j == mapHeight) {
                    // top right corner
                    batch.draw(mapTileset.getWallCornerTopRight(), i * 16, j * 16);
                    CollisionEntity collisionEntity = new CollisionEntity(i * 16, j * 16, 16, 16);
                    collisionEntity.addCollisionEntity(collisionEntity);
                }

                // draw floor
            }
        }
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }
}
