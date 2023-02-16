package Tasks;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

public class WeeklyTask extends Task {
    public WeeklyTask(String title, Type type) {
        super(title, type);
    }

    @Override                           //2023-02-17
    public boolean appearsln(LocalDate localDate) {
        LocalDate taskDate = this.getDateTime().toLocalDate();
        return taskDate.equals(localDate) ||
                (localDate.isAfter(taskDate) &&
                        localDate.getDayOfWeek() == taskDate.getDayOfWeek());
    }
}


