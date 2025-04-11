package todo.service;

import db.Database;
import db.Entity;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;
import todo.validator.TaskValidator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static db.Database.findByID;

public class TaskService {

    public static void setAsCompleted(int taskId) throws InvalidEntityException {
        Task task =(Task) Database.findByID(taskId);
        task.status = Task.Status.Completed;
        Database.update(task);
    }

    public static void setAsNotStarted(int taskId) throws InvalidEntityException {
        Task task =(Task) Database.findByID(taskId);
        task.status =Task.Status.NotStarted;
        Database.update(task);
    }

    public static void setAsInProgress(int taskId) throws InvalidEntityException {
        Task task =(Task) Database.findByID(taskId);
        task.status = Task.Status.InProgress;
        Database.update(task);
    }

    public static void taskCreation (String title, String description, String dueDateStr){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dueDate = dateFormat.parse(dueDateStr);
            Task task = new Task(title, description, dueDate);
            Database.add(task);
            TaskService.setAsNotStarted(task.id);
            /*TaskValidator.validate(task);*/

            System.out.println("Task added successfully!");
            System.out.println("ID: " + task.id);

        }
        catch (ParseException e) {
            System.out.println("Cannot save task.");
            System.out.println("Error: Invalid date format. Please use yyyy-MM-dd.");
        }
        catch (InvalidEntityException e) {
            System.out.println("Cannot save task.");
        }
    }

    public static void getTask(int taskID){
        Task task = (Task) Database.get(taskID);

        System.out.println("Task Details:");
        System.out.println("ID: " + task.id);
        System.out.println("Title: " + task.title);
        System.out.println("Description: " + task.description);
        System.out.println("Due Date: " + task.dueDate);
        System.out.println("Status: " + task.status);
    }

    public static void getTasks () {
        ArrayList<Step> relatedStep = new ArrayList<>();
        ArrayList<Entity> tasks = Database.getAll(Task.TASK_ENTITY_CODE);
        ArrayList<Entity> steps = Database.getAll(Step.STEP_ENTITY_CODE);

        if (tasks.isEmpty()) {
            throw new EntityNotFoundException("No entities found with entityCode=" + Task.TASK_ENTITY_CODE);
        }

        for (Entity task : tasks) {
            Task task1 = (Task) task;
            System.out.println("ID: " + task1.id);
            System.out.println("Title: " + task1.title);
            System.out.println("Due Date: " + task1.dueDate);
            System.out.println("Status: " + task1.status);
            System.out.println();

            for (Entity step : steps) {
                Step step1 = (Step) step;

                if (step1.taskRef == task1.id)
                    relatedStep.add(step1);
            }

            if (!(relatedStep.isEmpty())) {
                System.out.println("Steps:");

                for (Step step : relatedStep) {
                    System.out.println("    + " + step.title + ":");
                    System.out.println("         ID: " + step.id);
                    System.out.println("         Status: " + step.status);

                }
            }
        }
    }

    public static void getICompletedTasks () {
        ArrayList<Step> relatedStep = new ArrayList<>();
        ArrayList<Task> inCompleted = new ArrayList<>();
        ArrayList<Entity> steps = Database.getAll(Step.STEP_ENTITY_CODE);
        ArrayList<Entity> tasks = Database.getAll(Task.TASK_ENTITY_CODE);

        for (Entity task : tasks) {
            Task task1 = (Task) task;
            if (task1.status != Task.Status.Completed)
                inCompleted.add(task1);
        }

        for (Task task : inCompleted) {
            System.out.println("ID: " + task.id);
            System.out.println("Title: " + task.title);
            System.out.println("Due Date: " + task.dueDate);
            System.out.println("Status: " + task.status);
            System.out.println();

            for (Entity step : steps) {
                Step step1 = (Step) step;

                if (step1.taskRef == task.id)
                    relatedStep.add(step1);
            }

            if (!(relatedStep.isEmpty())) {
                System.out.println("Steps:");

                for (Step step : relatedStep) {
                    System.out.println("    + " + step.title + ":");
                    System.out.println("         ID: " + step.id);
                    System.out.println("         Status: " + step.status);

                }
            }
        }
    }

    public static void deleteTask ( int taskID){
        try {
            ArrayList<Entity> steps = Database.getAll(Step.STEP_ENTITY_CODE);
            Task task = (Task) findByID(taskID);

            for (Entity step : steps) {
                Step step1 = (Step) step;

                if (step1.taskRef == task.id)
                    Database.delete(step1.id);
            }

            Database.delete(task.id);

            System.out.println("Task deleted successfully!");
        }catch (EntityNotFoundException e){
            System.out.println("Error: Something happened.");
            throw new EntityNotFoundException(taskID);
        }
    }

    public static void updateTask (int taskID, String field, String newValue){
        try {
            Task task = (Task) Database.get(taskID);

            if (field.equals("1")){

                System.out.println("Task updated successfully!");
                System.out.println("Field: Title");
                System.out.println("Old value: " + task.title );
                task.title = newValue;
                Database.update(task);
                System.out.println("New value: " + newValue);
                System.out.println("Modification Date: " + task.getLastModificationDate());
            }

            if (field.equals("2")){
                ArrayList<Entity> steps = Database.getAll(Step.STEP_ENTITY_CODE);

                System.out.println("Task updated successfully!");
                System.out.println("Field: Status" );
                System.out.println("Old value: " + task.status);

                Task.Status myEnumValue = Task.Status.valueOf(newValue);
                task.status = myEnumValue;

                if (myEnumValue == Task.Status.Completed){
                    for (Entity step : steps) {
                        Step step1 = (Step) step;

                        if (step1.taskRef == task.id){
                            step1.status = Step.Status.Completed;
                            Database.update(step1);
                        }

                    }
                }



                Database.update(task);
                System.out.println("New value: " + newValue);
                System.out.println("Modification Date: " + task.getLastModificationDate());
            }

            if (field.equals("3")){
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date dueDate = dateFormat.parse(newValue);

                System.out.println("Task updated successfully!");
                System.out.println("Field: Due date");
                System.out.println("Old value: " + task.dueDate);
                task.dueDate = dueDate;
                Database.update(task);
                System.out.println("New value: " + newValue);
                System.out.println("Modification Date: " + task.getLastModificationDate());
            }

            if (field.equals("4")){

                System.out.println("Task updated successfully!");
                System.out.println("Field: Description");
                System.out.println("Old value: " + task.description);
                task.description = newValue;
                Database.update(task);
                System.out.println("New value: " + newValue);
                System.out.println("Modification Date: " + task.getLastModificationDate());
            }



        } catch (ParseException e) {
            System.out.println("Cannot update task with ID = " + taskID);
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
        } catch (InvalidEntityException e) {
            System.out.println("Cannot update task with ID = " + taskID);
        }

    }



}
