package com.realdolmen.map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.realdolmen.entities.CollisionEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tile {
    private float x;
    private float y;
    private TextureRegion textureRegion;

    public Tile(float x, float y, TextureRegion textureRegion, boolean collidable) {
        this.x = x;
        this.y = y;
        this.textureRegion = textureRegion;

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
}
