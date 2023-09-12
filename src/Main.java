import enumtype.Status;
import enumtype.TaskType;
import managers.*;
import org.w3c.dom.Node;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

public class Main {

    public static void main(String[] args) {

        InMemoryTaskManager memoryTaskManager = (InMemoryTaskManager) Managers.getDefault();

        memoryTaskManager.addTask(new Task("Работа", "Заработать 1000 рублей"));
        memoryTaskManager.addTask(new Task("Испечь пироги", "Приготовить пироги из капусты"));
        memoryTaskManager.addEpic(new Epic("Подъем", "Поднять все вещи"));
        memoryTaskManager.addEpic(new Epic("Переезд", "Перевезти все вещи в новую квартиру"));
        memoryTaskManager.addSubtask(new Subtask("Собрать вещи", "аккурасно сложить в чемодан", 3));
        memoryTaskManager.addSubtask(new Subtask("Чемодан", "вынести в коридор", 3));
        memoryTaskManager.addSubtask(new Subtask("Лифт", "вызвать лифт", 3));

        System.out.println("\n");

        System.out.println("Получение по идентификатору.\n ");

        System.out.println(memoryTaskManager.getTask(1));
        System.out.println(memoryTaskManager.getEpic(4));
        System.out.println(memoryTaskManager.getSubtask(5));
        System.out.println(memoryTaskManager.getTask(1));
        System.out.println(memoryTaskManager.getEpic(3));
        System.out.println(memoryTaskManager.getSubtask(7));
        System.out.println(memoryTaskManager.getTask(1));
        System.out.println(memoryTaskManager.getEpic(4));
        System.out.println(memoryTaskManager.getSubtask(6));
        System.out.println(memoryTaskManager.getTask(2));



        System.out.println("История - 1");
        for (Task task : memoryTaskManager.getHistory()) {
            System.out.println(" History " + task);
        }
        System.out.println("Id subtask for Epic");
        System.out.println(memoryTaskManager.getEpic(3).getSubtasksIdForEpic());
        System.out.println("------------------");
        System.out.println("\n");
        System.out.println("Удаление по идентификатору.\n");
        memoryTaskManager.removeByIdTask(1);
        System.out.println("История - 2");
        for (Task task : memoryTaskManager.getHistory()) {
            System.out.println(" History "  + task);
        }
        System.out.println("------------------");
        memoryTaskManager.removeByIdEpic(3);
        System.out.println("История - 3");
        for (Task task : memoryTaskManager.getHistory()) {
            System.out.println(" History "  + task);
        }


    }
}