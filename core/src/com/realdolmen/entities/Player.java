package com.realdolmen.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.kotcrab.vis.ui.VisUI;
import com.realdolmen.textures.AnimationFramesPlayer;
import com.realdolmen.world.World;

import java.util.ArrayList;
import java.util.List;


public class Player extends CollisionEntity {
    private final OrthographicCamera camera;
    private String facing;
    private ProgressBar healthBar;
    private boolean isInAnimation;
    private List<CollisionEntity> ignoreCollisions;

    // stats
    private float health;
    private float maxHealth;
    private float moveSpeed;
    private float attackSpeed;
    private float shootSpeed;
    private float attackDamge;
    private float dashCooldown;

    // animations
    private float elapsedTime;
    private AnimationFramesPlayer animationFramesPlayer;
    private Animation<TextureRegion> currentAnimation;
    private Animation<TextureRegion> nextAnimation;

    // draw
    private float drawWidth;
    private float drawHeight;

    public Player(float x, float y, float width, float height, OrthographicCamera camera) {
        super(x, y, 16, 16);
        ignoreCollisions = new ArrayList<>();

        this.drawWidth = width;
        this.drawHeight = height;

        this.health = 100;
        this.maxHealth = 100;
        this.moveSpeed = 3;
        this.attackSpeed = 1;
        this.shootSpeed = 1;
        this.attackDamge = 25;
        this.dashCooldown = 3;

        this.elapsedTime = 0;
        this.animationFramesPlayer = new AnimationFramesPlayer();
        createAnimationFrames();

        this.camera = camera;
        camera.translate(- Gdx.graphics.getWidth() / 2f, - Gdx.graphics.getHeight() / 2f);

        this.facing = FacingValues.DOWN.toString();
    }

    public void move(float moveX, float moveY) {
        if (this.move(this, moveX, moveY, ignoreCollisions).equals(this)) {
            camera.translate(moveX, moveY);
        }
    }

    public void update(World world) {

    }

    private void createAnimationFrames() {
        animationFramesPlayer.createPlayerFrames();
        currentAnimation = animationFramesPlayer.getPlayerIdleFront();
        nextAnimation = animationFramesPlayer.getPlayerIdleFront();
    }

    public void createUi(Stage stage) {
        // healthbar
        healthBar = new ProgressBar(0f, 100f, 1f, false, VisUI.getSkin());
        healthBar.setValue(health);
        healthBar.setColor(0, 1, 0, 1);
        healthBar.setWidth(Gdx.graphics.getWidth() / 3f);
        healthBar.setName("test");
        stage.addActor(healthBar);
    }

    public void takeDamage(float amount) {
        health -= amount;
        if (health <= 0) {
            currentAnimation = animationFramesPlayer.getPlayerDeath();
            elapsedTime = 0;
            isInAnimation = true;
        } else {
            currentAnimation = animationFramesPlayer.getPlayerTakeDamage();
            elapsedTime = 0;
            isInAnimation = true;
        }
    }

    private void setAnimation() {
        if (isInAnimation) {
            if (currentAnimation.isAnimationFinished(elapsedTime)) {
                elapsedTime = 0;
                currentAnimation = nextAnimation;
                isInAnimation = false;
            }
        } else {
            currentAnimation = nextAnimation;
        }

    }

    public void draw(Batch batch) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        healthBar.setValue(health);
        setAnimation();

        super.draw(batch, currentAnimation, drawWidth, drawHeight, false, elapsedTime);
        //batch.draw(currentAnimation.getKeyFrame(elapsedTime, true), this.getX() - 16, this.getY() - 10, this.getWidth() * 3, this.getHeight() * 3);
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

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public float getHealth() {
        return health;
    }

    public float getHealthPercentage() {
        return health / maxHealth;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public Animation<TextureRegion> getNextAnimation() {
        return nextAnimation;
    }

    public void setNextAnimation(Animation<TextureRegion> nextAnimation) {
        this.nextAnimation = nextAnimation;
    }

    public List<CollisionEntity> getIgnoreCollisions() {
        return ignoreCollisions;
    }

    public void setIgnoreCollisions(List<CollisionEntity> ignoreCollisions) {
        this.ignoreCollisions = ignoreCollisions;
    }

    public float getAttackDamge() {
        return attackDamge;
    }
}

