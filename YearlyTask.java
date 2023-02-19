package Tasks;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

public class YearlyTask extends Task {
    public YearlyTask(String title, Type type) {
        super(title, type);
    }

    @Override
    public boolean appearsln(LocalDate localDate) {
        LocalDate taskDate = this.getDateTime().toLocalDate();
        return taskDate.equals(localDate) ||
                (localDate.isAfter(taskDate) &&
                        localDate.getDayOfMonth() == taskDate.getDayOfMonth() &&
                        localDate.getMonthValue() == taskDate.getMonthValue());
    }
}
