
package Service;

import model.FeastMenu;
import Repository.FeastMenuRepository;
import java.util.ArrayList;
import java.util.Comparator;

public class FeastMenuService {
    private final FeastMenuRepository feastMenuRepository;

    public FeastMenuService(FeastMenuRepository feastMenuRepository) {
        this.feastMenuRepository = feastMenuRepository;
    }

    public FeastMenu findById(String menuId) {
        if (menuId == null) {
            return null;
        }
        for (FeastMenu menu : feastMenuRepository.getMenu()) {
            if (menu.getMenuId().equalsIgnoreCase(menuId.trim())) {
                return menu;
            }
        }
        return null;
    }

    public boolean isExist(String menuId) {
        return findById(menuId) != null;
    }

    public ArrayList<FeastMenu> getMenuSortedByPrice() {
        ArrayList<FeastMenu> menuSorted = feastMenuRepository.getMenu();
        menuSorted.sort(Comparator.comparingDouble(FeastMenu::getPrice));
        return menuSorted;
    }
}
