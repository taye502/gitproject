package todo.service;

import todo.interfaces.SortStrategy;
import todo.model.Task;

public class SortByIdAsc implements SortStrategy {
    @Override
    public int compare(Task t1, Task t2) {
        return Integer.compare(t1.getId(), t2.getId());
    }
}
