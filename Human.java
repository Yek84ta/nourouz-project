package example;

import db.Entity;

public class Human extends Entity {
    public String name;
    public static final int HUMAN_ENTITY_CODE = 14;
    public int age;

    public Human(String name, int age) {
        this.name = name;
        this.age = age;
    }


    @Override
    public Human clone() {
        return (Human) super.clone();
    }

    @Override
    public int getEntityCode() {
        return HUMAN_ENTITY_CODE;
    }

}