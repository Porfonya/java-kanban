
import enumtype.Status;
import enumtype.TaskType;
import exceprions.ManagerSaveException;
import managers.TaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public abstract class TaskManagerTest<T extends TaskManager> {
    T taskManager;
    Epic epic;
    Subtask subtask;

    public TaskManagerTest(T taskManager) {
        this.taskManager = taskManager;
    }

    @BeforeEach
    void beforeEach(TestInfo info) {
        if (info.getDisplayName().equals("testAddSubtask()")) {
            return;
        }
        epic = new Epic("Test addNewEpic", "Test addNewEPic description");
        subtask = new Subtask(3, "Test addNewSabtask", "Test addNewSubtaskDescription",
                Status.NEW, TaskType.SUBTASK, 1);
    }

    @Test
    void testAddTask() throws ManagerSaveException {


        Assertions.assertEquals(0, taskManager.getTasks().size(), "Список задач не пуст");


        Task task = new Task("Test addNewTask", "Test addNewTask description");
        Task task2 = new Task("Test addNewTask2", "Test addNewTask description2");
        task.setDuration(10);
        task.setStartTime(LocalDateTime.now());

        task2.setDuration(10);
        task2.setStartTime(LocalDateTime.now().plusMinutes(5));
        Assertions.assertEquals(0, task.getId(), "Id задачи некорректен");

        taskManager.addTask(task);
        taskManager.addTask(task2);
        assertTrue(taskManager.getTasks().containsKey(task.getId()), "Задача не добавилась");
        Assertions.assertEquals(task, taskManager.getTask(task.getId()), "Задача по id не получена");
        Assertions.assertEquals(2, taskManager.getTasks().size(), "Список задач пустой");
        assertTrue(taskManager.getTasks().containsKey(task.getId()), "Задача не добавилась");


    }

    @Test
    void testAddEpic() {
        Epic epic1 = new Epic("EpicOne", "Поднять все вещи еще раз");

        Epic epic2 = new Epic("Подъем", "Поднять все вещи");
        Assertions.assertEquals(0, epic1.getId(), "Id епика некорректен");
        taskManager.addEpic(epic);
        assertTrue(taskManager.getEpics().containsKey(epic.getId()), "Задача не добавилась");
        Assertions.assertEquals(epic, taskManager.getEpic(epic.getId()), "Эпик по id не получен");
        Assertions.assertEquals(1, taskManager.getEpics().size(), "Список эпиков не пустой");

    }

    @Test
    void testAddSubtask() {


        Epic epic = new Epic(2, "EpicOne", "Поднять все вещи еще раз", Status.NEW, TaskType.EPIC);
        Subtask subtask1 = new Subtask("222", "22222", 2);
        Subtask subtask2 = new Subtask("333", "33333", 2);
        Subtask subtask3 = new Subtask("444", "44444", 2);

        assertEquals(0, taskManager.getSubtasks().size(), "Без Эпика подзадача " +
                "не должна создаваться");

        taskManager.addEpic(epic);
        subtask1.setDuration(10);
        subtask1.setStartTime(LocalDateTime.now().plusMinutes(50));

        subtask2.setDuration(10);
        subtask2.setStartTime(LocalDateTime.now().plusMinutes(51));

        subtask3.setDuration(10);
        subtask3.setStartTime(LocalDateTime.now().plusMinutes(71));
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);
        taskManager.addSubtask(subtask3);
        assertNotEquals(0, subtask1.getId(), "Id подзадачи некорректен.");


        assertTrue(taskManager.getTasks().containsKey(subtask.getId()), "Задача не добавилась");

        assertFalse(taskManager.getListByEpic(epic.getId()).contains(subtask2), "Пересекаемая подзадача добавилась.");
    }


    @Test
    void testClearAllTask() {
        taskManager.clearAllTask();
        Assertions.assertEquals(0, taskManager.getTasks().size(), "Список задач не пустой");
        taskManager.clearAllTask();
    }

    @Test
    void testClearAllEpic() {
        taskManager.clearAllEpic();
        Assertions.assertEquals(0, taskManager.getEpics().size(), "Список еппиков не пустой");
        taskManager.clearAllEpic();
    }

    @Test
    void testClearAllSubtask() {
        taskManager.clearAllSubtask();
        assertEquals(0, taskManager.getSubtasks().size(), "Подзадача не удалена");
        taskManager.clearAllSubtask();
    }

    @Test
    void testRemoveByIdTask() {
        Task task = new Task("Test addNewTask", "Test addNewTask description");
        assertEquals(0, taskManager.getTasks().size(), "Список задач не пустой");
        taskManager.addTask(task);
        assertEquals(1, taskManager.getTasks().size(), "Список задач пуст");
        taskManager.removeByIdTask(task.getId());
        assertEquals(0, taskManager.getTasks().size(), "Список задач не пуст после удаления задачи");

    }

    @Test
    void testRemoveByIdSubtask() {

        taskManager.removeByIdSubtask(subtask.getId());
        assertEquals(0, taskManager.getSubtasks().size(), "Список подзадач не пуст после удаления ");
    }

    @Test
    void testRemoveByIdEpic() {

        taskManager.removeByIdEpic(epic.getId());
        assertEquals(0, taskManager.getEpics().size(), "Список епиков не пуст после удаления ");
    }

    @Test
    void testGetListByEpic() {
    }

    @Test
    void testUpdateTask() {
    }

    @Test
    void testUpdateSubtask() {
    }

    @Test
    void testUpdateEpic() {
    }

    @Test
    void testGetEpicStatus() {
        Status status = Status.NEW;

    }

}