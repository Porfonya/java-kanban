package tasks;

import enumtype.Status;
import enumtype.TaskType;
import java.time.LocalDateTime;


public class Subtask extends Task {

    protected final int epicId;

    public Subtask(String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
        this.taskType = TaskType.SUBTASK;
    }

    public Subtask(int id, String name, String description, Status status, TaskType typeTask, int epicId) {
        super(id, name, description, status, typeTask);
        this.epicId = epicId;
    }

    public Subtask(int id, String name, String description, Status status, TaskType taskType, long duration, LocalDateTime startTime, int epicId) {
        super(id, name, description, status, taskType, startTime, duration);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }


    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getClass().getName());
        stringBuilder.append("{id = ");
        stringBuilder.append(getId());
        stringBuilder.append(", name = '");
        stringBuilder.append(getName());
        stringBuilder.append("', description = '");
        stringBuilder.append(getDescription());
        stringBuilder.append("', status = '");
        stringBuilder.append(getStatus());
        stringBuilder.append("', taskType = '");
        stringBuilder.append(getTaskType());
        stringBuilder.append("', startTime = '");
        stringBuilder.append(startTime);
        stringBuilder.append("', duration = '");
        stringBuilder.append(getDuration());
        stringBuilder.append("', epicId= '");
        stringBuilder.append(epicId);
        stringBuilder.append("'}");
        return stringBuilder.toString();
    }
}
