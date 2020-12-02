package com.realdolmen.textures.animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.realdolmen.world.Settings;

public class AnimationHelpers {
    private static final float ANIMATION_LENGTH = Settings.getAnimationLength();

    public static Animation<TextureRegion> animationTextureFrames(TextureAtlas textureAtlas, String prefix, int frames) {
        TextureRegion[] animationFrames = new TextureRegion[frames];
        for(int i = 0; i < frames; i++) {
            animationFrames[i] = (textureAtlas.findRegion(prefix + (i + 1)));
        }
        return new Animation<>(ANIMATION_LENGTH, animationFrames);
    }
}