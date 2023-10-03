package tasks;

import enumtype.Status;
import enumtype.TaskType;
import java.time.LocalDateTime;

public class Task {
    private int id;

    private final String name;
    private final String description;

    public Status status;
    public TaskType taskType;
    protected long duration;
    protected  LocalDateTime startTime;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
        this.taskType = TaskType.TASK;

    }

    public Task(int id, String name, String description, Status status, TaskType taskType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.taskType = taskType;
    }

    public Task(int id, String name, String description, Status status, TaskType taskType, LocalDateTime startTime, long duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.taskType = taskType;
        this.startTime = startTime;
        this.duration = duration;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public LocalDateTime getEndTime() {
        if (startTime != null){
        return startTime.plusMinutes(duration);
        }
        else {
            return null;
        }
    }

    public LocalDateTime getStartTime() {

       return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getClass().getName());
        stringBuilder.append("id = ");
        stringBuilder.append(id);
        stringBuilder.append(", name = '");
        stringBuilder.append(name);
        stringBuilder.append("', description = '");
        stringBuilder.append(description);
        stringBuilder.append("', status = '");
        stringBuilder.append(status);
        stringBuilder.append("', typeTask = '");
        stringBuilder.append(taskType);
        stringBuilder.append("', startTime = '");
        stringBuilder.append(startTime);
        stringBuilder.append("', duration = '");
        stringBuilder.append(duration);
        stringBuilder.append("', endTime = '");
        stringBuilder.append(getEndTime());


        return stringBuilder.toString();
    }
}
