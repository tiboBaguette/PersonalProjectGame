package com.realdolmen.creatures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.realdolmen.collisions.CollisionEntity;
import com.realdolmen.textures.AnimationFrames;

public class Player {
    // variables
    private final int WIDTH = 16;
    private final int HEIGHT = 16;
    private float x;
    private float y;
    private OrthographicCamera camera;
    private String facing;

    private CollisionEntity collisionEntity;
    // animations
    private float elapsedTime = 0;
    private AnimationFrames animationFrames = new AnimationFrames();
    private Animation currentAnimation;

    public Player(float x, float y, OrthographicCamera camera) {
        this.x = x;
        this.y = y;
        this.camera = camera;
        collisionEntity = new CollisionEntity(x, y, WIDTH, HEIGHT);
        collisionEntity.addCollisionEntity(collisionEntity);
        this.facing = "DOWN";

        camera.translate(- Gdx.graphics.getWidth() / 2, - Gdx.graphics.getHeight() / 2);
    }

    public void move(float moveX, float moveY) {
        if (collisionEntity.move(collisionEntity, moveX, moveY)) {
            this.x += moveX;
            this.y += moveY;
            camera.translate(moveX, moveY);
        }
    }

    public void createAnimationFrames() {
        animationFrames.createPlayerFrames();
        currentAnimation = animationFrames.getPlayerRunFront();
    }

    public void draw(Batch batch) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        batch.draw((TextureRegion) currentAnimation.getKeyFrame(elapsedTime, true), x - 16, y - 12);
    }

//    public void dash() {
//        if (dashCooldown != 0) {
//            dashCooldown -= Gdx.graphics.getDeltaTime();
//            if (dashCooldown < 0) {
//                dashCooldown = 0;
//            }
//        }
//
//        if (dashCooldown == 0) {
//            dashCooldown = 2;
//            switch (facing) {
//                case "UP": move(0, 48); break;
//                case "DOWN": move(0, -48); break;
//                case "RIGHT": move(48, 0); break;
//                case "LEFT": move(-48, 0); break;
//            }
//        }
//    }

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

    public Animation getCurrentAnimation() {
        return currentAnimation;
    }

    public void setCurrentAnimation(Animation currentAnimation) {
        this.currentAnimation = currentAnimation;
    }

    public AnimationFrames getAnimationFrames() {
        return animationFrames;
    }

    public void setAnimationFrames(AnimationFrames animationFrames) {
        this.animationFrames = animationFrames;
    }

    public CollisionEntity getCollisionEntity() {
        return collisionEntity;
    }

    public String getFacing() {
        return facing;
    }

    public void setFacing(String facing) {
        this.facing = facing;
    }
}
