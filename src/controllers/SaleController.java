package src.controllers;

import java.lang.reflect.Field;
import java.util.Scanner;
import java.util.List;

import src.views.MenuView;

import src.models.Sale;
import src.models.Product;
import src.models.Customer;

import src.dao.SaleDAO;


public class SaleController {
    Scanner scanner = new Scanner(System.in);
    MenuView menu = new MenuView();

    Sale sale = new Sale(-1, -1, -1, "", -1, -1, -1);
    SaleDAO saleDAO = new SaleDAO();

    public void addSale(){
            
        Class<?> saleClass = sale.getClass();
        Field[] fields = saleClass.getDeclaredFields();

        for(Field field: fields){
            field.setAccessible(true);
            boolean validInput = false;

            while (!validInput) {
                if (field.getName().equals("id")) {
                    validInput = true;
                    continue;
                }
                menu.clearScreen();
                System.out.println("Enter " + field.getName() + ": ");
                try {
                    if (field.getType() == String.class) {
                        field.set(sale, scanner.nextLine());
                        validInput = true;
                    }
                    else if (field.getType() == int.class){
                        if (field.getName().equals("productId")) {
                            ProductController productController = new ProductController();
                            List<Product> products = productController.getProducts();
                            
                            System.out.println("Available products: ");
                            for (Product product: products) {
                                System.out.println(product.getId() + " - " + product.getDescription());
                            }
                        }
                        else if (field.getName().equals("customerId")) {
                            CustomerController customerController = new CustomerController();
                            List<Customer> customers = customerController.getCustomers();
                            
                            System.out.println("Available customers: ");
                            for (Customer customer: customers) {
                                System.out.println(customer.getId() + " - " + customer.getName());
                            }
                        }
                        field.set(sale, Integer.parseInt(scanner.nextLine()));
                        validInput = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }                
            }
        }
    }
    
}