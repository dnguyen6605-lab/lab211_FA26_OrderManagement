
package Repository;

import java.io.*;
import model.Customer;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {
    private final ArrayList<Customer> customerList= new ArrayList<>();
    private final String filePath = "customers.dat";

    public CustomerRepository() {
    }
    //add vào list
    public void insertCustomer(Customer cus){
        if(cus != null){
            customerList.add(cus);
        }
    }
    //update customer
    public void update(Customer updated) {
        for (int i = 0; i < customerList.size(); i++) {
            if (customerList.get(i).getId()
                            .equalsIgnoreCase(updated.getId())) {
                customerList.set(i, updated);
                return;
            }
        }
    }
    //check tồn tại
    public boolean isExit(String id){
        for (Customer customer : customerList) {
            if(customer.getId().equals(id)) return true;
        }
        return false;
    }
    //tìm theo ID
    public Customer findById(String id){
        for (Customer customer : customerList) {
            if(customer.getId().equals(id)) return customer;
        }
        return null;
    }
    // tìm theo tên (partial match, không phân biệt hoa/thường)
    public ArrayList<Customer> getCustomersByNameLike(String name) {
        ArrayList<Customer> result = new ArrayList<>();
        if (name == null || name.trim().isEmpty()) {
            return result;
        }
        String keyword = name.trim().toLowerCase();
        for (Customer customer : customerList) {
            if (customer.getName() != null && customer.getName().toLowerCase().contains(keyword)) {
                result.add(customer);
            }
        }
        return result;
    }

    // tìm theo tên chính xác (giữ để tương thích nếu cần dùng sau này)
    public Customer getCustomerByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        String keyword = name.trim();
        for (Customer customer : customerList) {
            if (customer.getName() != null && customer.getName().equalsIgnoreCase(keyword)) {
                return customer;
            }
        }
        return null;
    }
    //getallList
    public ArrayList<Customer> getCustomerList(){
        return customerList;
    }
    //đọc file.dat
    @SuppressWarnings("unchecked")
    public void loadFromFile() {
        File file = new File(filePath);
        if (!file.exists()) {
            customerList.clear();
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            customerList.clear(); // Xóa dữ liệu cũ trên RAM
            if (obj instanceof List) {
                customerList.addAll((List) obj); // Thêm dữ liệu đọc từ file vào customerList
            }
        } catch (FileNotFoundException | EOFException e) {
            customerList.clear();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Warning: Could not read customer data from file: " + e.getMessage());
            customerList.clear();
        }
    }

    // lưu file nè
    public void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(customerList); // Ghi đúng danh sách customerList
            System.out.println("Customer data has been successfully saved to \"customers.dat\".");
        } catch (IOException e) {
            System.out.println("Error saving customer data: " + e.getMessage());
        }
    }
}
