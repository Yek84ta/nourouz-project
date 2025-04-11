package todo.serializer;

import db.Entity;
import db.Serializer;
import todo.entity.Step;

public class StepSerializer implements Serializer {
    @Override
    public String serialize(Entity e) {
        Step step = (Step) e;
        String result = step.id + "|" + step.title + "|" + step.status + "|" + step.taskRef;
        return result;
    }

    @Override
    public Entity deserialize(String s) {
        String[] fields = s.split("\\|");
        Step step = new Step(fields[1].trim(), Integer.parseInt(fields[3].trim()));
        step.id = Integer.parseInt(fields[0].trim());
        step.status = Step.Status.valueOf(fields[2].trim());
        return step;
    }
}
