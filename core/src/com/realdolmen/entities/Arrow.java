package com.realdolmen.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.realdolmen.world.World;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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
    private float xOffset;
    private float yOffset;
    private List<CollisionEntity> ignoreCollisions;

    public Arrow(float damage, float startX, float startY, float targetX, float targetY) {
        // create the collision entity with /2 width & height
        super(startX, startY, WIDTH / 2f, HEIGHT / 2f);
        ignoreCollisions = new ArrayList<>();

        // set the image of the arrow
        this.texture = new Texture(Gdx.files.internal("core/assets/images/arrow.png"));
        sprite = new Sprite(texture);

        this.damage = damage;
        this.speed = 8;
        calculateVelocity(targetX, targetY);

        // set the collision box at the tip of the arrow
        float offset = WIDTH/2f;
        this.xOffset = offset * (float) Math.cos(angle);
        this.yOffset = offset * (float) Math.sin(angle);
        this.setX(getX() + xOffset);
        this.setY(getY() + yOffset);
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
        sprite.setPosition(this.getActualX(), this.getActualY());
        sprite.draw(batch);
    }

    // the collision entity is at the tip of the arrow this return the actual value for drawing etc
    public float getActualX() {
        return super.getX() - xOffset;
    }

    public float getActualY() {
        return super.getY() - yOffset;
    }
}
