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
    private List<CollisionEntity> ignoreCollisions;

    // stats
    private float dashCooldown;

    // animations
    private float elapsedTime;
    private Animation<TextureRegion> currentAnimation;
    private Animation<TextureRegion> nextAnimation;

    private PlayerAnimations playerAnimations;

    // draw
    private float drawWidth;
    private float drawHeight;

    public Player(float x, float y, float width, float height, OrthographicCamera camera) {
        super(x, y, 16, 16);
        ignoreCollisions = new ArrayList<>();

        this.drawWidth = width;
        this.drawHeight = height;

        // player variables
        this.dashCooldown = 3;

        // creature variables
        setMaxHealth(100);
        setHealth(100);
        setMoveSpeed(3);
        setAttackSpeed(1);
        setAttackDamage(25);

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
        healthBar = new ProgressBar(0f, 100f, 1f, false, VisUI.getSkin());
        healthBar.setValue(getHealth());
        healthBar.setColor(0, 1, 0, 1);
        healthBar.setWidth(Gdx.graphics.getWidth() / 3f);
        healthBar.setName("test");
        stage.addActor(healthBar);
    }

    public void takeDamage(float amount) {
        setHealth(getHealth() - amount);
        if (getHealth() <= 0) {
            currentAnimation = playerAnimations.getAnimation(PlayerAnimationType.DEATH);
            elapsedTime = 0;
            isInAnimation = true;
        } else {
            currentAnimation = playerAnimations.getAnimation(PlayerAnimationType.TAKE_DAMAGE);
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
        healthBar.setValue(getHealth());
        setAnimation();

        super.draw(batch, currentAnimation, drawWidth, drawHeight, false, elapsedTime);
    }
}

