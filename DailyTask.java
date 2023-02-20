package Tasks;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

public class DailyTask extends Task {

    public DailyTask(String title, String description, Type type) {
        super(title, description, type);
    }

    @Override
    public boolean appearsln(LocalDate localDate) {
        LocalDate taskDate = this.getDateTime().toLocalDate();
        return taskDate.equals(localDate);
    }
}