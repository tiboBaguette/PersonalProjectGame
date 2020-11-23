package com.realdolmen.world;

import com.badlogic.gdx.Gdx;
import com.realdolmen.entities.Arrow;
import com.realdolmen.entities.FacingValues;
import com.realdolmen.textures.animations.PlayerAnimationType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Input {
    private final World world;

    public Input(World world) {
        this.world = world;
    }

    public void generalInput() {
        // pause / resume
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            switch (world.getState()) {
                case RESUME:
                    world.setState(State.PAUSE);
                    break;
                case PAUSE:
                    world.setState(State.RESUME);
                    break;
            }
        }

        // JPA scoreboard
        if(Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.ENTER)) {
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

    public void playerInput() {
        // set idle animation
        if (world.getPlayer().getFacing().equals(FacingValues.UP.toString())) {
            world.getPlayer().setNextAnimation(world.getPlayer().getPlayerAnimations().getAnimation(PlayerAnimationType.IDLE_BACK));
        } else {
            world.getPlayer().setNextAnimation(world.getPlayer().getPlayerAnimations().getAnimation(PlayerAnimationType.IDLE_FRONT));
        }

        // player movement input
        if(Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.Q)){
            for (int i = 0; i < world.getPlayer().getMoveSpeed(); i++) {
                world.getPlayer().move(-1, 0);
            }
            world.getPlayer().setNextAnimation(world.getPlayer().getPlayerAnimations().getAnimation(PlayerAnimationType.RUN_FRONT));
            world.getPlayer().setFacing(FacingValues.LEFT.name());
        }

        if(Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.D)){
            for (int i = 0; i < world.getPlayer().getMoveSpeed(); i++) {
                world.getPlayer().move(1, 0);
            }
            world.getPlayer().setNextAnimation(world.getPlayer().getPlayerAnimations().getAnimation(PlayerAnimationType.RUN_FRONT));
            world.getPlayer().setFacing(FacingValues.RIGHT.name());
        }

        if(Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.Z)){
            for (int i = 0; i < world.getPlayer().getMoveSpeed(); i++) {
                world.getPlayer().move(0, 1);
            }
            world.getPlayer().setNextAnimation(world.getPlayer().getPlayerAnimations().getAnimation(PlayerAnimationType.RUN_BACK));
            world.getPlayer().setFacing(FacingValues.UP.name());
        }

        if(Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.S)){
            for (int i = 0; i < world.getPlayer().getMoveSpeed(); i++) {
                world.getPlayer().move(0, -1);
            }
            world.getPlayer().setNextAnimation(world.getPlayer().getPlayerAnimations().getAnimation(PlayerAnimationType.RUN_FRONT));
            world.getPlayer().setFacing(FacingValues.DOWN.name());
        }

        if(Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.UP)){
            world.getPlayer().move(0, 40);
        }
        if(Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.DOWN)){
            world.getPlayer().move(0, -40);
        }
        if(Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.LEFT)){
            world.getPlayer().move(-40, 0);
        }
        if(Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.RIGHT)){
            world.getPlayer().move(40, 0);
        }

        if(Gdx.input.isButtonJustPressed(com.badlogic.gdx.Input.Buttons.LEFT)){
            // create a new arrow on the player location
            Arrow arrow = new Arrow(world.getPlayer().getAttackDamage(),
                    world.getPlayer().getX(),
                    world.getPlayer().getY(),
                    Gdx.input.getX(),
                    Gdx.graphics.getHeight() - Gdx.input.getY());

            // add the arrow to the world and update statistics
            world.getArrows().add(arrow);
            world.getStatistics().setArrowsShot(world.getStatistics().getArrowsShot() + 1);
        }
    }
}
