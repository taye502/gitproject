package todo.service;

import todo.model.ImportantTask;
import todo.model.Task;

public class ImportantTaskFactory extends TaskFactory {
    @Override
    public Task createTask(int id, String title, String desc) {
        // urgentLevelを5と仮定 (利用者はあとで setUrgentLevel 変更可)
        return new ImportantTask(id, title, desc, 5);
    }
}

