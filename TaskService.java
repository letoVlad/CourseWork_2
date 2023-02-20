package Tasks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
                    getTasksGroupedByDate();
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
            System.out.println("Введите описание задачи:");
            String descriptionTask = getInput(scanner);
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
                    OneTimeTask oneTimeTask = new OneTimeTask(taskName, descriptionTask, meeting);
                    taskList.put(oneTimeTask.getId(), oneTimeTask);
                    break;
                case 2:
                    DailyTask dailyTask = new DailyTask(taskName, descriptionTask, meeting);
                    taskList.put(dailyTask.getId(), dailyTask);
                    break;
                case 3:
                    WeeklyTask weeklyTask = new WeeklyTask(taskName, descriptionTask, meeting);
                    taskList.put(weeklyTask.getId(), weeklyTask);
                    break;
                case 4:
                    MonthlyTask monthlyTask = new MonthlyTask(taskName, descriptionTask, meeting);
                    taskList.put(monthlyTask.getId(), monthlyTask);
                    break;
                case 5:
                    YearlyTask yearlyTask = new YearlyTask(taskName, descriptionTask, meeting);
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
            var date = LocalDate.parse(dateString, format);
            taskList.values()
                    .stream()
                    .filter(item -> item.appearsln(date))
                    .forEach(p -> System.out.println(p.getTitle()));
        } catch (DateTimeException e) {
            System.out.println("Введена дата не правильно");
        }
    }

    // Редактирование поля/описание задачи
    static void taskEditing() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите id задачи для редактирования");
        int taskId = scanner.nextInt();
        scanner.nextLine(); // очистка

        System.out.println("Введите новое название задачи");
        String taskNewTitle = scanner.nextLine();
        System.out.println("Введите новое описание задачи");
        String taskNewDescription = scanner.nextLine();
        if (taskId >= 0 && taskId < taskList.size()) { // Проверить, что taskId в допустимом диапазоне
            taskList.get(taskId).setTitle(taskNewTitle);
            taskList.get(taskId).setDescription(taskNewDescription);
            System.out.println("Задача успешно изменена");
        } else {
            System.out.println("Недопустимый идентификатор задачи");
        }
    }

    static void getTasksGroupedByDate() {
        List<Task> groupedTasks = new ArrayList<>();
        System.out.println("Введите дату, с которой нужно вывести задачи в формате d.MM.yyyy");
        DateTimeFormatter format = DateTimeFormatter.ofPattern("d.MM.yyyy");
        Scanner scanner = new Scanner(System.in);
        String dateString = scanner.nextLine();
        LocalDateTime dateFrom = LocalDateTime.of(LocalDate.parse(dateString, format), LocalTime.MIN);
        System.out.println("Введите дату, по которую нужно вывести задачи в формате d.MM.yyyy");
        String dateString2 = scanner.nextLine();
        LocalDateTime dateTo = LocalDateTime.of(LocalDate.parse(dateString2, format), LocalTime.MAX);

        for (Task task : taskList.values()) {
            if (!task.getDateTime().isBefore(dateFrom) && !task.getDateTime().isAfter(dateTo)) {
                groupedTasks.add(task);
            }
        }
        if (groupedTasks.isEmpty()) {
            System.out.println("Нет задач на заданный период");
        } else {
            System.out.println("Задачи на заданный период:");
            groupedTasks.forEach(s -> System.out.println(s.getTitle()));
        }
    }
}





