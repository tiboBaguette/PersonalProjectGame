package com.realdolmen.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.realdolmen.entities.Arrow;
import com.realdolmen.entities.Player;
import com.realdolmen.entities.Slime;
import com.realdolmen.map.Map;
import com.realdolmen.textures.MapTiles;

import java.util.ArrayList;
import java.util.List;

public class World {
    private Map map;
    private Player player;
    private List<Slime> slimes;
    private List<Arrow> arrows;
    private Statistics statistics;
    private Settings settings;

    public World() {
        this.arrows = new ArrayList<>();
        this.slimes = new ArrayList<>();
        this.statistics = new Statistics();
        this.settings = new Settings();

        // map
        MapTiles mapTileset = new MapTiles();
        mapTileset.createTextures();
        this.map = new Map(settings.getMapSize(),
                            settings.getMaxRoomWidth(),
                            settings.getMaxRoomHeight(),
                            settings.getMinRoomWidth(),
                            settings.getMinRoomHeight(),
                            this);
        this.map.generate(mapTileset);
        this.map.addEnemies();
    }

    public void update() {
        // update statistics
        statistics.setTime(statistics.getTime() + Gdx.graphics.getDeltaTime());

        // update player
        player.update(this);

        // update slimes
        for (int i = 0; i < slimes.size(); i++) { // moet met iterator -> ConcurrentModificationException
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

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Slime> getSlimes() {
        return slimes;
    }

    public void setSlimes(List<Slime> enemies) {
        this.slimes = enemies;
    }

    public void addSlime(Slime slime) {
        this.slimes.add(slime);
    }

    public List<Arrow> getArrows() {
        return arrows;
    }

    public void setArrows(List<Arrow> arrows) {
        this.arrows = arrows;
    }

    public void removeArrow(Arrow arrow) {
        arrow.removeCollisionEntity();
        arrows.remove(arrow);
    }

    public void removeSlime(Slime slime) {
        slime.removeCollisionEntity();
        slimes.remove(slime);
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }
}
