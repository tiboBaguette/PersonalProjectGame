package com.realdolmen;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.realdolmen.collisions.CollisionEntity;
import com.realdolmen.map.MapGeneration;
import com.realdolmen.textures.AnimationFrames;
import com.realdolmen.textures.MapTiles;

public class MyGdxGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Stage stage;

    // ui
    private Skin skin;

    // animations
    private float elapsedTime = 0;
    private AnimationFrames animationFrames = new AnimationFrames();

    // maps
    private MapTiles mapTileset = new MapTiles();
    MapGeneration mapGeneration = new MapGeneration(21, 21);

    // camera
    private OrthographicCamera camera;

    // player
    private float playerX = -16;
    private float playerY = -16;

    // collisions
    CollisionEntity testEntity = new CollisionEntity(-50, -50, 16, 16);
    CollisionEntity testEntity2 = new CollisionEntity(200, 200, 16, 16);
    CollisionEntity testEntity3 = new CollisionEntity(150, 200, 16, 16);
    CollisionEntity player = new CollisionEntity(playerX, playerY, 16, 16);

    @Override
    public void create() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        batch = new SpriteBatch();

        // camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false,screenWidth,screenHeight);
        camera.update();

        // map
        mapTileset.createTextures();

        // animations
        animationFrames.createFrames();

        // collisions
        // collision test
        testEntity.addCollisionEntity(testEntity);
        testEntity2.addCollisionEntity(testEntity2);
        testEntity3.addCollisionEntity(testEntity3);
        //
        player.addCollisionEntity(player);

        // button
        skin = new Skin(Gdx.files.internal("core/assets/ui/uiskin.json"));
        stage = new Stage();

        final TextButton button = new TextButton("Click me", skin, "default");

        button.setWidth(200f);
        button.setHeight(20f);
        button.setPosition(Gdx.graphics.getWidth() - 200f, Gdx.graphics.getHeight() - 20f);

        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                button.setText("You clicked the button");
            }
        });

        stage.addActor(button);
        Gdx.input.setInputProcessor(stage);
        // end buttton
    }

    @Override
    public void render() {
        //System.out.println("x" + playerX + "y" + playerY);
        // clear
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // camera movement
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            camera.translate(2,0);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            camera.translate(-2,0);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            camera.translate(0,-2);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            camera.translate(0,2);
        }

        // keyboard input
        if(Gdx.input.isKeyPressed(Input.Keys.Q)){
            player.move(player, -2, 0);
            animationFrames.setPlayerCurrentAnimation(animationFrames.getPlayerRunFront());
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            player.move(player, 2, 0);
            animationFrames.setPlayerCurrentAnimation(animationFrames.getPlayerRunFront());
        }

        if(Gdx.input.isKeyPressed(Input.Keys.Z)){
            player.move(player, 0, 2);
            animationFrames.setPlayerCurrentAnimation(animationFrames.getPlayerRunBack());
        }

        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            player.move(player, 0, -2);
            animationFrames.setPlayerCurrentAnimation(animationFrames.getPlayerRunFront());
        }

        // mouse input
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            playerX = Gdx.input.getX() - 24;
            playerY = Gdx.graphics.getHeight() - Gdx.input.getY() - 24; // mouse top left = 0,0  sprite bottom left = 0,0
        }

        // camera
        camera.update();

        // batch (player)
        batch.begin();
        elapsedTime += Gdx.graphics.getDeltaTime();
        // map
        mapGeneration.generateMap(batch, mapTileset);
        // player
        batch.draw((TextureRegion) animationFrames.getPlayerCurrentAnimation().getKeyFrame(elapsedTime, true), player.getX() - 16, player.getY() - 16);
        // collision test
        batch.draw((TextureRegion) animationFrames.getPlayerRunFront().getKeyFrame(elapsedTime, true), -50 - 16, -50 - 16);
        batch.draw((TextureRegion) animationFrames.getPlayerTakeDamage().getKeyFrame(elapsedTime, true), 200 - 16, 200 - 16);
        batch.draw((TextureRegion) animationFrames.getPlayerAttackUp().getKeyFrame(elapsedTime, true), 150 - 16, 200 - 16);
        //
        batch.setProjectionMatrix(camera.combined);
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
}