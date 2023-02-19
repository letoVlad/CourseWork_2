package Tasks;

import java.time.LocalDate;

public class OneTimeTask extends Task {
    public OneTimeTask(String title, Type type) {
        super(title, type);
    }

    @Override
    public boolean appearsln(LocalDate localDate) {
        LocalDate taskDate = this.getDateTime().toLocalDate();
        return taskDate.equals(localDate);
    }
}


