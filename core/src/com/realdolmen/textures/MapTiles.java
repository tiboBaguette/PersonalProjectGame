package com.realdolmen.textures;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MapTiles extends ApplicationAdapter {
    private TextureAtlas textureAtlas;
    private TextureRegion wallCornerTopLeft, wallCornerTopRight, wallCornerBottomLeft, wallCornerBottomRight;

    public void createTextures() {
        textureAtlas = new TextureAtlas(Gdx.files.internal("core/assets/maps/Tileset.atlas"));

        wallCornerTopLeft = new TextureRegion(textureAtlas.findRegion("tile000"));
        wallCornerTopRight = new TextureRegion(textureAtlas.findRegion("tile003"));
        wallCornerBottomLeft = new TextureRegion(textureAtlas.findRegion("tile048"));
        wallCornerBottomRight = new TextureRegion(textureAtlas.findRegion("tile051"));
    }

    public TextureRegion getWallCornerTopLeft() {
        return wallCornerTopLeft;
    }

    public TextureRegion getWallCornerTopRight() {
        return wallCornerTopRight;
    }

    public TextureRegion getWallCornerBottomLeft() {
        return wallCornerBottomLeft;
    }

    public TextureRegion getWallCornerBottomRight() {
        return wallCornerBottomRight;
    }
}
