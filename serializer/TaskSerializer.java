package todo.serializer;

import db.Entity;
import db.Serializer;
import todo.entity.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TaskSerializer implements Serializer {
    @Override
    public String serialize(Entity e) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Task task = (Task) e;
        String result = task.id + "|" + task.title + "|" + task.description + "|" +
                dateFormat.format(task.dueDate) + "|" +
                dateFormat.format(task.creationDate) + "|" +
                dateFormat.format(task.lastModificationDate) + "|" +
                task.status;
        return result;
    }

    @Override
    public Entity deserialize(String s) {
        try {
            String[] fields = s.split("\\|");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Task task = new Task(fields[1].trim(), fields[2].trim(), dateFormat.parse(fields[3].trim()));
            task.id = Integer.parseInt(fields[0].trim());
            task.status = Task.Status.valueOf(fields[6].trim());
            task.creationDate = dateFormat.parse(fields[4].trim());
            task.lastModificationDate = dateFormat.parse(fields[5].trim());

            return task;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
