package com.realdolmen.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Creature extends CollisionEntity {
    private float maxHealth;
    private float health;
    private float moveSpeed;
    private float attackSpeed;
    private float attackDamage;

    public Creature(float x, float y, float width, float height) {
        super(x, y, width, height);
    }
}
