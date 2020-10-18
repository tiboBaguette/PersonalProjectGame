package com.realdolmen.textures;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.List;

public class MapTiles extends ApplicationAdapter {
    private TextureRegion wallCornerTopLeft, wallCornerTopRight, wallCornerBottomLeft, wallCornerBottomRight;
    private List<TextureRegion> wallVertical, wallHorizontal, floor;
    private TextureRegion chest;

    public void createTextures() {
        TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("core/assets/maps/Tileset.atlas"));

        // corners
        wallCornerTopLeft = new TextureRegion(textureAtlas.findRegion("tile000"));
        wallCornerTopRight = new TextureRegion(textureAtlas.findRegion("tile003"));
        wallCornerBottomLeft = new TextureRegion(textureAtlas.findRegion("tile048"));
        wallCornerBottomRight = new TextureRegion(textureAtlas.findRegion("tile051"));

        // vertical walls
        wallVertical = new ArrayList<>();
        wallVertical.add(new TextureRegion(textureAtlas.findRegion("tile004")));
        wallVertical.add(new TextureRegion(textureAtlas.findRegion("tile032")));
        wallVertical.add(new TextureRegion(textureAtlas.findRegion("tile033")));
        wallVertical.add(new TextureRegion(textureAtlas.findRegion("tile035")));

        // horizontal walls
        wallHorizontal = new ArrayList<>();
        wallHorizontal.add(new TextureRegion(textureAtlas.findRegion("tile002")));
        wallHorizontal.add(new TextureRegion(textureAtlas.findRegion("tile036")));
        wallHorizontal.add(new TextureRegion(textureAtlas.findRegion("tile037")));

        // floor
        floor = new ArrayList<>();
        floor.add(new TextureRegion(textureAtlas.findRegion("tile005")));
        floor.add(new TextureRegion(textureAtlas.findRegion("tile005")));
        floor.add(new TextureRegion(textureAtlas.findRegion("tile006")));
        floor.add(new TextureRegion(textureAtlas.findRegion("tile021")));
        floor.add(new TextureRegion(textureAtlas.findRegion("tile022")));

        // chest
        chest = new TextureRegion(textureAtlas.findRegion("tile192"));
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

    public List<TextureRegion> getWallVertical() {
        return wallVertical;
    }

    public List<TextureRegion> getWallHorizontal() {
        return wallHorizontal;
    }

    public List<TextureRegion> getFloor() {
        return floor;
    }

    public TextureRegion getChest() {
        return chest;
    }
}
