package com.realdolmen.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.realdolmen.world.Settings;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class Entity {
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

    void draw(Batch batch, Animation<TextureRegion> currentAnimation, float drawWidth, float drawHeight, boolean flip, float elapsedTime) {
        // draw the center of the image at the center of the collision box
        // getWidth = collision width, drawWidth = TexturedRegion width
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

    public int getChunkX() {
        return (int)Math.floor(this.getX() / Settings.getTileSize() / Settings.getChunkSize());
    }

    public int getChunkY() {
        return (int)Math.floor(this.getY() / Settings.getTileSize() / Settings.getChunkSize());
    }
}
