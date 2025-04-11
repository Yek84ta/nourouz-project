package todo.entity;

import db.Database;
import db.Entity;
import db.Serializer;
import db.Trackable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Date;


public class Task extends Entity implements Trackable {


    public enum Status { NotStarted, InProgress, Completed }
    public String title;
    public String description;
    public Date dueDate;
    public Status status;
    public Date creationDate;
    public Date lastModificationDate;
    public static final int TASK_ENTITY_CODE = 8;




    public Task(String title, String description, Date dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = Status.NotStarted;
    }

    @Override
    public int getEntityCode() {
        return TASK_ENTITY_CODE;
    }

    @Override
    public void setCreationDate(Date date) {
        this.creationDate = date;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setLastModificationDate(Date date) {
        this.lastModificationDate = date;
    }

    @Override
    public Date getLastModificationDate() {
        return lastModificationDate;
    }



}