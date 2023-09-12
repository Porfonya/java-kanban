package managers;

import enumtype.Status;
import enumtype.TaskType;
import exceprions.ManagerSaveException;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.*;
import java.util.*;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTasksManager(File file) {

        this.file = file;
    }

    @Override
    public Map<Integer, Task> getTasks() {
        return super.getTasks();

    }

    @Override
    public Map<Integer, Subtask> getSubtasks() {

        return super.getSubtasks();
    }

    @Override
    public Map<Integer, Epic> getEpics() {

        return super.getEpics();
    }

    @Override
    public Task getTask(int taskId) {
        save();
        return super.getTask(taskId);
    }

    @Override
    public Epic getEpic(int epicId) {
        save();
        return super.getEpic(epicId);
    }

    @Override
    public Subtask getSubtask(int subtaskId) {
        save();
        return super.getSubtask(subtaskId);
    }

    @Override
    public void clearAllTask() {
        super.clearAllTask();
        save();
    }

    @Override
    public void clearAllEpic() {
        super.clearAllEpic();
        save();
    }

    @Override
    public void clearAllSubtask() {
        super.clearAllSubtask();
        save();
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void addSubtask(Subtask subtask) {
        super.addSubtask(subtask);
        save();
    }

    @Override
    public void removeByIdTask(int id) {
        super.removeByIdTask(id);
        save();
    }

    @Override
    public void removeByIdSubtask(int id) {
        super.removeByIdSubtask(id);
        save();
    }

    @Override
    public void removeByIdEpic(int id) {
        super.removeByIdEpic(id);
        save();
    }

    @Override
    public List<Subtask> getListByEpic(int epicId) {
        save();
        return super.getListByEpic(epicId);

    }

    @Override
    public void updateTask(Task task) {
        save();
        super.updateTask(task);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        save();
        super.updateSubtask(subtask);
    }

    @Override
    public void updateEpic(Epic epic) {
        save();
        super.updateEpic(epic);
    }

    @Override
    public Status getEpicStatus(int epicId) {
        save();
        return super.getEpicStatus(epicId);
    }

    @Override
    public List<Task> getHistory() {
        save();
        return super.getHistory();
    }


    String toString(Task task) {
        if (task instanceof Subtask) {
            return String.format("%d, %-7s, %-25s, %-10s, %-25s, %-5s \n", task.getId(), task.getTaskType(), task.getName(), task.getStatus(),
                    task.getDescription(), ((Subtask) task).getEpicId());
        } else {
            return String.format("%d, %-7s, %-25s, %-10s, %-25s \n", task.getId(), task.getTaskType(), task.getName(), task.getStatus(),
                    task.getDescription());
        }

    }


    public void save() {

        try (
                Writer fileWriter = new FileWriter(file)) {
            fileWriter.write("id, type,       name,               status,       description,               epic");
            fileWriter.write('\n');

            for (Task task : getTasks().values()) {
                fileWriter.write(toString(task));
            }
            for (Epic epic :
                    getEpics().values()) {
                fileWriter.write(toString(epic));
            }
            for (Subtask subtask :
                    getSubtasks().values()) {
                fileWriter.write(toString(subtask));
            }
            fileWriter.write("\n");
            for (Task task : super.getHistory()) {
                fileWriter.write(task.getId() + ",");
            }

        } catch (IOException exp) {
            throw new ManagerSaveException("Произошла ошибка при сохранении в файл");
        }
    }

    private static List<Integer> historyFromString(String value) {
        List<Integer> historyTaskId = new ArrayList<>();
        if (value != null) {
            String[] valueOuts = value.split(",");
            for (String valueOut : valueOuts) {
                historyTaskId .add(Integer.valueOf(valueOut));
            }
        }
        return historyTaskId ;
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager manager = new FileBackedTasksManager(file);
        try {
            Reader fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);
            br.readLine();
            while (br.ready()) {
                String line = br.readLine();
                ;
                if (line.isEmpty()) {
                    break;
                }
                fromString(line);
            }
            while (br.ready()) {
                String line = br.readLine();
                if (!line.isEmpty()) {
                    historyFromString(line);
                }
            }

        } catch (Exception exp) {
            throw new ManagerSaveException("Произошла ошибка при загрузке данных из файла");
        }
        return manager;
    }

    private static Task fromString(String value) {
        Task task;

        String[] valueOut = value.split(",");
        int id = Integer.parseInt(valueOut[0].trim());
        TaskType type = TaskType.valueOf(valueOut[1].trim());
        String name = valueOut[2].trim();

        Status status = Status.valueOf(valueOut[3].trim());
        String description = valueOut[4].trim();
        if (type.equals(TaskType.SUBTASK)) {
            int epic = Integer.parseInt(valueOut[5].trim());
            task = new Subtask(id, name, description, status, type, epic);
        } else if (type.equals(TaskType.EPIC)){
            task = new Epic(id, name, description, status, type);
        } else{
            task = new Task(id, name, description, status, type);
        }
        task.setId(id);

        return task;
    }


    public static void main(String[] args) {


        System.out.println("Новый таскменеджер");
        File file = new File("./resources", "FileBackupTasks.csv");

        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        //  fileBackedTasksManager.fromString("Переезд Новая квартира");
        fileBackedTasksManager.addTask(new Task("Работа", "Заработать 1000 рублей"));
        fileBackedTasksManager.addTask(new Task("Испечь пироги", "Приготовить пироги из капусты"));
        fileBackedTasksManager.addEpic(new Epic("Подъем", "Поднять все вещи"));
        fileBackedTasksManager.addEpic(new Epic("Переезд", "Перевезти все вещи в новую квартиру"));
        fileBackedTasksManager.addSubtask(new Subtask("Собрать вещи", "аккурасно сложить в чемодан", 3));
        fileBackedTasksManager.addSubtask(new Subtask("Чемодан", "вынести в коридор", 3));
        fileBackedTasksManager.addSubtask(new Subtask("Лифт", "вызвать лифт", 3));
        System.out.println(fileBackedTasksManager.getTask(1));
        System.out.println(fileBackedTasksManager.getEpic(4));
        System.out.println(fileBackedTasksManager.getSubtask(5));
        System.out.println(fileBackedTasksManager.getTask(1));
        System.out.println(fileBackedTasksManager.getEpic(3));
        System.out.println(fileBackedTasksManager.getSubtask(7));
        System.out.println(fileBackedTasksManager.getTask(1));
        System.out.println(fileBackedTasksManager.getEpic(4));
        System.out.println(fileBackedTasksManager.getSubtask(6));
        System.out.println(fileBackedTasksManager.getTask(2));

        fileBackedTasksManager.getTask(1);

        System.out.println(fileBackedTasksManager.getSubtask(3));



    }
}
