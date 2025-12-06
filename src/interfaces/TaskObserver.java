package interfaces;

import model.Task;

public interface TaskObserver {
    void onTaskUpdated(Task task);
}
