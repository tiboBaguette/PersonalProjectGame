package com.realdolmen.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.realdolmen.textures.animations.SlimeAnimationType;
import com.realdolmen.textures.animations.SlimeAnimations;
import com.realdolmen.world.Settings;
import com.realdolmen.world.World;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Slime extends Enemy {
    private Boolean dealDamage;
    private Boolean die;
    private List<CollisionEntity> ignoreCollisions;

    // animations
    private float elapsedTime;
    private SlimeAnimations slimeAnimations;
    private Animation<TextureRegion> currentAnimation;
    private Animation<TextureRegion> nextAnimation;
    private SpriteBatch spriteBatch;
    private boolean flip;
    private boolean isInAnimation;

    // stats
    private final float attackDistance;
    private int stage;

    // draw
    private final float drawWidth;
    private final float drawHeight;

    public Slime(float x, float y, int stage) {
        super(x, y, Settings.getSlimeSize() / 2f * stage, Settings.getSlimeSize() / 2f * stage);
        ignoreCollisions = new ArrayList<>();

        this.drawWidth = Settings.getSlimeSize() * 2 * stage;
        this.drawHeight = Settings.getSlimeSize() * 2 * stage;

        // slime variables
        this.stage = stage;
        this.attackDistance = (float) Settings.getSlimeAttackDistance() * stage;

        // creature variables
        setMaxHealth(Settings.getSlimeMaxHealth() * stage);
        setHealth(Settings.getSlimeMaxHealth() * stage);
        setAttackDamage(Settings.getSlimeAttackDamage() * stage /2f);
        setMoveSpeed(Settings.getSlimeMoveSpeed());
        setAttackSpeed(Settings.getSlimeAttackSpeed());

        // animation
        this.elapsedTime = 0;
        slimeAnimations = new SlimeAnimations();
        flip = false;
        isInAnimation = false;
        // set the default animation
        currentAnimation = slimeAnimations.getAnimation(SlimeAnimationType.IDLE);

        this.dealDamage = false;
        this.die = false;
    }

    public CollisionEntity move(float moveX, float moveY) {
        return this.move(this, moveX, moveY, ignoreCollisions);
    }

    public void update(World world) {
        // hit player
        if (dealDamage) {
            // todo check if player is still in range
            // if the 2nd attack frame is finished deal damage
            if (currentAnimation.getKeyFrameIndex(elapsedTime) == 2) {
                world.getPlayer().takeDamage(getAttackDamage());
                dealDamage = false;

                // update statistics
                world.getStatistics().setDamageTaken(world.getStatistics().getDamageTaken() + getAttackDamage());
            }
        }

        // die
        if (getHealth() <= 0) {
            this.removeCollisionEntity();
            if (die) {
                // update statistics
                world.getStatistics().setKills(world.getStatistics().getKills() + 1);

                if (stage > 1) { // if stage > 1 spawn 2 more slimes of a lower stage
                    for (int i = 0; i < 2; i++) {
                        Slime slime = new Slime(getX(), getY(), stage-1);
                        // prevent slimes from spawning inside each other
                        checkSpawnLocation();

                        world.addSlime(slime);
                    }
                }

                // remove the slime
                world.removeSlime(this);
            }
        }

        // move, attack & idle
        float distanceToPlayerX = world.getPlayer().getX() - this.getX();
        float distanceToPlayerY = world.getPlayer().getY() - this.getY();
        float distanceToPlayer = (float) Math.sqrt(Math.pow(distanceToPlayerX, 2) + Math.pow(distanceToPlayerY, 2));

        if (distanceToPlayer < attackDistance) { // attack
            // get the angle to the player
            float angle = (float) Math.toDegrees(Math.atan2(world.getPlayer().getY() - this.getY(), world.getPlayer().getX() - this.getX()));

            if(angle < 0){
                angle += 360;
            }

            if (angle >= 45 && angle < 135 && !isInAnimation) {
                nextAnimation = slimeAnimations.getAnimation(SlimeAnimationType.ATTACK_UP);
                flip = false;
                isInAnimation = true;
            } else if (angle >= 135 && angle < 225 && !isInAnimation) {
                nextAnimation = slimeAnimations.getAnimation(SlimeAnimationType.ATTACK_SIDE);
                flip = true;
                isInAnimation = true;
            } else if (angle >= 225 && angle < 315 && !isInAnimation) {
                nextAnimation = slimeAnimations.getAnimation(SlimeAnimationType.ATTACK_DOWN);
                flip = false;
                isInAnimation = true;
            } else if (!isInAnimation) {
                nextAnimation = slimeAnimations.getAnimation(SlimeAnimationType.ATTACK_SIDE);
                setAnimation();
                flip = false;
                isInAnimation = true;
            }
        } else if (distanceToPlayer < 24 * 16 && !isInAnimation) { // move to player
            nextAnimation = slimeAnimations.getAnimation(SlimeAnimationType.MOVE);
            float moveX = getMoveSpeed() * (distanceToPlayerX / distanceToPlayer);
            float moveY = getMoveSpeed() * (distanceToPlayerY / distanceToPlayer);
            move(moveX, 0);
            move(0, moveY);
        } else if (!isInAnimation) { // idle
            nextAnimation = slimeAnimations.getAnimation(SlimeAnimationType.IDLE);
        }
    }

    public void checkSpawnLocation() {
        // prevent slimes from spawning inside each other
        while (!this.move(1, 0).equals(this)) {
            this.setX(this.getX()+1);
        }
    }

    public void takeDamage(float damage) {
        // todo take damage animations
        if (getHealth() > 0) {
            setHealth(getHealth() - damage);
            if (getHealth() <= 0) {
                elapsedTime = 0;
                currentAnimation = slimeAnimations.getAnimation(SlimeAnimationType.DEATH);
            }
        }
    }

    private void setAnimation() {
        // if attack is finished
        if (currentAnimation.equals(slimeAnimations.getAnimation(SlimeAnimationType.ATTACK_DOWN)) ||
                currentAnimation.equals(slimeAnimations.getAnimation(SlimeAnimationType.ATTACK_UP)) ||
                currentAnimation.equals(slimeAnimations.getAnimation(SlimeAnimationType.ATTACK_SIDE))) {
            // deal damage at the 2nd frame in the attack animation
            if (currentAnimation.getKeyFrameIndex(elapsedTime) == 1) {
                dealDamage = true;
            }
        }

        if (currentAnimation.isAnimationFinished(elapsedTime)) {
            // if death is finished
            if (currentAnimation.equals(slimeAnimations.getAnimation(SlimeAnimationType.DEATH))) {
                die = true;
            }

            // set the next animation
            elapsedTime = 0;
            currentAnimation = nextAnimation;
            isInAnimation = false;
        }
    }

    public void draw(Batch batch) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        setAnimation();

        if (!die) {
            super.draw(batch, currentAnimation, drawWidth, drawHeight, flip, elapsedTime);
        }
    }
}
