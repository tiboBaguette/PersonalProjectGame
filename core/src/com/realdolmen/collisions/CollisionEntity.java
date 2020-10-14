package com.realdolmen.collisions;

import java.util.ArrayList;
import java.util.List;

public class CollisionEntity {
    private float x;
    private float y;
    private float width;
    private float height;

    private static List<CollisionEntity> collisionEntities = new ArrayList<>();
    boolean collision;

    public CollisionEntity(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void move(CollisionEntity collisionEntity1, float moveX, float moveY) {
        collision = false;
        for (CollisionEntity collisionEntity2 : collisionEntities) {
            // check for collisions
            if (collisionEntity1.getX() + collisionEntity1.getWidth() / 4 + moveX > collisionEntity2.getX() - collisionEntity2.getWidth() / 4) {
                if (collisionEntity1.getX() - collisionEntity1.getWidth() / 4 + moveX < collisionEntity2.getX() + collisionEntity2.getWidth() / 4) {
                    if (collisionEntity1.getY() + collisionEntity1.getHeight() / 4 + moveY > collisionEntity2.getY() - collisionEntity2.getHeight() / 4) {
                        if (collisionEntity1.getY() - collisionEntity1.getHeight() / 4 + moveY < collisionEntity2.getY() + collisionEntity2.getHeight() / 4) {
                            if (!collisionEntity1.equals(collisionEntity2)) {
                                // collision
                                collision = true;
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
        }
    }

    public List<CollisionEntity> getCollisionEntities() {
        return collisionEntities;
    }

    public void addCollisionEntity(CollisionEntity collisionEntity) {
        collisionEntities.add(collisionEntity);
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
}
