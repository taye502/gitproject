package todo.interfaces;

import todo.model.Task;

public interface SortStrategy {
    int compare(Task t1, Task t2);
}
