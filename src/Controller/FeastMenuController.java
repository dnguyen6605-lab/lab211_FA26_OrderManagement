
package Controller;

import model.FeastMenu;
import Service.FeastMenuService;
import java.util.ArrayList;
import util.Inputter;
public class FeastMenuController {
    private final FeastMenuService feastMenuService;
    private final Inputter inputter;

    public FeastMenuController(FeastMenuService feastMenuService, Inputter inputter) {
        this.feastMenuService = feastMenuService;
        this.inputter = inputter;
    }
    //show danh sách
    public void showMenus(){
        ArrayList<FeastMenu> menuList = feastMenuService.getMenuSortedByPrice();
        if (menuList.isEmpty()) {
            System.out.println("Cannot read data from feastMenu.csv. Please check it.");
            return;
        }

        System.out.println("--------------------------------------------------");
        System.out.println(" List of Set Menus for ordering party:");
        System.out.println("--------------------------------------------------");
        for (FeastMenu feastMenu : menuList) {
            System.out.println("Code        :" + feastMenu.getMenuId());
            System.out.println("Name        :" + feastMenu.getMenuName());
            System.out.printf("Price       : %,.0f Vnd%n", feastMenu.getPrice());
            System.out.println("Ingredients:");
            String ingredients = feastMenu.getIngredients();
            if (ingredients != null) {
                String[] items = ingredients.split("#");
                for (String item : items) {
                    if (!item.trim().isEmpty()) {
                        System.out.println(item.trim());
                    }
                }
            }
            System.out.println("--------------------------------------------------");
        }
    }  
    
}
