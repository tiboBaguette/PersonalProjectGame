package com.realdolmen.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.realdolmen.textures.AnimationFramesPlayer;


public class Player extends CollisionEntity {
    private final OrthographicCamera camera;
    private String facing;

    // animations
    private float elapsedTime;
    private AnimationFramesPlayer animationFramesPlayer;
    private Animation<TextureRegion> currentAnimation;

    public Player(float x, float y, float width, float height, OrthographicCamera camera) {
        super(x, y, width, height);

        this.elapsedTime = 0;
        this.animationFramesPlayer = new AnimationFramesPlayer();

        this.camera = camera;
        camera.translate(- Gdx.graphics.getWidth() / 2f, - Gdx.graphics.getHeight() / 2f);

        this.facing = "DOWN";
    }

    public void move(float moveX, float moveY) {
        if (this.move(this, moveX, moveY)) {
            camera.translate(moveX, moveY);
        }
    }


    public void createAnimationFrames() {
        animationFramesPlayer.createPlayerFrames();
        currentAnimation = animationFramesPlayer.getPlayerRunFront();
    }

    public void draw(Batch batch) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        batch.draw(currentAnimation.getKeyFrame(elapsedTime, true), this.getX() - this.getWidth(), this.getY() - this.getHeight());
    }

    public String getFacing() {
        return facing;
    }

    public void setFacing(String facing) {
        this.facing = facing;
    }

    public AnimationFramesPlayer getAnimationFrames() {
        return animationFramesPlayer;
    }

    public void setAnimationFrames(AnimationFramesPlayer animationFramesPlayer) {
        this.animationFramesPlayer = animationFramesPlayer;
    }

    public Animation<TextureRegion> getCurrentAnimation() {
        return currentAnimation;
    }

    public void setCurrentAnimation(Animation<TextureRegion> currentAnimation) {
        this.currentAnimation = currentAnimation;
    }
}

