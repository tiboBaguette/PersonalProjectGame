package com.realdolmen.textures.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class TextureHelpers {
    public static TextureAtlas getTextureAtlas(String atlas) {
        return new TextureAtlas(Gdx.files.internal(atlas));
    }
}
