package tasks;

import enumtype.TaskType;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Integer> subtasksIdForEpic = new ArrayList<>();


    public Epic(String name, String description) {
        super(name, description);
        this.taskType = TaskType.EPIC;

    }

    public List<Integer> getSubtasksIdForEpic() {
        return subtasksIdForEpic;
    }
}
