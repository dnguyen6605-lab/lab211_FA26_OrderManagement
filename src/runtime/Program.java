package runtime;

import Controller.CustomerController;
import Controller.FeastMenuController;
import Controller.OrderController;
import Repository.CustomerRepository;
import Repository.FeastMenuRepository;
import Repository.OrderRepository;
import Service.FeastMenuService;
import Service.CustomerService;
import Service.OrderService;
import util.Inputter;
import util.Menu;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        //sử dụng DI new ở main-> inject vào contructor 
        Scanner sc = new Scanner(System.in);
        Inputter inputter = new Inputter(sc);

       
        CustomerRepository customerRepository = new CustomerRepository();
        FeastMenuRepository feastMenuRepository = new FeastMenuRepository();
        OrderRepository orderRepository = new OrderRepository();

        // Load data on startup
        customerRepository.loadFromFile();
        feastMenuRepository.loadFromCSV();
        orderRepository.loadFromFile();

        // 3. Services (Constructor Injection)
        CustomerService customerService = new CustomerService(customerRepository);
        FeastMenuService feastMenuService = new FeastMenuService(feastMenuRepository);
        OrderService orderService = new OrderService(orderRepository, customerService, feastMenuService);

        // 4. Controllers (Constructor Injection)
        CustomerController customerController = new CustomerController(customerService, inputter);
        FeastMenuController feasteMenuController = new FeastMenuController(feastMenuService, inputter);
        OrderController orderController = new OrderController(orderService, inputter);

        // 5. Main Menu Loop
        Menu mainMenu = new Menu("=== TRADITIONAL FEAST ORDER MANAGEMENT ===");
        mainMenu.addOption("Register customers");           // 1
        mainMenu.addOption("Update customer information");  // 2
        mainMenu.addOption("Search customer by name");      // 3
        mainMenu.addOption("Display feast menus");          // 4
        mainMenu.addOption("Place a feast order");          // 5
        mainMenu.addOption("Update order information");     // 6
        mainMenu.addOption("Save data to file");            // 7
        mainMenu.addOption("Display Customer list");        // 8
        mainMenu.addOption("Display Order list");           // 9
        mainMenu.addOption("Quit");                         // 0

        boolean running = true;
        while (running) {
            int choice = mainMenu.getChoice(inputter);
            switch (choice) {
                case 1: customerController.addCustomer(); break;
                case 2: customerController.updateCustomer(); break;
                case 3: customerController.searchCustomerByName(); break;
                case 4: feasteMenuController.showMenus(); break;
                case 5: orderController.placeOrder(); break;
                case 6: orderController.updateOrder(); break;
                case 7: customerController.saveCustomerData(); orderController.saveData(); break;
                case 8: customerController.showCustomerList(); break;
                case 9: orderController.showOrders(); break;
                case 0: running = false; break;
            }
        }
        sc.close();
        System.out.println("Goodbye! see you again");
    }
}
