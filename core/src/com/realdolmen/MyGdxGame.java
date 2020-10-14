package com.realdolmen;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.realdolmen.collisions.CollisionEntity;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {
    private SpriteBatch batch;
    private Stage stage;

    // ui
    private Skin skin;

    // animations
    private float elapsedTime = 0;
    private AnimationFrames animationFrames = new AnimationFrames();

    // maps
    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;

    // camera
    private OrthographicCamera camera;

    // variables
    private float playerX = 0;
    private float playerY = 0;

    // collisions
    CollisionEntity testEntity = new CollisionEntity(150, 100, 24, 48);
    CollisionEntity testEntity2 = new CollisionEntity(200, 200, 24, 48);
    CollisionEntity testEntity3 = new CollisionEntity(150, 200, 24, 48);
    CollisionEntity player = new CollisionEntity(playerX, playerY, 24, 48);

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
        tiledMap = new TmxMapLoader().load("core/assets/maps/CollisionMap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        Gdx.input.setInputProcessor(this);

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

        if(Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
            tiledMap.getLayers().get(0).setVisible(!tiledMap.getLayers().get(0).isVisible());
        }

        if(Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
            tiledMap.getLayers().get(1).setVisible(!tiledMap.getLayers().get(1).isVisible());
        }

        // keyboard input
        if(Gdx.input.isKeyPressed(Input.Keys.Q)){
            player.move(player, -4, 0);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            player.move(player, 4, 0);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.Z)){
            player.move(player, 0, 4);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            player.move(player, 0, -4);
        }

        // mouse input
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            playerX = Gdx.input.getX() - 24;
            playerY = Gdx.graphics.getHeight() - Gdx.input.getY() - 24; // mouse top left = 0,0  sprite bottom left = 0,0
        }

        // camera & map
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        // batch (player)
        batch.begin();
        elapsedTime += Gdx.graphics.getDeltaTime();
        batch.draw((TextureRegion) animationFrames.playerAttackDown.getKeyFrame(elapsedTime, true), player.getX(), player.getY());
        // collision test
        batch.draw((TextureRegion) animationFrames.playerRunFront.getKeyFrame(elapsedTime, true), 150, 100);
        batch.draw((TextureRegion) animationFrames.playerTakeDamage.getKeyFrame(elapsedTime, true), 200, 200);
        batch.draw((TextureRegion) animationFrames.playerAttackSide.getKeyFrame(elapsedTime, true), 150, 200);
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

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
