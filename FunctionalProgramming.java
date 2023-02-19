package Tasks;

import java.util.*;
import java.util.stream.Collectors;

public class FunctionalProgramming {
    public static void main(String[] args) {
        test();
    }

    public static void test() {
        System.out.println("Введите текст: ");
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        Map<String, Long> wordCountMap = Arrays.stream(str.split(" "))
                .collect(Collectors.groupingBy(String::toLowerCase, Collectors.counting()));

        long wordCount = wordCountMap
                .values()
                .stream()
                .reduce(0L, Long::sum);
        System.out.println("В тексте " + wordCount + " слов");


        System.out.println("TOP10:");
        wordCountMap.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed().thenComparing(Map.Entry.comparingByKey()))
                .limit(10)
                .forEach(entry -> System.out.println(entry.getValue() + " — " + entry.getKey()));
    }
}


