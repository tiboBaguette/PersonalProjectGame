package com.realdolmen.textures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationFramesPlayer extends AnimationFrames {
    private Animation<TextureRegion> playerCurrentAnimation;
    private Animation<TextureRegion> playerIdleFront;
    private Animation<TextureRegion> playerIdleBack;
    private Animation<TextureRegion> playerRunFront;
    private Animation<TextureRegion> playerRunBack;
    private Animation<TextureRegion> playerAttackSide;
    private Animation<TextureRegion> playerAttackUp;
    private Animation<TextureRegion> playerAttackDown;
    private Animation<TextureRegion> playerTakeDamage;
    private Animation<TextureRegion> playerPushSide;
    private Animation<TextureRegion> playerPullSide;
    private Animation<TextureRegion> playerPushUp;
    private Animation<TextureRegion> playerPushDown;
    private Animation<TextureRegion> playerPullUp;
    private Animation<TextureRegion> playerPullDown;
    private Animation<TextureRegion> playerDeath;
    private Animation<TextureRegion> playerPitfall;

    public void createPlayerFrames() {
        TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("core/assets/animations/PlayerAnimations.atlas"));

        TextureRegion[] playerIdleFrontFrames = new TextureRegion[8];
        playerIdleFrontFrames[0] = (textureAtlas.findRegion("IdleFront1"));
        playerIdleFrontFrames[1] = (textureAtlas.findRegion("IdleFront2"));
        playerIdleFrontFrames[2] = (textureAtlas.findRegion("IdleFront3"));
        playerIdleFrontFrames[3] = (textureAtlas.findRegion("IdleFront4"));
        playerIdleFrontFrames[4] = (textureAtlas.findRegion("IdleFront5"));
        playerIdleFrontFrames[5] = (textureAtlas.findRegion("IdleFront6"));
        playerIdleFrontFrames[6] = (textureAtlas.findRegion("IdleFront7"));
        playerIdleFrontFrames[7] = (textureAtlas.findRegion("IdleFront8"));
        playerIdleFront = new Animation<>(0.125f,playerIdleFrontFrames);

        TextureRegion[] playerIdleBackFrames = new TextureRegion[8];
        playerIdleBackFrames[0] = (textureAtlas.findRegion("IdleBack1"));
        playerIdleBackFrames[1] = (textureAtlas.findRegion("IdleBack2"));
        playerIdleBackFrames[2] = (textureAtlas.findRegion("IdleBack3"));
        playerIdleBackFrames[3] = (textureAtlas.findRegion("IdleBack4"));
        playerIdleBackFrames[4] = (textureAtlas.findRegion("IdleBack5"));
        playerIdleBackFrames[5] = (textureAtlas.findRegion("IdleBack6"));
        playerIdleBackFrames[6] = (textureAtlas.findRegion("IdleBack7"));
        playerIdleBackFrames[7] = (textureAtlas.findRegion("IdleBack8"));
        playerIdleBack = new Animation<>(0.125f,playerIdleBackFrames);

        TextureRegion[] playerRunFrontFrames = new TextureRegion[6];
        playerRunFrontFrames[0] = (textureAtlas.findRegion("RunFront1"));
        playerRunFrontFrames[1] = (textureAtlas.findRegion("RunFront2"));
        playerRunFrontFrames[2] = (textureAtlas.findRegion("RunFront3"));
        playerRunFrontFrames[3] = (textureAtlas.findRegion("RunFront4"));
        playerRunFrontFrames[4] = (textureAtlas.findRegion("RunFront5"));
        playerRunFrontFrames[5] = (textureAtlas.findRegion("RunFront6"));
        playerRunFront = new Animation<>(0.125f,playerRunFrontFrames);

        TextureRegion[] playerRunBackFrames = new TextureRegion[6];
        playerRunBackFrames[0] = (textureAtlas.findRegion("RunBack1"));
        playerRunBackFrames[1] = (textureAtlas.findRegion("RunBack2"));
        playerRunBackFrames[2] = (textureAtlas.findRegion("RunBack3"));
        playerRunBackFrames[3] = (textureAtlas.findRegion("RunBack4"));
        playerRunBackFrames[4] = (textureAtlas.findRegion("RunBack5"));
        playerRunBackFrames[5] = (textureAtlas.findRegion("RunBack6"));
        playerRunBack = new Animation<>(0.125f,playerRunBackFrames);

        TextureRegion[] playerAttackSideFrames = new TextureRegion[3];
        playerAttackSideFrames[0] = (textureAtlas.findRegion("AttackSide1"));
        playerAttackSideFrames[1] = (textureAtlas.findRegion("AttackSide2"));
        playerAttackSideFrames[2] = (textureAtlas.findRegion("AttackSide3"));
        playerAttackSide = new Animation<>(0.125f,playerAttackSideFrames);

        TextureRegion[] playerAttackUpFrames = new TextureRegion[3];
        playerAttackUpFrames[0] = (textureAtlas.findRegion("AttackUp1"));
        playerAttackUpFrames[1] = (textureAtlas.findRegion("AttackUp2"));
        playerAttackUpFrames[2] = (textureAtlas.findRegion("AttackUp3"));
        playerAttackUp = new Animation<>(0.125f,playerAttackUpFrames);

        TextureRegion[] playerAttackDownFrames = new TextureRegion[3];
        playerAttackDownFrames[0] = (textureAtlas.findRegion("AttackDown1"));
        playerAttackDownFrames[1] = (textureAtlas.findRegion("AttackDown2"));
        playerAttackDownFrames[2] = (textureAtlas.findRegion("AttackDown3"));
        playerAttackDown = new Animation<>(0.125f,playerAttackDownFrames);

        TextureRegion[] playerTakeDamageFrames = new TextureRegion[3];
        playerTakeDamageFrames[0] = (textureAtlas.findRegion("TakeDamage1"));
        playerTakeDamageFrames[1] = (textureAtlas.findRegion("TakeDamage2"));
        playerTakeDamageFrames[2] = (textureAtlas.findRegion("TakeDamage3"));
        playerTakeDamage = new Animation<>(0.125f,playerTakeDamageFrames);

        TextureRegion[] playerDeathFrames = new TextureRegion[5];
        playerDeathFrames[0] = (textureAtlas.findRegion("Death1"));
        playerDeathFrames[1] = (textureAtlas.findRegion("Death2"));
        playerDeathFrames[2] = (textureAtlas.findRegion("Death3"));
        playerDeathFrames[3] = (textureAtlas.findRegion("Death4"));
        playerDeathFrames[4] = (textureAtlas.findRegion("Death5"));
        playerDeath = new Animation<>(0.125f,playerDeathFrames);

        playerCurrentAnimation = playerIdleFront;
    }

    public Animation<TextureRegion> getPlayerCurrentAnimation() {
        return playerCurrentAnimation;
    }

    public Animation<TextureRegion> getPlayerIdleFront() {
        return playerIdleFront;
    }

    public Animation<TextureRegion> getPlayerIdleBack() {
        return playerIdleBack;
    }

    public Animation<TextureRegion> getPlayerRunFront() {
        return playerRunFront;
    }

    public Animation<TextureRegion> getPlayerRunBack() {
        return playerRunBack;
    }

    public Animation<TextureRegion> getPlayerAttackSide() {
        return playerAttackSide;
    }

    public Animation<TextureRegion> getPlayerAttackUp() {
        return playerAttackUp;
    }

    public Animation<TextureRegion> getPlayerAttackDown() {
        return playerAttackDown;
    }

    public Animation<TextureRegion> getPlayerTakeDamage() {
        return playerTakeDamage;
    }

    public Animation<TextureRegion> getPlayerPushSide() {
        return playerPushSide;
    }

    public Animation<TextureRegion> getPlayerPullSide() {
        return playerPullSide;
    }

    public Animation<TextureRegion> getPlayerPushUp() {
        return playerPushUp;
    }

    public Animation<TextureRegion> getPlayerPushDown() {
        return playerPushDown;
    }

    public Animation<TextureRegion> getPlayerPullUp() {
        return playerPullUp;
    }

    public Animation<TextureRegion> getPlayerPullDown() {
        return playerPullDown;
    }

    public Animation<TextureRegion> getPlayerDeath() {
        return playerDeath;
    }

    public Animation<TextureRegion> getPlayerPitfall() {
        return playerPitfall;
    }
}
