package todo.entity;

import db.Database;
import db.Entity;
import db.Serializer;

import java.text.ParseException;

public class Step extends Entity {
    public enum Status { NotStarted, Completed }

    public String title;
    public Status status;
    public int taskRef;// task ID ==
    public static final int STEP_ENTITY_CODE = 7;


    public Step(String title, int taskRef) {
        this.title = title;
        this.taskRef = taskRef;
        this.status = Status.NotStarted;
    }


    @Override
    public int getEntityCode() {
        return STEP_ENTITY_CODE;
    }


}
