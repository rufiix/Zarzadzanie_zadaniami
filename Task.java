import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int nextId = 1;

    public enum Status { TODO, IN_PROGRESS, DONE }
    public enum Priority { LOW, MEDIUM, HIGH }

    private final int id;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private LocalDate dueDate;
    private final LocalDate createdAt;

    public Task(String title, String description, Priority priority, LocalDate dueDate) {
        this.id = nextId++;
        this.title = title;
        this.description = description;
        this.priority = (priority == null) ? Priority.MEDIUM : priority;
        this.status = Status.TODO;
        this.dueDate = dueDate;
        this.createdAt = LocalDate.now();
    }

    // Gettery / Settery
    public int getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public LocalDate getCreatedAt() { return createdAt; }

    public void markDone() { this.status = Status.DONE; }

    @Override
    public String toString() {
        String due = (dueDate == null) ? "-" : dueDate.toString();
        return String.format("[%d] %s\n  opis: %s\n  status: %s  priorytet: %s  termin: %s  utworzono: %s",
                id, title, (description == null ? "-" : description),
                status, priority, due, createdAt);
    }

    // Metody pomocnicze do serializacji id (przy odczycie z pliku trzeba ustawić nextId)
    public static void setNextId(int next) { nextId = next; }
    public static int getNextId() { return nextId; }
}
