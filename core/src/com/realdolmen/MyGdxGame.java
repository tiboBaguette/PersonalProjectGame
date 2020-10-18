package com.realdolmen;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.realdolmen.entities.Player;
import com.realdolmen.map.Map;
import com.realdolmen.textures.MapTiles;

public class MyGdxGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Stage stage;

    // ui
    private Skin skin;

    // map
    private MapTiles mapTileset = new MapTiles();
    private Map map = new Map(5, 41, 41, 21, 21); // mapSize is how many times extra rooms get generated

    // camera
    private OrthographicCamera camera;

    // player
    private Player player;

    @Override
    public void create() {
        // batch
        batch = new SpriteBatch();

        // map
        mapTileset.createTextures();
        map.generate(mapTileset);

        // camera
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);
        camera.update();

        // player
        player = new Player(0, 0, 12, 12, camera);

        // animations
        player.createAnimationFrames();

        // button
        skin = new Skin(Gdx.files.internal("core/assets/ui/uiskin.json"));
        stage = new Stage();

        final TextButton button = new TextButton("Generate New Map", skin, "default");

        button.setWidth(200f);
        button.setHeight(20f);
        button.setPosition(Gdx.graphics.getWidth() - 200f, Gdx.graphics.getHeight() - 20f);

        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                //room.generate(batch, mapTileset);
                //map.generate(batch, mapTileset);
                System.out.println("player X: " + player.getX() + "player Y: " + player.getY());
            }
        });

        stage.addActor(button);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render() {
        // clear
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // input
        input();

        // camera
        camera.update();

        // batch
        batch.begin();
        // map
        map.draw(batch);
        // player
        player.draw(batch);
        batch.setProjectionMatrix(camera.combined);
        // stage
        stage.draw();
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
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
        // player movement input
        if(Gdx.input.isKeyPressed(Input.Keys.Q)){
            player.move(-8, 0);
            player.setCurrentAnimation(player.getAnimationFrames().getPlayerRunFront());
            player.setFacing("LEFT");
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            player.move(8, 0);
            player.setCurrentAnimation(player.getAnimationFrames().getPlayerRunFront());
            player.setFacing("RIGHT");
        }

        if(Gdx.input.isKeyPressed(Input.Keys.Z)){
            player.move(0, 8);
            player.setCurrentAnimation(player.getAnimationFrames().getPlayerRunBack());
            player.setFacing("UP");
        }

        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            player.move(0, -8);
            player.setCurrentAnimation(player.getAnimationFrames().getPlayerRunFront());
            player.setFacing("DOWN");
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
    }
}