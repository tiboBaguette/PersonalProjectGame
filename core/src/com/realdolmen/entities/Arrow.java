package com.realdolmen.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Arrow extends CollisionEntity {
    private static final int WIDTH = 16;
    private static final int HEIGHT = 16;

    private Texture texture;
    private Sprite sprite;
    private float damage;
    private float speed;
    private float speedX;
    private float speedY;

    public Arrow(float damage, float startX, float startY, float targetX, float targetY) {
        super(startX, startY, WIDTH, HEIGHT);
        this.damage = damage;
        this.speed = 30;
        this.texture = new Texture(Gdx.files.internal("core/assets/images/arrow.png"));
        sprite = new Sprite(texture);

        calculateVelocity(targetX, targetY);
    }

    public void update() {
        move(speedX, speedY);

    }

    public void move(float moveX, float moveY) {
        this.move(this, moveX, moveY);
    }

    private void calculateVelocity(float x2, float y2) {
        // todo player width & height
        float angle = (float) Math.atan2(y2 - Gdx.graphics.getHeight() / 2f, x2 - Gdx.graphics.getWidth() / 2f);
        this.speedX = (float) (speed * Math.cos(angle));
        this.speedY = (float) (speed * Math.sin(angle));
    }

    public void draw(Batch batch) {
        sprite.setPosition(this.getX(), this.getY());
        sprite.draw(batch);
    }
}
