package com.realdolmen.entities;

public abstract class Entity {
    private static final int TILE_SIZE = 16;
    private static final int CHUNK_SIZE = 8;

    private float x;
    private float y;
    private float width;
    private float height;

    public Entity(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getChunkX() {
        return (int)Math.floor(this.getX() / TILE_SIZE / CHUNK_SIZE);
    }

    public int getChunkY() {
        return (int)Math.floor(this.getY() / TILE_SIZE / CHUNK_SIZE);
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

    @Override
    public String toString() {
        return "Entity{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
