package todo.service;

import todo.model.RoutineTask;
import todo.model.Task;

public class RoutineTaskFactory extends TaskFactory {
    @Override
    public Task createTask(int id, String title, String desc) {
        // intervalを7日と仮定など
        return new RoutineTask(id, title, desc, 7);
    }
}



