package com.realdolmen.map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.realdolmen.collisions.CollisionEntity;

public class Tile {
    private float x;
    private float y;
    private TextureRegion textureRegion;
    private boolean collidable;

    public Tile(float x, float y, TextureRegion textureRegion, boolean collidable) {
        this.x = x;
        this.y = y;
        this.textureRegion = textureRegion;
        this.collidable = collidable;

        if (collidable) {
            createCollisionEntities();
        }
    }

    private void createCollisionEntities() {
        CollisionEntity collisionEntity = new CollisionEntity(x * 16, y * 16, 16, 16);
        collisionEntity.addCollisionEntity(collisionEntity);
    }

    public void draw(Batch batch) {
        batch.draw(textureRegion, x * 16, y * 16);
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

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public void setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }
}
