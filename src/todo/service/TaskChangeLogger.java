package todo.service;

import todo.interfaces.TaskObserver;
import todo.model.Task;

public class TaskChangeLogger implements TaskObserver {
    @Override
    public void onTaskUpdated(Task task) {
        System.out.println("[Logger] タスク更新通知: " + task);
    }
}
