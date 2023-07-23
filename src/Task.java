public class Task {
    private int id;

    private final String name;
    private final String description;

    Status status;
    TypeTask typeTask;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
        this.typeTask = TypeTask.TASK;

    }

    public Task(int id, String name, String description, Status status, TypeTask typeTask) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.typeTask = typeTask;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        stringBuilder.append(typeTask);
        stringBuilder.append("'}");

        return stringBuilder.toString();
    }
}
