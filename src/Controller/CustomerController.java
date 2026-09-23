
package Controller;

import Service.CustomerService;
import java.util.ArrayList;
import model.Customer;
import util.Inputter;

public class CustomerController {
    private CustomerService customerService;
    private Inputter inputter;

    public CustomerController(CustomerService customerService, Inputter inputter) {
        this.customerService = customerService;
        this.inputter = inputter;
    }
    //thêm khách hàng
    public void addCustomer(){
        while (true) {
            String id = inputter.getCustomerId();
            if (customerService.isExit(id)) {
                System.out.println("This customer ID already exists.");
            } else {
                String name = inputter.getCustomerName();
                String phone = inputter.getPhone();
                String email = inputter.getEmail();

                customerService.addCustomer(new Customer(id, name, phone, email));
                System.out.println("Customer registered successfully!");
            }

            if (!inputter.getYesNo("Do you want to continue registering another customer? (Y/N): ")) {
                break;
            }
        }
    }
    public void updateCustomer(){
        while(true){
            String id = inputter.getCustomerId();
            Customer cus  = customerService.findById(id);
            if(cus == null){
                System.out.println("customer not exist");
            }else{
                System.out.println("current information: " +
                        cus.getName() + " | " + cus.getPhone() + " | " + cus.getEmail());
                String name = inputter.getCustomerNameAllowEmpty();
                String phone = inputter.getPhoneAllowEmpty();
                String email = inputter.getEmailAllowEmpty();

                customerService.updateCustomer(cus.getId(), name, phone, email);
                System.out.println("update Customer successfull");
            }

            if (!inputter.getYesNo("Do you want to continue updating another customer? (Y/N): ")) {
                break;
            }
        }
    }
    public ArrayList<Customer> searchCustomerByName(){
        String name = inputter.getString("please enter customer name: ");
        ArrayList<Customer> customerList = customerService.searchCustomerByName(name);
        if(customerList == null || customerList.isEmpty()){
            System.out.println("customer does not exist");
            return new ArrayList<>();
        }
        showCustomerInfor(customerList);
        return customerList;
    }
    public void showCustomerList(){
        ArrayList<Customer> customerList = customerService.getCustomerList();
        if(customerList.isEmpty()){
            System.out.println("List is null");
        }
        else{
            showCustomerInfor(customerList);
        }
    }
    public void showCustomerInfor(ArrayList<Customer> customerList){
        System.out.println("Customers information:");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("%-6s| %-25s| %-11s| %s%n", "Code", "Customer Name", "Phone", "Email");
        System.out.println("--------------------------------------------------------------------------------");
        for (Customer customer : customerList) {
            System.out.printf("%-6s| %-25s| %-11s| %s%n", 
                    customer.getId(), customer.getName(), customer.getPhone(), customer.getEmail());
        }
        System.out.println("--------------------------------------------------------------------------------");
    } 
    public void saveCustomerData(){
        customerService.saveTofile();
    }

}
