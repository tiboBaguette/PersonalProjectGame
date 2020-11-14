package com.realdolmen.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.realdolmen.textures.AnimationFramesSlimeGreen;
import com.realdolmen.world.World;

import java.util.ArrayList;
import java.util.List;

public class Slime extends Enemy {
    // animations
    private float elapsedTime;
    private AnimationFramesSlimeGreen animationFramesSlimeGreen;
    private Animation<TextureRegion> currentAnimation;
    private Animation<TextureRegion> nextAnimation;
    private SpriteBatch spriteBatch;
    private boolean flip;
    private boolean isInAnimation;
    private Boolean dealDamage;
    private Boolean die;
    private List<CollisionEntity> ignoreCollisions;

    // stats
    private final float attackDistance;
    private int stage;

    // draw
    private final float drawWidth;
    private final float drawHeight;


    public Slime(float x, float y, float width, float height, int stage) {
        super(x, y, width / 2 * stage, height / 2 * stage);
        ignoreCollisions = new ArrayList<>();

        this.drawWidth = width * 2 * stage;
        this.drawHeight = height * 2 * stage;

        // slime variables
        this.stage = stage;
        this.attackDistance = (float) 1.5 * stage;

        // creature variables
        setMaxHealth(100 * stage);
        setHealth(100 * stage);
        setAttackDamage(5 * stage /2f);
        setMoveSpeed(2);
        setAttackSpeed(1);

        this.elapsedTime = 0;
        this.animationFramesSlimeGreen = new AnimationFramesSlimeGreen();
        createAnimationFrames();

        flip = false;
        isInAnimation = false;

        this.dealDamage = false;
        this.die = false;
    }

    public CollisionEntity move(float moveX, float moveY) {
        return this.move(this, moveX, moveY, ignoreCollisions);
    }

    private void createAnimationFrames() {
        animationFramesSlimeGreen.createPlayerFrames();
        currentAnimation = animationFramesSlimeGreen.getSlimeIdle();
        nextAnimation = animationFramesSlimeGreen.getSlimeIdle();
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
                        Slime slime = new Slime(getX(), getY(), 16, 16, stage-1);
                        // prevent slimes from spawning inside eachother
                        while (!slime.move(1, 0).equals(slime)) {
                            slime.setX(slime.getX()+1);
                        }
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

        if (distanceToPlayer < attackDistance * 16) { // attack
            // get the angle to the player
            float angle = (float) Math.toDegrees(Math.atan2(world.getPlayer().getY() - this.getY(), world.getPlayer().getX() - this.getX()));

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
        } else if (distanceToPlayer < 24 * 16 && !isInAnimation) { // move to player
            nextAnimation = animationFramesSlimeGreen.getSlimeMove();
            float moveX = getMoveSpeed() * (distanceToPlayerX / distanceToPlayer);
            float moveY = getMoveSpeed() * (distanceToPlayerY / distanceToPlayer);
            move(moveX, 0);
            move(0, moveY);
        } else if (!isInAnimation) { // idle
            nextAnimation = animationFramesSlimeGreen.getSlimeIdle();
        }
    }

    public void takeDamage(float damage) {
        // todo take damage animations
        if (getHealth() > 0) {
            setHealth(getHealth() - damage);
            if (getHealth() <= 0) {
                elapsedTime = 0;
                currentAnimation = animationFramesSlimeGreen.getSlimeDeath();
            }
        }
    }

    private void setAnimation() {
        // if attack is finished
        if (currentAnimation.equals(animationFramesSlimeGreen.getSlimeAttackDown()) ||
                currentAnimation.equals(animationFramesSlimeGreen.getSlimeAttackUp()) ||
                currentAnimation.equals(animationFramesSlimeGreen.getSlimeAttackSide())) {
            // deal damage at the 2nd frame in the attack animation
            if (currentAnimation.getKeyFrameIndex(elapsedTime) == 1) {
                dealDamage = true;
            }
        }

        if (currentAnimation.isAnimationFinished(elapsedTime)) {
            // if death is finished
            if (currentAnimation.equals(animationFramesSlimeGreen.getSlimeDeath())) {
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

    public float getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(float elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public AnimationFramesSlimeGreen getAnimationFramesSlimeGreen() {
        return animationFramesSlimeGreen;
    }

    public void setAnimationFramesSlimeGreen(AnimationFramesSlimeGreen animationFramesSlimeGreen) {
        this.animationFramesSlimeGreen = animationFramesSlimeGreen;
    }

    public Animation<TextureRegion> getCurrentAnimation() {
        return currentAnimation;
    }

    public void setCurrentAnimation(Animation<TextureRegion> currentAnimation) {
        this.currentAnimation = currentAnimation;
    }

    public Animation<TextureRegion> getNextAnimation() {
        return nextAnimation;
    }

    public void setNextAnimation(Animation<TextureRegion> nextAnimation) {
        this.nextAnimation = nextAnimation;
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public void setSpriteBatch(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
    }

    public boolean isFlip() {
        return flip;
    }

    public void setFlip(boolean flip) {
        this.flip = flip;
    }

    public boolean isInAnimation() {
        return isInAnimation;
    }

    public void setInAnimation(boolean inAnimation) {
        isInAnimation = inAnimation;
    }

    public Boolean getDealDamage() {
        return dealDamage;
    }

    public void setDealDamage(Boolean dealDamage) {
        this.dealDamage = dealDamage;
    }

    public Boolean getDie() {
        return die;
    }

    public void setDie(Boolean die) {
        this.die = die;
    }

    public List<CollisionEntity> getIgnoreCollisions() {
        return ignoreCollisions;
    }

    public void setIgnoreCollisions(List<CollisionEntity> ignoreCollisions) {
        this.ignoreCollisions = ignoreCollisions;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }
}
