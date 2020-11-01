package com.realdolmen.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.realdolmen.world.World;

import java.util.ArrayList;
import java.util.List;

public class Arrow extends CollisionEntity {
    private static final int WIDTH = 16;
    private static final int HEIGHT = 16;

    private Texture texture;
    private Sprite sprite;
    private float angle;
    private float damage;
    private float speed;
    private float speedX;
    private float speedY;
    private List<CollisionEntity> ignoreCollisions;

    public Arrow(float damage, float startX, float startY, float targetX, float targetY) {
        super(startX, startY, WIDTH / 4f, HEIGHT / 4f);
        ignoreCollisions = new ArrayList<>();

        this.damage = damage;
        this.speed = 8;
        this.texture = new Texture(Gdx.files.internal("core/assets/images/arrow.png"));
        this.angle = 0;
        sprite = new Sprite(texture);

        calculateVelocity(targetX, targetY);
    }

    public void update(World world) {
        ignoreCollisions.add(world.getPlayer());
        CollisionEntity entity = move(speedX, speedY, ignoreCollisions); // move returns this if there are no collisions or the entity it hit
        if (!entity.equals(this)) {
            // check if it hit an enemy
            for (Slime slime : world.getSlimes()) {
                if (entity.equals(slime)) {
                    slime.takeDamage(damage);
                    world.getStatistics().setDamageDone(world.getStatistics().getDamageDone() + damage);
                }
            }
            // remove the arrow
            world.removeArrow(this);
        }
    }

    private CollisionEntity move(float moveX, float moveY, List<CollisionEntity> ignoreCollisionss) {
        return this.move(this, moveX, moveY, ignoreCollisionss);
    }

    private void calculateVelocity(float x2, float y2) {
        this.angle = (float) Math.atan2(y2 - Gdx.graphics.getHeight() / 2f, x2 - Gdx.graphics.getWidth() / 2f);
        this.speedX = (float) (speed * Math.cos(angle));
        this.speedY = (float) (speed * Math.sin(angle));
        sprite.rotate((float) Math.toDegrees(angle) - 135);
    }

    public void draw(Batch batch) {
        sprite.setPosition(this.getX(), this.getY());
        sprite.draw(batch);
    }
}
