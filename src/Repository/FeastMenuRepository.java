
package Repository;

import model.FeastMenu;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FeastMenuRepository {
    private final ArrayList<FeastMenu> feastMenuList = new ArrayList<>();
    private final String filePath = "FeastMenu.csv";

    public FeastMenuRepository() {
        loadFromCSV();
    }

    public ArrayList<FeastMenu> getMenu() {
        return feastMenuList;
    }

    public boolean loadFromCSV() {
        File file = new File(filePath);
        feastMenuList.clear();
        if (!file.exists()) {
            return false;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",", 4);
                if (parts.length < 4) {
                    continue;
                }

                String menuId = parts[0].trim();
                String menuName = parts[1].trim();
                double price = Double.parseDouble(parts[2].trim());
                String ingredients = parts[3].trim().replace("\"", "");

                feastMenuList.add(new FeastMenu(menuId, menuName, price, ingredients));
            }
            return true;
        } catch (IOException | NumberFormatException e) {
            return false;
        }
    }
}
