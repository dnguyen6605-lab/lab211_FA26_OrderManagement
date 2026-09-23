package Repository;

import model.Order;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {
    private List<Order> list = new ArrayList<>();
    private final String filePath = "feast_order_service.dat";

    public OrderRepository() {
    }

    public void add(Order order) {
        if (order != null) {
            list.add(order);
        }
    }

    public void update(Order order) {
        if (order == null) return;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getOrderId() == order.getOrderId()) {
                list.set(i, order);
                return;
            }
        }
    }

    public List<Order> getAll() {
        return list;
    }

    public Order findById(int id) {
        for (Order order : list) {
            if (order.getOrderId() == id) {
                return order;
            }
        }
        return null;
    }

    public boolean isExist(int id) {
        return findById(id) != null;
    }

    public boolean isDuplicate(String cusId, String menuId, LocalDate date) {
        if (cusId == null || menuId == null || date == null) {
            return false;
        }
        String normalizedCusId = cusId.trim();
        String normalizedMenuId = menuId.trim();
        if (normalizedCusId.isEmpty() || normalizedMenuId.isEmpty()) {
            return false;
        }
        for (Order order : list) {
            if (order.getCusId() != null && order.getMenuId() != null &&
                order.getCusId().equalsIgnoreCase(normalizedCusId) &&
                order.getMenuId().equalsIgnoreCase(normalizedMenuId) &&
                order.getEventDate() != null &&
                order.getEventDate().equals(date)) {
                return true;
            }
        }
        return false;
    }

    public int generateId() {
        if (list == null || list.isEmpty()) {
            return 1;
        }
        int max = 0;
        for (Order order : list) {
            if (order.getOrderId() > max) {
                max = order.getOrderId();
            }
        }
        return max + 1;
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile() {
        File file = new File(filePath);
        if (!file.exists()) {
            list = new ArrayList<>();
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                list = (List<Order>) obj;
            }
        } catch (EOFException e) {
            list = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Warning: Could not read order data from file: " + e.getMessage());
            list = new ArrayList<>();
        }
    }

    public void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(list);
            System.out.println("Order data has been successfully saved to \"feast_order_service.dat\".");
        } catch (IOException e) {
            System.out.println("Error saving order data: " + e.getMessage());
        }
    }
}
