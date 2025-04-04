package db;

import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;

import java.util.ArrayList;
import java.util.HashMap;


public class Database {

    private static ArrayList<Entity> entities = new ArrayList<>();

    public static int idCreation;
    private static HashMap<Integer, Validator> validators = new HashMap<>();

    private Database() {
    }
    public static void registerValidator(int entityCode, Validator validator) {
        if (validators.containsKey(entityCode)){
            throw new IllegalArgumentException("The Entity you mentioned already exists in its current state, and it's not possible to create a duplicate or modify it in this way.");
        }
        validators.put(entityCode, validator);
    }

    public static void add(Entity e) throws InvalidEntityException {
        Validator validator = validators.get(e.getEntityCode());
        validator.validate(e);

        entities.add(e);
        idCreation ++;
        e.id = idCreation;
    }

    public static Entity get(int id) {
        for (Entity entity : entities)
            if (entity.id == id)
                return entity.clone();

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

    public static void update(Entity e) throws InvalidEntityException {
        Validator validator = validators.get(e.getEntityCode());
        validator.validate(e);

        for (Entity entity : entities)
            if (entity.id == e.id) {
                entities.set(entities.indexOf(entity), e.clone());

                return;
            }
        throw new EntityNotFoundException();
    }
}