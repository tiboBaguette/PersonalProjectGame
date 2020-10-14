package com.realdolmen.textures;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationFrames extends ApplicationAdapter {
    private TextureAtlas textureAtlas;
    private Animation playerCurrentAnimation;
    private Animation playerIdleFront;
    private Animation playerIdleBack;
    private Animation playerRunFront;
    private Animation playerRunBack;
    private Animation playerAttackSide;
    private Animation playerAttackUp;
    private Animation playerAttackDown;
    private Animation playerTakeDamage;
    private Animation playerPushSide;
    private Animation playerPullSide;
    private Animation playerPushUp;
    private Animation playerPushDown;
    private Animation playerPullUp;
    private Animation playerPullDown;
    private Animation playerDeath;
    private Animation playerPitfall;

    public void setPlayerCurrentAnimation(Animation playerAnimation) {
        this.playerCurrentAnimation = playerAnimation;
    }

    public void createFrames() {
        textureAtlas = new TextureAtlas(Gdx.files.internal("core/assets/animations/PlayerAnimations.atlas"));

        TextureRegion[] playerIdleFrontFrames = new TextureRegion[8];
        playerIdleFrontFrames[0] = (textureAtlas.findRegion("IdleFront1"));
        playerIdleFrontFrames[1] = (textureAtlas.findRegion("IdleFront2"));
        playerIdleFrontFrames[2] = (textureAtlas.findRegion("IdleFront3"));
        playerIdleFrontFrames[3] = (textureAtlas.findRegion("IdleFront4"));
        playerIdleFrontFrames[4] = (textureAtlas.findRegion("IdleFront5"));
        playerIdleFrontFrames[5] = (textureAtlas.findRegion("IdleFront6"));
        playerIdleFrontFrames[6] = (textureAtlas.findRegion("IdleFront7"));
        playerIdleFrontFrames[7] = (textureAtlas.findRegion("IdleFront8"));
        playerIdleFront = new Animation(0.125f,playerIdleFrontFrames);

        TextureRegion[] playerIdleBackFrames = new TextureRegion[8];
        playerIdleBackFrames[0] = (textureAtlas.findRegion("IdleBack1"));
        playerIdleBackFrames[1] = (textureAtlas.findRegion("IdleBack2"));
        playerIdleBackFrames[2] = (textureAtlas.findRegion("IdleBack3"));
        playerIdleBackFrames[3] = (textureAtlas.findRegion("IdleBack4"));
        playerIdleBackFrames[4] = (textureAtlas.findRegion("IdleBack5"));
        playerIdleBackFrames[5] = (textureAtlas.findRegion("IdleBack6"));
        playerIdleBackFrames[6] = (textureAtlas.findRegion("IdleBack7"));
        playerIdleBackFrames[7] = (textureAtlas.findRegion("IdleBack8"));
        playerIdleBack = new Animation(0.125f,playerIdleBackFrames);

        TextureRegion[] playerRunFrontFrames = new TextureRegion[6];
        playerRunFrontFrames[0] = (textureAtlas.findRegion("RunFront1"));
        playerRunFrontFrames[1] = (textureAtlas.findRegion("RunFront2"));
        playerRunFrontFrames[2] = (textureAtlas.findRegion("RunFront3"));
        playerRunFrontFrames[3] = (textureAtlas.findRegion("RunFront4"));
        playerRunFrontFrames[4] = (textureAtlas.findRegion("RunFront5"));
        playerRunFrontFrames[5] = (textureAtlas.findRegion("RunFront6"));
        playerRunFront = new Animation(0.125f,playerRunFrontFrames);

        TextureRegion[] playerRunBackFrames = new TextureRegion[6];
        playerRunBackFrames[0] = (textureAtlas.findRegion("RunBack1"));
        playerRunBackFrames[1] = (textureAtlas.findRegion("RunBack2"));
        playerRunBackFrames[2] = (textureAtlas.findRegion("RunBack3"));
        playerRunBackFrames[3] = (textureAtlas.findRegion("RunBack4"));
        playerRunBackFrames[4] = (textureAtlas.findRegion("RunBack5"));
        playerRunBackFrames[5] = (textureAtlas.findRegion("RunBack6"));
        playerRunBack = new Animation(0.125f,playerRunBackFrames);

        TextureRegion[] playerAttackSideFrames = new TextureRegion[3];
        playerAttackSideFrames[0] = (textureAtlas.findRegion("AttackSide1"));
        playerAttackSideFrames[1] = (textureAtlas.findRegion("AttackSide2"));
        playerAttackSideFrames[2] = (textureAtlas.findRegion("AttackSide3"));
        playerAttackSide = new Animation(0.125f,playerAttackSideFrames);

        TextureRegion[] playerAttackUpFrames = new TextureRegion[3];
        playerAttackUpFrames[0] = (textureAtlas.findRegion("AttackUp1"));
        playerAttackUpFrames[1] = (textureAtlas.findRegion("AttackUp2"));
        playerAttackUpFrames[2] = (textureAtlas.findRegion("AttackUp3"));
        playerAttackUp = new Animation(0.125f,playerAttackUpFrames);

        TextureRegion[] playerAttackDownFrames = new TextureRegion[3];
        playerAttackDownFrames[0] = (textureAtlas.findRegion("AttackDown1"));
        playerAttackDownFrames[1] = (textureAtlas.findRegion("AttackDown2"));
        playerAttackDownFrames[2] = (textureAtlas.findRegion("AttackDown3"));
        playerAttackDown = new Animation(0.125f,playerAttackDownFrames);

        TextureRegion[] playerTakeDamageFrames = new TextureRegion[3];
        playerTakeDamageFrames[0] = (textureAtlas.findRegion("TakeDamage1"));
        playerTakeDamageFrames[1] = (textureAtlas.findRegion("TakeDamage2"));
        playerTakeDamageFrames[2] = (textureAtlas.findRegion("TakeDamage3"));
        playerTakeDamage = new Animation(0.125f,playerTakeDamageFrames);

        playerCurrentAnimation = playerIdleFront;
    }

    public Animation getPlayerCurrentAnimation() {
        return playerCurrentAnimation;
    }

    public Animation getPlayerIdleFront() {
        return playerIdleFront;
    }

    public Animation getPlayerIdleBack() {
        return playerIdleBack;
    }

    public Animation getPlayerRunFront() {
        return playerRunFront;
    }

    public Animation getPlayerRunBack() {
        return playerRunBack;
    }

    public Animation getPlayerAttackSide() {
        return playerAttackSide;
    }

    public Animation getPlayerAttackUp() {
        return playerAttackUp;
    }

    public Animation getPlayerAttackDown() {
        return playerAttackDown;
    }

    public Animation getPlayerTakeDamage() {
        return playerTakeDamage;
    }

    public Animation getPlayerPushSide() {
        return playerPushSide;
    }

    public Animation getPlayerPullSide() {
        return playerPullSide;
    }

    public Animation getPlayerPushUp() {
        return playerPushUp;
    }

    public Animation getPlayerPushDown() {
        return playerPushDown;
    }

    public Animation getPlayerPullUp() {
        return playerPullUp;
    }

    public Animation getPlayerPullDown() {
        return playerPullDown;
    }

    public Animation getPlayerDeath() {
        return playerDeath;
    }

    public Animation getPlayerPitfall() {
        return playerPitfall;
    }
}
