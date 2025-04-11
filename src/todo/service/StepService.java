package todo.service;

import db.Database;
import db.Entity;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;
import todo.validator.StepValidator;

import java.util.ArrayList;

import static db.Database.entities;


public class StepService {
    public static void setAsCompleted(int taskRef) {
        ArrayList<Entity> steps = Database.getAll(Step.STEP_ENTITY_CODE);

        for (Entity stepEXM : steps){
            Step step = (Step) stepEXM;
            if (step.taskRef == taskRef)
                step.status = Step.Status.Completed;
        }

    }

    public static void setAsNotStarted(int taskRef) {
        ArrayList<Entity> steps = Database.getAll(Step.STEP_ENTITY_CODE);

        for (Entity stepEXM : steps){
            Step step = (Step) stepEXM;
            if (step.taskRef == taskRef)
                step.status = Step.Status.NotStarted;
        }

    }

    public static void stepCreation(String title, int taskRef ) throws InvalidEntityException {
        try {
            Step step = new Step(title, taskRef);
            Database.add(step);
            setAsNotStarted(taskRef);

            /*StepValidator.validate(step);*/

            System.out.println("Step added successfully!");
            System.out.println("ID:" + step.id);

        } catch (InvalidEntityException e) {
            System.out.println("Cannot save step.");
            throw new InvalidEntityException("The entity that you mentioned is not valid.");
        }


    }

    public static void deleteStep ( int stepID){
        try {
            Step step = (Step) Database.findByID(stepID);
            Database.delete(step.id);
            System.out.println("Step deleted successfully!");
        }catch (EntityNotFoundException e){
            System.out.println("Error: Something happened.");
            throw new EntityNotFoundException(stepID);
        }
    }

    public static void updateStep (int stepID, String field, String newValue){
        try {
            Step step = (Step) Database.get(stepID);

            if (field.equals("1")){

                System.out.println("Step updated successfully!");
                System.out.println("Field: " + field);
                System.out.println("Old value: " + step.title);
                step.title = newValue;
                Database.update(step);
                System.out.println("New value: " + newValue);
            }

            if (field.equals("2")){
                ArrayList<Entity> steps = Database.getAll(Step.STEP_ENTITY_CODE);
                ArrayList<Entity> relatedSteps = new ArrayList<>();
                Task task = (Task) Database.findByID(step.taskRef);
                int flag = 0;

                System.out.println("Step updated successfully!");
                System.out.println("Field: " + field);
                System.out.println("Old value: " + step.status);

                for (Entity stepEXM : steps){
                    Step exmStep = (Step) stepEXM;
                    if (exmStep.taskRef == task.id)
                        relatedSteps.add(exmStep);
                }

                Step.Status myEnumValue = Step.Status.valueOf(newValue);
                step.status = myEnumValue;

                if (task.status == Task.Status.NotStarted)
                    if (step.status == Step.Status.Completed){
                        TaskService.setAsInProgress(task.id);
                        Database.update(task);
                    }

                for (Entity stepEXM : relatedSteps)
                {
                    Step exmStep = (Step) stepEXM;
                  if (exmStep.status != Step.Status.Completed){
                      flag = 1;
                  }
                }

                if (flag == 0){
                    TaskService.setAsCompleted(task.id);
                    Database.update(task);
                }



                Database.update(step);
                System.out.println("New value: " + newValue);

            }
        } catch (InvalidEntityException e) {
            System.out.println("Cannot update step with ID = " + stepID);
        }

    }


}
