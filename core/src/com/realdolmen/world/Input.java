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
                    world.pause();
                    break;
                case PAUSE:
                    world.setState(State.RESUME);
                    break;
            }
        }

//        // JPA scoreboard
//        if(Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.ENTER)) {
//            EntityManagerFactory emf = null;
//            EntityManager em = null;
//            try {
//                emf = Persistence.createEntityManagerFactory("mysqlcontainernone");
//                em = emf.createEntityManager();
//                EntityTransaction tx = em.getTransaction();
//
//                tx.begin();
//                em.persist(world.getStatistics());
//                tx.commit();
//            } finally {
//                if(em!=null) {
//                    em.close();
//                }
//                if(emf!=null) {
//                    emf.close();
//                }
//            }
//        }

    }

    public void playerInput() {
        // set idle animation
        if (world.getPlayer().getFacing().equals(FacingValues.UP.toString())) {
            world.getPlayer().setNextAnimation(world.getPlayer().getPlayerAnimations().getAnimation(PlayerAnimationType.IDLE_BACK), false);
        } else {
            world.getPlayer().setNextAnimation(world.getPlayer().getPlayerAnimations().getAnimation(PlayerAnimationType.IDLE_FRONT), false);
        }

        // player movement input
        if(Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.Q)){
            // move
            for (int i = 0; i < world.getPlayer().getMoveSpeed(); i++) {
                world.getPlayer().move(-1, 0);
            }

            // set animation
            world.getPlayer().setNextAnimation(world.getPlayer().getPlayerAnimations().getAnimation(PlayerAnimationType.RUN_FRONT), true);
            world.getPlayer().setFacing(FacingValues.LEFT.name());
        }

        if(Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.D)){
            // move
            for (int i = 0; i < world.getPlayer().getMoveSpeed(); i++) {
                world.getPlayer().move(1, 0);
            }

            // set animation
            world.getPlayer().setNextAnimation(world.getPlayer().getPlayerAnimations().getAnimation(PlayerAnimationType.RUN_FRONT), false);
            world.getPlayer().setFacing(FacingValues.RIGHT.name());
        }

        if(Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.Z)){
            // move
            for (int i = 0; i < world.getPlayer().getMoveSpeed(); i++) {
                world.getPlayer().move(0, 1);
            }

            // set animation
            world.getPlayer().setNextAnimation(world.getPlayer().getPlayerAnimations().getAnimation(PlayerAnimationType.RUN_BACK), false);
            world.getPlayer().setFacing(FacingValues.UP.name());
        }

        if(Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.S)){
            // move
            for (int i = 0; i < world.getPlayer().getMoveSpeed(); i++) {
                world.getPlayer().move(0, -1);
            }

            // set animation
            world.getPlayer().setNextAnimation(world.getPlayer().getPlayerAnimations().getAnimation(PlayerAnimationType.RUN_FRONT), false);
            world.getPlayer().setFacing(FacingValues.DOWN.name());
        }

        // shoot arrow
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

        // melee attack
        if(Gdx.input.isButtonJustPressed(com.badlogic.gdx.Input.Buttons.RIGHT)){
            // get angle between mouse and player
            float angle = (float) Math.atan2(Gdx.input.getY() - Gdx.graphics.getHeight() / 2f, Gdx.input.getX() - Gdx.graphics.getWidth() / 2f);
            angle = (float) Math.toDegrees(angle);
            angle -= 45;
            if (angle < 0) { angle += 360; }

            // player melee attack
            if (angle > 0 && angle <= 90) {
                // down
                world.getPlayer().setFacing(FacingValues.DOWN.name());
                world.getPlayer().setCurrentAnimation(world.getPlayer().getPlayerAnimations().getAnimation(PlayerAnimationType.ATTACK_DOWN), true, false);
            } else if (angle > 90 && angle <= 180) {
                // left
                world.getPlayer().setFacing(FacingValues.LEFT.name());
                world.getPlayer().setCurrentAnimation(world.getPlayer().getPlayerAnimations().getAnimation(PlayerAnimationType.ATTACK_SIDE), true, true);
            } else if (angle > 180 && angle <= 270) {
                // up
                world.getPlayer().setFacing(FacingValues.UP.name());
                world.getPlayer().setCurrentAnimation(world.getPlayer().getPlayerAnimations().getAnimation(PlayerAnimationType.ATTACK_UP), true, false);
            } else if (angle > 270 && angle <= 360) {
                // right
                world.getPlayer().setFacing(FacingValues.RIGHT.name());
                world.getPlayer().setCurrentAnimation(world.getPlayer().getPlayerAnimations().getAnimation(PlayerAnimationType.ATTACK_SIDE), true, false);
            }
        }








        // debug movement
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
    }
}
