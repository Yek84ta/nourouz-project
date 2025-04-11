package db;

import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;
import todo.service.StepService;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Database {

    public static ArrayList<Entity> entities = new ArrayList<>();
    public static int idCreation;
    private static HashMap<Integer, Validator> validators = new HashMap<>();
    private static HashMap<Integer, Serializer> serializers = new HashMap<>();
    private static final String DB_FILE = "db.txt";


    private Database() {
    }

    public static void registerValidator(int entityCode, Validator validator) {
        if (validators.containsKey(entityCode)){
            throw new IllegalArgumentException("The Entity you mentioned already exists in its current state, and it's not possible to create a duplicate or modify it in this way.");
        }
        validators.put(entityCode, validator);
    }

    public static void registerSerializer(int entityCode, Serializer serializer) {
        if (serializers.containsKey(entityCode)) {
            throw new IllegalArgumentException("The Entity you mentioned already exists in its current state, and it's not possible to create a duplicate or modify it in this way.");
        }

        serializers.put(entityCode, serializer);
    }

    public static void add(Entity e) throws InvalidEntityException {
        Validator validator = validators.get(e.getEntityCode());
        validator.validate(e);

        if (e instanceof Trackable) {
            Trackable trackableEntity = (Trackable) e;
            Date currentDate = new Date();
            trackableEntity.setCreationDate(currentDate);
            trackableEntity.setLastModificationDate(currentDate);
        }

        entities.add(e);
        idCreation ++;
        e.id = idCreation;
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

        if (e instanceof Trackable) {
            Trackable trackableEntity = (Trackable) e;
            Date currentDate = new Date();
            trackableEntity.setLastModificationDate(currentDate);
        }

        for (Entity entity : entities)
            if (entity.id == e.id) {
                entities.set(entities.indexOf(entity), e.clone());
                return;
            }
        throw new EntityNotFoundException();
    }

    public static Entity findByID (int id){
        for (Entity entity : entities)
            if (entity.id == id)
                return entity;
        throw new EntityNotFoundException(id);
    }


    public static Entity get(int id) {
        for (Entity entity : entities)
            if (entity.id == id)
                return entity.clone();
        throw new EntityNotFoundException(id);
    }

    public static ArrayList<Entity> getAll(int entityCode) {
        ArrayList<Entity> result = new ArrayList<>();

        for (Entity entity : entities) {
            if (entity.getEntityCode() == entityCode) {
                result.add(entity.clone());
            }
        }

        return result;
    }

    public static void save() {
        for (Entity entity : entities) {
            Serializer serializer = serializers.get(entity.getEntityCode());
            serializer.serialize(entity);

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(DB_FILE, true))) {
                    writer.write("Task" + ":" + serializer);
                    writer.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }

    public static void load() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DB_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    String entityCode = parts[0];
                    String serializedData = parts[1];

                    if (serializers.containsKey(entityCode)) {
                        Serializer serializer = serializers.get(entityCode);
                        Entity entity = serializer.deserialize(serializedData);
                        Database.add(entity);

                        System.out.println("Loaded: " + entity);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidEntityException e){
            System.out.println("Something has happened. Please try again.");
        }
    }
}