
package Service;

import Repository.CustomerRepository;
import model.Customer;
import java.util.ArrayList;
import java.util.Comparator;

public class CustomerService {
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    
    public void addCustomer(Customer cus){
        customerRepository.insertCustomer(cus);
    }
    public boolean isExit(String id){
        return customerRepository.isExit(id);
    }
    public boolean updateCustomer(String id, String name, String phone, String email){
        Customer cus = findById(id);
        if (cus == null){
            return false;
        }
          if (name != null && !name.trim().isEmpty()) {
            cus.setName(name.trim());
        }
        if (phone != null && !phone.trim().isEmpty()) {
            cus.setPhone(phone.trim());
        }
        if (email != null && !email.trim().isEmpty()) {
            cus.setEmail(email.trim());
        }
        
        customerRepository.update(cus);
        return true;
    }
    public Customer findById(String id){
        return customerRepository.findById(id);
    }
    public ArrayList<Customer> searchCustomerByName(String name){
        ArrayList<Customer> result = customerRepository.getCustomersByNameLike(name);
        result.sort(Comparator.comparing(Customer::getName, String.CASE_INSENSITIVE_ORDER));
        return result;
    }
    public ArrayList<Customer>getCustomerList(){
        ArrayList<Customer> cusListSorted = customerRepository.getCustomerList();
        cusListSorted.sort(Comparator.comparing(Customer::getName, String.CASE_INSENSITIVE_ORDER));
        return cusListSorted;
    }
    public void saveTofile(){
        customerRepository.saveToFile();
    }
}
