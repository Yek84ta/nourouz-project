package todo.validator;

import db.Entity;
import db.Validator;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;
import static db.Database.entities;


public class StepValidator implements Validator {

    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if (!(entity instanceof Step))
            throw new IllegalArgumentException("Error: Cannot find task with " + entity.id);

        Step step = (Step) entity ;

        if (step.title == null || step.title.trim().isEmpty())
            throw new InvalidEntityException("Error: What you mentioned is not a valid title.");

        for (Entity entity1 : entities){
            if (entity1.id == step.taskRef)
                return;
        }

        throw new InvalidEntityException("Error: The step that you mentioned doesn't exist.");

    }
}
