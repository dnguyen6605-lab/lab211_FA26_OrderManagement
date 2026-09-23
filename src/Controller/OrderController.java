package Controller;

import Service.OrderService;
import model.Customer;
import model.FeastMenu;
import model.Order;
import util.Inputter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrderController {
    private final OrderService orderService;
    private final Inputter inputter;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public OrderController(OrderService orderService, Inputter inputter) {
        this.orderService = orderService;
        this.inputter = inputter;
    }

    public void placeOrder() {
        while (true) {
            String cusId = inputter.getCustomerId();
            if (orderService.getCustomer(cusId) == null) {
                System.out.println("This customer does not exist.");
            } else {
                String menuId;
                while (true) {
                    menuId = inputter.getString("Enter Set Menu code: ").trim().toUpperCase();
                    if (orderService.getMenu(menuId) != null) {
                        break;
                    }
                    System.out.println("Set menu code does not exist. Please try again.");
                }

                int numOfTables = inputter.getPositiveInt("Enter number of tables: ");
                LocalDate eventDate = inputter.getFutureDate();

                if (orderService.isDuplicate(cusId, menuId, eventDate)) {
                    System.out.println("Duplicate data!");
                } else {
                    Order order = new Order(0, cusId, menuId, numOfTables, eventDate, 0);
                    boolean success = orderService.addOrder(order);
                    if (success) {
                        Customer customer = orderService.getCustomer(cusId);
                        FeastMenu menu = orderService.getMenu(menuId);

                        System.out.printf("Customer order information [Order ID: %d]%n", order.getOrderId());
                        System.out.println("Code           : " + cusId);
                        System.out.println("Customer name  : " + (customer != null ? customer.getName() : ""));
                        System.out.println("Phone number   : " + (customer != null ? customer.getPhone() : ""));
                        System.out.println("Email          : " + (customer != null ? customer.getEmail() : ""));
                        System.out.println("Code of Set Menu: " + menuId);
                        System.out.println("Set menu name  : " + (menu != null ? menu.getMenuName() : ""));
                        System.out.println("Event date     : " + eventDate.format(dateFormatter));
                        System.out.println("Number of tables: " + numOfTables);
                        System.out.printf("Price          : %,.0f Vnd%n", (menu != null ? menu.getPrice() : 0.0));
                        System.out.println("Ingredients:");
                        if (menu != null && menu.getIngredients() != null) {
                            String[] items = menu.getIngredients().split("#");
                            for (String item : items) {
                                if (!item.trim().isEmpty()) {
                                    System.out.println(item.trim());
                                }
                            }
                        }
                        System.out.printf("Total cost     : %,.0f Vnd%n", order.getTotalCost());
                    } else {
                        System.out.println("Failed to place order.");
                    }
                }
            }

            if (!inputter.getYesNo("Do you want to continue placing another order? (Y/N): ")) {
                break;
            }
        }
    }

    public void updateOrder() {
        while (true) {
            int orderId = inputter.getInt("Enter Order ID to update: ");
            Order order = orderService.findById(orderId);
            if (order == null) {
                System.out.println("This Order does not exist.");
            } else if (order.getEventDate().isBefore(LocalDate.now())) {
                System.out.println("Cannot update a past order.");
            } else {
                System.out.println("Current Information - Order ID: " + order.getOrderId() +
                        ", Customer ID: " + order.getCusId() +
                        ", Set Menu: " + order.getMenuId() +
                        ", Tables: " + order.getNumOfTables() +
                        ", Event Date: " + order.getEventDate().format(dateFormatter));

                String menuId = inputter.getStringAllowEmpty("Enter new Set Menu code (leave empty to skip): ").trim().toUpperCase();
                if (!menuId.isEmpty()) {
                    while (!menuId.isEmpty() && orderService.getMenu(menuId) == null) {
                        System.out.println("Set menu code does not exist.");
                        menuId = inputter.getStringAllowEmpty("Enter new Set Menu code (leave empty to skip): ").trim().toUpperCase();
                    }
                }

                Integer numOfTables = inputter.getPositiveIntAllowEmpty("Enter new number of tables (leave empty to skip): ");
                LocalDate eventDate = inputter.getFutureDateAllowEmpty();

                boolean updated = orderService.updateOrder(orderId, menuId, numOfTables, eventDate);
                if (updated) {
                    System.out.println("Order updated successfully!");
                }
            }

            if (!inputter.getYesNo("Do you want to continue updating another order? (Y/N): ")) {
                break;
            }
        }
    }

    public void showOrders() {
        List<Order> list = orderService.getAllSortedByDate();
        if (list.isEmpty()) {
            System.out.println("No data in the system.");
        } else {
            printOrderTable(list);
        }
    }

    private void printOrderTable(List<Order> list) {
        System.out.println("Orders information:");
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.printf("%-4s| %-11s | %-11s | %-8s | %-14s | %-6s | %s%n",
                "ID", "Event Date", "Customer ID", "Set Menu", "Price", "Tables", "Total Cost");
        System.out.println("--------------------------------------------------------------------------------------------");
        for (Order order : list) {
            FeastMenu menu = orderService.getMenu(order.getMenuId());
            double price = (menu != null) ? menu.getPrice() : (order.getNumOfTables() > 0 ? order.getTotalCost() / order.getNumOfTables() : 0.0);
            System.out.printf("%-4d| %-10s  | %-11s | %-8s | %,10.0f Vnd | %6d | %,11.0f Vnd%n",
                    order.getOrderId(),
                    order.getEventDate().format(dateFormatter),
                    order.getCusId(),
                    order.getMenuId(),
                    price,
                    order.getNumOfTables(),
                    order.getTotalCost());
        }
        System.out.println("--------------------------------------------------------------------------------------------");
    }

    public void saveData() {
        orderService.saveData();
    }
}
