package Tasks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class TaskService {
    private static final Map<Integer, Task> taskList = new HashMap<>();
    private static final Map<Integer, Task> removedTasks = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.printf("1- Внести задачу%n2- Вывести все задачи" +
                    "%n3- удалить задачу%n4- проверить список задач на дату%n5- выход%n ");
            int menu = scanner.nextInt();
            switch (menu) {
                case 1:
                    add();
                    break;
                case 2:
                    addList();
                    break;
                case 3:
                    remove();
                    break;
                case 4:
                    getAllByDate();
                    break;
                case 5:
                    taskEditing();
                    break;
                case 6:
                    getTaskGroupedByDate();
                    break;
                case 7:
                    return;
            }
        }
    }

    //Добавить задачу
    static void add() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите название задачи:");
            String taskName = getInput(scanner);
            System.out.println("Введите встречу WORK/PERSONAL");
            Type meeting = Type.valueOf(scanner.nextLine().toUpperCase());
            System.out.println("Введите повторяемость задачи:");
            System.out.println("1: однократная");
            System.out.println("2: ежедневная");
            System.out.println("3: еженедельная");
            System.out.println("4: ежемесячная");
            System.out.println("5: ежегодная");
            int repeatability = scanner.nextInt();
            switch (repeatability) {
                case 1:
                    OneTimeTask oneTimeTask = new OneTimeTask(taskName, meeting);
                    taskList.put(oneTimeTask.getId(), oneTimeTask);
                    break;
                case 2:
                    DailyTask dailyTask = new DailyTask(taskName, meeting);
                    taskList.put(dailyTask.getId(), dailyTask);
                    break;
                case 3:
                    WeeklyTask weeklyTask = new WeeklyTask(taskName, meeting);
                    taskList.put(weeklyTask.getId(), weeklyTask);
                    break;
                case 4:
                    MonthlyTask monthlyTask = new MonthlyTask(taskName, meeting);
                    taskList.put(monthlyTask.getId(), monthlyTask);
                    break;
                case 5:
                    YearlyTask yearlyTask = new YearlyTask(taskName, meeting);
                    taskList.put(yearlyTask.getId(), yearlyTask);
                    break;
            }
        } catch (IncorrectArgumentException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String getInput(Scanner scanner) throws IncorrectArgumentException {
        String input = scanner.nextLine();
        if (input.isEmpty()) {
            throw new IncorrectArgumentException("Название задачи пустое");
        }
        return input;
    }

    // Вывести все + удаленные задачи
    static void addList() {
        System.out.println("Список всех активных задач");
        taskList.values()
                .forEach(System.out::println);

        System.out.println("Список всех удаленных задач:");
        removedTasks.values()
                .forEach(System.out::println);
    }

    // Переместить задачу в коризну(removedTasks) а после удалить её из(taskList)
    static void remove() {
        try {
            System.out.println("Введите id задачи, которую следует удалить:");
            Scanner removeIdScanner = new Scanner(System.in);
            int removeId = removeIdScanner.nextInt();

            if (!taskList.containsKey(removeId)) {
                throw new TaskNotFoundException("Задача с таким id не найдена");
            }

            Task removedTask = taskList.remove(removeId);
            removedTasks.put(removeId, removedTask);

            System.out.println("Задача с id " + removeId + " успешно удалена");
        } catch (TaskNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // Вывести все задачи на число
    static void getAllByDate() {
        try {
            System.out.println("Введите дату d.MM.yyyy");
            DateTimeFormatter format = DateTimeFormatter.ofPattern("d.MM.yyyy");
            Scanner localDate = new Scanner(System.in);
            String dateString = localDate.nextLine();
            LocalDate.parse(dateString, format);
            taskList.values()
                    .stream()
                    .filter(item -> item.appearsln(LocalDate.parse(dateString, format)))
                    .forEach(p -> System.out.println(p.getTitle()));
        } catch (DateTimeException e) {
            System.out.println("Введена дата не правильно");
        }
    }

    // Редактирование поля задачи
    static void taskEditing() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите id задачи для редактирования");
        int taskId = scanner.nextInt();
        scanner.nextLine(); // очистка

        System.out.println("Введите новое значение");
        String taskNewTitle = scanner.nextLine();
        if (taskId >= 0 && taskId < taskList.size()) { // Проверить, что taskId в допустимом диапазоне
            taskList.get(taskId).setTitle(taskNewTitle);
            System.out.println("Задача успешно изменена");
        } else {
            System.out.println("Недопустимый идентификатор задачи");
        }
    }

    static void getTaskGroupedByDate() {
        System.out.println("Введите c какой даты выводить задачи в формате -  d.MM.yyyy");
        DateTimeFormatter format = DateTimeFormatter.ofPattern("d.MM.yyyy");
        Scanner scanner = new Scanner(System.in);
        String dateString = scanner.nextLine();


        System.out.println("Введите по какую дату выводить задачи в формате - d.MM.yyyy");
        String dateString2 = scanner.nextLine();

        LocalDate dateFrom = LocalDate.parse(dateString, format);
        LocalDate dateTo = LocalDate.parse(dateString2, format);

        for (Task entry : taskList.values()) {

            if (entry.appearsln(dateFrom) != entry.appearsln(dateTo.plusDays(1))) {
                System.out.println(entry.getTitle());
            }
        }
    }
}




