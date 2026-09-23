package util;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private String title;
    private final List<String> options = new ArrayList<>();

    public Menu(String title) {
        this.title = title;
    }

    public void addOption(String option) {
        options.add(option);
    }

    public void show() {
        System.out.println();
        System.out.println(title);
        for (int i = 0; i < options.size() - 1; i++) {
            System.out.printf("%d. %s%n", (i + 1), options.get(i));
        }
        if (!options.isEmpty()) {
            System.out.printf("0. %s%n", options.get(options.size() - 1));
        }
    }

    public int getChoice(Inputter inputter) {
        show();
        int maxOption = options.size() - 1;
        while (true) {
            int choice = inputter.getInt("Please select an option [0 - " + maxOption + "]: ");
            if (choice >= 0 && choice <= maxOption) {
                return choice;
            }
            System.out.println("Invalid choice! Please select an option between 0 and " + maxOption + ".");
        }
    }
}
