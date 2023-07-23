
public class Subtask extends Task {

    private int epicId;

    public Subtask(String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
        this.typeTask = TypeTask.SUBTASK;
    }

    public Subtask(int id, String name, String description, Status status, TypeTask typeTask, int epicId) {
        super(id, name, description, status, typeTask);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }


    @Override
    public String toString() {
        return (super.toString().substring((this.getClass().getSuperclass().getName().length() - 4
        ), (super.toString().length()) - 1) + ", epicId= " + epicId + "'}");
    }
}
