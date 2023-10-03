package tasks;

import enumtype.Status;
import enumtype.TaskType;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Epic extends Task {
    private final List<Integer> subtasksIdForEpic = new ArrayList<>();


    public Epic(String name, String description) {
        super(name, description);
        this.taskType = TaskType.EPIC;

    }

    public Epic(int id, String name, String description, Status status, TaskType type) {
        super(id, name, description,status,type);
    }

    public Epic(int id, String name, String description, Status status, TaskType taskType, LocalDateTime startTime, long duration) {
        super(id, name, description, status, taskType, startTime, duration);
    }

    public List<Integer> getSubtasksIdForEpic() {
        return subtasksIdForEpic;
    }
}
