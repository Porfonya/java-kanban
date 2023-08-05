package tasks;

import enumtype.TaskType;

public class Epic extends Task {

    public Epic(String name, String description) {
        super(name, description);
        this.taskType = TaskType.EPIC;

    }
}
