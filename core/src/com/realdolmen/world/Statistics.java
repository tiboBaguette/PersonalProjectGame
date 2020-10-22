package com.realdolmen.world;

public class Statistics {
    private int kills;
    private float time;
    private float arrowsShot;
    private float damageDone;
    private float damageTaken;

    public Statistics() {
        kills = 0;
        time = 0;
        arrowsShot = 0;
        damageDone = 0;
        damageTaken = 0;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public float getArrowsShot() {
        return arrowsShot;
    }

    public void setArrowsShot(float arrowsShot) {
        this.arrowsShot = arrowsShot;
    }

    public float getDamageDone() {
        return damageDone;
    }

    public void setDamageDone(float damageDone) {
        this.damageDone = damageDone;
    }

    public float getDamageTaken() {
        return damageTaken;
    }

    public void setDamageTaken(float damageTaken) {
        this.damageTaken = damageTaken;
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "kills=" + kills +
                ", time=" + time +
                ", arrowsShot=" + arrowsShot +
                ", damageDone=" + damageDone +
                ", damageTaken=" + damageTaken +
                '}';
    }
}
