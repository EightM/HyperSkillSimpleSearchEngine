package search;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    private static ArrayList<String> people = new ArrayList<>();
    private static Map<String, List<Integer>> index = new HashMap<>();

    public static void main(String[] args) throws IOException {
        if (args.length == 2) {
            String fileName = args[1];
            Path path = Paths.get(fileName);
            people = (ArrayList<String>) Files.readAllLines(path);
            createIndex();
        }

        Scanner scanner = new Scanner(System.in);
        boolean isExit = false;
        while (!isExit) {
            printMenu();
            int menuAction = Integer.parseInt(scanner.nextLine());
            isExit = handleMenuAction(menuAction);
        }
        System.out.println("Bye!");
    }

    private static void createIndex() {
        for (int i = 0; i < people.size(); i++) {
            for (String word : people.get(i).split(" ")) {
                if (index.get(word) == null) {
                    var numberOfLines = new ArrayList<Integer>();
                    numberOfLines.add(i);
                    index.put(word, numberOfLines);
                } else {
                    index.get(word).add(i);
                }
            }
        }
    }

    private static boolean handleMenuAction(int menuAction) {
        boolean isExit = false;
        switch (menuAction) {
            case 1:
                findPeople();
                break;
            case 2:
                printAllPeople();
                break;
            case 0:
                isExit = true;
                break;
            default:
                System.out.println("Incorrect option! Try again.");
        }

        return isExit;
    }

    private static void printAllPeople() {
        System.out.println("=== List of people ===");
        people.forEach(System.out::println);
    }

    private static void printMenu() {
        System.out.println("=== Menu ===");
        System.out.println("1. Find a person");
        System.out.println("2. Print all people");
        System.out.println("0. Exit");
    }

    private static void findPeople() {
        Scanner scanner = new Scanner(System.in);

        List<Integer> findIndexes = new ArrayList<>();
        System.out.println("Enter a name or email to search all suitable people:");
        String searchData = scanner.nextLine().toLowerCase();
        for (var entry : index.entrySet()) {
            if (entry.getKey().toLowerCase().equals(searchData)) {
                findIndexes.addAll(entry.getValue());
            }
        }
        System.out.printf("%d persons found%n", findIndexes.size());
        findIndexes.stream()
                .map(findIndex -> people.get(findIndex))
                .forEach(System.out::println);

    }
}
