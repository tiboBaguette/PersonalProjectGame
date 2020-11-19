package com.realdolmen.world;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
public class Statistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    @Column(name = "Kills")
    private int kills;
    @Column(name = "Time")
    private float time;
    @Column(name = "ArrowsShot")
    private float arrowsShot;
    @Column(name = "DamageDone")
    private float damageDone;
    @Column(name = "DamageTaken")
    private float damageTaken;

    public Statistics() {
        kills = 0;
        time = 0;
        arrowsShot = 0;
        damageDone = 0;
        damageTaken = 0;
    }
}
