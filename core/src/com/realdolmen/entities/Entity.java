package com.realdolmen.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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

    void draw(Batch batch, Animation<TextureRegion> currentAnimation, float drawWidth, float drawHeight, boolean flip, float elapsedTime) {
        // draw the center of the image at the center of the collision box
        float x = getX() + getWidth()/2 - drawWidth/2;
        float y = getY() + getHeight()/2 - drawHeight/2;
        float width = drawWidth;
        float height = drawHeight;
        if (flip) {
            width *= -1;
            x += drawWidth;
        }

        batch.draw(currentAnimation.getKeyFrame(elapsedTime, true), x, y, width, height);
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
