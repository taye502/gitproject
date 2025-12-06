package interfaces;

import model.Task;

public interface SortStrategy {
    int compare(Task t1, Task t2);
}
