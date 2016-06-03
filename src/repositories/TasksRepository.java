package repositories;

import enums.TaskStatusEnum;
import models.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class TasksRepository extends BaseRepository<Task> {
    public TasksRepository(String filePath, Class<Task> taskClass) {
        super(filePath, taskClass);
    }

    protected void readItem(BufferedReader br, Task item) {
        try {
            item.setUserId(Integer.parseInt(br.readLine()));
            item.setTitle(br.readLine());
            item.setContent(br.readLine());
            item.setTaskStatus(TaskStatusEnum.valueOf(br.readLine()));
        } catch (IOException ioe) {
            ioe.getStackTrace();
        }
    }

    protected void writeItem(PrintWriter pw, Task item) {
        pw.println(item.getId());
        pw.println(item.getUserId());
        pw.println(item.getTitle());
        pw.println(item.getContent());
        pw.println(item.getTaskStatus().toString());
    }

    public ArrayList<Task> getByUserId(int userId) {
        return this.getAll().stream().filter(t -> t.getUserId() == userId).collect(Collectors.toCollection(ArrayList<Task>::new));
    }
}
