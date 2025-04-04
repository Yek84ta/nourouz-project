package db;

import db.exception.EntityNotFoundException;

import java.util.ArrayList;

public class Database {

    private static ArrayList<Entity> entities = new ArrayList<>();
    public static int idCreation;

    private Database() {
    }

    public static void add(Entity e) {
        entities.add(e);
        idCreation ++;
        e.id = idCreation;
    }

    public static Entity get(int id) {
        for (Entity entity : entities)
            if (entity.id == id)
                return entity.clone(); // Return a clone instead of original
        throw new EntityNotFoundException(id);
    }

    public static void delete(int id) {
        for (Entity entity : entities)
            if (entity.id == id) {
                entities.remove(entity);
                return;
            }
        throw new EntityNotFoundException(id);
    }

    public static void update(Entity e) {
        for (Entity entity : entities)
            if (entity.id == e.id) {
                entities.set(entities.indexOf(entity), e.clone());
                return;
            }
        throw new EntityNotFoundException();
    }
}