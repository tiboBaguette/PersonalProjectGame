package com.realdolmen.textures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.List;

public class MapTiles {
    private TextureRegion wallCornerTopLeft, wallCornerTopRight, wallCornerBottomLeft, wallCornerBottomRight;
    private List<TextureRegion> wallVertical, wallHorizontal, floor;
    private TextureRegion chest;

    public void createTextures() {
        TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("core/assets/maps/Tileset.atlas"));

        // corners
        wallCornerTopLeft = new TextureRegion(textureAtlas.findRegion("tile008"));
        wallCornerTopRight = new TextureRegion(textureAtlas.findRegion("tile011"));
        wallCornerBottomLeft = new TextureRegion(textureAtlas.findRegion("tile056"));
        wallCornerBottomRight = new TextureRegion(textureAtlas.findRegion("tile059"));

        // vertical walls
        wallVertical = new ArrayList<>();
        wallVertical.add(new TextureRegion(textureAtlas.findRegion("tile012")));
        wallVertical.add(new TextureRegion(textureAtlas.findRegion("tile040")));
        wallVertical.add(new TextureRegion(textureAtlas.findRegion("tile041")));
        wallVertical.add(new TextureRegion(textureAtlas.findRegion("tile043")));

        // horizontal walls
        wallHorizontal = new ArrayList<>();
        wallHorizontal.add(new TextureRegion(textureAtlas.findRegion("tile010")));
        wallHorizontal.add(new TextureRegion(textureAtlas.findRegion("tile044")));
        wallHorizontal.add(new TextureRegion(textureAtlas.findRegion("tile045")));

        // floor
        floor = new ArrayList<>();
        floor.add(new TextureRegion(textureAtlas.findRegion("tile013")));
        floor.add(new TextureRegion(textureAtlas.findRegion("tile013")));
        floor.add(new TextureRegion(textureAtlas.findRegion("tile014")));
        floor.add(new TextureRegion(textureAtlas.findRegion("tile029")));
        floor.add(new TextureRegion(textureAtlas.findRegion("tile030")));

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
