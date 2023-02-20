package Tasks;

import java.time.LocalDate;

public class OneTimeTask extends Task {
    public OneTimeTask(String title, String descriptionTask, Type type) {
        super(title, descriptionTask, type);
    }

    @Override
    public boolean appearsln(LocalDate localDate) {
        LocalDate taskDate = this.getDateTime().toLocalDate();
        return taskDate.equals(localDate);
    }
}


