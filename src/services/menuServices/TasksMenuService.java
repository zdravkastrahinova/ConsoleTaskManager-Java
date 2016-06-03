package services.menuServices;

import enums.TaskStatusEnum;
import models.Task;
import repositories.TasksRepository;
import services.AuthenticationService;

import java.util.ArrayList;
import java.util.Scanner;

public class TasksMenuService {
    private static Scanner sc;

    public static void renderTaskMenu() {
        System.out.println();
        System.out.println("Choose option: ");
        System.out.println("1. View your tasks");
        System.out.println("2. View task details");
        System.out.println("3. Add new task");
        System.out.println("4. Update task");
        System.out.println("5. Delete task");
        System.out.println("6. Logout");
        System.out.println("0. Exit");

        sc = new Scanner(System.in);
        int taskOption = Integer.parseInt(sc.nextLine());

        switch (taskOption) {
            case 1:
                renderTaskListing();
                renderTaskMenu();
                break;

            case 2:
                renderTaskListing();
                renderTaskDetails();
                renderTaskMenu();
                break;

            case 3:
                renderTaskInserting();
                renderTaskMenu();
                break;

            case 4:
                renderTaskListing();
                renderTaskUpdating();
                renderTaskMenu();
                break;

            case 5:
                renderTaskListing();
                renderTaskDeleting();
                renderTaskMenu();
                break;

            case 6:
                UsersMenuService.renderUserLogout();
                break;

            case 0:
                System.exit(0);
                break;
        }
    }

    private static void renderTaskListing() {
        TasksRepository tasksRepo = new TasksRepository("tasks.txt", Task.class);
        ArrayList<Task> tasks = tasksRepo.getByUserId(AuthenticationService.getLoggedUser().getId());

        if (tasks.size() == 0) {
            System.out.println("No tasks to show.");
            System.out.println("------------------------------");
            return;
        }

        for (Task task : tasks) {
            System.out.println(task.getId());
            System.out.println(task.getTitle());
            System.out.println(task.getContent());
            System.out.println(task.getTaskStatus());
            System.out.println("------------------------------");
        }
    }

    private static void renderTaskDetails() {
        System.out.print("Enter task number which details you want to see: ");
        int taskId = Integer.parseInt(sc.nextLine());

        TasksRepository tasksRepo = new TasksRepository("tasks.txt", Task.class);
        Task task = tasksRepo.getById(taskId);

        if (task == null || task.getUserId() != AuthenticationService.getLoggedUser().getId()) {
            System.out.println("Task does not exists.");
            System.out.println("------------------------------");
            return;
        }

        System.out.println(task.getId());
        System.out.println(task.getTitle());
        System.out.println(task.getContent());
        System.out.println(task.getTaskStatus());
        System.out.println("------------------------------");
    }

    private static void renderTaskInserting() {
        TasksRepository tasksRepo = new TasksRepository("tasks.txt", Task.class);
        Task task = new Task();

        sc = new Scanner(System.in);
        System.out.print("Enter title: ");
        task.setTitle(sc.nextLine());

        System.out.print("Enter content: ");
        task.setContent(sc.nextLine());

        task.setUserId(AuthenticationService.getLoggedUser().getId());
        task.setTaskStatus(TaskStatusEnum.UnDone);

        tasksRepo.insert(task);

        System.out.println("Successfully added");
        System.out.println("------------------------------");
    }

    private static void renderTaskUpdating() {
        System.out.println("Set task number you want to edit: ");
        int taskId = Integer.parseInt(sc.nextLine());

        TasksRepository tasksRepo = new TasksRepository("tasks.txt", Task.class);
        Task updatedTask = tasksRepo.getById(taskId);

        if (updatedTask == null) {
            System.out.println("Task does not exists.");
            System.out.println("------------------------------");
            return;
        }

        System.out.println(updatedTask.getTitle());
        System.out.println(updatedTask.getContent());
        System.out.println(updatedTask.getTaskStatus());
        System.out.println("-------------------------");
        System.out.println();

        System.out.println("Choose what you want to update: ");
        System.out.println("1. Title");
        System.out.println("2. Content");
        System.out.println("3. Status");

        sc = new Scanner(System.in);
        int updatingOption = Integer.parseInt(sc.nextLine());

        switch (updatingOption) {
            case 1:
                System.out.print("Enter title: ");
                updatedTask.setTitle(sc.nextLine());
                break;

            case 2:
                System.out.print("Enter content: ");
                updatedTask.setContent(sc.nextLine());
                break;

            case 3:
                System.out.println("Choose status: ");
                System.out.println("1. UnDone");
                System.out.println("2. InProgress");
                System.out.println("3. Done");

                int status = Integer.parseInt(sc.nextLine());
                switch (status) {
                    case 1: updatedTask.setTaskStatus(TaskStatusEnum.UnDone); break;
                    case 2: updatedTask.setTaskStatus(TaskStatusEnum.InProgress); break;
                    case 3: updatedTask.setTaskStatus(TaskStatusEnum.Done); break;
                }
                break;
        }

        tasksRepo.update(updatedTask);

        System.out.println("Successfully updated");
        System.out.println("------------------------------");
    }

    private static void renderTaskDeleting() {
        System.out.println("Set task number you want to delete: ");
        int taskId = Integer.parseInt(sc.nextLine());

        TasksRepository tasksRepo = new TasksRepository("tasks.txt", Task.class);
        Task deletedTask = tasksRepo.getById(taskId);

        if (deletedTask == null) {
            System.out.println("Task does not exists.");
            System.out.println("------------------------------");
            return;
        }

        tasksRepo.delete(deletedTask);

        System.out.println("Successfully deleted");
        System.out.println("------------------------------");
    }
}