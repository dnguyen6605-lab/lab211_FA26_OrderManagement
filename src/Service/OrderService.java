bpackage Service;

import Repository.OrderRepository;
import model.Customer;
import model.FeastMenu;
import model.Order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final FeastMenuService feastMenuService;

    public OrderService
    public boolean addOrder(Order order) {
        if (order == null) {
            return false;
        }

        if (customerService.findById(order.getCusId()) == null) {
            return false;
        }

        if (!feastMenuService.isExist(order.getMenuId())) {
            return false;
        }

        if (isDuplicate(order.getCusId(), order.getMenuId(), order.getEventDate())) {
            return false;
        }

        int newId = orderRepository.generateId();
        order.setOrderId(newId);
        order.setTotalCost(calculateTotalCost(order.getMenuId(), order.getNumOfTables()));
        orderRepository.add(order);
        return true;
    }

    public boolean updateOrder(int orderId, String menuId, Integer numOfTables, LocalDate eventDate) {
        Order order = findById(orderId);
        if (order == null) {
            return false;
        }

        if (order.getEventDate().isBefore(LocalDate.now())) {
            System.out.println("Cannot update a past order.");
            return false;
        }

        String targetMenuId = (menuId != null && !menuId.trim().isEmpty()) ? menuId.trim() : order.getMenuId();
        LocalDate targetDate = (eventDate != null) ? eventDate : order.getEventDate();

        for (Order existingOrder : orderRepository.getAll()) {
            if (existingOrder.getOrderId() != orderId &&
                existingOrder.getCusId() != null && order.getCusId() != null &&
                existingOrder.getCusId().equalsIgnoreCase(order.getCusId()) &&
                existingOrder.getMenuId() != null && targetMenuId != null &&
                existingOrder.getMenuId().equalsIgnoreCase(targetMenuId) &&
                existingOrder.getEventDate() != null &&
                existingOrder.getEventDate().equals(targetDate)) {
                System.out.println("Duplicate data!");
                return false;
            }
        }

        if (menuId != null && !menuId.trim().isEmpty()) {
            if (!feastMenuService.isExist(menuId.trim())) {
                System.out.println("Set menu code does not exist.");
                return false;
            }
            order.setMenuId(menuId.trim());
        }

        if (numOfTables != null && numOfTables > 0) {
            order.setNumOfTables(numOfTables);
        }

        if (eventDate != null) {
            order.setEventDate(eventDate);
        }

        order.setTotalCost(calculateTotalCost(order.getMenuId(), order.getNumOfTables()));
        orderRepository.update(order);
        return true;
    }

    public Order findById(int id) {
        return orderRepository.findById(id);
    }

    public boolean isExist(int id) {
        return orderRepository.isExist(id);
    }

    public boolean isDuplicate(String cusId, String menuId, LocalDate eventDate) {
        return orderRepository.isDuplicate(cusId, menuId, eventDate);
    }

    public List<Order> getAllSorted() {
        return getAllSortedByDate();
    }

    public List<Order> getAllSortedByDate() {
        List<Order> sortedList = new ArrayList<>(orderRepository.getAll());
        sortedList.sort(Comparator.comparing(Order::getEventDate));
        return sortedList;
    }

    public double calculateTotalCost(String menuId, int numOfTables) {
        FeastMenu menu = feastMenuService.findById(menuId);
        if (menu != null) {
            return menu.getPrice() * numOfTables;
        }
        return 0.0;
    }

    public String getCustomerName(String cusId) {
        Customer customer = customerService.findById(cusId);
        return customer != null ? customer.getName() : "";
    }

    public Customer getCustomer(String cusId) {
        return customerService.findById(cusId);
    }

    public String getMenuName(String menuId) {
        FeastMenu menu = feastMenuService.findById(menuId);
        return menu != null ? menu.getMenuName() : "";
    }

    public FeastMenu getMenu(String menuId) {
        return feastMenuService.findById(menuId);
    }

    public void saveData() {
        orderRepository.saveToFile();
    }
}
