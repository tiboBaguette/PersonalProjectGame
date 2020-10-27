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
import com.realdolmen.world.World;
import com.realdolmen.entities.facingValues;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class MyGdxGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Stage stage;
    private World world;
    private OrthographicCamera camera;

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
        world.setPlayer(new Player(0, 0, 48, 48, camera));

        // ui
        VisUI.load(VisUI.SkinScale.X2);
        stage = new Stage();
        world.getPlayer().createUi(stage);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render() {
        // clear
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // input
        input();

        // update
        world.update();

        // draw batch
        batch.begin();
        world.draw(batch);
        batch.setProjectionMatrix(camera.combined);
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
        if (world.getPlayer().getFacing().equals(facingValues.UP.toString())) {
            world.getPlayer().setNextAnimation(world.getPlayer().getAnimationFrames().getPlayerIdleBack());
        } else {
            world.getPlayer().setNextAnimation(world.getPlayer().getAnimationFrames().getPlayerIdleFront());
        }

        // player movement input
        if(Gdx.input.isKeyPressed(Input.Keys.Q)){
            for (int i = 0; i < world.getPlayer().getMoveSpeed(); i++) {
                world.getPlayer().move(-1, 0);
            }
            world.getPlayer().setNextAnimation(world.getPlayer().getAnimationFrames().getPlayerRunFront());
            world.getPlayer().setFacing(facingValues.LEFT.name());
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            for (int i = 0; i < world.getPlayer().getMoveSpeed(); i++) {
                world.getPlayer().move(1, 0);
            }
            world.getPlayer().setNextAnimation(world.getPlayer().getAnimationFrames().getPlayerRunFront());
            world.getPlayer().setFacing(facingValues.RIGHT.name());
        }

        if(Gdx.input.isKeyPressed(Input.Keys.Z)){
            for (int i = 0; i < world.getPlayer().getMoveSpeed(); i++) {
                world.getPlayer().move(0, 1);
            }
            world.getPlayer().setNextAnimation(world.getPlayer().getAnimationFrames().getPlayerRunBack());
            world.getPlayer().setFacing(facingValues.UP.name());
        }

        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            for (int i = 0; i < world.getPlayer().getMoveSpeed(); i++) {
                world.getPlayer().move(0, -1);
            }
            world.getPlayer().setNextAnimation(world.getPlayer().getAnimationFrames().getPlayerRunFront());
            world.getPlayer().setFacing(facingValues.DOWN.name());
        }

        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            world.getPlayer().move(0, 40);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            world.getPlayer().move(0, -40);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            world.getPlayer().move(-40, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            world.getPlayer().move(40, 0);
        }

        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            Arrow arrow = new Arrow(10, world.getPlayer().getX(), world.getPlayer().getY(), Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
            world.getArrows().add(arrow);
            world.getStatistics().setArrowsShot(world.getStatistics().getArrowsShot() + 1);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            EntityManagerFactory emf = null;
            EntityManager em = null;
            try {
                emf = Persistence.createEntityManagerFactory("mysqlcontainernone");
                em = emf.createEntityManager();
                EntityTransaction tx = em.getTransaction();

                tx.begin();
                em.persist(world.getStatistics());
                tx.commit();
            } finally {
                if(em!=null) {
                    em.close();
                }
                if(emf!=null) {
                    emf.close();
                }
            }
        }
    }
}