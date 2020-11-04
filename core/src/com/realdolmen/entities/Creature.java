package com.realdolmen.entities;

public abstract class Creature extends CollisionEntity {
    private float maxHealth;
    private float health;
    private float moveSpeed;
    private float attackSpeed;
    private float attackDamage;

    public Creature(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public void setAttackSpeed(float attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public void setAttackDamage(float attackDamage) {
        this.attackDamage = attackDamage;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public float getHealth() {
        return health;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    public float getAttackDamage() {
        return attackDamage;
    }
}
