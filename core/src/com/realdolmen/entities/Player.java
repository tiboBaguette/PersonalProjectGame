package com.realdolmen.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.kotcrab.vis.ui.VisUI;
import com.realdolmen.textures.animations.PlayerAnimationType;
import com.realdolmen.textures.animations.PlayerAnimations;
import com.realdolmen.world.Settings;
import com.realdolmen.world.World;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Player extends Creature {
    private final OrthographicCamera camera;
    private String facing;
    private ProgressBar healthBar;
    private boolean isInAnimation;
    private boolean flipAnimation;
    private boolean flipNextAnimation;
    private List<CollisionEntity> ignoreCollisions;
    private World world;

    // stats
    private float dashCooldown;

    // animations
    private float elapsedTime;
    private Animation<TextureRegion> currentAnimation;
    private Animation<TextureRegion> nextAnimation;

    private PlayerAnimations playerAnimations;

    // draw
    private int drawWidth;
    private int drawHeight;

    public Player(float x, float y, OrthographicCamera camera, World world) {
        super(x, y, 16, 16);
        ignoreCollisions = new ArrayList<>();

        this.drawWidth = Settings.getPlayerSize();
        this.drawHeight = Settings.getPlayerSize();

        // world
        this.world = world;

        // player variables
        this.dashCooldown = 3;

        // creature variables
        setMaxHealth(Settings.getPlayerMaxHealth());
        setHealth(Settings.getPlayerMaxHealth());
        setMoveSpeed(Settings.getPlayerMoveSpeed());
        setAttackSpeed(Settings.getPlayerAttackSpeed());
        setAttackDamage(Settings.getPlayerAttackDamage());

        this.elapsedTime = 0;
        playerAnimations = new PlayerAnimations();

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
        world.getInput().playerInput();
    }

    public void createUi(Stage stage) {
        // healthbar
        healthBar = new ProgressBar(0f, getMaxHealth(), 1f, false, VisUI.getSkin());
        healthBar.setValue(getHealth());
        healthBar.setColor(0, 1, 0, 1);
        healthBar.setWidth(Gdx.graphics.getWidth() / 3f);
        stage.addActor(healthBar);
    }

    public void takeDamage(float amount) {
        // take damage and update health bar
        setHealth(getHealth() - amount);
        healthBar.setValue(getHealth());

        // player animations
        if (getHealth() <= 0) {
            setCurrentAnimation(playerAnimations.getAnimation(PlayerAnimationType.DEATH), true, false);
        } else {
            setCurrentAnimation(playerAnimations.getAnimation(PlayerAnimationType.TAKE_DAMAGE), true, false);
        }
    }

    public void setCurrentAnimation(Animation<TextureRegion> animation, boolean isInAnimation, boolean flipAnimation) {
        currentAnimation = animation;
        this.flipAnimation = flipAnimation;
        this.isInAnimation = isInAnimation;
        elapsedTime = 0;
    }

    public void setNextAnimation(Animation<TextureRegion> animation, boolean flipAnimation) {
        nextAnimation = animation;
        this.flipNextAnimation = flipAnimation;
    }

    private void setAnimation() {
        if (isInAnimation) {
            if (currentAnimation.isAnimationFinished(elapsedTime)) {
                // remove the player
                if (currentAnimation.equals(playerAnimations.getAnimation(PlayerAnimationType.DEATH))) {
                    // todo: remove player
                }

                // set next animation & reset variables
                currentAnimation = nextAnimation;
                flipAnimation = flipNextAnimation;
                elapsedTime = 0;
                isInAnimation = false;
            }
        } else {
            // set next animation
            currentAnimation = nextAnimation;
            flipAnimation = flipNextAnimation;
        }
    }

    public void draw(Batch batch) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        setAnimation();

        super.draw(batch, currentAnimation, drawWidth, drawHeight, flipAnimation, elapsedTime);
    }
}

