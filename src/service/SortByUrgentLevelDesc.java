package service;

import interfaces.SortStrategy;
import model.Task;

public class SortByUrgentLevelDesc implements SortStrategy {
    @Override
    public int compare(Task t1, Task t2) {
        return Integer.compare(t2.getUrgentLevel(), t1.getUrgentLevel());
    }
}
