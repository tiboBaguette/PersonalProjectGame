package com.realdolmen.textures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lombok.Getter;

@Getter
public class AnimationFramesSlimeGreen {
    private Animation<TextureRegion> slimeCurrentAnimation;
    private Animation<TextureRegion> slimeIdle;
    private Animation<TextureRegion> slimeMove;
    private Animation<TextureRegion> slimeSpawn;
    private Animation<TextureRegion> slimeDeath;
    private Animation<TextureRegion> slimeAttackSide;
    private Animation<TextureRegion> slimeAttackUp;
    private Animation<TextureRegion> slimeAttackDown;

    public void createPlayerFrames() {
        TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("core/assets/animations/SlimeGreenAnimations.atlas"));

        TextureRegion[] slimeIdleFrames = new TextureRegion[6];
        slimeIdleFrames[0] = (textureAtlas.findRegion("Idle1"));
        slimeIdleFrames[1] = (textureAtlas.findRegion("Idle2"));
        slimeIdleFrames[2] = (textureAtlas.findRegion("Idle3"));
        slimeIdleFrames[3] = (textureAtlas.findRegion("Idle4"));
        slimeIdleFrames[4] = (textureAtlas.findRegion("Idle5"));
        slimeIdleFrames[5] = (textureAtlas.findRegion("Idle6"));
        slimeIdle = new Animation<>(0.125f,slimeIdleFrames);

        TextureRegion[] slimeMoveFrames = new TextureRegion[7];
        slimeMoveFrames[0] = (textureAtlas.findRegion("Move1"));
        slimeMoveFrames[1] = (textureAtlas.findRegion("Move2"));
        slimeMoveFrames[2] = (textureAtlas.findRegion("Move3"));
        slimeMoveFrames[3] = (textureAtlas.findRegion("Move4"));
        slimeMoveFrames[4] = (textureAtlas.findRegion("Move5"));
        slimeMoveFrames[5] = (textureAtlas.findRegion("Move6"));
        slimeMoveFrames[6] = (textureAtlas.findRegion("Move7"));
        slimeMove = new Animation<>(0.125f,slimeMoveFrames);

        TextureRegion[] slimeSpawnFrames = new TextureRegion[12];
        slimeSpawnFrames[0] = (textureAtlas.findRegion("Spawn1"));
        slimeSpawnFrames[1] = (textureAtlas.findRegion("Spawn2"));
        slimeSpawnFrames[2] = (textureAtlas.findRegion("Spawn3"));
        slimeSpawnFrames[3] = (textureAtlas.findRegion("Spawn4"));
        slimeSpawnFrames[4] = (textureAtlas.findRegion("Spawn5"));
        slimeSpawnFrames[5] = (textureAtlas.findRegion("Spawn6"));
        slimeSpawnFrames[6] = (textureAtlas.findRegion("Spawn7"));
        slimeSpawnFrames[7] = (textureAtlas.findRegion("Spawn8"));
        slimeSpawnFrames[8] = (textureAtlas.findRegion("Spawn9"));
        slimeSpawnFrames[9] = (textureAtlas.findRegion("Spawn10"));
        slimeSpawnFrames[10] = (textureAtlas.findRegion("Spawn11"));
        slimeSpawnFrames[11] = (textureAtlas.findRegion("Spawn12"));
        slimeSpawn = new Animation<>(0.125f,slimeSpawnFrames);

        TextureRegion[] slimeDeathFrames = new TextureRegion[6];
        slimeDeathFrames[0] = (textureAtlas.findRegion("Death1"));
        slimeDeathFrames[1] = (textureAtlas.findRegion("Death2"));
        slimeDeathFrames[2] = (textureAtlas.findRegion("Death3"));
        slimeDeathFrames[3] = (textureAtlas.findRegion("Death4"));
        slimeDeathFrames[4] = (textureAtlas.findRegion("Death5"));
        slimeDeathFrames[5] = (textureAtlas.findRegion("Death6"));
        slimeDeath = new Animation<>(0.125f,slimeDeathFrames);

        TextureRegion[] slimeAttackSideFrames = new TextureRegion[7];
        slimeAttackSideFrames[0] = (textureAtlas.findRegion("AttackSide1"));
        slimeAttackSideFrames[1] = (textureAtlas.findRegion("AttackSide2"));
        slimeAttackSideFrames[2] = (textureAtlas.findRegion("AttackSide3"));
        slimeAttackSideFrames[3] = (textureAtlas.findRegion("AttackSide4"));
        slimeAttackSideFrames[4] = (textureAtlas.findRegion("AttackSide5"));
        slimeAttackSideFrames[5] = (textureAtlas.findRegion("AttackSide6"));
        slimeAttackSideFrames[6] = (textureAtlas.findRegion("AttackSide7"));
        slimeAttackSide = new Animation<>(0.125f,slimeAttackSideFrames);

        TextureRegion[] slimeAttackUpFrames = new TextureRegion[7];
        slimeAttackUpFrames[0] = (textureAtlas.findRegion("AttackUp1"));
        slimeAttackUpFrames[1] = (textureAtlas.findRegion("AttackUp2"));
        slimeAttackUpFrames[2] = (textureAtlas.findRegion("AttackUp3"));
        slimeAttackUpFrames[3] = (textureAtlas.findRegion("AttackUp4"));
        slimeAttackUpFrames[4] = (textureAtlas.findRegion("AttackUp5"));
        slimeAttackUpFrames[5] = (textureAtlas.findRegion("AttackUp6"));
        slimeAttackUpFrames[6] = (textureAtlas.findRegion("AttackUp7"));
        slimeAttackUp = new Animation<>(0.125f,slimeAttackUpFrames);

        TextureRegion[] slimeAttackDownFrames = new TextureRegion[7];
        slimeAttackDownFrames[0] = (textureAtlas.findRegion("AttackDown1"));
        slimeAttackDownFrames[1] = (textureAtlas.findRegion("AttackDown2"));
        slimeAttackDownFrames[2] = (textureAtlas.findRegion("AttackDown3"));
        slimeAttackDownFrames[3] = (textureAtlas.findRegion("AttackDown4"));
        slimeAttackDownFrames[4] = (textureAtlas.findRegion("AttackDown5"));
        slimeAttackDownFrames[5] = (textureAtlas.findRegion("AttackDown6"));
        slimeAttackDownFrames[6] = (textureAtlas.findRegion("AttackDown7"));
        slimeAttackDown = new Animation<>(0.125f,slimeAttackDownFrames);

        slimeCurrentAnimation = slimeIdle;
    }
}
