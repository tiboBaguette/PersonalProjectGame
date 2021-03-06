package com.realdolmen.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.List;

public class CollisionEntity extends Entity {
    private static final List<CollisionEntity> collisionEntities = new ArrayList<>();

    public CollisionEntity(float x, float y, float width, float height) {
        super(x, y, width, height);
        collisionEntities.add(this);
    }

    public CollisionEntity move(CollisionEntity collisionEntity1, float moveX, float moveY, List<CollisionEntity> ignoreCollisions) {
        boolean collision;
        for (CollisionEntity collisionEntity2 : collisionEntities) {
            // only check same or neighboring chunks
            if (Math.abs(collisionEntity1.getChunkX() - collisionEntity2.getChunkX()) <= 1) {
                if (Math.abs(collisionEntity1.getChunkY() - collisionEntity2.getChunkY()) <= 1) {
                    // check for collisions
                    if (collisionEntity1.getX() + collisionEntity1.getWidth() + moveX > collisionEntity2.getX()) {
                        if (collisionEntity1.getX() + moveX < collisionEntity2.getX() + collisionEntity2.getWidth()) {
                            if (collisionEntity1.getY() + collisionEntity1.getHeight() + moveY > collisionEntity2.getY()) {
                                if (collisionEntity1.getY() + moveY < collisionEntity2.getY() + collisionEntity2.getHeight()) {
                                    // check if its not the same entity
                                    if (!collisionEntity1.equals(collisionEntity2)) {
                                        collision = true;
                                        // if the entity is in ignoreCollisions set collision to false
                                        for (CollisionEntity collisionEntity : ignoreCollisions) {
                                            if (collisionEntity.equals(collisionEntity2)) {
                                                collision = false;
                                                break;
                                            }
                                        }
                                        // if there was a collision return the entity
                                        if (collision) {
                                            return collisionEntity2;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // move
        collisionEntity1.setX(collisionEntity1.getX() + moveX);
        collisionEntity1.setY(collisionEntity1.getY() + moveY);
        return collisionEntity1;
    }

    public void addCollisionEntity(CollisionEntity collisionEntity) {
        collisionEntities.add(collisionEntity);
    }

    public void removeCollisionEntity() {
        collisionEntities.remove(this);
    }

    public static List<CollisionEntity> getCollisionEntities() {
        return collisionEntities;
    }

    public void debugCollisions(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(1, 1, 0, 1);
        shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());
    }
}
