package Tasks;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Task {
    private static int IdGenerator;
    private String title;
    private Type type;
    private int id = IdGenerator;
    private LocalDateTime dateTime;
    private String description;


    public Task(String title, String description, Type type) {
        this.title = title;
        this.type = type;
        this.description = description;
        this.dateTime = LocalDateTime.now();
        IdGenerator++;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public abstract boolean appearsln(LocalDate localDate);

    public Type getType() {
        return type;
    }

    public void setTitle(String title) {
        this.title = title;

    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", type=" + type +
                ", id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                '}';
    }

}