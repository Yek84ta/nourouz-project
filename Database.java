package db;

import db.exception.EntityNotFoundException;

import java.util.ArrayList;

public class Database {

    private static ArrayList<Entity> entities = new ArrayList<>();

    private Database() {
    }

    public static void add(Entity e) {
        entities.add(e);
        e.id = entities.size();
    }

    public static Entity get(int id) {
        for (Entity entity : entities)
            if (entity.id == id)
                return entity;
        throw new EntityNotFoundException(id);
    }

    public static void delete(int id) {
        for (Entity entity : entities)
            if (entity.id == id)
                entities.remove(entity);
        throw new EntityNotFoundException(id);
    }

    public static void update(Entity e) {
        for (Entity entity : entities)
            if (entity.id == e.id) {
                entities.set(entities.indexOf(entity), e);
                return;
            }
        throw new EntityNotFoundException();
    }
}