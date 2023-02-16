package Tasks;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

public class DailyTask extends Task {

    public DailyTask(String title, Type type) {
        super(title, type);
    }

    @Override
    public boolean appearsln(LocalDate localDate) {
        LocalDate taskDate = this.getDateTime().toLocalDate();
        return taskDate.equals(localDate);
    }
}
