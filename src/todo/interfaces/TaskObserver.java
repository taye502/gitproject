package todo.interfaces;

import todo.model.Task;

public interface TaskObserver {
    void onTaskUpdated(Task task);
}
