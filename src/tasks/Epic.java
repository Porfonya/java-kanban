package tasks;

import enumtype.Status;
import enumtype.TaskType;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Integer> subtasksIdForEpic = new ArrayList<>();


    public Epic(String name, String description) {
        super(name, description);
        this.taskType = TaskType.EPIC;

    }

    public Epic(int id, String name, String description, Status status, TaskType type) {
        super(id, name, description,status,type);
    }

    public List<Integer> getSubtasksIdForEpic() {
        return subtasksIdForEpic;
    }
}
