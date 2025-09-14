import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TaskManager implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Task> tasks = new ArrayList<>();

    public void addTask(Task t) { tasks.add(t); }

    public boolean removeTask(int id) {
        return tasks.removeIf(t -> t.getId() == id);
    }

    public Task findById(int id) {
        for (Task t : tasks) if (t.getId() == id) return t;
        return null;
    }

    public List<Task> listAll() {
        return new ArrayList<>(tasks);
    }

    public boolean markDone(int id) {
        Task t = findById(id);
        if (t != null) { t.markDone(); return true; }
        return false;
    }

    // Save whole TaskManager to file (serializacja obiektowa)
    public void saveToFile(String path) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(path)))) {
            oos.writeObject(this);
        }
    }

    // Load TaskManager from file
    public static TaskManager loadFromFile(String path) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(path)))) {
            TaskManager tm = (TaskManager) ois.readObject();
            // Zaktualizuj nextId tak, aby nowe ID były unikalne
            int max = 0;
            for (Task t : tm.tasks) if (t.getId() > max) max = t.getId();
            Task.setNextId(max + 1);
            return tm;
        }
    }
}
