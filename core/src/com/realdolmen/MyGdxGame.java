package com.realdolmen;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kotcrab.vis.ui.VisUI;
import com.realdolmen.entities.*;
import com.realdolmen.world.World;

public class MyGdxGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Stage stage;
    private World world;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;

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

        // world
        world = new World();
        // player
        world.setPlayer(new Player(0, 0, camera, world));

        // ui
        VisUI.load(VisUI.SkinScale.X2);
        stage = new Stage();
        world.getPlayer().createUi(stage);
        Gdx.input.setInputProcessor(stage);

        // shaperederer
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render() {
        switch (world.getState()) {
            case RESUME:
                resume();
                break;
            case PAUSE:
                pause();
                break;
        }
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
        // clear
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // input
        world.getInput().generalInput();

        // update
        world.pause();

        // draw batch
        batch.begin();
        world.draw(batch);
        batch.setProjectionMatrix(camera.combined);
        stage.draw();
        batch.end();

        // darken screen
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 0.6f);
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    @Override
    public void resume() {
        // clear
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // input
        world.getInput().generalInput();

        // update
        world.update();

        // draw batch
        batch.begin();
        world.draw(batch);
        batch.setProjectionMatrix(camera.combined);
        stage.draw();
        batch.end();

        // debug collisions
        debugCollisions();

        // camera
        camera.update();
    }

    private void debugCollisions() {
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        for (CollisionEntity collisionEntity : CollisionEntity.getCollisionEntities()) {
            collisionEntity.debugCollisions(shapeRenderer);
        }

        shapeRenderer.end();
    }
}