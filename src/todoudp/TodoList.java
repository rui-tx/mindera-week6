package todoudp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class TodoList {

    private String name;
    private PriorityQueue<TodoItem> list;

    public TodoList(String name) {
        this.name = name;
        this.list = new PriorityQueue<>();
    }

    public int addTask(String taskName) {
        TodoItem newTodo = new TodoItem(taskName, "", false, 0);
        this.list.offer(newTodo);

        return newTodo.getId();
    }

    public int addTask(String taskName, String taskDescription, boolean completed, int priority) {
        TodoItem newTodo = new TodoItem(taskName, taskDescription, completed, priority);
        this.list.offer(newTodo);

        return newTodo.getId();
    }

    public void addTask(TodoItem todo) {
        this.list.offer(todo);

        System.out.println("Todo created:");
        todo.print();
        System.out.println();
    }

    public void removeTask(TodoItem todo) {
        if (!this.list.contains(todo)) {
            return;
        }

        this.list.remove(todo);
    }

    public void removeTask(int id) {
        TodoItem todoToDelete = getTodoWithId(id);
        if (todoToDelete == null) {
            throw new NoSuchElementException();
        }

        this.list.remove(todoToDelete);
        //System.out.println("Todo item removed with success!");
    }

    private TodoItem getTodoWithId(int id) {
        Iterator<TodoItem> iterator = this.list.iterator();

        while (iterator.hasNext()) {
            TodoItem currentItem = iterator.next();
            if (currentItem.getId() == id) {
                return currentItem;
            }
        }

        return null;
    }

    public TodoItem getNext() {
        return this.list.peek();
    }

    public void printNext() {
        this.list.peek().print();
    }

    public TodoItem[] getAllTasks() {
        return list.toArray(new TodoItem[0]);
    }

    public String getAllTasksString() {
        Iterator<TodoItem> iterator = this.list.iterator();
        String taskList = "";
        while (iterator.hasNext()) {
            TodoItem currentItem = iterator.next();
            taskList = taskList.concat(currentItem.id + " - " + currentItem.getName() + "\n");
        }

        return taskList;
    }

    public TodoItem[] getIncompleteTasks() {
        Iterator<TodoItem> iterator = this.list.iterator();
        ArrayList<TodoItem> uncompletedTasks = new ArrayList<>();

        while (iterator.hasNext()) {
            TodoItem currentItem = iterator.next();
            if (!currentItem.completed) {
                uncompletedTasks.add(currentItem);
            }
        }

        return uncompletedTasks.toArray(new TodoItem[0]);
    }

    public TodoItem[] getCompleteTasks() {
        Iterator<TodoItem> iterator = this.list.iterator();
        ArrayList<TodoItem> completedTasks = new ArrayList<>();

        while (iterator.hasNext()) {
            TodoItem currentItem = iterator.next();
            if (currentItem.completed) {
                completedTasks.add(currentItem);
            }
        }

        return completedTasks.toArray(new TodoItem[0]);
    }

    public void printAllTasks() {
        Iterator<TodoItem> iterator = this.list.iterator();
        while (iterator.hasNext()) {
            iterator.next().print();
            System.out.println();
        }
    }

    public void markTaskComplete(TodoItem todo) {
        if (!this.list.contains(todo)) {
            return;
        }

        Iterator<TodoItem> iterator = this.list.iterator();
        while (iterator.hasNext()) {
            TodoItem currentItem = iterator.next();
            if (currentItem.equals(todo)) {
                currentItem.completed = true;
                return;
            }
        }
    }

    public void markTaskIncomplete(TodoItem todo) {
        if (!this.list.contains(todo)) {
            return;
        }

        Iterator<TodoItem> iterator = this.list.iterator();
        while (iterator.hasNext()) {
            TodoItem currentItem = iterator.next();
            if (currentItem.equals(todo)) {
                currentItem.completed = false;
                return;
            }
        }
    }

    public static class TodoItem implements Comparable<TodoItem> {

        private static int uniqueIdentifier = 1;

        private int id;
        private String name;
        private String description;
        private boolean completed;
        private int priority;

        public TodoItem(String name, String description, boolean completed, int priority) {
            this.id = uniqueIdentifier;
            this.name = name;
            this.description = description;
            this.completed = completed;
            this.priority = priority;

            uniqueIdentifier++;
        }

        public void print() {
            System.out.printf("Task [%d]: %s \n", this.id, this.name);
            System.out.printf("Description: %s \n", this.description);
            System.out.printf("Completed: %s \n", this.completed);
            System.out.printf("Priority: %d \n", this.priority);
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public int compareTo(TodoItem o) {
            return this.priority > o.priority ? -1 : this.priority == o.priority ? 0 : 1;
        }
    }
}
