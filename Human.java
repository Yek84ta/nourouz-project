package example;

import db.Entity;

public class Human extends Entity {
    public String name;

    public Human(String name) {
        this.name = name;
    }

    @Override
    public Human clone() {
        return (Human) super.clone();
    }
}