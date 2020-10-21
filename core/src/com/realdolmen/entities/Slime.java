package com.realdolmen.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.realdolmen.textures.AnimationFramesSlimeGreen;
import com.sun.org.apache.xpath.internal.operations.Bool;

public class Slime extends CollisionEntity {
    // animations
    private float elapsedTime;
    private AnimationFramesSlimeGreen animationFramesSlimeGreen;
    private Animation<TextureRegion> currentAnimation;
    private Animation<TextureRegion> nextAnimation;
    private SpriteBatch spriteBatch;
    private boolean flip;
    private boolean isInAnimation;
    private Player player;
    private Boolean dealDamage;

    // stats
    private float health;
    private float maxHealth;
    private float moveSpeed;
    private float attackSpeed;
    private float attackDamage;


    public Slime(float x, float y, float width, float height, Player player) {
        super(x, y, width, height);

        this.health = 100;
        this.maxHealth = 100;
        this.moveSpeed = 1;
        this.attackSpeed = 1;
        this.attackDamage = 5;

        this.player = player;

        this.elapsedTime = 0;
        this.animationFramesSlimeGreen = new AnimationFramesSlimeGreen();
        createAnimationFrames();

        flip = false;
        isInAnimation = false;

        this.dealDamage = false;
    }

    public void move(float moveX, float moveY) {
        this.move(this, moveX, moveY);
    }

    private void createAnimationFrames() {
        animationFramesSlimeGreen.createPlayerFrames();
        currentAnimation = animationFramesSlimeGreen.getSlimeIdle();
        nextAnimation = animationFramesSlimeGreen.getSlimeIdle();
    }

    public void update(Player player) {
        // move towards the player
        float distanceToPlayerX = player.getX() - this.getX();
        float distanceToPlayerY = player.getY() - this.getY();
        float distanceToPlayer = (float) Math.sqrt(Math.pow(distanceToPlayerX, 2) + Math.pow(distanceToPlayerY, 2));

        if (distanceToPlayer < 1.5 * 16) {
            float angle = (float) Math.toDegrees(Math.atan2(player.getY() - this.getY(), player.getX() - this.getX()));

            if(angle < 0){
                angle += 360;
            }

            if (angle >= 45 && angle < 135 && !isInAnimation) {
                nextAnimation = animationFramesSlimeGreen.getSlimeAttackUp();
                flip = false;
                isInAnimation = true;
            } else if (angle >= 135 && angle < 225 && !isInAnimation) {
                nextAnimation = animationFramesSlimeGreen.getSlimeAttackSide();
                flip = true;
                isInAnimation = true;
            } else if (angle >= 225 && angle < 315 && !isInAnimation) {
                nextAnimation = animationFramesSlimeGreen.getSlimeAttackDown();
                flip = false;
                isInAnimation = true;
            } else if (!isInAnimation) {
                nextAnimation = animationFramesSlimeGreen.getSlimeAttackSide();
                setAnimation();
                flip = false;
                isInAnimation = true;
            }
        } else if (distanceToPlayer < 24 * 16 && !isInAnimation) {
            nextAnimation = animationFramesSlimeGreen.getSlimeMove();
            float moveX = moveSpeed * (distanceToPlayerX / distanceToPlayer);
            float moveY = moveSpeed * (distanceToPlayerY / distanceToPlayer);
            move(moveX, 0);
            move(0, moveY);
        } else if (!isInAnimation) {
            currentAnimation = animationFramesSlimeGreen.getSlimeIdle();
        }

        if (dealDamage) {
            player.takeDamage(attackDamage);
            dealDamage = false;
        }

    }

    private void setAnimation() {
        if (currentAnimation.isAnimationFinished(elapsedTime)) {
            if (currentAnimation.equals(animationFramesSlimeGreen.getSlimeAttackDown()) ||
                currentAnimation.equals(animationFramesSlimeGreen.getSlimeAttackUp()) ||
                currentAnimation.equals(animationFramesSlimeGreen.getSlimeAttackSide())) {
                // todo check if player is in attack range
                dealDamage = true;
            }
            elapsedTime = 0;
            currentAnimation = nextAnimation;
            isInAnimation = false;
        }
    }

    public void draw(Batch batch) {
        elapsedTime += Gdx.graphics.getDeltaTime();

        if (flip) {
            batch.draw(currentAnimation.getKeyFrame(elapsedTime, true), this.getX() - this.getWidth() + 32, this.getY() - this.getHeight(), this.getWidth() * -1 * 4, this.getHeight() * 4);
        } else {
            batch.draw(currentAnimation.getKeyFrame(elapsedTime, true), this.getX() - this.getWidth(), this.getY() - this.getHeight());
        }

        setAnimation();
    }
}
