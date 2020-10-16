package com.realdolmen.collisions;

import java.util.ArrayList;
import java.util.List;

public class CollisionEntity {
    private final int TILE_SIZE = 16;
    private final int CHUNK_SIZE = 8;

    private float x;
    private float y;
    private float width;
    private float height;
    private int chunkX;
    private int chunkY;

    private static List<CollisionEntity> collisionEntities = new ArrayList<>();
    boolean collision;

    public CollisionEntity(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean move(CollisionEntity collisionEntity1, float moveX, float moveY) {
        collision = false;
        for (CollisionEntity collisionEntity2 : collisionEntities) {
            // only check same or neighboring chunks
            if (Math.abs(collisionEntity1.getChunkX() - collisionEntity2.getChunkX()) <= 1) {
                if (Math.abs(collisionEntity1.getChunkY() - collisionEntity2.getChunkY()) <= 1) {
                    // check for collisions
                    if (collisionEntity1.getX() + collisionEntity1.getWidth() / 2 + moveX > collisionEntity2.getX() - collisionEntity2.getWidth() / 2) {
                        if (collisionEntity1.getX() - collisionEntity1.getWidth() / 2 + moveX < collisionEntity2.getX() + collisionEntity2.getWidth() / 2) {
                            if (collisionEntity1.getY() + collisionEntity1.getHeight() / 2 + moveY > collisionEntity2.getY() - collisionEntity2.getHeight() / 2) {
                                if (collisionEntity1.getY() - collisionEntity1.getHeight() / 2 + moveY < collisionEntity2.getY() + collisionEntity2.getHeight() / 2) {
                                    if (!collisionEntity1.equals(collisionEntity2)) {
                                        // collision
                                        collision = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // move
        if (!collision) {
            collisionEntity1.setX(collisionEntity1.getX() + moveX);
            collisionEntity1.setY(collisionEntity1.getY() + moveY);
            setChunkCoordinates(x, y);
            return true;
        } else {
            return false;
        }
    }

    public void addCollisionEntity(CollisionEntity collisionEntity) {
        setChunkCoordinates(x, y);
        collisionEntities.add(collisionEntity);
    }

    public void setChunkCoordinates(float x, float y) {
        chunkX = (int)Math.floor(x / TILE_SIZE / CHUNK_SIZE);
        chunkY = (int)Math.floor(y / TILE_SIZE / CHUNK_SIZE);
    }

    public List<CollisionEntity> getCollisionEntities() {
        return collisionEntities;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public int getChunkX() {
        return chunkX;
    }

    public void setChunkX(int chunkX) {
        this.chunkX = chunkX;
    }

    public int getChunkY() {
        return chunkY;
    }

    public void setChunkY(int chunkY) {
        this.chunkY = chunkY;
    }
}
