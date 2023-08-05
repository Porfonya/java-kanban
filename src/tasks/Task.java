package tasks;

import enumtype.Status;
import enumtype.TaskType;

public class Task {
    private int id;

    private final String name;
    private final String description;

    public Status status;
    public TaskType taskType;

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

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getClass().getName());
        stringBuilder.append("{id = ");
        stringBuilder.append(id);
        stringBuilder.append(", name = '");
        stringBuilder.append(name);
        stringBuilder.append("', description = '");
        stringBuilder.append(description);
        stringBuilder.append("', status = '");
        stringBuilder.append(status);
        stringBuilder.append("', typeTask = '");
        stringBuilder.append(taskType);
        stringBuilder.append("'}");

        return stringBuilder.toString();
    }
}
