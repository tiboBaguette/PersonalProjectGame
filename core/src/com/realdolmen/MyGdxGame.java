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
import com.realdolmen.creatures.Player;
import com.realdolmen.map.Map;
import com.realdolmen.map.Room;
import com.realdolmen.textures.MapTiles;

public class MyGdxGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Stage stage;

    // ui
    private Skin skin;

    // map
    private MapTiles mapTileset = new MapTiles();
    private Map map = new Map(1000, 1000,10, 5, 41, 41, 21, 21);
    // rooms
    // private Room room = new Room(100, 100, 31, 31);

    // camera
    private OrthographicCamera camera;

    // player
    private Player player;

    @Override
    public void create() {
        // batch
        batch = new SpriteBatch();

        // camera
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);
        camera.update();

        // player
        player = new Player(screenWidth / 2 - 12, screenHeight / 2 - 16, camera);

        // map

        mapTileset.createTextures();
        map.generate(batch, mapTileset);
        //room.generate(batch, mapTileset);

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
                map.generate(batch, mapTileset);
            }
        });

        stage.addActor(button);
        Gdx.input.setInputProcessor(stage);
        // end buttton
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
        //room.draw(batch);
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
    }
}