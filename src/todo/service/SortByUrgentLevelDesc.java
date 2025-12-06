package todo.service;

import todo.interfaces.SortStrategy;
import todo.model.Task;

public class SortByUrgentLevelDesc implements SortStrategy {
    @Override
    public int compare(Task t1, Task t2) {
        return Integer.compare(t2.getUrgentLevel(), t1.getUrgentLevel());
    }
}
