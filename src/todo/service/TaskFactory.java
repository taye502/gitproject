package todo.service;

import todo.model.Task;

public abstract class TaskFactory {
    public abstract Task createTask(int id, String title, String desc);
}
