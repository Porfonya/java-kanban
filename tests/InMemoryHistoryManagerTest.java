
import managers.InMemoryHistoryManager;
import managers.InMemoryTaskManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class InMemoryHistoryManagerTest {
   private InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

    @BeforeEach
    public void beforeEach(TestInfo info) {

        if(info.getDisplayName().equals("shouldBeEmpty()")){
            return;
        }
        historyManager = new InMemoryHistoryManager();
        Task task2 = new Task("newTask2", "NewDescription2");
        Task task3 = new Task("newTask3", "NewDescription3");
        Task task4 = new Task("newTask4", "NewDescription4");

    }

    @Test
    public void shouldBeEmpty() {
        Task task = new Task("newTask", "NewDescription");
        historyManager.add(task);
        assertEquals(1, historyManager.getHistory().size(), "История пуста");
        historyManager.remove(task.getId());
        assertTrue(historyManager.getHistory().isEmpty(),"История не пуста");
    }

    @Test
    public void duplication() {

    }

    @Test
    public void deleteFirstHistoryTask() {
    }

    @Test
    public void deleteLastHistoryTask()  {

    }

    @Test
    public void deleteMiddleHistoryTask()  {

    }

}