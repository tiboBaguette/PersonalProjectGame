package com.realdolmen.textures.animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

public class PlayerAnimations {
    private final String atlasName = "core/assets/animations/PlayerAnimations.atlas";
    private final TextureAtlas textureAtlas;

    public HashMap<PlayerAnimationType, Animation<TextureRegion>> animations;

    public PlayerAnimations() {
        animations = new HashMap<>();
        textureAtlas = TextureHelpers.getTextureAtlas(atlasName);

        // create the animations
        animations.put(PlayerAnimationType.IDLE_FRONT, idleFrontAnimations());
        animations.put(PlayerAnimationType.IDLE_BACK, idleBackAnimations());
        animations.put(PlayerAnimationType.RUN_FRONT, runFrontAnimations());
        animations.put(PlayerAnimationType.RUN_BACK, runBackAnimations());
        animations.put(PlayerAnimationType.ATTACK_SIDE, attackSideAnimations());
        animations.put(PlayerAnimationType.ATTACK_UP, attackUpAnimations());
        animations.put(PlayerAnimationType.ATTACK_DOWN, attackDownAnimations());
        animations.put(PlayerAnimationType.TAKE_DAMAGE, takeDamageAnimations());
        animations.put(PlayerAnimationType.DEATH, deathAnimations());
    }

    public Animation<TextureRegion> getAnimation(PlayerAnimationType playerAnimationType) {
        return animations.get(playerAnimationType);
    }

    private Animation<TextureRegion> idleFrontAnimations() {
        return AnimationHelpers.animationTextureFrames(
                textureAtlas,
                "IdleFront",
                8
        );
    }

    private Animation<TextureRegion> idleBackAnimations() {
        return AnimationHelpers.animationTextureFrames(
                textureAtlas,
                "IdleBack",
                8
        );
    }

    private Animation<TextureRegion> runFrontAnimations() {
        return AnimationHelpers.animationTextureFrames(
                textureAtlas,
                "RunFront",
                6
        );
    }

    private Animation<TextureRegion> runBackAnimations() {
        return AnimationHelpers.animationTextureFrames(
                textureAtlas,
                "RunBack",
                6
        );
    }

    private Animation<TextureRegion> attackSideAnimations() {
        return AnimationHelpers.animationTextureFrames(
                textureAtlas,
                "AttackSide",
                3
        );
    }

    private Animation<TextureRegion> attackUpAnimations() {
        return AnimationHelpers.animationTextureFrames(
                textureAtlas,
                "AttackUp",
                3
        );
    }

    private Animation<TextureRegion> attackDownAnimations() {
        return AnimationHelpers.animationTextureFrames(
                textureAtlas,
                "AttackDown",
                3
        );
    }

    private Animation<TextureRegion> takeDamageAnimations() {
        return AnimationHelpers.animationTextureFrames(
                textureAtlas,
                "TakeDamage",
                3
        );
    }

    private Animation<TextureRegion> deathAnimations() {
        return AnimationHelpers.animationTextureFrames(
                textureAtlas,
                "Death",
                5
        );
    }
}
