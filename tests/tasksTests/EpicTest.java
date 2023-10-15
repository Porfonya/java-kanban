package tasksTests;

import enumtype.Status;
import enumtype.TaskType;
import exceprions.ManagerSaveException;
import managers.InMemoryTaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import tasks.Epic;
import tasks.Subtask;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
    Epic epic;

    @BeforeEach
    public void beforeEach(TestInfo info) throws InterruptedException {
        epic = new Epic("Test addNewEpic1", "Test addNewEPic description1");
        inMemoryTaskManager.addEpic(epic);
        if (info.getDisplayName().equals("shouldBeEmpty()")) {

            return;
        }

        epic = new Epic(1, "Test addNewEpic1", "Test addNewEPic description1", Status.NEW, TaskType.EPIC);
        inMemoryTaskManager.addEpic(epic);
        inMemoryTaskManager.addSubtask(new Subtask(2, "Test addNewSabtask1", "Test addNewSubtaskDescription1",
                Status.NEW, TaskType.SUBTASK, 1));
        inMemoryTaskManager.addSubtask(new Subtask(3, "Test addNewSabtask2", "Test addNewSubtaskDescription2",
                Status.NEW, TaskType.SUBTASK, 1));

    }

    @Test
    public void shouldBeEmpty() throws ManagerSaveException {
        assertTrue(inMemoryTaskManager.getListByEpic(epic.getId()).isEmpty());
    }

    @Test
    public void allSubtasksWithStatusNew() throws ManagerSaveException {
        for (Subtask subtask : inMemoryTaskManager.getListByEpic(1)) {
            Assertions.assertEquals(Status.NEW, subtask.getStatus(), "Не все подзадачи со статусом NEW");
        }
    }

    @Test
    public void allSubtasksWithStatusDone() throws ManagerSaveException {
        for (Subtask subtask : inMemoryTaskManager.getListByEpic(epic.getId())) {
            subtask.setStatus(Status.DONE);
        }
        for (Subtask subtask : inMemoryTaskManager.getListByEpic(epic.getId())) {
            Assertions.assertEquals(Status.DONE, subtask.getStatus(), "Не все подзадачи в статусе DONE");
        }

    }

    @Test
    public void allSubtasksWithStatusInProgress() {
        for (Subtask subtask : inMemoryTaskManager.getListByEpic(epic.getId())) {
            subtask.setStatus(Status.IN_PROGRESS);
        }
        for (Subtask subtask : inMemoryTaskManager.getListByEpic(epic.getId())) {
            Assertions.assertEquals(Status.IN_PROGRESS, subtask.getStatus(), "Не все подзадачи " +
                    "в статусе IN_PROGRESS");
        }

    }

    @Test
    public void subtasksWithStatusDoneAndNew() {
        Subtask subtask = inMemoryTaskManager.getListByEpic(1).get(0);
        subtask.setStatus(Status.DONE);
        for (Subtask subtaskNew : inMemoryTaskManager.getListByEpic(epic.getId())) {
            assertTrue(List.of(Status.NEW, Status.DONE).contains(subtaskNew.status));
        }
    }


}