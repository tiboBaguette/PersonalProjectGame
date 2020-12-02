package com.realdolmen.textures.animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

public class SlimeAnimations {
    private final TextureAtlas textureAtlas;

    public HashMap<SlimeAnimationType, Animation<TextureRegion>> animations;

    public SlimeAnimations() {
        animations = new HashMap<>();
        String atlasName = "core/assets/animations/SlimeGreenAnimations.atlas";
        textureAtlas = TextureHelpers.getTextureAtlas(atlasName);

        // create the animations
        animations.put(SlimeAnimationType.IDLE,idleAnimations());
        animations.put(SlimeAnimationType.MOVE,moveAnimations());
        animations.put(SlimeAnimationType.SPAWN,spawnAnimations());
        animations.put(SlimeAnimationType.DEATH,deathAnimations());
        animations.put(SlimeAnimationType.ATTACK_SIDE,attackSideAnimations());
        animations.put(SlimeAnimationType.ATTACK_UP,attackUpAnimations());
        animations.put(SlimeAnimationType.ATTACK_DOWN,attackDownAnimations());
    }

    public Animation<TextureRegion> getAnimation(SlimeAnimationType slimeAnimationType) {
        return animations.get(slimeAnimationType);
    }

    private Animation<TextureRegion> idleAnimations() {
        return AnimationHelpers.animationTextureFrames(
                textureAtlas,
                "Idle",
                6
        );
    }

    private Animation<TextureRegion> moveAnimations() {
        return AnimationHelpers.animationTextureFrames(
                textureAtlas,
                "Move",
                7
        );
    }

    private Animation<TextureRegion> spawnAnimations() {
        return AnimationHelpers.animationTextureFrames(
                textureAtlas,
                "Spawn",
                12
        );
    }

    private Animation<TextureRegion> deathAnimations() {
        return AnimationHelpers.animationTextureFrames(
                textureAtlas,
                "Death",
                6
        );
    }

    private Animation<TextureRegion> attackSideAnimations() {
        return AnimationHelpers.animationTextureFrames(
                textureAtlas,
                "AttackSide",
                7
        );
    }

    private Animation<TextureRegion> attackUpAnimations() {
        return AnimationHelpers.animationTextureFrames(
                textureAtlas,
                "AttackUp",
                7
        );
    }

    private Animation<TextureRegion> attackDownAnimations() {
        return AnimationHelpers.animationTextureFrames(
                textureAtlas,
                "AttackDown",
                7
        );
    }
}
