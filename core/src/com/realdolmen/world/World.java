package com.realdolmen.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.realdolmen.entities.Arrow;
import com.realdolmen.entities.Player;
import com.realdolmen.entities.Slime;
import com.realdolmen.map.Map;
import com.realdolmen.map.mapGenerator.MapGenerator;
import com.realdolmen.textures.animations.PlayerAnimationType;
import com.realdolmen.textures.animations.SlimeAnimationType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class World {
    private Map map;
    private Player player;
    private List<Slime> slimes;
    private List<Arrow> arrows;
    private Statistics statistics;
    private MapGenerator mapGenerator;
    private State state;
    private Input input;

    public World() {
        this.arrows = new ArrayList<>();
        this.slimes = new ArrayList<>();
        this.statistics = new Statistics();

        // generate new map
        mapGenerator = new MapGenerator(this);
        this.map = mapGenerator.generateNewMap();

        // game state
        state = State.RESUME;

        // input
        input = new Input(this);
    }

    public void update() {
        // update statistics
        statistics.setTime(statistics.getTime() + Gdx.graphics.getDeltaTime());

        // update player
        player.update(this);

        // update slimes
        for (int i = 0; i < slimes.size(); i++) { // needs iterator -> ConcurrentModificationException
            Slime slime = slimes.get(i);
            slime.update(this);
        }

        // update arrows
        for (int i = 0; i < arrows.size(); i++) {
            Arrow arrow = arrows.get(i);
            player.getIgnoreCollisions().add(arrow);
            arrow.update(this);
        }
    }

    public void pause() {
        // set player idle animation
        player.setNextAnimation(player.getPlayerAnimations().getAnimation(PlayerAnimationType.IDLE_FRONT));

        // set slime idle animation
        for (int i = 0; i < slimes.size(); i++) { // needs iterator -> ConcurrentModificationException
            Slime slime = slimes.get(i);
            slime.setNextAnimation(slime.getSlimeAnimations().getAnimation(SlimeAnimationType.IDLE));
        }
    }

    public void draw(Batch batch) {
        // map
        map.draw(batch);

        // player
        player.draw(batch);

        // slimes
        for (Slime slime : slimes) {
            slime.draw(batch);
        }

        // arrows
        for (Arrow arrow : arrows) {
            arrow.draw(batch);
        }
    }

    public void addSlime(Slime slime) {
        this.slimes.add(slime);
    }

    public void removeArrow(Arrow arrow) {
        arrow.removeCollisionEntity();
        arrows.remove(arrow);
    }

    public void removeSlime(Slime slime) {
        slime.removeCollisionEntity();
        slimes.remove(slime);
    }
}
