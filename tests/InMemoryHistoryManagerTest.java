
import managers.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import tasks.Task;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;


class InMemoryHistoryManagerTest {
    private final InMemoryTaskManager taskManager = new InMemoryTaskManager();

    @BeforeEach
    public void beforeEach(TestInfo info) {

        if (info.getDisplayName().equals("shouldBeEmpty()")) {
            return;
        }
        Task task1 = new Task("newTask2", "NewDescription2");
        Task task2 = new Task("newTask3", "NewDescription3");
        Task task3 = new Task("newTask4", "NewDescription4");
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        taskManager.getTask(task1.getId());
        taskManager.getTask(task2.getId());
        taskManager.getTask(task3.getId());

    }

    @Test
    public void shouldBeEmpty() {
        Task task = new Task("newTask", "NewDescription");
        taskManager.addTask(task);
        taskManager.getTask(task.getId());
        assertEquals(1, taskManager.getHistory().size(), "История пуста");
        taskManager.removeByIdTask(task.getId());
        assertTrue(taskManager.getHistory().isEmpty(), "История не пуста");
    }

    @Test
    public void duplication() {

        HashMap<Integer, Integer> dubl = new HashMap<>();
        for (Task task : taskManager.getHistory()) {
            dubl.put(task.getId(), dubl.getOrDefault(task.getId(), 0) + 1);
        }
        for (Integer value : dubl.values()) {
            Assertions.assertEquals(1, value);
        }
    }

    @Test
    public void deleteFirstHistoryTask() {

        int historyFirstTask = taskManager.getHistory().get(0).getId();
        taskManager.removeByIdTask(1);
        assertNotEquals(historyFirstTask, taskManager.getHistory().get(0).getId());
    }

    @Test
    public void deleteLastHistoryTask() {

        int historyLastTask = taskManager.getHistory().get(taskManager.getHistory().size() - 1).getId();
        taskManager.removeByIdTask(3);
        assertNotEquals(historyLastTask, taskManager.getHistory().get(taskManager.getHistory().size() - 1).getId());

    }

    @Test
    public void deleteMiddleHistoryTask() {
        int middleValue = Math.round(taskManager.getHistory().size() - 1) / 2;
        int historyMidleTask = taskManager.getHistory().get(middleValue).getId();
        taskManager.removeByIdTask(2);
        assertNotEquals(historyMidleTask, taskManager.getHistory().get(middleValue).getId());
    }

}