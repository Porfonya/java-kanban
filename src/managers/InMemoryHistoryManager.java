package managers;

import tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Node<Task>> locationHistoryTask;
    private Node<Task> head;
    private Node<Task> tail;

    public InMemoryHistoryManager() {
        locationHistoryTask = new HashMap<>();
    }

    private static class Node<E> {
        public E data;
        public Node<E> next;
        public Node<E> prev;

        public Node(Node<E> prev, E data, Node<E> next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }

    }
    private void linkLast(Task task) {

        if (locationHistoryTask.containsKey(task.getId())) {
            remove(task.getId());
        }
        final Node<Task> oldTail = tail;
        final Node<Task> newNode = new Node<>(oldTail, task, null);
        tail = newNode;
        locationHistoryTask.put(task.getId(), newNode);
        if (oldTail == null)
            head = newNode;
        else
            oldTail.next = newNode;
    }

    private List<Task> getTasks() {
        List<Task> result = new ArrayList<>();
        Node<Task> current = head;
        while (current != null) {
            result.add(current.data);
            current = current.next;
        }
        return result;
    }

    private void removeNode(Node<Task> node) {
        if (node != null) {
            if (head == node) {
                head = node.next;
            }
            if (node.next != null) {
                node.next.prev = node.prev;
            }

            if (node.prev != null) {
                node.prev.next = node.next;
            }
        }
    }

    @Override
    public void add(Task task) {
        linkLast(task);

    }

    @Override
    public void remove(int id) {
        removeNode(locationHistoryTask.get(id));
        locationHistoryTask.remove(id);
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }


}
