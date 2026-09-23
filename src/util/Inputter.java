package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Scanner;

public class Inputter {
    private  final Scanner sc;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
            .withResolverStyle(ResolverStyle.STRICT);

    public Inputter(Scanner sc) {
        this.sc = sc;
    }


    public  String getString(String string) {
        while (true) {
            System.out.print(string);
            String input = sc.nextLine();
            if (input != null && !input.trim().isEmpty()) {
                return input.trim();
            }
            System.out.println("Input cannot be empty. Please try again.");
        }
    }
    public  String getStringAllowEmpty(String string) {
        System.out.print(string);
        String input = sc.nextLine();
        return input == null ? "" : input.trim();
    }

    public  int getInt(String string) {
        while (true) {
            System.out.print(string);
            String input = sc.nextLine();
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format! Please enter an integer.");
            }
        }
    }

    public  int getPositiveInt(String string) {
        while (true) {
            int num = getInt(string);
            if (num > 0) {
                return num;
            }
            System.out.println("Value must be a positive integer (> 0). Please try again.");
        }
    }

    public  Integer getPositiveIntAllowEmpty(String string) {
        while (true) {
            System.out.print(string);
            String input = sc.nextLine();
            if (input == null || input.trim().isEmpty()) {
                return null;
            }
            try {
                int val = Integer.parseInt(input.trim());
                if (val > 0) {
                    return val;
                }
                System.out.println("Value must be a positive integer (> 0). Please try again.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format! Please enter an integer.");
            }
        }
    }

    public  String getCustomerId() {
        while (true) {
            String id = getString("Enter Customer ID (C/G/K followed by 4 digits, e.g. C0012): ").trim().toUpperCase();
            if (id.matches("^[CGK]\\d{4}$")) {
                return id;
            }
            System.out.println("Invalid Customer ID! Format must start with C, G, or K followed by 4 digits.");
        }
    }

    public  String getCustomerName() {
        while (true) {
            String name = getString("Enter Customer Name (2 - 25 characters): ");
            if (name.length() >= 2 && name.length() <= 25) {
                return name;
            }
            System.out.println("Customer name must be between 2 and 25 characters.");
        }
    }

    public  String getCustomerNameAllowEmpty() {
        while (true) {
            String name = getStringAllowEmpty("Enter new Customer Name (2 - 25 characters, leave empty to skip): ");
            if (name.isEmpty()) {
                return "";
            }
            if (name.length() >= 2 && name.length() <= 25) {
                return name;
            }
            System.out.println("Customer name must be between 2 and 25 characters.");
        }
    }

    public  String getPhone() {
        while (true) {
            String phone = getString("Enter Phone Number (10 digits, starts with 03, 05, 07, 08, 09): ");
            if (phone.matches("^(03|05|07|08|09)\\d{8}$")) {
                return phone;
            }
            System.out.println("Invalid phone number!.");
        }
    }

    public String getPhoneAllowEmpty() {
        while (true) {
            String phone = getStringAllowEmpty("Enter new Phone Number (leave empty to skip): ");
            if (phone.isEmpty()) {
                return "";
            }
            if (phone.matches("^(03|05|07|08|09)\\d{8}$")) {
                return phone;
            }
            System.out.println("Invalid phone number!");
        }
    }

    public  String getEmail() {
        while (true) {
            String email = getString("Enter Email ");
            if (email.matches("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$")) {
                return email;
            }
            System.out.println("Invalid email format!");
        }
    }

    public  String getEmailAllowEmpty() {
        while (true) {
            String email = getStringAllowEmpty("Enter new Email (leave empty to skip): ");
            if (email.isEmpty()) {
                return "";
            }
            if (email.matches("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$")) {
                return email;
            }
            System.out.println("Invalid email format! Please try again.");
        }
    }

    public  LocalDate getFutureDate() {
        while (true) {
            String dateStr = getString("Enter Event Date (dd/MM/yyyy): ");
            try {
                LocalDate date = LocalDate.parse(dateStr, dateFormatter);
                if (date.isAfter(LocalDate.now())) {
                    return date;
                }
                System.out.println("Event date must be in the future (after today)!");
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format or non-existent calendar date! Please use dd/MM/yyyy.");
            }
        }
    }

    public  LocalDate getFutureDateAllowEmpty() {
        while (true) {
            String dateStr = getStringAllowEmpty("Enter new Event Date (dd/MM/yyyy, leave empty to skip): ");
            if (dateStr.isEmpty()) {
                return null;
            }
            try {
                LocalDate date = LocalDate.parse(dateStr, dateFormatter);
                if (date.isAfter(LocalDate.now())) {
                    return date;
                }
                System.out.println("Event date must be in the future (after today)!");
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format or non-existent calendar date! Please use dd/MM/yyyy.");
            }
        }
    }

    public  boolean getYesNo(String string) {
        while (true) {
            System.out.print(string);
            String input = sc.nextLine();
            if (input != null) {
                String trimmed = input.trim().toLowerCase();
                if (trimmed.equals("y") || trimmed.equals("yes")) {
                    return true;
                }
                if (trimmed.equals("n") || trimmed.equals("no")) {
                    return false;
                }
            }
            System.out.println("Please enter Y or N.");
        }
    }
}
