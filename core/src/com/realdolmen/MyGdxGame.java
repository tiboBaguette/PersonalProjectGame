package com.realdolmen;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kotcrab.vis.ui.VisUI;
import com.realdolmen.entities.Arrow;
import com.realdolmen.entities.Player;
import com.realdolmen.entities.Slime;
import com.realdolmen.map.Map;
import com.realdolmen.textures.MapTiles;
import com.realdolmen.entities.facingValues;

import java.util.ArrayList;
import java.util.List;

public class MyGdxGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Stage stage;

    // map
    private MapTiles mapTileset = new MapTiles();
    private Map map = new Map(5, 41, 41, 21, 21); // mapSize is how many times extra rooms get generated

    // camera
    private OrthographicCamera camera;

    // player
    private Player player;

    // enemies
    private List<Slime> enemies;

    // arrows
    private List<Arrow> arrows;

    @Override
    public void create() {
        // batch
        batch = new SpriteBatch();

        // map
        mapTileset.createTextures();
        map.generate(mapTileset);
        enemies = map.addEnemies();

        // camera
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);
        camera.update();

        // player
        player = new Player(0, 0, 16, 8, camera);
        map.setPlayer(player);
        arrows = new ArrayList<>();

        // ui
        VisUI.load(VisUI.SkinScale.X2);
        stage = new Stage();

        player.createUi(stage);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render() {
        // clear
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        for (Slime slime : enemies) {
            slime.update(player);
        }
        for (Arrow arrow : arrows) {
            arrow.update();
        }

        // input
        input();

        // batch
        batch.begin();
        // map
        map.draw(batch);
        // arrows
        for (Arrow arrow : arrows) {
            arrow.draw(batch);
        }
        // player
        player.draw(batch);
        batch.setProjectionMatrix(camera.combined);
        // stage
        stage.draw();
        batch.end();

        // camera
        camera.update();
    }

    @Override
    public void dispose() {
        batch.dispose();
        VisUI.dispose();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    private void input() {
        // set idle animation
        if (player.getFacing().equals(facingValues.UP.toString())) {
            player.setNextAnimation(player.getAnimationFrames().getPlayerIdleBack());
        } else {
            player.setNextAnimation(player.getAnimationFrames().getPlayerIdleFront());
        }

        // player movement input
        if(Gdx.input.isKeyPressed(Input.Keys.Q)){
            for (int i = 0; i < player.getMoveSpeed(); i++) {
                player.move(-1, 0);
            }
            player.setNextAnimation(player.getAnimationFrames().getPlayerRunFront());
            player.setFacing(facingValues.LEFT.name());
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            for (int i = 0; i < player.getMoveSpeed(); i++) {
                player.move(1, 0);
            }
            player.setNextAnimation(player.getAnimationFrames().getPlayerRunFront());
            player.setFacing(facingValues.RIGHT.name());
        }

        if(Gdx.input.isKeyPressed(Input.Keys.Z)){
            for (int i = 0; i < player.getMoveSpeed(); i++) {
                player.move(0, 1);
            }
            player.setNextAnimation(player.getAnimationFrames().getPlayerRunBack());
            player.setFacing(facingValues.UP.name());
        }

        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            for (int i = 0; i < player.getMoveSpeed(); i++) {
                player.move(0, -1);
            }
            player.setNextAnimation(player.getAnimationFrames().getPlayerRunFront());
            player.setFacing(facingValues.DOWN.name());
        }

        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            player.move(0, 40);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            player.move(0, -40);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            player.move(-40, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            player.move(40, 0);
        }

        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            Arrow arrow = new Arrow(10, player.getX(), player.getY(), Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
            arrows.add(arrow);
        }
    }
}