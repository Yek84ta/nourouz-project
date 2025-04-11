import db.Database;
import db.Entity;
import db.Serializer;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;
import todo.serializer.StepSerializer;
import todo.serializer.TaskSerializer;
import todo.service.StepService;
import todo.service.TaskService;
import todo.validator.StepValidator;
import todo.validator.TaskValidator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InvalidEntityException {
        Scanner scanner = new Scanner(System.in);


        Database.registerValidator(Task.TASK_ENTITY_CODE, new TaskValidator());
        Database.registerValidator(Step.STEP_ENTITY_CODE, new StepValidator());
        Database.registerSerializer(Task.TASK_ENTITY_CODE, new TaskSerializer());
        Database.registerSerializer(Step.STEP_ENTITY_CODE, new StepSerializer());

        Database.load();

        System.out.println("Welcome to the To-Do List program!");
        System.out.println("Here, you can manage your daily tasks and never forget anything.");
        System.out.println("Let's go!");

        while (true) {
            System.out.println("Please select one section (enter the mentioned number):");
            System.out.println("1. Add");
            System.out.println("2. Delete");
            System.out.println("3. Update");
            System.out.println("4. Get");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Add section (type wanted part):");
                    System.out.println("1. Add task");
                    System.out.println("2. Add step");
                    System.out.println("3. Exit");

                    String addChoice = scanner.nextLine();
                    if (addChoice.equals("1")) {
                        System.out.println("Please fill in the required fields:");
                        System.out.println("Title:");
                        String title = scanner.nextLine();
                        System.out.println("Description:");
                        String description = scanner.nextLine();
                        System.out.println("Due date (yyyy-MM-dd):");
                        String dueDateStr = scanner.nextLine();

                        TaskService.taskCreation(title, description, dueDateStr);

                    } else if (addChoice.equals("2")) {
                        System.out.println("Please fill in the required fields:");
                        System.out.println("Task ID:");
                        int taskID = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Title:");
                        String title = scanner.nextLine();

                        StepService.stepCreation(title, taskID);
                    }
                    break;

                case 2:
                    System.out.println("Delete section (type wanted part):");
                    System.out.println("1. Delete task");
                    System.out.println("2. Delete step");
                    System.out.println("3. Exit");

                    String deleteChoice = scanner.nextLine();
                    if (deleteChoice.equals("1")) {
                        System.out.println("Enter Task ID to delete:");
                        int taskID = scanner.nextInt();
                        TaskService.deleteTask(taskID);

                    } else if (deleteChoice.equals("2")) {
                        System.out.println("Enter Step ID to delete:");
                        int stepID = scanner.nextInt();
                        StepService.deleteStep(stepID);
                    }
                    break;

                case 3:
                    System.out.println("Update section (type wanted part):");
                    System.out.println("1. Update task");
                    System.out.println("2. Update step");
                    System.out.println("3. Exit");

                    String updateChoice = scanner.nextLine();
                    if (updateChoice.equals("1")) {
                        System.out.println("Enter task ID to update:");
                        int taskID = scanner.nextInt();
                        scanner.nextLine();

                        System.out.println("Please choose one of the fields below and enter its new value:");
                        System.out.println("1. Title");
                        System.out.println("2. Status");
                        System.out.println("3. Due date");
                        System.out.println("4. Description");
                        String field = scanner.next();
                        String newValue = scanner.next();

                        TaskService.updateTask(taskID, field, newValue);
                    } else if (updateChoice.equals("2")) {
                        System.out.println("Enter step ID to update:");
                        int stepID = scanner.nextInt();
                        scanner.nextLine();

                        System.out.println("Please choose one of the fields below and enter its new value:");
                        System.out.println("1. Title");
                        System.out.println("2. Status");
                        String field = scanner.next();
                        String newValue = scanner.next();

                        StepService.updateStep(stepID, field, newValue);
                    }
                    break;

                case 4:
                    System.out.println("Get section (type wanted part):");
                    System.out.println("1. Get task by ID");
                    System.out.println("2. Get all tasks");
                    System.out.println("3. Get incomplete tasks");
                    System.out.println("4. Exit");

                    String getChoice = scanner.nextLine();
                    if (getChoice.equals("1")) {
                        System.out.println("Enter Task ID:");
                        int taskID = scanner.nextInt();
                        TaskService.getTask(taskID);
                    }
                    else if (getChoice.equals("2")) {
                        TaskService.getTasks();
                    }
                    else if (getChoice.equals("3")) {
                        TaskService.getICompletedTasks();
                    }
                    break;

                case 5:
                    Database.save();
                    System.out.println("Exiting the program...");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}