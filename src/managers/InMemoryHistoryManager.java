package managers;

import tasks.Task;

import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {


    private final LinkedList<Task> history = new LinkedList<>();

    @Override
    public void add(Task task) {

        int MAX_HISTORY_LENGTH = 10;
        if (history.size() > 0 && history.size() > MAX_HISTORY_LENGTH) {
            history.removeFirst();
        }
        history.add(task);

    }

    @Override
    public LinkedList<Task> getHistory() {
        return history;
    }
}
